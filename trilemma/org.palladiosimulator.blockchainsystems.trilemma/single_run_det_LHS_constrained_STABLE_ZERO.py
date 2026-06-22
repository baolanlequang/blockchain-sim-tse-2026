import numpy as np
import pandas as pd
from scipy.stats import qmc

# -----------------------------
# 1. Experimental setup
# -----------------------------

SEED = 42
N_SAMPLES = 500
ZERO_ATTACK_SHARE = 0.15
EXACT_25_ATTACK_SHARE = 0.10
ATTACKER_FRACTION_MAX = 1.0 / 4.0  # maximum attacker fraction is exactly 25%

rng = np.random.default_rng(SEED)

# -----------------------------
# 2. Parameter ranges
# -----------------------------

param_ranges = {
    "Hnode": (0.1, 10.0),
    "Hlink": (0.1, 10.0),
    "block_creation_interval": (60, 600),
    "hashrate_concentration": (0.0, 1.0),
    "max_block_size": (0.25, 8.0),
    "inbound_connections": (1, 125),
    "outbound_connections": (1, 8),
    "attacker_fraction": (0.0, ATTACKER_FRACTION_MAX),
    "validator_count": (20, 1000),
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
]

for p in integer_params:
    df[p] = df[p].round().astype(int)

# -----------------------------
# 6. Attacker assignment
# -----------------------------
# Rationale:
# - LHS samples attacker_fraction values in [0, 0.25].
# - Realized attacker_fraction must never exceed 25%.
# - Exact 25% is rare naturally because validator_count must be divisible by 4.
# - Therefore, selected rows are forced to exactly 25%.
# - Zero-attacker controls use the lowest attacker-fraction strata.

n_zero = int(round(N_SAMPLES * ZERO_ATTACK_SHARE))
n_exact_25 = int(round(N_SAMPLES * EXACT_25_ATTACK_SHARE))

if n_zero + n_exact_25 >= N_SAMPLES:
    raise ValueError(
        "ZERO_ATTACK_SHARE + EXACT_25_ATTACK_SHARE must leave ordinary LHS rows."
    )

# Lowest attacker-fraction strata become zero-attacker control contexts.
order = np.argsort(lhs_unit[:, attacker_col])
zero_idx = order[:n_zero]
remaining_idx = np.setdiff1d(
    np.arange(N_SAMPLES),
    zero_idx,
    assume_unique=False
)

# Rows whose sampled attacker_fraction is closest to 25%
# become exact-25 reference cases.
closest_to_25 = remaining_idx[
    np.argsort(
        np.abs(
            df.loc[remaining_idx, "attacker_fraction"].to_numpy()
            - ATTACKER_FRACTION_MAX
        )
    )
]

exact_25_idx = closest_to_25[:n_exact_25]

# Default attacker counts.
# Use round for the sampled value, then cap with floor(25% of validators)
# so the realized fraction never exceeds 25%.
sampled_attackers = np.round(
    df["attacker_fraction"] * df["validator_count"]
).astype(int)

max_attackers_25 = np.floor(
    ATTACKER_FRACTION_MAX * df["validator_count"]
).astype(int)

df["number_of_attackers"] = np.minimum(
    sampled_attackers,
    max_attackers_25
)

# Enforce explicit zero-attacker cases.
df.loc[zero_idx, "attacker_fraction"] = 0.0
df.loc[zero_idx, "number_of_attackers"] = 0

# Enforce exact 25% cases by making validator_count divisible by 4.
vc = df.loc[exact_25_idx, "validator_count"].to_numpy()

vc4 = np.rint(vc / 4).astype(int) * 4
vc4 = np.clip(vc4, 20, 1000)
vc4 = (vc4 // 4) * 4
vc4 = np.maximum(vc4, 20)

df.loc[exact_25_idx, "validator_count"] = vc4
df.loc[exact_25_idx, "attacker_fraction"] = ATTACKER_FRACTION_MAX
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

assert (df["attacker_fraction_realized"] <= ATTACKER_FRACTION_MAX).all()
assert np.isclose(
    df["attacker_fraction_realized"].max(),
    ATTACKER_FRACTION_MAX
)

assert (
    df["attacker_fraction_realized"] == ATTACKER_FRACTION_MAX
).sum() >= n_exact_25

assert (df["attacker_fraction_realized"] < 0.5).all()

# -----------------------------
# 9. Cleanup + save
# -----------------------------

df.insert(0, "config_id", range(1, len(df) + 1))

outfile = "optimized_deterministic_lhs_configurations.csv"
df.to_csv(outfile, index=False)

print("LHS configurations generated.")
print(f"Total zero-attacker contexts: {(df['number_of_attackers'] == 0).sum()}")
print(
    f"Exact 25% attacker contexts: "
    f"{(df['attacker_fraction_realized'] == ATTACKER_FRACTION_MAX).sum()}"
)
print(
    f"Realized attacker fraction > 0.25: "
    f"{(df['attacker_fraction_realized'] > ATTACKER_FRACTION_MAX).sum()}"
)
print(
    f"Max realized attacker fraction: "
    f"{df['attacker_fraction_realized'].max():.6f}"
)

print(
    df[
        [
            "config_id",
            "attacker_fraction",
            "validator_count",
            "number_of_attackers",
            "attacker_fraction_realized",
        ]
    ]
    .sort_values("attacker_fraction_realized", ascending=False)
    .head(10)
)