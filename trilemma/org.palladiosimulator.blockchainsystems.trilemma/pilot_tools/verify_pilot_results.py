#!/usr/bin/env python3
"""Audit completed refined-pilot JSON results.

Checks presence/completion of RefinedExecutionAudit and verifies that event
replications sharing one network_realization_id reuse the exact same structural
fingerprints. This script deliberately reports incomplete/undefined cases; it
does not replace or impute them.
"""
from __future__ import annotations
import argparse, json, sys
from collections import defaultdict
from pathlib import Path


def walk(o):
    if isinstance(o,dict):
        yield o
        for v in o.values(): yield from walk(v)
    elif isinstance(o,list):
        for v in o: yield from walk(v)

def find_dict(root, keys):
    for d in walk(root):
        if all(k in d for k in keys): return d
    return None

def main():
    ap=argparse.ArgumentParser(); ap.add_argument('--results',type=Path,default=Path('result_trilemma'))
    a=ap.parse_args(); files=sorted(a.results.glob('result_config_*.json'))
    if not files:
        print('[FAIL] no result_config_*.json files found'); return 2
    good=True; by_real=defaultdict(list); incomplete=0; undefined_spsm=0
    for p in files:
        try: root=json.loads(p.read_text(encoding='utf-8'))
        except Exception as e: print('[FAIL]',p.name,e); good=False; continue
        inp=root.get('inputParameters',{}) if isinstance(root,dict) else {}
        audit=find_dict(root,['measurementWindowCompleted','transactionFollowUpCompleted','measurementCanonicalBlocks'])
        creation=find_dict(root,['networkSeed','eventSeed','topologyFingerprint','networkRealizationFingerprint'])
        if audit is None or creation is None:
            print(f'[FAIL] {p.name}: refined audit missing'); good=False; continue
        complete=bool(audit['measurementWindowCompleted']) and bool(audit['transactionFollowUpCompleted'])
        if not complete: incomplete+=1
        if audit.get('selfishMiningSuccessProbability') is None: undefined_spsm+=1
        started=int(audit.get('selfishMiningAttackRoundsStarted',0)); succ=int(audit.get('successfulSelfishMiningAttackRounds',0)); fail=int(audit.get('failedSelfishMiningAttackRounds',0)); amb=int(audit.get('ambiguousSelfishMiningAttackRounds',0))
        if started != succ+fail+amb:
            print(f'[FAIL] {p.name}: SPSM count identity violated'); good=False
        rid=inp.get('network_realization_id')
        if rid: by_real[rid].append((p,inp,creation))
    for rid,rr in by_real.items():
        fps={x[2].get('networkRealizationFingerprint') for x in rr}
        top={x[2].get('topologyFingerprint') for x in rr}
        bw={x[2].get('bandwidthAllocationFingerprint') for x in rr}
        lat={x[2].get('latencyAllocationFingerprint') for x in rr}
        attackers={tuple(x[2].get('attackerNodeIds',[])) for x in rr}
        nseed={str(x[1].get('network_seed')) for x in rr}
        eseed=[str(x[1].get('event_seed')) for x in rr]
        if any(len(s)!=1 for s in [fps,top,bw,lat,attackers,nseed]):
            print(f'[FAIL] {rid}: structural realization changes across R_E'); good=False
        if len(eseed)!=len(set(eseed)):
            print(f'[FAIL] {rid}: event_seed is not unique across R_E'); good=False
    print(f'Checked {len(files)} result files; network realizations={len(by_real)}; incomplete={incomplete}; undefined SPSM={undefined_spsm}')
    print('RESULT AUDIT:', 'PASS' if good else 'FAIL')
    return 0 if good else 2
if __name__=='__main__': raise SystemExit(main())
