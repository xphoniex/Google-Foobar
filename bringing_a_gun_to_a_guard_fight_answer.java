/*
Bringing a Gun to a Guard Fight
===============================

Uh-oh - you've been cornered by one of Commander Lambdas elite guards! Fortunately, you grabbed a beam weapon from an abandoned guardpost while you were running through the station, so you have a chance to fight your way out. But the beam weapon is potentially dangerous to you as well as to the elite guard: its beams reflect off walls, meaning youll have to be very careful where you shoot to avoid bouncing a shot toward yourself!

Luckily, the beams can only travel a certain maximum distance before becoming too weak to cause damage. You also know that if a beam hits a corner, it will bounce back in exactly the same direction. And of course, if the beam hits either you or the guard, it will stop immediately (albeit painfully). 

Write a function answer(dimensions, your_position, guard_position, distance) that gives an array of 2 integers of the width and height of the room, an array of 2 integers of your x and y coordinates in the room, an array of 2 integers of the guard's x and y coordinates in the room, and returns an integer of the number of distinct directions that you can fire to hit the elite guard, given the maximum distance that the beam can travel.

The room has integer dimensions [1 < x_dim <= 1000, 1 < y_dim <= 1000]. You and the elite guard are both positioned on the integer lattice at different distinct positions (x, y) inside the room such that [0 < x < x_dim, 0 < y < y_dim]. Finally, the maximum distance that the beam can travel before becoming harmless will be given as an integer 1 < distance <= 10000.

For example, if you and the elite guard were positioned in a room with dimensions [3, 2], you_position [1, 1], guard_position [2, 1], and a maximum shot distance of 4, you could shoot in seven different directions to hit the elite guard (given as vector bearings from your location): [1, 0], [1, 2], [1, -2], [3, 2], [3, -2], [-3, 2], and [-3, -2]. As specific examples, the shot at bearing [1, 0] is the straight line horizontal shot of distance 1, the shot at bearing [-3, -2] bounces off the left wall and then the bottom wall before hitting the elite guard with a total shot distance of sqrt(13), and the shot at bearing [1, 2] bounces off just the top wall before hitting the elite guard with a total shot distance of sqrt(5).

Languages
=========

To provide a Python solution, edit solution.py
To provide a Java solution, edit solution.java

Test cases
==========

Inputs:
    (int list) dimensions = [3, 2]
    (int list) captain_position = [1, 1]
    (int list) badguy_position = [2, 1]
    (int) distance = 4
Output:
    (int) 7

Inputs:
    (int list) dimensions = [300, 275]
    (int list) captain_position = [150, 150]
    (int list) badguy_position = [185, 100]
    (int) distance = 500
Output:
    (int) 9

Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will be removed from your home folder.

*/

package com.google.challenges; 
import java.util.*;

public class Answer {   
	
	static Map<String, Integer> slopes = new HashMap<String,Integer>();
	static LinkedList<int[]> map = new LinkedList<int[]>();
	
	static int y;
	static int x;

	static int by;
	static int bx;

	static int distance;

	static int rightMargin;
	static int leftMargin;
	static int topMargin;
	static int downMargin;

	public static int gcd(int a, int b) {
		if ( b == 0 )
			return Math.abs(a);
		else
			return Math.abs(gcd(b, a%b));
	}
    
