/*
Running with Bunnies
====================

You and your rescued bunny prisoners need to get out of this collapsing death trap of a space station - and fast! Unfortunately, some of the bunnies have been weakened by their long imprisonment and can't run very fast. Their friends are trying to help them, but this escape would go a lot faster if you also pitched in. The defensive bulkhead doors have begun to close, and if you don't make it through in time, you'll be trapped! You need to grab as many bunnies as you can and get through the bulkheads before they close. 

The time it takes to move from your starting point to all of the bunnies and to the bulkhead will be given to you in a square matrix of integers. Each row will tell you the time it takes to get to the start, first bunny, second bunny, ..., last bunny, and the bulkhead in that order. The order of the rows follows the same pattern (start, each bunny, bulkhead). The bunnies can jump into your arms, so picking them up is instantaneous, and arriving at the bulkhead at the same time as it seals still allows for a successful, if dramatic, escape. (Don't worry, any bunnies you don't pick up will be able to escape with you since they no longer have to carry the ones you did pick up.) You can revisit different spots if you wish, and moving to the bulkhead doesn't mean you have to immediately leave - you can move to and from the bulkhead to pick up additional bunnies if time permits.

In addition to spending time traveling between bunnies, some paths interact with the space station's security checkpoints and add time back to the clock. Adding time to the clock will delay the closing of the bulkhead doors, and if the time goes back up to 0 or a positive number after the doors have already closed, it triggers the bulkhead to reopen. Therefore, it might be possible to walk in a circle and keep gaining time: that is, each time a path is traversed, the same amount of time is used or added.

Write a function of the form answer(times, time_limit) to calculate the most bunnies you can pick up and which bunnies they are, while still escaping through the bulkhead before the doors close for good. If there are multiple sets of bunnies of the same size, return the set of bunnies with the lowest prisoner IDs (as indexes) in sorted order. The bunnies are represented as a sorted list by prisoner ID, with the first bunny being 0. There are at most 5 bunnies, and time_limit is a non-negative integer that is at most 999.

For instance, in the case of
[
  [0, 2, 2, 2, -1],  # 0 = Start
  [9, 0, 2, 2, -1],  # 1 = Bunny 0
  [9, 3, 0, 2, -1],  # 2 = Bunny 1
  [9, 3, 2, 0, -1],  # 3 = Bunny 2
  [9, 3, 2, 2,  0],  # 4 = Bulkhead
]
and a time limit of 1, the five inner array rows designate the starting point, bunny 0, bunny 1, bunny 2, and the bulkhead door exit respectively. You could take the path:

Start End Delta Time Status
    -   0     -    1 Bulkhead initially open
    0   4    -1    2
    4   2     2    0
    2   4    -1    1
    4   3     2   -1 Bulkhead closes
    3   4    -1    0 Bulkhead reopens; you and the bunnies exit

With this solution, you would pick up bunnies 1 and 2. This is the best combination for this space station hallway, so the answer is [1, 2].

Languages
=========

To provide a Python solution, edit solution.py
To provide a Java solution, edit solution.java

Test cases
==========

Inputs:
    (int) times = [[0, 1, 1, 1, 1], [1, 0, 1, 1, 1], [1, 1, 0, 1, 1], [1, 1, 1, 0, 1], [1, 1, 1, 1, 0]]
    (int) time_limit = 3
Output:
    (int list) [0, 1]

Inputs:
    (int) times = [[0, 2, 2, 2, -1], [9, 0, 2, 2, -1], [9, 3, 0, 2, -1], [9, 3, 2, 0, -1], [9, 3, 2, 2, 0]]
    (int) time_limit = 1
Output:
    (int list) [1, 2]
*/
package com.google.challenges; 
import java.util.*;

class subsetComparator implements Comparator<LinkedList<Integer>> {
    @Override
    public int compare(LinkedList<Integer> a, LinkedList<Integer> b) {

    	if (a.size() != b.size())
    		return (b.size() - a.size());
    	else {
    		for (int i = 0; i < a.size(); i++) {

    			if (a.get(i) < b.get(i))
    				return a.get(i) - b.get(i);

    			else if (a.get(i) > b.get(i))
    				return a.get(i) - b.get(i);

    		}
    		return 1;
    	}
    }
}

