package CourseScheduler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileReader;

public class CourseScheduler {

  public static List<Course> courses = new ArrayList<Course>();

  public static void main(String[] args) throws Exception {
    try {
      if (args.length < 1) throw new Exception("Filename not provided");

      String scheduleJSON = readJSON(args[0]);
      addCourses(scheduleJSON);
      scheduleCourses();

    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      System.exit(0);
    }
  }

  public static void addCourses(String jsonString) throws Exception {
    JSONArray jsonArr = new JSONArray(jsonString);

    for (int i = 0; i < jsonArr.length(); i++) {
      JSONObject jsonObj = (JSONObject) jsonArr.get(i);

      if (!jsonObj.has("name")) {
        throw new Exception("json structure must contain a name for a course.");
      }

      String classname = jsonObj.getString("name");

      if (!jsonObj.has("prerequisites")) {
        throw new Exception("json structure must contain an array of prerequisites for a given course.");
      }

      JSONArray prJsonArr = jsonObj.getJSONArray("prerequisites");
      List<String> prerequisites = new ArrayList<>();

      for (int j = 0; j < prJsonArr.length(); j++) {
        prerequisites.add(prJsonArr.getString(j));
      }

      courses.add(new Course(classname, prerequisites));
    }
  }

  public static String readJSON(String path) throws Exception {
    StringBuilder result = new StringBuilder();
    BufferedReader buf = new BufferedReader(new FileReader(path));
    String line = buf.readLine();

    while (line != null) {
      result.append(line);
      line = buf.readLine();
    }

    return result.toString();
  }

  public static void scheduleCourses() throws Exception {
    List<String> schedule = new ArrayList<>();
    Queue<Course> queue = new LinkedList<>();

    // find all courses with 0 prerequisites - there has to be at least one otherwise it's an invalid catalog
    for (Course c : courses) {
      if (c.prerequisites.size() == 0) {
        queue.offer(c);
      }
    }

    if (queue.size() == 0) {
      throw new Exception("There are no courses with 0 prerequisites.");
    }

    // kahn's algorithm
    while (!queue.isEmpty() && queue.peek() != null) {
      Course course = queue.poll();
      schedule.add(course.classname);

      for (int i = 0; i < courses.size(); i++) {
        if (courses.get(i).prerequisites.contains(course.classname)) {
          courses.get(i).prerequisites.remove(course.classname);

          // schedule course if all of its prerequisites have been satisfied
          if (courses.get(i).prerequisites.size() == 0) {
            queue.offer(courses.get(i));
          }

        }
      }
    }

    if (schedule.size() != courses.size()) {
      throw new Exception("Not all courses have been scheduled due to circular dependecies or prerequisite not offered.");
    }

    for (String c : schedule) {
      System.out.println(c);
    }
  }
}