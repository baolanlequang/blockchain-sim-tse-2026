"""
LHS configuration generator for ANOVA and design narrowing.

IMPORTANT:
- This script produces ONLY the 8 experimental parameters listed in Table 1.
- Bandwidth heterogeneity and hash-rate concentration are sampled as
  normalized, dimensionless control variables in [0.05, 1.0].
- Mapping to Dirichlet parameters (alpha) is done INSIDE the simulator,
  not here.
"""

import numpy as np
import pandas as pd
from scipy.stats import qmc

# -----------------------------
# 1. Experimental setup
# -----------------------------

SEED = 42
N_SAMPLES = 50

# -----------------------------
# 2. Parameter ranges (Table 1)
# -----------------------------

param_ranges = {
    "bandwidth_heterogeneity": (0.05, 1.0),   # dimensionless
    "block_creation_interval": (300, 1200),   # seconds
    "hashrate_concentration": (0.05, 1.0),    # normalized HHI*
    "max_block_size": (0.5, 2.5),             # MB
    "inbound_connections": (1, 250),          # integer
    "outbound_connections": (1, 16),          # integer
    "fraction_of_validators": (0, 15000),     # integer
    "validator_count": (5000, 30000),         # integer
}

param_names = list(param_ranges.keys())
DIM = len(param_names)

# -----------------------------
# 3. Generate optimized LHS
# -----------------------------

sampler = qmc.LatinHypercube(
    d=DIM,
    seed=SEED,
    optimization="random-cd"
)

lhs_unit = sampler.random(n=N_SAMPLES)

# -----------------------------
# 4. Scale to parameter ranges
# -----------------------------

lhs_scaled = np.zeros_like(lhs_unit)

for i, param in enumerate(param_names):
    low, high = param_ranges[param]
    lhs_scaled[:, i] = low + lhs_unit[:, i] * (high - low)

df = pd.DataFrame(lhs_scaled, columns=param_names)

# -----------------------------
# 5. Integer parameters
# -----------------------------

integer_params = [
    "inbound_connections",
    "outbound_connections",
    "validator_count",
    "fraction_of_validators",
]

for p in integer_params:
    df[p] = df[p].round().astype(int)

# -----------------------------
# 6. Semantic constraints
# -----------------------------

# Fraction cannot exceed total validators
df["fraction_of_validators"] = np.minimum(
    df["fraction_of_validators"],
    df["validator_count"]
)

# Connectivity constraints
df["inbound_connections"] = np.minimum(
    df["inbound_connections"],
    df["validator_count"] - 1
)

df["outbound_connections"] = np.minimum(
    df["outbound_connections"],
    df["validator_count"] - 1
)

# Lower bounds
df["inbound_connections"] = df["inbound_connections"].clip(lower=1)
df["outbound_connections"] = df["outbound_connections"].clip(lower=1)

# -----------------------------
# 7. Final checks
# -----------------------------

assert (df["fraction_of_validators"] <= df["validator_count"]).all()
assert (df["inbound_connections"] <= df["validator_count"] - 1).all()
assert (df["outbound_connections"] <= df["validator_count"] - 1).all()

# -----------------------------
# 8. Add config_id and save
# -----------------------------

df.insert(0, "config_id", range(1, len(df) + 1))
df.to_csv("optimized_deterministic_lhs_configurations.csv", index=False)

print("LHS configurations generated.")
