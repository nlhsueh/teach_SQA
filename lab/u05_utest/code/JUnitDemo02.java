package fcu.junitdemo;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class JUnitDemo02 {

	@Ignore
	@Test
	public void test1() {
		F f = new F();
		try {
			assertEquals(12, f.div(12, 1));

			assertEquals(13, f.div(12, 1));
			assertEquals(24, f.div(12, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(expected = Exception.class)
	public void test2() throws Exception {
		F f = new F();
		assertEquals(13, f.div(12, 1));
		assertEquals(1, f.div(12, 0));
	}

	/*
	 * The class to be tested
	 */
	class F {
		public int div(int x, int y) throws Exception {
			// if (y==0) throw new Exception("divided by 0");

			return x / y;
		}
	}// class F

}