package CourseAssign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Faculty  implements Comparable<Faculty> {
	public String name;
	public String category;
	List <String> coursePreferences;

	//Course Assignments. Each assignment is 0.5
	String assignment1 = "";
	String assignment2 = "";
	String assignment3 = "";

	Faculty (String facultyName, String facultyCategory, List <String> facultyPreferences) {
		name = facultyName;
		category = facultyCategory;
		coursePreferences = facultyPreferences;
	}

	boolean prefersCourse (String courseName) {
		return coursePreferences.contains(courseName);			
	}

	double getAvailability () {

		//Check available slots
		int freeSlots = 0;
		if (assignment1.isEmpty()) {
			freeSlots++;
		}
		if (assignment2.isEmpty()) {
			freeSlots++;
		}
		if (assignment3.isEmpty()) {
			freeSlots++;
		}
		
		//category X1. Any one assignment makes faculty fully busy.
		if (category.equals("X1")) {
			//1 free slot is enough.
			if ( freeSlots == 3) {
				return 0.5;
			}
			else {
				return 0;
			}
		}
		
		//category X2. Two assignment makes faculty fully busy.
		if (category.equals("X2")) {
			if ( freeSlots == 3) {
				return 1.0;
			}
			else if ( freeSlots == 2) {
				return 0.5;
			}
			else {
				return 0;
			}			
		}

		//category X3. Three assignment makes faculty fully busy.
		if (category.equals("X3")) {
			if ( freeSlots == 3) {
				return 1.5;
			}
			else if ( freeSlots == 2) {
				return 1.0;
			}
			else if ( freeSlots == 1) {
				return 0.5;
			}
			else if ( freeSlots == 0) {
				return 0;
			}
		}
		
		//You are here means, faculty is of unknown category. return 0.
		return 0;
	}
	
	public void assignHalfCourse (String courseName) {

		//No need to check for availability. Already done. Just assign.
		if (assignment1.isEmpty()) {
			assignment1 = courseName;
			return;
		}
		else if (assignment2.isEmpty()) {
			assignment2 = courseName;
			return;
		}
		else if (assignment3.isEmpty()) {
			assignment3 = courseName;
			return;
		}
	}
	
	public void unAssignHalfCourse (String courseName) {

		if (assignment1.equals(courseName)) {
			assignment1 = "";
			return;
		}
		else if (assignment2.equals(courseName)) {
			assignment2 = "";
			return;
		}
		else if (assignment3.equals(courseName)) {
			assignment3 = "";
			return;
		}
	}
	
	
	Faculty getClone () {
		List <String> coursePreferencesCopy = new ArrayList <String>();
		for (String coursePref : coursePreferences) {
			coursePreferencesCopy.add(coursePref);
		}
		
		Faculty newFaculty = new Faculty (name, category, coursePreferencesCopy);
		return newFaculty;
	}
	
	public List<String> getCoursesListForDisplay () {
		//Count course assignments. We need to find if partial assignment or full.
		Set<String> uniqueCourseNamesSet = new HashSet<String> ();
		List<String> courseNamesList = new ArrayList<String> ();
		
		if (assignment1.isEmpty() == false) {
			uniqueCourseNamesSet.add(assignment1);
			courseNamesList.add(assignment1);
		}
		if (assignment2.isEmpty() == false) {
			uniqueCourseNamesSet.add(assignment2);
			courseNamesList.add(assignment2);
		}
		if (assignment2.isEmpty() == false) {
			uniqueCourseNamesSet.add(assignment3);
			courseNamesList.add(assignment3);
		}
		
		List<String> courseNamesDisplayList = new ArrayList<String> ();
		for (String courseName : uniqueCourseNamesSet) {
			if (courseName.isEmpty()) continue;
			
			int frequency = Collections.frequency(courseNamesList, courseName);
			if (frequency == 1) {
				courseNamesDisplayList.add(courseName + " - Partial");
			}
			else
			{
				courseNamesDisplayList.add(courseName + " - Full");
			}
		}

		/*
		System.out.println();
		System.out.println(this.name);
		System.out.println(this.category);
		System.out.println(coursePreferences);
		System.out.println(this.assignment1 + " - " + this.assignment2 + " - " + this.assignment3);
		System.out.println(courseNamesDisplayList);
		System.out.println();
		*/
		
		return courseNamesDisplayList;
	}
	
	public String toString () {
		List<String> courseNamesDisplayList = new ArrayList<String> ();
		courseNamesDisplayList.add(assignment1);
		courseNamesDisplayList.add(assignment2);
		courseNamesDisplayList.add(assignment3);
		
		return name + "-" + category + "-" + coursePreferences + " - " + courseNamesDisplayList;
	}
	
	@Override
    public int compareTo(Faculty other)  {
		// Sort in descending order based on assignedCourses
        return this.name.compareTo(other.name);
    }

}


