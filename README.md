# Course Scheduler
CourseScheduler is a program which takes as input a course catalog and prints
a valid schedule to stdout. A course cannot be scheduled before its prerequisite
courses have been satisfied.


## Requirements
This program was written in Java (build 9u181) on macOS and uses a bash script to run.
A public json library was used to read json input. (https://github.com/stleary/JSON-java)
This .jar must be included in the base folder. This .jar is provided and included in the bash script.


## Input
The format of the json is as follows:

```
[{
    "name" : "coursename",
    "prerequisites" : ["Course 1", "Course2"]
}]
```

There are some test json files as well as examples provided in the test and example folder respectively.


## Running the scheduler
To run the program, open terminal, navigate to the CourseScheduler folder and type
```
$ ./scheduler FILENAME
```

Where 'FILENAME' is the filename/path to the json containing courses you'd like to schedule.

To try an example, enter the following command from within the CourseScheduler folder:
```
$ ./scheduler test/math.json
```

This will print out:
```
Algebra 1
Geometry
Combinatorics
Algebra 2
Pre Calculus
Calculus I
Statistics
```


## Design
Course scheduling is a graph problem. The courses were scheduled using Kahn's algorithm.
There is a Course class which holds the class name and prerequisites. This class was written so that
the scheduler could be flexible. For example, if you could only take a class based on your grade or
other factors, the code could be adapted easily enough to check for grade level.

To schedule courses, each course with 0 prerequisites is first entered into a queue. Then the course
is popped from the queue, added to the schedule and then checked to see if it is a prerequisite for
other courses. If the popped course is a prerequisite, that course will be removed from other course
prerequisites to signify that it has been satisfied. After, there is a check to see if the other course
has 0 prerequisites which would mean that it can be scheduled. So if it no longer has any prerequisites,
add this course to the queue. This will repeat until all off the courses have been scheduled.


## Performance Analysis
```
O(N^2)
```
Where N is the list of courses. For each course, we add to the queue, we loop through all of the
courses and check whether the Course popped from the queue is a prerequisite of another course in
the catalog.


