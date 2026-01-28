import numpy as np
import pandas as pd
from scipy.stats import qmc

# -----------------------------
# 1. Experimental setup
# -----------------------------

SEED = 42                 # For reproducibility
N_SAMPLES = 50            # Number of configurations to generate
DIM = 9                   # Number of parameters

# -----------------------------
# 2. Define parameter ranges
# -----------------------------
# Continuous parameters are defined by (min, max)
# Integer parameters will be rounded after sampling

param_ranges = {
    "bandwidth": (10, 250),                         # Mbps (continuous)
    "block_creation_interval": (300, 1200),         # seconds (continuous)
    "hashing_power": (100, 500),                     # TH/s (continuous)
    "max_block_size": (0.5, 2.5),                    # MB (continuous)

    "inbound_connectivity": (1, 250),                # integer
    "outbound_connectivity": (1, 16),                # integer
    "validator_count": (5000, 30000),                # integer
    "crashed_validators": (0, 15000),                # integer (constrained)
    "workload": (200, 600000),                       # integer (tx/day)
}

param_names = list(param_ranges.keys())

# -----------------------------
# 3. Generate optimized LHS
# -----------------------------
# Space-filling optimized LHS using random coordinate descent

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
# 5. Map continuous samples to integers
# -----------------------------

integer_params = [
    "inbound_connectivity",
    "outbound_connectivity",
    "validator_count",
    "crashed_validators",
    "workload",
]

for p in integer_params:
    df[p] = df[p].round().astype(int)

# -----------------------------
# 6. Enforce semantic constraints
# -----------------------------

# Crashed validators cannot exceed total validators
df["crashed_validators"] = np.minimum(
    df["crashed_validators"],
    df["validator_count"]
)

# Connectivity cannot exceed available peers
df["inbound_connectivity"] = np.minimum(
    df["inbound_connectivity"],
    df["validator_count"] - 1
)
df["outbound_connectivity"] = np.minimum(
    df["outbound_connectivity"],
    df["validator_count"] - 1
)

# Enforce lower bounds
df["inbound_connectivity"] = df["inbound_connectivity"].clip(lower=1)
df["outbound_connectivity"] = df["outbound_connectivity"].clip(lower=1)

# -----------------------------
# 7. Final sanity checks
# -----------------------------

if df.duplicated().any():
    print("Warning: duplicate configurations detected after rounding.")

assert (df["crashed_validators"] <= df["validator_count"]).all()
assert (df["outbound_connectivity"] <= df["validator_count"] - 1).all()
assert (df["inbound_connectivity"] <= df["validator_count"] - 1).all()

# -----------------------------
# 8. Add config_id (NEW)
# -----------------------------
# config_id is metadata, not a parameter.
# We use the row index to ensure uniqueness and reproducibility.

df.insert(0, "config_id", range(1, len(df) + 1))

# -----------------------------
# 9. Save configurations
# -----------------------------

df.to_csv("optimized_deterministic_lhs_configurations.csv", index=False)
print("Optimized LHS configurations generated successfully.")
