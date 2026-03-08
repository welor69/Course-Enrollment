import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    static HashMap<String, HashSet<String>> prereqs = new HashMap<>();
    static HashMap<String, HashSet<String>> completed = new HashMap<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        printHelp();

        while (true) {

            System.out.print("Your command: ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String command = parts[0].toUpperCase();

            switch (command) {

                case "HELP":
                    printHelp();
                    break;

                case "ADD_COURSE":
                    if (parts.length < 2) {
                        System.out.println("Usage: ADD_COURSE <course>");
                        break;
                    }
                    addCourse(parts[1]);
                    break;

                case "ADD_PREREQ":
                    if (parts.length < 3) {
                        System.out.println("Usage: ADD_PREREQ <course> <prereq>");
                        break;
                    }
                    addPrereq(parts[1], parts[2]);
                    break;

                case "PREREQS":
                    if (parts.length < 2) {
                        System.out.println("Usage: PREREQS <course>");
                        break;
                    }
                    showPrereqs(parts[1]);
                    break;

                case "COMPLETE":
                    if (parts.length < 3) {
                        System.out.println("Usage: COMPLETE <student> <course>");
                        break;
                    }
                    completeCourse(parts[1], parts[2]);
                    break;

                case "DONE":
                    if (parts.length < 2) {
                        System.out.println("Usage: DONE <student>");
                        break;
                    }
                    showCompleted(parts[1]);
                    break;

                case "CAN_TAKE":
                    if (parts.length < 3) {
                        System.out.println("Usage: CAN_TAKE <student> <course>");
                        break;
                    }
                    canTake(parts[1], parts[2]);
                    break;

                case "EXIT":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Unknown command. Type HELP.");
            }
        }
    }

    static void printHelp() {
        System.out.println("Course Enrollment Planner – Commands:");
        System.out.println("HELP");
        System.out.println("ADD_COURSE <course>");
        System.out.println("ADD_PREREQ <course> <prereq>");
        System.out.println("PREREQS <course>");
        System.out.println("COMPLETE <student> <course>");
        System.out.println("DONE <student>");
        System.out.println("CAN_TAKE <student> <course>");
        System.out.println("EXIT");
    }

    static void addCourse(String course) {

        prereqs.putIfAbsent(course, new HashSet<>());

        System.out.println("Added course: " + course);
    }

    static void addPrereq(String course, String prereq) {

        if (course.equals(prereq)) {
            System.out.println("A course cannot be its own prerequisite.");
            return;
        }

        prereqs.putIfAbsent(course, new HashSet<>());
        prereqs.putIfAbsent(prereq, new HashSet<>());

        prereqs.get(course).add(prereq);

        System.out.println("Added prereq: " + prereq + " -> " + course);
    }

    static void showPrereqs(String course) {

        if (!prereqs.containsKey(course)) {
            System.out.println("Course not found");
            return;
        }

        System.out.println("Prereqs for " + course + ": " + prereqs.get(course));
    }

    static void completeCourse(String student, String course) {

        completed.putIfAbsent(student, new HashSet<>());

        completed.get(student).add(course);

        System.out.println(student + " completed " + course);
    }

    static void showCompleted(String student) {

        if (!completed.containsKey(student)) {
            System.out.println("No record");
            return;
        }

        System.out.println(student + " completed courses: " + completed.get(student));
    }

    static void canTake(String student, String course) {

        HashSet<String> coursePrereqs = prereqs.get(course);

        if (coursePrereqs == null || coursePrereqs.isEmpty()) {
            System.out.println("YES");
            return;
        }

        HashSet<String> studentCompleted = completed.getOrDefault(student, new HashSet<>());

        for (String p : coursePrereqs) {
            if (!studentCompleted.contains(p)) {
                System.out.println("NO");
                return;
            }
        }

        System.out.println("YES");
    }
}