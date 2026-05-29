import numpy as np
import pandas as pd
from scipy.stats import qmc

# -----------------------------
# 1. Experimental setup
# -----------------------------

SEED = 42
N_SAMPLES = 500
ZERO_ATTACK_SHARE = 0.15          # 15% explicit non-adversarial control cases
EXACT_25_ATTACK_SHARE = 0.10      # 10% exact 25% reference/boundary cases
ATTACKER_FRACTION_MAX = 1.0 / 3.0 # allow attacker fractions above 25%, but still sub-majority

rng = np.random.default_rng(SEED)

# -----------------------------
# 2. Parameter ranges
# -----------------------------

param_ranges = {
    "Hnode": (0.1, 10.0),
    "Hlink": (0.1, 10.0),
    "block_creation_interval": (60, 00),
    "hashrate_concentration": (0.0, 1.0),
    "max_block_size": (0.25, 8.0),
    "inbound_connections": (1, 250),
    "outbound_connections": (1, 16),
    "attacker_fraction": (0.0, ATTACKER_FRACTION_MAX),
    "validator_count": (20, 3000),
}

param_names = list(param_ranges.keys())
DIM = len(param_names)
attacker_col = param_names.index("attacker_fraction")

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
    "block_creation_interval",
    "max_block_size",
]

for p in integer_params:
    df[p] = df[p].round().astype(int)

# -----------------------------
# 6. Attacker assignment
# -----------------------------
# Rationale:
# - LHS samples continuous attacker_fraction values in [0, ATTACKER_FRACTION_MAX].
# - Values above 0.25 are now intentionally included by the upper range.
# - Exact 0.25 is still rare naturally, so we reserve rows closest to 0.25
#   as calibrated 25% reference cases.
# - Zero-attacker controls use the lowest attacker-fraction strata, rather than
#   randomly overwriting rows and damaging upper-range coverage.

n_zero = int(round(N_SAMPLES * ZERO_ATTACK_SHARE))
n_exact_25 = int(round(N_SAMPLES * EXACT_25_ATTACK_SHARE))

if n_zero + n_exact_25 >= N_SAMPLES:
    raise ValueError("ZERO_ATTACK_SHARE + EXACT_25_ATTACK_SHARE must leave ordinary LHS rows.")

# Lowest attacker-fraction strata become zero-attacker control contexts.
order = np.argsort(lhs_unit[:, attacker_col])
zero_idx = order[:n_zero]
remaining_idx = np.setdiff1d(np.arange(N_SAMPLES), zero_idx, assume_unique=False)

# Rows whose continuous attacker fraction is closest to 0.25 become exact-25 references.
closest_to_25 = remaining_idx[np.argsort(np.abs(df.loc[remaining_idx, "attacker_fraction"].to_numpy() - 0.25))]
exact_25_idx = closest_to_25[:n_exact_25]

# Default attacker counts for all rows.
df["number_of_attackers"] = (
    df["attacker_fraction"] * df["validator_count"]
).round().astype(int)

# Optional safety cap at ATTACKER_FRACTION_MAX; this permits >25% but avoids accidental majority.
max_attackers = np.floor(ATTACKER_FRACTION_MAX * df["validator_count"]).astype(int)
df["number_of_attackers"] = np.minimum(df["number_of_attackers"], max_attackers)

# Enforce explicit zero-attacker cases.
df.loc[zero_idx, "attacker_fraction"] = 0.0
df.loc[zero_idx, "number_of_attackers"] = 0

# Enforce exact 25% cases by making validator_count divisible by 4.
vc = df.loc[exact_25_idx, "validator_count"].to_numpy()
vc4 = np.rint(vc / 4).astype(int) * 4
vc4 = np.clip(vc4, 20, 3000) # ensures values stay within validaing-node counts
vc4 = (vc4 // 4) * 4
vc4 = np.maximum(vc4, 20)

df.loc[exact_25_idx, "validator_count"] = vc4
df.loc[exact_25_idx, "attacker_fraction"] = 0.25
df.loc[exact_25_idx, "number_of_attackers"] = (vc4 // 4).astype(int)

# -----------------------------
# 7. Semantic constraints
# -----------------------------

df["number_of_attackers"] = np.minimum(
    df["number_of_attackers"],
    df["validator_count"]
)

df["inbound_connections"] = np.minimum(
    df["inbound_connections"],
    df["validator_count"] - 1
)

df["outbound_connections"] = np.minimum(
    df["outbound_connections"],
    df["validator_count"] - 1
)

df["inbound_connections"] = df["inbound_connections"].clip(lower=1)
df["outbound_connections"] = df["outbound_connections"].clip(lower=1)

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
assert (df["number_of_attackers"] == 0).sum() == n_zero
assert np.isclose((df["attacker_fraction_realized"] == 0.25).sum(), n_exact_25)
assert (df["attacker_fraction_realized"] > 0.25).any()
assert (df["attacker_fraction_realized"] < 0.5).all()

# -----------------------------
# 9. Cleanup + save
# -----------------------------

df.insert(0, "config_id", range(1, len(df) + 1))
outfile = "optimized_deterministic_lhs_configurations.csv"
df.to_csv(outfile, index=False)

print("LHS configurations generated.")
print(f"Total zero-attacker contexts: {(df['number_of_attackers'] == 0).sum()}")
print(f"Exact 25% attacker contexts: {(df['attacker_fraction_realized'] == 0.25).sum()}")
print(f"Realized attacker fraction > 0.25: {(df['attacker_fraction_realized'] > 0.25).sum()}")
print(f"Realized attacker fraction >= 0.30: {(df['attacker_fraction_realized'] >= 0.30).sum()}")
print(f"Max realized attacker fraction: {df['attacker_fraction_realized'].max():.6f}")

print(df[[
    "config_id",
    "attacker_fraction",
    "validator_count",
    "number_of_attackers",
    "attacker_fraction_realized"
]].sort_values("attacker_fraction_realized", ascending=False).head(10))
