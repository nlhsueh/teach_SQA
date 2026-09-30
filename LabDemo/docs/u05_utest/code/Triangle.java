package fcu.junitdemo;

/*
 * Please write a junit program to test this program
 * Note this maybe a wrong program
 */
public class Triangle {
	int a, b, c;


	public Triangle(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/*
	 * Please test this function
	 */
	public String checkTriangle() {
		String result = "";
		
		if ((a + b < c) || (b + c < a) || (c + a < b))
			result = "not a triangle";
		else if (a == b && b == c && a == c) {
			result = "regular triangle";
		} else if ((a * a + b * b == c * c) || (b*b+c*c == a*a) || (c*c+a*a == b*b)) {
			result = "right-angled triangle";
		} else result = "triangle";
		
		return result;
	}

	/*
	 * To get the length of the third line if the triangle is a right-angle
	 * triangle This is a method specification (no real code inside) Please
	 * write the junit test first, then come back here to write the code
	 */
	public static double getLength(double a, double b) {
		return 0; // make compiler happy
	}

}
