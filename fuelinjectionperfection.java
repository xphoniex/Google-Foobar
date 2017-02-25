/*

Fuel Injection Perfection
=========================

Commander Lambda has asked for your help to refine the automatic quantum antimatter fuel injection system for her LAMBCHOP doomsday device. It's a great chance for you to get a closer look at the LAMBCHOP - and maybe sneak in a bit of sabotage while you're at it - so you took the job gladly. 

Quantum antimatter fuel comes in small pellets, which is convenient since the many moving parts of the LAMBCHOP each need to be fed fuel one pellet at a time. However, minions dump pellets in bulk into the fuel intake. You need to figure out the most efficient way to sort and shift the pellets down to a single pellet at a time. 

The fuel control mechanisms have three operations: 

1) Add one fuel pellet
2) Remove one fuel pellet
3) Divide the entire group of fuel pellets by 2 (due to the destructive energy released when a quantum antimatter pellet is cut in half, the safety controls will only allow this to happen if there is an even number of pellets)

Write a function called answer(n) which takes a positive integer as a string and returns the minimum number of operations needed to transform the number of pellets to 1. The fuel intake control panel can only display a number up to 309 digits long, so there won't ever be more pellets than you can express in that many digits.

For example:
answer(4) returns 2: 4 -> 2 -> 1
answer(15) returns 5: 15 -> 16 -> 8 -> 4 -> 2 -> 1


Languages
=========

To provide a Python solution, edit solution.py
To provide a Java solution, edit solution.java

Test cases
==========

Inputs:
    (string) n = "4"
Output:
    (int) 2

Inputs:
    (string) n = "15"
Output:
    (int) 5

Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will be removed from your home folder.

*/

import java.util.*;

public class Answer {

	public static String blus(String a, String b) {

		if (a.length() < b.length()) {
		
			String s = a;

			a = b;
			b = s;
		}

		String answer = "";

		int lenB = b.length() - 1;
		int lenA = a.length() - 1;

		int carry = 0;

		while (lenB >= 0) {

			if (a.charAt(lenA) == '0' && b.charAt(lenB) == '0') {
				if (carry == 0)
					answer = '0' + answer;
				else {
					answer = '1' + answer;
					carry = 0;
				}
			} else if (a.charAt(lenA) == '1' ^ b.charAt(lenB) == '1') {
				if (carry == 0)
					answer = '1' + answer;
				else {
					answer = '0' + answer;
				}
			} else if (a.charAt(lenA) == '1' && b.charAt(lenB) == '1') {
				if (carry == 0) {
					answer = '0' + answer;
					carry = 1;
				} else
					answer = '1' + answer;
			} 

			lenB--;
			lenA--;
		}

		if (carry == 0 && lenA >= 0)
			return (a.substring(0,lenA+1) + answer);

		while (lenA >= 0) {
			if (carry == 0) {
				answer = a.charAt(lenA) + answer;
			} else {
				if (a.charAt(lenA) == '0') {
					answer = '1' + answer;
					carry = 0;
				} else
					answer = '0' + answer;
			}
			lenA--;
		}

		if (carry==1)
			answer = '1' + answer;

		return answer;
	}

	public static String bultiply (String a, String b) {

		if (a.length() < b.length()) {
		
			String s = a;

			a = b;
			b = s;
		}

		int lenB = b.length() - 1;
		String shift = "";
		String answer = "";

//		String tmp = "";
//		Integer tmpLen = 0;
//		Integer partialAt = 0;
//		Integer tmpScore = 0;

		while (lenB >= 0) {

			if (b.charAt(lenB) == '1')
				answer = blus(answer, a + shift);

			shift += "0";

			lenB--;
		}

		return answer;
	}

	public static String decToBin(String n) {
		
		String answer = "";

		int len = n.length() - 1;

		String multiplier = "1";

		while (len >= 0) {

			switch(n.charAt(len)) {
				case ('1'):
					answer = blus(answer, bultiply("1",multiplier));
					break;
				case ('2'):
					answer = blus(answer, bultiply("10",multiplier));
					break;
				case ('3'):
					answer = blus(answer, bultiply("11",multiplier));
					break;
				case ('4'):
					answer = blus(answer, bultiply("100",multiplier));
					break;
				case ('5'):
					answer = blus(answer, bultiply("101",multiplier));
					break;		
				case ('6'):
					answer = blus(answer, bultiply("110",multiplier));
					break;
				case ('7'):
					answer = blus(answer, bultiply("111",multiplier));
					break;
				case ('8'):
					answer = blus(answer, bultiply("1000",multiplier));
					break;
				case ('9'):
					answer = blus(answer, bultiply("1001",multiplier));
					break;																
			}

			multiplier = bultiply(multiplier,"1010"); // x10

			len--;
		}

		return answer;

	}

	public static int answer(String n) {


		//String b = decToBin(n);
		//b = "11011100000010101101100111001000001110011111010101011010001000110010111010101000111011101111101000101101111111101001110111110110110101011001000000101100101000011100101101111111111100000111000100100000000010101111111010111000011001000110101100000011111000000001001101101001010001001101111100110110110101000100000110101001100101010111100111001101100001111100011100110000111110111110000010000110110010101010111001100100000111010000111010010111001101000011001001101111011110010011101101110101111010101111001101110011110000110110000100000110110101001101110000000100111111011000000000110000101110110000100101101010010111001011010000011010110000100111011010011001111010110111001111011010100001011000110000111110110000111111000110110011001101100110000110100110111111010011101010101001111011011100101001110000100001111000100001101110000111110011110001111001010100010000110010010110010010101100000000001100110111011011111010100111111010011110011010010010111010110011000100101010011110000100100110001011101001100011011100100001001101001100001101101100000100111000110110100001111001011001000010001010111001100010100010111000101000011110010001111000100001111011001111101011111111110011011000011001001001010111101101001110101110100111100101100010000011000000101111101000010111011000010110000100110011100100111001001010111001110111101011110110000101001110001110011110111110001010100100110110011000111010100111010000101111010010110000111101110101110111000000000110101111100110111011001111000000111101101001001010011101110000110110000111101100001100110011000011110000111001010111010110111110110001001010010101010101001101000100001101001000101001100100111100001001010000000011010000111000101100110100110110000101111111001110011111000101101010101111101000010110110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110001110111100100101001101010111111111";
		
		String b = "11";
		int count = 0;
		int len = b.length() - 1;


		while(len>0 && b != "1") {
			
			if (b.charAt(len) == '0') {

				count++;
				b = b.substring(0,b.length()-1);
				//len--;

			} else if (len > 1 && b.charAt(len-1) == '1' && b.charAt(len) == '1') {

				b = blus(b,"1");
				count++;
				len = b.length();

			} else {

				b = b.substring(0,b.length()-1);
				
				count++;
				count++;
			}

			len--;
		}

		return count;
	}

	public static void main(String[] args) {

		String test = "2";
		System.out.println(answer(test));

	}

}
