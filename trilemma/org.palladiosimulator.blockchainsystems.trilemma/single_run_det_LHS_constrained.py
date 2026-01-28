import numpy as np
import pandas as pd
from scipy.stats import qmc

# -----------------------------
# 1. Experimental setup
# -----------------------------

SEED = 42                 # For reproducibility
N_SAMPLES = 50            # Number of configurations to generate
DIM = 9                   # Number of parameters

rng = np.random.default_rng(SEED)

# -----------------------------
# 2. Define parameter ranges
# -----------------------------
# Continuous parameters are defined by (min, max)
# Integer parameters will be rounded after sampling

param_ranges = {
    "bandwidth": (10, 250),                # Mbps (continuous)
    "block_interval": (300, 1200),          # seconds (continuous)
    "hashing_power": (100, 500),            # TH/s (continuous)
    "max_block_size": (0.5, 2.5),            # MB (continuous)

    "inbound_conn": (1, 250),               # integer
    "outbound_conn": (1, 16),               # integer
    "validator_count": (5000, 30000),       # integer
    "crashed_nodes": (0, 15000),             # integer (will be constrained)
    "workload": (200, 600000),               # integer (tx/day)
}

param_names = list(param_ranges.keys())

# -----------------------------
# 3. Generate optimized LHS
# -----------------------------
# We use SciPy's LatinHypercube with random coordinate descent optimization.
# This improves space-filling while preserving LHS marginal stratification.

sampler = qmc.LatinHypercube(
    d=DIM,
    seed=SEED,
    optimization="random-cd"   # space-filling / discrepancy-based optimization
)

# Sample in unit hypercube [0,1]^d
lhs_unit = sampler.random(n=N_SAMPLES)

# -----------------------------
# 4. Scale to parameter ranges
# -----------------------------
lhs_scaled = np.zeros_like(lhs_unit)

for i, param in enumerate(param_names):
    low, high = param_ranges[param]
    lhs_scaled[:, i] = low + lhs_unit[:, i] * (high - low)

# Put into DataFrame for clarity
df = pd.DataFrame(lhs_scaled, columns=param_names)

# -----------------------------
# 5. Map continuous samples to integers
# -----------------------------
# Integer-valued parameters are rounded.
# This slightly weakens theoretical LHS guarantees, but is acceptable
# since the design is only used to generate simulation inputs.

integer_params = [
    "inbound_conn",
    "outbound_conn",
    "validator_count",
    "crashed_nodes",
    "workload",
]

for p in integer_params:
    df[p] = df[p].round().astype(int)

# -----------------------------
# 6. Enforce semantic constraints
# -----------------------------
# IMPORTANT: These are not statistical constraints, but *model semantics*.

# Crashed nodes cannot exceed total validating nodes
df["crashed_nodes"] = np.minimum(
    df["crashed_nodes"],
    df["validator_count"]
)

# Connectivity cannot exceed possible peers
df["inbound_conn"] = np.minimum(
    df["inbound_conn"],
    df["validator_count"] - 1
)
df["outbound_conn"] = np.minimum(
    df["outbound_conn"],
    df["validator_count"] - 1
)

# Ensure bounds are still respected after clipping
df["inbound_conn"] = df["inbound_conn"].clip(lower=1)
df["outbound_conn"] = df["outbound_conn"].clip(lower=1)

# -----------------------------
# 7. Final sanity checks
# -----------------------------

# Check for duplicates (can happen after rounding)
if df.duplicated().any():
    print("Warning: duplicate configurations detected after rounding.")

# Optional: assert critical constraints
assert (df["crashed_nodes"] <= df["validator_count"]).all()
assert (df["outbound_conn"] <= df["validator_count"] - 1).all()
assert (df["inbound_conn"] <= df["validator_count"] - 1).all()

# -----------------------------
# 8. Save configurations
# -----------------------------
df.to_csv("optimized_lhs_configurations.csv", index=False)

print("Optimized LHS configurations generated successfully.")
