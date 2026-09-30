package fcu.junitdemo;

import java.util.Random;

public class BubbleSort {	
		
	public void bubbleSort(int[] data) {
		int length = data.length-1;
		int temp=0;		
		
		for (int path=0; path<length; path++) {
			for (int i=0; i<length-path; i++) {
				if (data[i] > data[i+1]) {
					temp = data[i];
					data[i] = temp;
					data[i+1] = data[i];
				}
			}
		}		
	}

}