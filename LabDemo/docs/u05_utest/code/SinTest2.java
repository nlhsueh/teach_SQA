package fcu.junitdemo;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SinTest2 {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSinDouble() {
		// fail("Not yet implemented");
		Sin s = new Sin();
		Assert.assertEquals(0.5, s.sin((Math.PI)/6), 0.1);
	}

	@Ignore("no ready")
	@Test
	public void testSinDoubleInt() {
		// fail("Not yet implemented");
		Sin s = new Sin();
		Assert.assertEquals(0.4, s.sin(0.4, 1));
		Assert.assertEquals(0.3894, s.sin(0.4, 2), 0.001);
	}

	@Test
	public void testFactorial() {
		Sin s = new Sin();
		Assert.assertEquals(1.0, s.factorial(1));
		Assert.assertEquals(2.0, s.factorial(2));
		Assert.assertEquals(6.0, s.factorial(3));
		Assert.assertEquals(24.0, s.factorial(4));
	}

}
