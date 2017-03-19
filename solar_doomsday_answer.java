/*

Solar Doomsday
==============

Who would've guessed? Doomsday devices take a LOT of power. Commander Lambda wants to supplement the LAMBCHOP's quantum antimatter reactor core with solar arrays, and she's tasked you with setting up the solar panels. 

Due to the nature of the space station's outer paneling, all of its solar panels must be squares. Fortunately, you have one very large and flat area of solar material, a pair of industrial-strength scissors, and enough MegaCorp Solar Tape(TM) to piece together any excess panel material into more squares. For example, if you had a total area of 12 square yards of solar material, you would be able to make one 3x3 square panel (with a total area of 9). That would leave 3 square yards, so you can turn those into three 1x1 square solar panels.

Write a function answer(area) that takes as its input a single unit of measure representing the total area of solar panels you have (between 1 and 1000000 inclusive) and returns a list of the areas of the largest squares you could make out of those panels, starting with the largest squares first. So, following the example above, answer(12) would return [9, 1, 1, 1].

Languages
=========

To provide a Python solution, edit solution.py
To provide a Java solution, edit solution.java

Test cases
==========

Inputs:
    (int) area = 12
Output:
    (int list) [9, 1, 1, 1]

Inputs:
    (int) area = 15324
Output:
    (int list) [15129, 169, 25, 1]

Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will be removed from your home folder.


foobar:~/solar_doomsday guest$ cat constraints.txt
Java
====

Your code will be compiled using standard Java 7. It must implement the answer() method in the solution stub.

Execution time is limited. Some classes are restricted (e.g. java.lang.ClassLoader). You will see a notice if you use a restricted class when you verify your solution.

Third-party libraries, input/output operations, spawning threads or processes and changes to the execution environment are not allowed.

Python
======

Your code will run inside a Python 2.7.6 sandbox.

Standard libraries are supported except for bz2, crypt, fcntl, mmap, pwd, pyexpat, select, signal, termios, thread, time, unicodedata, zipimport, zlib.


*/

package com.google.challenges; 
import java.util.*;

public class Answer {   
    public static int maxSquare(int number, int start, int end) {
		int mid =  (start+end) / 2;
		int mid2 = mid * mid;

		if (end - start == 1 || start - end == 1) {
			if ((mid+1)*(mid+1) <= number)
				return (mid+1)*(mid+1);
			else
				return mid2;
		} else {
			if (mid2 < number)
				return maxSquare(number, mid, end);
			else if (mid2 > number)
				return maxSquare(number, start, mid);
			else if (mid2 == number) {
				return mid2;
			}
		}

		return 0;
	}
	
    public static int[] answer(int area) {

        // Your code goes here.
        List<Integer> result = new ArrayList<Integer>(); 
		while (area > 0) {
			int next = maxSquare(area, 1, 1000);
			result.add(next);
			area -= next;
		}

		int[] answer = new int[result.size()];

		for (int i = 0; i < answer.length ; i++)
			answer[i] = result.get(i);

		return answer;

    } 
}
