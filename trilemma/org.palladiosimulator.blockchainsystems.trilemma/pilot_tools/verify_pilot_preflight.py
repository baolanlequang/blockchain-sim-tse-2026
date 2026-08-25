#!/usr/bin/env python3
"""Preflight verifier for the revised TSE pilot.

This script is intentionally conservative: it validates the execution manifest,
refined pilot configuration, and materialized pilot models before a scientific
pilot is launched. It does not run 3SIM.

Exit code 0 means all checked invariants passed. Any failed check returns a
non-zero exit code so local scripts/CI/SLURM can stop before wasting compute.
"""
from __future__ import annotations
import argparse, csv, json, math, sys, xml.etree.ElementTree as ET
from collections import defaultdict
from pathlib import Path

REQ_SAMPLE = [
    'connection_count','block_creation_interval','maximum_block_size',
    'validating_node_count','node_bandwidth_heterogeneity',
    'link_bandwidth_heterogeneity','hashing_power_concentration',
    'fraction_of_attackers','number_of_attackers','realized_fraction_of_attackers',
    'transaction_arrival_rate'
]
REQ_HIER = ['manifest_pair_id','network_instance','event_replication',
            'network_realization_id','network_seed','event_seed','run_id']


def fail(msg:str):
    print(f'[FAIL] {msg}')
    return False

def ok(msg:str):
    print(f'[PASS] {msg}')
    return True

def parse_xml(p:Path):
    return ET.parse(p).getroot()

def attrs(root, tag):
    return [x.attrib for x in root.iter(tag)]

def repo_file(folder:Path):
    a=folder/'Net.bscmrepository'; b=folder/'Net.blockchainsystemcomponentrepository'
    return a if a.exists() else b if b.exists() else None

