import numpy as np
import pandas as pd
from scipy.stats import qmc

# -----------------------------
# 1. Experimental setup
# -----------------------------

SEED = 42
N_SAMPLES = 500

# -----------------------------
# 2. Parameter ranges (Table 1)
# -----------------------------

param_ranges = {
    "Hnode": (0.1, 10.0),                   # dimensionless
    "Hlink": (0.1, 10.0),                   # Dirichlet alpha
    "block_creation_interval": (60, 1200),  # seconds, integer
    "hashrate_concentration": (0.0, 1.0),  # normalized HHI*
    "max_block_size": (0.25, 8.0),          # MB, integer
    "inbound_connections": (1, 250),        # integer
    "outbound_connections": (1, 16),        # integer
    "attacker_fraction": (0.0, 0.25),       # fraction of validators, float
    "validator_count": (20, 5000),          # integer
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
# 5. Integer parameters only
# -----------------------------

integer_params = [
    "inbound_connections",
    "outbound_connections",
    "validator_count",
    "block_creation_interval",
    "max_block_size",
]

for p in integer_params:
    df[p] = df[p].round().astype(int)

# -----------------------------
# 6. Derived dependent parameters
# -----------------------------

# Convert attacker fraction into an integer attacker count
df["number_of_attackers"] = (
    df["attacker_fraction"] * df["validator_count"]
).round().astype(int)

# Optional: realized fraction after rounding to integer attackers
df["attacker_fraction_realized"] = (
    df["number_of_attackers"] / df["validator_count"]
)

# -----------------------------
# 7. Semantic constraints
# -----------------------------

# Attackers cannot exceed total validators
df["number_of_attackers"] = np.minimum(
    df["number_of_attackers"],
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

# Recompute realized fraction after final attacker clipping
df["attacker_fraction_realized"] = (
    df["number_of_attackers"] / df["validator_count"]
)

# -----------------------------
# 8. Final checks
# -----------------------------

assert (df["number_of_attackers"] <= df["validator_count"]).all()
assert (df["number_of_attackers"] >= 0).all()
assert (df["inbound_connections"] <= df["validator_count"] - 1).all()
assert (df["outbound_connections"] <= df["validator_count"] - 1).all()
assert (df["inbound_connections"] >= 1).all()
assert (df["outbound_connections"] >= 1).all()

# -----------------------------
# 9. Cleanup + save
# -----------------------------

# Keep both sampled and realized fractions for traceability.
# If you want to remove the sampled fraction, uncomment the next line:
# df = df.drop(columns=["attacker_fraction"])

df.insert(0, "config_id", range(1, len(df) + 1))

df.to_csv("optimized_deterministic_lhs_configurations.csv", index=False)

print("LHS configurations generated.")
print(df[[
    "config_id",
    "attacker_fraction",
    "validator_count",
    "number_of_attackers",
    "attacker_fraction_realized"
]].head(10))