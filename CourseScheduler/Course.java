package CourseScheduler;

import java.util.HashSet;
import java.util.*;

public class Course {

  public String classname;
  public HashSet<String> prerequisites;

  Course(String classname, List<String> prereqs) {
    this.classname = classname;
    this.prerequisites = new HashSet<String>();

    for (String pr : prereqs) {
      prerequisites.add(pr);
    }
  }

}

