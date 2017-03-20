/*

Inputs:
    (int) maze = [[0, 1, 1, 0], 
    		  [0, 0, 0, 1], 
    		  [1, 1, 0, 0], 
    		  [1, 1, 1, 0]]
Output:
    (int) 7


Inputs:
    (int) maze = [[0, 0, 0, 0, 0, 0], 
    		  [1, 1, 1, 1, 1, 0], 
    		  [0, 0, 0, 0, 0, 0], 
  		  [0, 1, 1, 1, 1, 1], 
    		  [0, 1, 1, 1, 1, 1], 
    		  [0, 0, 0, 0, 0, 0]]
Output:
    (int) 11

*/

//package​ ​com.google.challenges;​ ​

import java.util.*;

class point {
	public int x;
	public int y;

	point (int _y, int _x) { x = _x; y = _y; }
}

public class Answer {

	public static void placeP(int[][] minRegular, int[][] visited, Queue<point> q, point p, int h, int w) {

		visited[p.y][p.x] = 1;

		if (p.x > 0 && minRegular[p.y][p.x-1] > 0)
				minRegular[p.y][p.x] = Math.max(minRegular[p.y][p.x], minRegular[p.y][p.x-1] + 1);
		if (p.x < w-1 && minRegular[p.y][p.x+1] > 0)
				minRegular[p.y][p.x] = Math.max(minRegular[p.y][p.x], minRegular[p.y][p.x+1] + 1);
		if (p.y > 0 && minRegular[p.y-1][p.x] > 0)
				minRegular[p.y][p.x] = Math.max(minRegular[p.y][p.x], minRegular[p.y-1][p.x] + 1);
		if (p.y < h-1 && minRegular[p.y+1][p.x] > 0)
				minRegular[p.y][p.x] = Math.max(minRegular[p.y][p.x], minRegular[p.y+1][p.x] + 1);

		if (p.x < w-1 && visited[p.y][p.x+1] == 0)
			q.add(new point(p.y, p.x + 1));
		if (p.x > 0 && visited[p.y][p.x-1] == 0)
			q.add(new point(p.y, p.x - 1));
		if (p.y < h-1 && visited[p.y + 1][p.x] == 0)
			q.add(new point(p.y + 1, p.x));
		if (p.y > 0 && visited[p.y - 1][p.x] == 0)
			q.add(new point(p.y - 1, p.x));

	}

	public static int answer(int[][] maze) {

		int h = maze.length;
		int w = maze[0].length;

		int min = Integer.MAX_VALUE;
		int solved;

		int[][] minRegular = new int[h][w];
		int[][] visited = new int[h][w];

		minRegular[0][0] = 1;
		visited[0][0] = 1;

		LinkedList<point> ones = new LinkedList<point>();

		Queue<point> q = new LinkedList<point>();

		q.add(new point(1,0));
		q.add(new point(0,1));

		while (!q.isEmpty()) {

			point p = q.poll();

			if (visited[p.y][p.x] == 0)
				if (maze[p.y][p.x] == 1)
					ones.add(new point(p.y,p.x));


			visited[p.y][p.x] = 1;

			if (maze[p.y][p.x] != 1) {

				placeP(minRegular, visited, q, p, h, w);

			}
		}

		solved = minRegular[h-1][w-1];


		int min2 = Integer.MAX_VALUE;
		int solved2;

		int[][] minRegular2 = new int[h][w];
		int[][] visited2 = new int[h][w];

		minRegular2[h-1][w-1] = 1;
		visited2[h-1][w-1] = 1;

		Queue<point> q2 = new LinkedList<point>();

		q2.add(new point(h-2,w-1));
		q2.add(new point(h-1,w-2));

		while (!q2.isEmpty()) {

			point p2 = q2.poll();

			visited2[p2.y][p2.x] = 1;

			if (maze[p2.y][p2.x] != 1) {

				placeP(minRegular2, visited2, q2, p2, h, w);

			}
		}

		solved2 = minRegular[0][0];
		
		if (solved>0)
			min = Math.min(min,	solved);

		int minPlus1 = min + 1;
		
		for (int o = 0; o < ones.size(); o++)
		{
			point oPoint = ones.get(o);
			int y = oPoint.y;
			int x = oPoint.x;

			ArrayList<Integer> neighbours = new ArrayList<Integer>();
			ArrayList<Integer> neighbours2 = new ArrayList<Integer>();

			if (y-1 >= 0 && minRegular[y-1][x] > 0)
				neighbours.add(minRegular[y-1][x]);
			if (y+1 < h && minRegular[y+1][x] > 0)
				neighbours.add(minRegular[y+1][x]);
			if (x-1 >= 0 && minRegular[y][x-1] > 0)
				neighbours.add(minRegular[y][x-1]);
			if (x+1 < w && minRegular[y][x+1] > 0)
				neighbours.add(minRegular[y][x+1]);

			if (y-1 >= 0 && minRegular2[y-1][x] > 0)
				neighbours2.add(minRegular2[y-1][x]);
			if (y+1 < h && minRegular2[y+1][x] > 0)
				neighbours2.add(minRegular2[y+1][x]);
			if (x-1 >= 0 && minRegular2[y][x-1] > 0)
				neighbours2.add(minRegular2[y][x-1]);
			if (x+1 < w && minRegular2[y][x+1] > 0)
				neighbours2.add(minRegular2[y][x+1]);

			Collections.sort(neighbours);
			Collections.sort(neighbours2);

			// 1 + neighbours(0) + neighbours2(0)
			if (neighbours.size()>0 && neighbours2.size()>0) {
				min = Math.min(min, 1+neighbours.get(0)+neighbours2.get(0));
			}			

		}

		return min;
	}


	public static void main(String[] args) {
	
		int[][] maze2 = {{0, 1, 1, 0}, 
			 	 {0, 0, 0, 1}, 
			 	 {1, 1, 0, 0}, 
				 {1, 1, 1, 0}};
					


		int[][] maze = {{0, 0, 0, 0, 0}, 
				{1, 1, 1, 1, 0}, 
				{0, 0, 0, 0, 0},
				{0, 1, 0, 1, 1}, 
				{0, 0, 0, 0, 0}};

/*

		int[][] maze = {{0, 0, 0, 0, 0, 0},
				{1, 1, 1, 1, 1, 0},
				{0, 0, 0, 0, 0, 0},
				{1, 1, 0, 1, 1, 1},
				{0, 1, 0, 1, 1, 1},
				{0, 0, 0, 0, 0, 0}};
*/
    		System.out.println("min: " + answer(maze));
	}

}
