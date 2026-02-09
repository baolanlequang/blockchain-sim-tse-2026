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
    "validator_fraction": (0.0, 0.51),         # fraction of validators
    "attacker_fraction": (0.0, 0.51),          # fraction of validators
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
]

for p in integer_params:
    df[p] = df[p].round().astype(int)

# -----------------------------
# 6. Derived dependent parameters
# -----------------------------

df["fraction_of_validators"] = (
    df["validator_fraction"] * df["validator_count"]
).round().astype(int)

df["number_of_attackers"] = (
    df["attacker_fraction"] * df["validator_count"]
).round().astype(int)

# -----------------------------
# 7. Semantic constraints
# -----------------------------

# Validators cannot exceed total
df["fraction_of_validators"] = np.minimum(
    df["fraction_of_validators"],
    df["validator_count"]
)

# Attackers cannot exceed validators
df["number_of_attackers"] = np.minimum(
    df["number_of_attackers"],
    df["fraction_of_validators"]
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
# 8. Final checks
# -----------------------------

assert (df["fraction_of_validators"] <= df["validator_count"]).all()
assert (df["number_of_attackers"] <= df["fraction_of_validators"]).all()
assert (df["inbound_connections"] <= df["validator_count"] - 1).all()
assert (df["outbound_connections"] <= df["validator_count"] - 1).all()

# -----------------------------
# 9. Cleanup + save
# -----------------------------

df = df.drop(columns=["validator_fraction", "attacker_fraction"])
df.insert(0, "config_id", range(1, len(df) + 1))

df.to_csv("optimized_deterministic_lhs_configurations.csv", index=False)

print("LHS configurations generated.")
