package CourseAssign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FacultyCourseAssignment implements Comparable<FacultyCourseAssignment> {

	public List <Faculty> facultyList = new ArrayList<Faculty> ();
	public List <Course> courseList = new ArrayList<Course> ();
	public int assignedCourses = 0;

	FacultyCourseAssignment (List <Faculty> facultyList, List <Course> courseList) {
		this.facultyList = facultyList;
		this.courseList = courseList;
		
		for (Course course : courseList) {
			if (course.isAssigned())
				assignedCourses++;
		}
	}

	@Override
    public int compareTo(FacultyCourseAssignment other)  {
		// Sort in descending order based on assignedCourses
        return Integer.compare(other.assignedCourses, this.assignedCourses);
    }
	
	public String toString () {
		String consolidatedString = "Following are the course assignments faculty wise.\n";

		Collections.sort(facultyList);
		
		for (Faculty faculty : facultyList) {
			String str = "Faculty Name : " + faculty.name + ", Category : " + faculty.category + ", Courses : ";
			str += faculty.getCoursesListForDisplay();
			str += "\n";
			consolidatedString += str;
		}
		
		return consolidatedString;
	}
}
