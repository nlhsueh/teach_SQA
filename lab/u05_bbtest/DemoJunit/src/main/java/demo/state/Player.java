package demo.state;

/**
 * 一個玩家本來有 100 的能量，每一移動一次就會消耗 2 能量，當打鬥時每次出拳就會消耗 5 能量，
 * 被打就會消耗 20; 當能量低於 30 時就會進入「虛弱」狀態，此時出拳會多消耗 20% 能量。
 * 吃到仙丹就回提升 10 能量，若超過 100 能量就會進入「超人」狀態，此時移動只會消耗 1 能量。
 * Action:
 *   設計測試案例以進行狀態測試
 */
public class Player {
    private int energy;
    private static final int MAX_ENERGY = 100;
    private static final int MIN_WEAK_ENERGY = 30;

    public Player() {
        this.energy = 100; // 初始能量
    }

    // 移動
    public void move() {
        if (isSuperman()) {
            energy -= 1; // 超人狀態移動消耗 1 能量
        } else {
            energy -= 2; // 正常移動消耗 2 能量
        }
        checkEnergyBounds();
    }

    // 出拳
    public void punch() {
        int punchEnergyCost = 5;
        if (isWeak()) {
            punchEnergyCost *= 1.2; // 虛弱狀態消耗增加 20%
        }
        energy -= punchEnergyCost;
        checkEnergyBounds();
    }

    // 被打
    public void takeHit() {
        energy -= 20; // 被打消耗 20 能量
        checkEnergyBounds();
    }

    // 吃仙丹
    public void eatElixir() {
        energy += 10; // 吃仙丹增加 10 能量
    }

    // 狀態檢查
    public String getStatus() {
        if (isSuperman()) {
            return "超人";
        } else if (isWeak()) {
            return "虛弱";
        } else {
            return "正常";
        }
    }

    private boolean isSuperman() {
        return energy > MAX_ENERGY;
    }

    private boolean isWeak() {
        return energy < MIN_WEAK_ENERGY;
    }

    private void checkEnergyBounds() {
        if (energy < 0) {
            energy = 0; // 能量不能低於 0
        }
    }

    // 回傳當前能量
    public int getEnergy() {
        return energy;
    }
}
