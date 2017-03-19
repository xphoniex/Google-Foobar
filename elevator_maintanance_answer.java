/*
Elevator Maintenance
====================

You've been assigned the onerous task of elevator maintenance - ugh! It wouldn't be so bad, except that all the elevator documentation has been lying in a disorganized pile at the bottom of a filing cabinet for years, and you don't even know what elevator version numbers you'll be working on. 

Elevator versions are represented by a series of numbers, divided up into major, minor and revision integers. New versions of an elevator increase the major number, e.g. 1, 2, 3, and so on. When new features are added to an elevator without being a complete new version, a second number named "minor" can be used to represent those new additions, e.g. 1.0, 1.1, 1.2, etc. Small fixes or maintenance work can be represented by a third number named "revision", e.g. 1.1.1, 1.1.2, 1.2.0, and so on. The number zero can be used as a major for pre-release versions of elevators, e.g. 0.1, 0.5, 0.9.2, etc (Commander Lambda is careful to always beta test her new technology, with her loyal henchmen as subjects!).

Given a list of elevator versions represented as strings, write a function answer(l) that returns the same list sorted in ascending order by major, minor, and revision number so that you can identify the current elevator version. The versions in list l will always contain major numbers, but minor and revision numbers are optional. If the version contains a revision number, then it will also have a minor number.

For example, given the list l as ["1.1.2", "1.0", "1.3.3", "1.0.12", "1.0.2"], the function answer(l) would return the list ["1.0", "1.0.2", "1.0.12", "1.1.2", "1.3.3"]. If two or more versions are equivalent but one version contains more numbers than the others, then these versions must be sorted ascending based on how many numbers they have, e.g ["1", "1.0", "1.0.0"]. The number of elements in the list l will be at least 1 and will not exceed 100.

Languages
=========

To provide a Python solution, edit solution.py
To provide a Java solution, edit solution.java

Test cases
==========

Inputs:
    (string list) l = ["1.1.2", "1.0", "1.3.3", "1.0.12", "1.0.2"]
Output:
    (string list) ["1.0", "1.0.2", "1.0.12", "1.1.2", "1.3.3"]

Inputs:
    (string list) l = ["1.11", "2.0.0", "1.2", "2", "0.1", "1.2.1", "1.1.1", "2.0"]
Output:
    (string list) ["0.1", "1.1.1", "1.2", "1.2.1", "1.11", "2", "2.0", "2.0.0"]

Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will be removed from your home folder.
*/

package com.google.challenges; 
import java.util.*;

class version {
	public int major;
	public int minor;
	public int revision;
	public int length;
	public int type; // 3 : 0.0.0		2 : 0.0			1: 0

	version(int _major, int _minor, int _revision, int _length, int _type) {
		major = _major;
		minor = _minor;
		revision = _revision;
		length = _length;
		type = _type;
	};

	@Override
    public String toString() {
    	if (type == 3)
        	return String.format("%d.%d.%d", major, minor, revision);
        else if (type ==2)
        	return String.format("%d.%d", major, minor);
        else if (type ==1)
        	return String.format("%d", major);
        else
        	return null;
    }
}

class versionComparator implements Comparator<version> {
    @Override
    public int compare(version a, version b) {
    	if (a.major > b.major)
    		return 1;
    	else if (a.major < b.major)
    		return -1;
    	else 
    	{
    		if (a.minor > b.minor)
    			return 1;
    		else if (a.minor < b.minor)
    			return -1;
    		else 
    		{
    			if (a.revision > b.revision)
    				return 1;
    			else if (a.revision < b.revision)
    				return -1;
    			else
    			{
    				if (a.length > b.length)
    					return 1;
    				else if (a.length < b.length)
    					return -1;
    				else
    					return 0;
    			}
    		}
    	}
    }
}

public class Answer {   
    public static String[] answer(String[] l) { 

        // Your code goes here.
        
		ArrayList<version> v = new ArrayList<version>();

		for (int i = 0; i < l.length ; i++ )
		{
			String a = l[i];
			version aa = new version(0,0,0,a.length(),0);

			if (a.indexOf(".") > 0) 
			{
				aa.major = Integer.parseInt(a.substring(0,a.indexOf(".")));
				a = a.substring(a.indexOf(".")+1);

				if (a != "")
					aa.type = 2; // major + minor ... so far

				if (a.indexOf(".") > 0) {
					aa.minor = Integer.parseInt( a.substring(0,a.indexOf(".")) );
					a = a.substring(a.indexOf(".")+1);

					if (a != "")
						aa.type = 3;	// major + minor + revision

					aa.revision = Integer.parseInt(a);

				}
				else
					aa.minor = Integer.parseInt(a);

			} else {

				aa.major = Integer.parseInt(a);
				aa.type = 1; //only major
			}
			
			v.add(aa);
		}

		Collections.sort(v, new versionComparator());

		String[] answer = new String[l.length];
		for (int i = 0; i < l.length ; i++)
			answer[i] = v.get(i).toString();
		
		return answer;

    } 
}
