package xdemo;

import xdemo.People;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeopleTest {

    @Test
    void bmi() {
        People jack = new People("Jack", 1.7, 80, 2000);
//        assertEquals(26.6, jack.bmi(), 0.1);
    }
}