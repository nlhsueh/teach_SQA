package fcu.junitdemo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TriangleTest {
	String regular, triangle, rightAngle, notTriangle;	
	
	@Before
	public void setup() {
		regular = "regular triangle";
		triangle = "triangle";
		rightAngle = "right-angled triangle";
		notTriangle = "not a triangle";
	}

	@Test
	public void test() {
		//regular
		Triangle t= new Triangle(1,1,1);
		assertEquals(regular, t.checkTriangle());
		t=new Triangle (6,6,6);
		assertEquals(regular, t.checkTriangle());
		
		//not a triangle
		t=new Triangle (2,2,6);
		assertEquals(notTriangle, t.checkTriangle());
		t=new Triangle (6,2,2);
		assertEquals(notTriangle, t.checkTriangle());		
		t=new Triangle (2,6,2);
		assertEquals(notTriangle, t.checkTriangle());

		
		//right angled
		t=new Triangle(3,4,5);
		assertEquals(rightAngle, t.checkTriangle());		
		t=new Triangle(5,4,3);
		assertEquals(rightAngle, t.checkTriangle());
		t=new Triangle(4,5,3);
		assertEquals(rightAngle, t.checkTriangle());
		
		//just a triangle
		t=new Triangle(2,2,3);
		assertEquals(triangle, t.checkTriangle());
		t=new Triangle(8,9,7);
		assertEquals(triangle, t.checkTriangle());	
		
	}

}
