package xdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class SwimmingPoolTest {

    SwimmingPool pool;

    @BeforeEach
    void init() {
        pool = new SwimmingPool();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/swimming.csv", numLinesToSkip = 1)
    void price_test(String member, String group, int age, String day, int time, double price) {
        boolean isMember= member.equals("Y");
        boolean isGroup=group.equals("Y");
        boolean early=time<7;

        double actual_price = pool.price(day, isMember, isGroup, age, early);
        assertEquals(price, actual_price);

    }
}