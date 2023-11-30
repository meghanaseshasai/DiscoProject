package CourseAssign;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseAssign {

	public static void main(String[] args) {

		//Create course optimiser class. it reads files and optimizes the course assignment
		CourseOptimizer co = new CourseOptimizer ();
		
		//Read file and build faculty list with course preferences.
		co.readFile("./facultypref.csv");
		
		//Generate a sorted list of courses.
		co.generateCourseList();
	
		//System.out.println(co);
		
		//Do optimized assignment
		co.doOptimizedAssignment();
		
		//Print Results of the best assignment
		co.printResults ();
		
	}

	

	
}


