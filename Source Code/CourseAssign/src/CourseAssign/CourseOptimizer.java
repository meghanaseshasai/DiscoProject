package CourseAssign;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseOptimizer {

	List <Faculty> facultyList = new ArrayList<Faculty> ();
	List <Course> courseList = new ArrayList<Course> ();
	List <FacultyCourseAssignment> facultyCourseAssignmentList = new ArrayList<FacultyCourseAssignment> ();
	
	CourseOptimizer () {
		//Empty constructor
	}
	
	public void readFile (String filePath) {
		
		//Open file to read it line by line. If file open fails (file not there, no permissions etc, the it throws exception).
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String line;
            // Skip the header row
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String facultyName = parts[0];
                String category = parts[1];
                List<String> coursePreferences = new ArrayList<>();
                
                for (int i = 2; i < parts.length; i++) {
                    coursePreferences.add(parts[i]);
                }

                Faculty faculty = new Faculty(facultyName, category, coursePreferences);
                facultyList.add(faculty);                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void generateCourseList () {
		
		//We will go through the faculty list and read the course names and find their popularity.
		//We will also store list of courses for later use.
		
		//Lets have a Map for storing course names with popularity.
		//We will use course name as key and popularity as value.
		Map<String, Integer> coursePopularityDictionary = new HashMap <String, Integer> ();
		
		for (Faculty faculty : facultyList) {
			List<String> coursePreferences = faculty.coursePreferences;
			for (String course : coursePreferences) {
				//Check if Map already has course. 
				if (coursePopularityDictionary.containsKey(course)) {
					//Means some faulty already wants this. Increase its popularity.
					int currentPopularity =  coursePopularityDictionary.get(course);
					currentPopularity++;
					
					//Put it back into the map.
					coursePopularityDictionary.put(course, currentPopularity);
				}
				else {
					//Means the course is being seen for the 1st time. Add it to map with popularity of 1.
					coursePopularityDictionary.put(course, 1);
				}
			}
		}
		
		//Now that we have a map of courses with their popularity, lets construct course class and add it to course List.
		Set<String> courseNamesSet = coursePopularityDictionary.keySet();
		for (String courseName : courseNamesSet) {
			//Get popularity
			int coursePopularity =  coursePopularityDictionary.get(courseName);
			
			//Construct course class.
			Course course = new Course (courseName, coursePopularity);
			
			//Add it to course list.
			courseList.add(course);
		}
		
		//Now sort the Course list in ascending order of popularity
		Collections.sort(courseList, Comparator.comparingInt(course -> course.popularity));

	}

	public void generateRandomAssignment () {

		//Lets create a copy of the facultyList and Course List
		List <Faculty> facultyListLocal = new ArrayList<Faculty> ();
		List <Course> courseListLocal = new ArrayList<Course> ();

		for (Faculty faculty : facultyList) {
			Faculty facultyCopy = faculty.getClone();
			facultyListLocal.add(facultyCopy);
		}
		
		for (Course course : courseList) {
			Course courseCopy = course.getClone();
			courseListLocal.add(courseCopy);
		}
		
		//Now sort the Course list in ascending order of popularity
		Collections.sort(courseListLocal, Comparator.comparingInt(course -> course.popularity));
		
		//change the order of faculty randomly.
		Collections.shuffle(facultyListLocal);
		
		//Now iterate over the courses and assign to faculty
		for (int j = 0; j < courseListLocal.size(); j++) {
			Course course = courseListLocal.get(j);
		
			//For this course, iterate over the faculty list. 
			//If the faculty is free and prefers the course, assign it.
			
			boolean assigned = false;
			for (int i = 0; i < facultyListLocal.size(); i++) {
				Faculty faculty = facultyListLocal.get(i);
				
				//Check if faculty prefers course. if not check next
				if (faculty.prefersCourse(course.courseName) == false)
					continue;
				
				//Check if the faculty is free. If not go to next.
				double availability = faculty.getAvailability ();
				if (availability == 0.0) 
					continue;	//No availability. Check next.
				
				if (availability >= 1.0) {
					//Fully assign the course to faculty
					faculty.assignHalfCourse(course.courseName);
					faculty.assignHalfCourse(course.courseName);
					
					//Fully Assign faculty to course
					course.assignHalfFaculty(faculty.name);
					course.assignHalfFaculty(faculty.name);
					
					//Update the lists with these assigned courses and faculty.
					facultyListLocal.set(i, faculty);
					courseListLocal.set(j, course);
					assigned = true;
				}
				
				if (availability == 0.5) {
					//Partial assign. But only if another faculty can take this course.
					//Search for 2nd faculty for half assignment.
					for (int k = i+1; k < facultyListLocal.size(); k++) {
						Faculty additionalfaculty = facultyListLocal.get(k);
						//Check if this faculty is free and prefers this course.
						if (additionalfaculty.prefersCourse(course.courseName) && (additionalfaculty.getAvailability () > 0)) {
							//Yay!! Found additional faculty.
							//Fully assign the course to faculty and additionalfaculty together.
							faculty.assignHalfCourse(course.courseName);
							additionalfaculty.assignHalfCourse(course.courseName);
							
							//Fully Assign faculty to course
							course.assignHalfFaculty(faculty.name);
							course.assignHalfFaculty(additionalfaculty.name);

							//Update the lists with these assigned courses and faculty.
							facultyListLocal.set(i, faculty);
							facultyListLocal.set(k, additionalfaculty);
							courseListLocal.set(j, course);
							assigned = true;
							break;
						}
					}
				}
				
				if (assigned) {
					break;
				}
			}	
		}

		//System.out.println (facultyListLocal);
		//System.out.println (courseListLocal);
		
		FacultyCourseAssignment facultyCourseAssignment = new FacultyCourseAssignment (facultyListLocal, courseListLocal);
		this.facultyCourseAssignmentList.add(facultyCourseAssignment);
		
		//Now sort the Course assignment list in descending order of order of course assignments
		Collections.sort(facultyCourseAssignmentList);
	}
	
	public void doOptimizedAssignment () {
		int maxAttempts = 100;
		
		for (int i = 0; i < maxAttempts; i++) {
			generateRandomAssignment ();
		}
	}
	
	
	public String toString () {
		return facultyList + "\n" + courseList;
	}
	
	

	public void printResults () {
		FacultyCourseAssignment bestAssignment = facultyCourseAssignmentList.get(0);
		
		System.out.println ("Successfully assigned " + bestAssignment.assignedCourses + " / " + courseList.size() + " courses");
		System.out.println (bestAssignment);
	}
	
}