def main():
    ap=argparse.ArgumentParser()
    ap.add_argument('--project', type=Path, default=Path('.'))
    ap.add_argument('--manifest', type=Path, default=Path('pilot_replication_manifest.csv'))
    ap.add_argument('--config', type=Path, default=Path('testmodels/configuration_refined_pilot.json'))
    args=ap.parse_args()
    project=args.project.resolve(); manifest=(project/args.manifest).resolve(); cfgp=(project/args.config).resolve()
    good=True
    print(f'Project : {project}')
    print(f'Manifest: {manifest}')
    print(f'Config  : {cfgp}\n')

    if not manifest.exists(): return 2 if fail(f'manifest missing: {manifest}') is False else 2
    if not cfgp.exists(): return 2 if fail(f'config missing: {cfgp}') is False else 2
    cfg=json.loads(cfgp.read_text(encoding='utf-8'))
    good &= ok('hierarchical manifest required') if str(cfg.get('requireHierarchicalManifest','')).lower()=='true' else fail('requireHierarchicalManifest must be true')
    good &= ok('experimentPhase=pilot') if str(cfg.get('experimentPhase','')).lower()=='pilot' else fail('experimentPhase must be pilot')
    good &= ok('engine executes one row at a time') if cfg.get('engineSimulationType')=='Single' and str(cfg.get('engineNumberOfMonteCarloRounds'))=='1' else fail('engineSimulationType=Single and engineNumberOfMonteCarloRounds=1 required')
    good &= ok('transaction size fixed at 500 B') if str(cfg.get('transactionSizeBytes'))=='500' else fail('transactionSizeBytes must be 500')
    good &= ok('confirmation depth fixed at 6') if str(cfg.get('confirmationDepthBlocks'))=='6' else fail('confirmationDepthBlocks must be 6')
    good &= ok('gamma fixed at 0.5') if math.isclose(float(cfg.get('selfishMiningGamma','nan')),0.5) else fail('selfishMiningGamma must be 0.5')
    good &= ok('baseline endpoint bandwidth fixed at 1000 Mbps') if math.isclose(float(cfg.get('baselineBandwidthPerEndpointMbps','nan')),1000.0) else fail('baselineBandwidthPerEndpointMbps must be 1000')

    with manifest.open(newline='',encoding='utf-8-sig') as f: rows=list(csv.DictReader(f))
    if not rows: good &= fail('manifest has no execution rows')
    cols=set(rows[0]) if rows else set()
    missing=[c for c in REQ_SAMPLE+REQ_HIER if c not in cols]
    good &= ok('manifest contains sampled + R_S/R_E columns') if not missing else fail('missing manifest columns: '+', '.join(missing))

    pair_ids=sorted({r.get('manifest_pair_id','') for r in rows})
    declared=int(cfg.get('declaredSamplePairs', cfg.get('numberOfMonteCarloRounds','0')))
    good &= ok(f'declared sampled-pair count = {declared}') if declared==len(pair_ids) else fail(f'declaredSamplePairs={declared}, manifest distinct pairs={len(pair_ids)}')

    by_real=defaultdict(list)
    by_pair_inst={}
    runids=set()
    for r in rows:
        rid=r.get('network_realization_id',''); by_real[rid].append(r)
        run=r.get('run_id','')
        if run in runids: good &= fail(f'duplicate run_id: {run}')
        runids.add(run)
        key=(r.get('manifest_pair_id',''),r.get('network_instance',''))
        seed=r.get('network_seed','')
        if key in by_pair_inst and by_pair_inst[key]!=seed: good &= fail(f'network seed changes for {key}')
        by_pair_inst[key]=seed
    for rid, rr in by_real.items():
        nseeds={r['network_seed'] for r in rr}; eseeds=[r['event_seed'] for r in rr]
        sigs={tuple(r.get(c,'') for c in REQ_SAMPLE) for r in rr}
        if len(nseeds)!=1: good &= fail(f'{rid}: network_seed changes across R_E')
        if len(eseeds)!=len(set(eseeds)): good &= fail(f'{rid}: duplicate event_seed')
        if len(sigs)!=1: good &= fail(f'{rid}: sampled values change across R_E')
    if good: ok(f'R_S/R_E seed contract validated for {len(by_real)} network realizations and {len(rows)} executions')

    # Inspect P01-P24 when present. These checks verify fixed settings and the
    # per-row values materialized by the generator; runtime still reapplies rows.
    tm=project/'testmodels'
    checked=0
    for pid in pair_ids:
        folder=tm/f'threesim-{pid}'
        if not folder.exists(): continue
        try:
            b=parse_xml(folder/'Net.blockchainsystem'); t=parse_xml(folder/'Net.transactions'); l=parse_xml(folder/'Net.linkallocation')
            rp=repo_file(folder)
            if rp is None: raise RuntimeError('component repository missing')
            r=parse_xml(rp)
            spec=next(b.iter('Specification'))
            sizes={int(x.attrib['Size']) for x in t.iter('Values') if 'Size' in x.attrib}
            lat=[[(int(v.attrib['Latency']),float(v.attrib['Probability'])) for v in d if v.tag=='Values'] for d in l.iter('latencySpecification')]
            val=[[(int(v.attrib['Duration']),float(v.attrib['Probability'])) for v in d if v.tag=='Values'] for d in r.iter('ValidationDuration')]
            if sizes!={500}: good &= fail(f'{pid}: transaction sizes {sorted(sizes)} != [500]')
            expected_lat=[(95,.2),(100,.6),(110,.2)]
            if not lat or any(x!=expected_lat for x in lat): good &= fail(f'{pid}: latency distribution differs from prescribed 95/100/110 ms')
            expected_val=[(4600,.5),(5000,.3),(6000,.2)]
            if not val or any(x!=expected_val for x in val): good &= fail(f'{pid}: validation-duration distribution differs from prescribed 4.6/5.0/6.0 s')
            if int(spec.attrib.get('NumOfRequiredSecurityConfirmations','-1'))!=6: good &= fail(f'{pid}: confirmation depth != 6')
            checked+=1
        except Exception as e:
            good &= fail(f'{pid}: model check error: {e}')
    if checked: ok(f'fixed-model settings inspected in {checked} materialized pilot folders')

    base=tm/'refined-base'/'Net.blockchainsystem'
    good &= ok('refined-base exists') if base.exists() else fail('testmodels/refined-base/Net.blockchainsystem is missing')

    print('\nPRECHECK RESULT:', 'PASS' if good else 'FAIL')
    return 0 if good else 2

if __name__=='__main__':
    raise SystemExit(main())
