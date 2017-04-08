/*
Bringing a Gun to a Guard Fight
===============================

Uh-oh - you've been cornered by one of Commander Lambdas elite guards! Fortunately, you grabbed a beam weapon from an 
abandoned guardpost while you were running through the station, so you have a chance to fight your way out. 
But the beam weapon is potentially dangerous to you as well as to the elite guard: its beams reflect off walls, 
meaning youll have to be very careful where you shoot to avoid bouncing a shot toward yourself!

Luckily, the beams can only travel a certain maximum distance before becoming too weak to cause damage. 
You also know that if a beam hits a corner, it will bounce back in exactly the same direction. And of course, 
if the beam hits either you or the guard, it will stop immediately (albeit painfully). 

Write a function answer(dimensions, your_position, guard_position, distance) that gives an array of 2 integers of the 
width and height of the room, an array of 2 integers of your x and y coordinates in the room, an array of 2 integers 
of the guard's x and y coordinates in the room, and returns an integer of the number of distinct directions that you 
can fire to hit the elite guard, given the maximum distance that the beam can travel.

The room has integer dimensions [1 < x_dim <= 1000, 1 < y_dim <= 1000]. You and the elite guard are both positioned on 
the integer lattice at different distinct positions (x, y) inside the room such that [0 < x < x_dim, 0 < y < y_dim]. 
Finally, the maximum distance that the beam can travel before becoming harmless will be given as an integer 
1 < distance <= 10000.

For example, if you and the elite guard were positioned in a room with dimensions [3, 2], you_position [1, 1], 
guard_position [2, 1], and a maximum shot distance of 4, you could shoot in seven different directions to hit the 
elite guard (given as vector bearings from your location): [1, 0], [1, 2], [1, -2], [3, 2], [3, -2], [-3, 2], and 
[-3, -2]. As specific examples, the shot at bearing [1, 0] is the straight line horizontal shot of distance 1, the 
shot at bearing [-3, -2] bounces off the left wall and then the bottom wall before hitting the elite guard with a 
total shot distance of sqrt(13), and the shot at bearing [1, 2] bounces off just the top wall before hitting the elite 
guard with a total shot distance of sqrt(5).

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