public class Answer {
    
	static Set<LinkedList<Integer>> set = new HashSet<LinkedList<Integer>>();
	static LinkedList<LinkedList<Integer>> permutations = new LinkedList<LinkedList<Integer>>();

	public static void permute(LinkedList<LinkedList<Integer>> subsets, int at, int index) {

		LinkedList<Integer> subset = subsets.get(index);

		for (int i = at; i < subset.size(); i++) {

			Collections.swap(subset, i, at);

			if (!set.contains(subset)) {

				LinkedList<Integer> new_perm = new LinkedList<Integer> (subset);

				set.add(subset);
				permutations.add(new_perm);

			}

			permute(subsets, at+1, index);
			
			Collections.swap(subset, at, i);
		}


	}


	public static LinkedList<LinkedList<Integer>> subsets(int bunnies) {

		LinkedList<LinkedList<Integer>> subsets = new LinkedList<LinkedList<Integer>>();

		int max = (int)Math.pow(2,bunnies) - 1;

		int maxLen = Integer.toBinaryString(max).length();

		String bin;

		for (; max >= 0 ; max--) {
			
			bin = Integer.toBinaryString(max);

			int diff = maxLen - bin.length();

			for (int l = 0; l < diff; l++)
				bin = "0" + bin;

			LinkedList<Integer> subset = new LinkedList<Integer>();

			for (int i = 0 ; i < bin.length(); i++) {
				if (bin.charAt(i) == '1') {
					subset.add(i);
				}
			}

			subsets.add(subset);
		}

		Collections.sort(subsets, new subsetComparator());

		return subsets;

	}

	
    public static int[] answer(int[][] times, int time_limit) { 

        // Your code goes here.
		int len = times.length;

		LinkedList<LinkedList<Integer>> subsets = subsets(len-2);
		
		int[][] min = new int[len][len];
		
		int src = 0;
		
	    for (int z = 0; z < len; z++)
	    {
    		for (src = 0; src < len; src++)
    		{
    		    if (z==0) {
        			for (int i=0; i<len; ++i)
        				min[src][i] = Integer.MAX_VALUE;
        			min[src][src] = 0;
    		    }
    
    			for (int i=0; i<len; ++i)
    	            for (int j=0; j<len; ++j)
    	                if (min[src][i] != Integer.MAX_VALUE && min[src][i] + times[i][j] < min[src][j])
    	                    min[src][j] = min[src][i] + times[i][j];
    	    }
	    }
	   
	   
		for (src = 0; src < len; src++)
		{
			for (int i=0; i<len; ++i)
	        {
	            for (int j=0; j<len; ++j)
	            {
	                if (min[src][i] + times[i][j] < min[src][j]) {
	                    int[] cycle = new int[len-2];
	                    
	                    for (int c = 0 ; c < len-2 ; c++)
	                        cycle[c] = c;
	                   
	                    return cycle;
	                }
	            }
	        }
	    }
	    
	    // -=-
	    
	    int from;
	    int time;

	    for (int index = 0; index < subsets.size(); index++)
	    {
			permute(subsets, 0, index);
			
			//Collections.sort(permutations, new subsetComparator());

			for (int p = 0 ; p < permutations.size(); p++ ) {
			    
				from = 0;
				time = time_limit;

				LinkedList<Integer> perm = permutations.get(p);
				
				for (int i = 0; i < perm.size(); i++) {

					time -= min[from][perm.get(i)+1];
					from = perm.get(i)+1;
					
				}

				time -= min[from][len-1];

				if (time >= 0) {
				    
				    int[] result = new int[subsets.get(index).size()];
				    
				    for (int r = 0 ; r < subsets.get(index).size(); r++)
				        result[r] = subsets.get(index).get(r);
				    
				    set.clear();
			        permutations.clear();
			    
				    return result;
				}

			}

			set.clear();
			permutations.clear();

		}

		int[] result = new int[0];

		return result;
    } 
}
