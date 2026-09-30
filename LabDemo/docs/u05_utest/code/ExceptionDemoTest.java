package fcu.junitdemo;

import static org.junit.Assert.*;

import org.junit.Test;


class ExceptionDemo {
	
	public int gcd(int x, int y) {
		return 1;
		
	}
}

public class ExceptionDemoTest {

	@Test
	public void testGCD() {
		ExceptionDemo d = new ExceptionDemo();

		assertEquals(1, d.gcd(12, 12));
	}
}	

	
