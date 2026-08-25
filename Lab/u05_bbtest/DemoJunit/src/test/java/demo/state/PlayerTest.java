package demo.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player p01;

    @BeforeEach
    void setUp() {
        p01 = new Player();
    }

    /**
     * 一直走路，從正常到虛弱的狀態
     * 虛弱狀態的體能消耗測試
     */
    @Test
    void weakTest() {
        int count = 0;
        do {
            p01.move();
            count++;
        } while (count < 36);
        assertEquals(28, p01.getEnergy());
        assertTrue(p01.getStatus().equals("虛弱"));

        // 測試虛弱的消耗狀況
        p01.punch();
        assertEquals(22, p01.getEnergy());

        p01.takeHit();
        assertEquals(2, p01.getEnergy());

    }

    /**
     * 從正常到超人狀態
     * 測試超人的體能消耗狀況
     */


}