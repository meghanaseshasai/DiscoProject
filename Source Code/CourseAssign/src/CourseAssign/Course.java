package CourseAssign;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Course {
	String courseName;
	int popularity;
	String faculty1 = "";
	String faculty2 = "";
	
	Course (String courseName, int popularity) {
		this.courseName = courseName;
		this.popularity = popularity;
	}
	
	public void assignHalfFaculty (String facultyName) {
		if (faculty1.isEmpty()) {
			faculty1 = facultyName;
		}
		else if (faculty2.isEmpty()) {
			faculty2 = facultyName;
		}
	}

	public void unAssignHalfFaculty (String facultyName) {
		if (faculty1 == facultyName) {
			faculty2 = faculty1;
			faculty2 = "";
		}
		else if (faculty2.isEmpty()) {
			faculty2 = "";
		}
	}
	
	boolean isAssigned () {
		if ( (faculty1.isEmpty() == false) && (faculty2.isEmpty() == false) ) {
			return true;
		}
		else
			return false;
	}
	
	Course getClone () {
		Course newCourse = new Course (courseName, popularity);
		return newCourse;
	}
	
	public String toString () {
		return courseName + "-" + popularity;
	}
}
