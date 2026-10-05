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
    ap.add_argument('--allow-incomplete',action='store_true',help='do not fail the audit because some runs are incomplete')
    a=ap.parse_args(); files=sorted(a.results.glob('result_config_*.json'))
    if not files:
        print('[FAIL] no result_config_*.json files found'); return 2
    good=True; by_real=defaultdict(list); incomplete=0; undefined_spsm=0; reasons=defaultdict(int)
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
        # Transaction gossip must reach every validator. Knowledge entries are kept for
        # the whole run, so a working gossip layer gives ~ submissions * N_V entries.
        # (A single-slot network callback once disabled gossip: ratio = 1/N_V.)
        submitted=int(audit.get('totalTransactionSubmissionsAllPhases',0) or 0)
        known=int(audit.get('maxTransactionKnowledgeEntriesObserved',0) or 0)
        nv=int(creation.get('validatingNodeCount',0) or 0)
        if submitted>0 and nv>0 and known < 0.99*submitted*nv:
            print(f'[FAIL] {p.name}: transaction gossip coverage {known/(submitted*nv):.3f} < 0.99'); good=False
        if not inp.get('lambda_tx_effective'):
            print(f'[FAIL] {p.name}: lambda_tx_effective missing (simulator predates demand fix)'); good=False
        elif inp.get('transaction_arrival_rate') in (None, ''):
            print(f'[FAIL] {p.name}: manifest has no transaction_arrival_rate (old rho-based manifest)'); good=False
        elif abs(float(inp['lambda_tx_effective']) - float(inp['transaction_arrival_rate'])) > 1e-9 * max(1.0, float(inp['transaction_arrival_rate'])):
            print(f'[FAIL] {p.name}: simulated rate {inp["lambda_tx_effective"]} != manifest transaction_arrival_rate {inp["transaction_arrival_rate"]}'); good=False
        # Transaction window (manuscript): submission from kappa_tx_warm blocks before
        # measurement until K_tx = min(K_tx_max, kappa_measure * N_V) measurement blocks.
        if not audit.get('transactionWindowEnabled'):
            print(f'[FAIL] {p.name}: transaction window not enabled (old jar, or transaction_measurement_blocks_max = 0)'); good=False
        else:
            kmeas=int(audit.get('measuredBlocksPerValidator',0) or 0)
            expected_ktx=min(100, kmeas*nv) if nv>0 else None
            if expected_ktx is not None and int(audit.get('transactionMeasurementTargetBlocks',0)) != expected_ktx:
                print(f'[WARN] {p.name}: K_tx = {audit.get("transactionMeasurementTargetBlocks")} differs from min(100, kappa_measure*N_V) = {expected_ktx}')
            if int(audit.get('transactionWarmupBlocks',-1)) != 30:
                print(f'[WARN] {p.name}: kappa_tx_warm = {audit.get("transactionWarmupBlocks")} (manuscript: 30)')
            if complete and int(audit.get('transactionWindowEndTimeMs',0) or 0) <= 0:
                print(f'[FAIL] {p.name}: run complete but transaction window end not recorded'); good=False
        q_a=creation.get('realizedAdversarialHashingPowerShare')
        if q_a is not None and float(q_a) >= 0.5:
            print(f'[WARN] {p.name}: attackers hold {float(q_a):.2f} of hashing power (majority); selfish mining becomes a majority takeover')
        if audit.get('terminationReason') == 'INACTIVITY' and audit.get('transactionWindowEnabled'):
            print(f'[FAIL] {p.name}: INACTIVITY stop with the transaction window (jar predates the inactivity fix)'); good=False
        if audit.get('terminationReason','').startswith('WORKLOAD_LIMIT'):
            print(f'[WARN] {p.name}: stopped by execution guard {audit["terminationReason"]} in phase {audit.get("executionPhaseAtTermination")}')
        reasons[audit.get('terminationReason','?')]+=1
        if not complete:
            print(f'[INCOMPLETE] {p.name}: {audit.get("terminationReason")} in phase {audit.get("executionPhaseAtTermination")}')
        # A complete run must have produced a usable transaction window.
        if complete and audit.get('transactionWindowEnabled'):
            if float(audit.get('transactionsPerSecond',0) or 0) <= 0:
                print(f'[FAIL] {p.name}: complete run with zero TPS'); good=False
            if int(audit.get('transactionFollowUpObservationCount',0) or 0) == 0:
                print(f'[FAIL] {p.name}: complete run without measurement-window transactions'); good=False
        # Reorganizations: blocks counted for window control but no longer canonical at the end.
        target=int(audit.get('measurementTargetCanonicalBlocks',0) or 0)-int(audit.get('warmupTargetCanonicalBlocks',0) or 0)
        final=int(audit.get('measurementCanonicalBlocks',0) or 0)
        if complete and target>0 and final < 0.8*target:
            print(f'[WARN] {p.name}: only {final} of {target} measurement blocks are canonical at the end (reorganizations)')
        if complete and float(audit.get('staleBlockRatio',0) or 0) > 0.5:
            print(f'[WARN] {p.name}: stale-block ratio {float(audit["staleBlockRatio"]):.2f} > 0.5')
        # Single-attacker design: the attacker must hold exactly the manifest share.
        alpha=inp.get('adversarial_hashing_power_share')
        if alpha not in (None,''):
            n_att=int(creation.get('numberOfAttackers',0) or 0)
            if n_att != (1 if float(alpha)>0 else 0):
                print(f'[FAIL] {p.name}: {n_att} attackers for adversarial share {alpha} (expected a single attacker)'); good=False
            elif q_a is not None and abs(float(q_a)-float(alpha))>1e-9:
                print(f'[FAIL] {p.name}: realized adversarial share {float(q_a):.6f} != manifest {float(alpha):.6f} (jar predates the single-attacker change)'); good=False
        b_manifest=inp.get('transaction_batch_size')
        if b_manifest not in (None,'') and int(float(b_manifest)) != int(audit.get('transactionBatchSize',1) or 1):
            print(f'[FAIL] {p.name}: transaction batch size {audit.get("transactionBatchSize",1)} != manifest {b_manifest}'); good=False
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
    print('Termination reasons:', dict(reasons))
    if incomplete and not a.allow_incomplete:
        print(f'[FAIL] {incomplete} run(s) incomplete (use --allow-incomplete to audit them anyway)'); good=False
    print('RESULT AUDIT:', 'PASS' if good else 'FAIL')
    return 0 if good else 2
if __name__=='__main__': raise SystemExit(main())
