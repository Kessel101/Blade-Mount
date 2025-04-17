package efs.task.collections.Hero;

public class HeroStats {
    private int inf_leader;
    private int cav_leader;
    private int arch_leader;

    public HeroStats(int inf_leader, int cav_leader, int arch_leader) {
        this.inf_leader = inf_leader;
        this.cav_leader = cav_leader;
        this.arch_leader = arch_leader;
    }

    public HeroStats() {
        this.inf_leader = 0;
        this.cav_leader = 0;
        this.arch_leader = 0;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final HeroStats heroStats = new HeroStats();

        public Builder inf_leader(int inf_leader) {
            heroStats.inf_leader = inf_leader;
            return this;
        }

        public Builder cav_leader(int cav_leader) {
            heroStats.cav_leader = cav_leader;
            return this;
        }

        public Builder arch_leader(int arch_leader) {
            heroStats.arch_leader = arch_leader;
            return this;
        }

        public Builder total_leader(int inf_leader, int cav_leader, int arch_leader) {
            heroStats.inf_leader = inf_leader;
            heroStats.cav_leader = cav_leader;
            heroStats.arch_leader = arch_leader;
            return this;
        }

        public HeroStats build() {
            return heroStats;
        }
    }
}
