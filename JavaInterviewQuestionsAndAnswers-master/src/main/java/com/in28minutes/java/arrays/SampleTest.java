package com.in28minutes.java.arrays;

public class SampleTest {
	
	public static void main(String[] arg) {
		
		/*String s3 = "Value1";
		String s2 = "Value2";
		for (int i = 0; i < 100000; ++i) {
		s3 = s3 + s2;
		}
		System.out.println(s3);*/
		
		StringBuffer s3 = new StringBuffer("Value1");
		String s2 = "Value2";
		for (int i = 0; i < 100000; ++i) {
		s3.append(s2);
		}
		System.out.println(s3);
		
		
		
		/*String ref1 = "Prakash";
		String ref2 = "Prakash";
		String ref3 = "Prakash";
		String ref4 = "Prakash";
		 ref4 = "Nithyi";
		 System.out.println(ref2);
*/
		
	}

}