    public static int answer(int[] dimensions, int[] captain_position, int[] badguy_position, int _distance) { 

        // Your code goes here.
        
		distance = _distance;

		Map<String, Integer> captured = new HashMap<String,Integer>();
		Map<String, Integer> vectors = new HashMap<String,Integer>();

		int wExtend = 1 + ((distance + captain_position[0]) / dimensions[0]);
		int hExtend = 1 + ((distance + captain_position[1]) / dimensions[1]);

		x = captain_position[0];
		y = captain_position[1];

		bx = badguy_position[0];
		by = badguy_position[1];

		// ====================

		int deltaY = by - y;
		int deltaX = bx - x;
		int deltaGCD = gcd(deltaX,deltaY);
		double deltaD = Math.sqrt(Math.pow(deltaY,2) + Math.pow(deltaX,2));
		
		deltaY /= deltaGCD;
		deltaX /= deltaGCD;

		int count = 0;

		if (distance - deltaD >= 0) {
			count = 1;
			captured.put(bx + "-" + by, 1);
			slopes.put(deltaY + "-" + deltaX, deltaGCD);
			vectors.put(deltaY + "-" + deltaX, 1);
			//System.out.println("Slope added! " + deltaY + "-" + deltaX + " #" + deltaGCD);
		}

		// ====================

		fillH(x, y, hExtend, dimensions, true);
		fillV(x, y, hExtend, wExtend, dimensions, true);

		fillH(badguy_position[0], badguy_position[1], hExtend, dimensions, false);
		fillV(badguy_position[0], badguy_position[1], hExtend, wExtend, dimensions, false);
		
		// ====================

		Integer slopeMultiply;
		
		int[] m;

		for (int i = 0 ; i < map.size(); i++) {
		    
		    m = map.get(i);

		    if ( captured.get(m[0]+"-"+m[1]) == null) {

			    deltaY = m[1] - y;
			    deltaX = m[0] - x;
			    //deltaD = Math.sqrt(Math.pow(deltaY,2) + Math.pow(deltaX,2));
    		    deltaGCD = gcd(deltaX,deltaY);
    			deltaX /= deltaGCD;
    			deltaY /= deltaGCD;
    		    
    			slopeMultiply = slopes.get(deltaY + "-" + deltaX);
    			if (vectors.get(deltaY + "-" + deltaX) == null)
    			{
        			if (slopeMultiply == null) {
    
        				count++;
        				vectors.put(deltaY + "-" + deltaX, 1);
        
        			} else {
        			
        				if (deltaGCD < slopeMultiply) {
        					count++;
        					vectors.put(deltaY + "-" + deltaX, 1);
        				}
        			}
    			}
        		captured.put(m[0]+"-"+m[1],1);
		    }
		}
		
		slopes.clear();
		map.clear();
		captured.clear();
		vectors.clear();
		
		return count;
    }
    
    
	public static void addToMap(int currentX, int currentY) {

		int deltaY = currentY - y;
		int deltaX = currentX - x;

		int[] target = {currentX, currentY};

		double deltaD = Math.sqrt(Math.pow(deltaY,2) + Math.pow(deltaX,2));
		
		if (distance - deltaD >= 0)
			map.add(target);

	}

	public static void addSlope(int currentX, int currentY) {
		
		int deltaY = currentY - y;
		int deltaX = currentX - x;
		int deltaGCD = gcd(deltaX,deltaY);
		
		double deltaD = Math.sqrt(Math.pow(deltaY,2) + Math.pow(deltaX,2));
		
		deltaX /= deltaGCD;
		deltaY /= deltaGCD;

		if (distance - deltaD >= 0) {
			if (slopes.get(deltaY + "-" + deltaX) == null)
				slopes.put(deltaY + "-" + deltaX, deltaGCD);

			else if (deltaGCD < slopes.get(deltaY + "-" + deltaX))		// un ?
				slopes.put(deltaY + "-" + deltaX, deltaGCD);
		}

	}

	public static void fillV(int _currentX, int currentY, int hExtend, int wExtend, int[] dimensions, boolean isHero) {

		if (isHero) {
			
			rightMargin = dimensions[0] -  x;
			leftMargin = x;

		} else {

			rightMargin = dimensions[0] -  bx;
			leftMargin = bx;
		}

		int currentX = _currentX;

		for (int i = 1 ; i <= wExtend ; i++) {

			currentX += rightMargin * 2;
			rightMargin = dimensions[0] - rightMargin;

			if(isHero)
				addSlope(currentX,currentY);
			else
				addToMap(currentX,currentY);

			fillH(currentX, currentY, hExtend, dimensions, isHero);

		}

		currentX = _currentX;

		for (int i = 1 ; i <= wExtend ; i++) {

			currentX -= leftMargin * 2;
			leftMargin = dimensions[0] - leftMargin;

			if(isHero)
				addSlope(currentX,currentY);
			else
				addToMap(currentX,currentY);

			fillH(currentX, currentY, hExtend, dimensions, isHero);

		}

	}

	public static void fillH(int currentX, int _currentY, int hExtend, int[] dimensions, boolean isHero) {

		if (isHero) {
			
			topMargin = dimensions[1] - y;
			downMargin = y;

		} else {
			
			topMargin = dimensions[1] - by;
			downMargin = by;

		}

		int currentY = _currentY;

		for (int i = 1 ; i <=  hExtend ; i++) {

			currentY += topMargin * 2;
			topMargin = dimensions[1] - topMargin;

			if(isHero)
				addSlope(currentX,currentY);
			else
				addToMap(currentX,currentY);

		}

		currentY = _currentY;

		for (int i = 1 ; i <=  hExtend ; i++) {

			currentY -= downMargin * 2;
			downMargin = dimensions[1] - downMargin;

			if(isHero)
				addSlope(currentX,currentY);
			else
				addToMap(currentX,currentY);
		}

	}
}
