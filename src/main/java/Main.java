import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static newUser inputNewUser = new newUser();
    private static Admin adminAction = new Admin();
    private static newCourse addCourse = new newCourse();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
//        System.out.println("This is a first time log in");
//        System.out.println("Input username and password");
//        if (adminAction.adminLogIn(in.nextLine(), in.nextLine()) == false) {
//            System.out.println("Input username and password");
//            adminAction.adminLogIn(in.nextLine(), in.nextLine());
//        }
        logIn();
//      adminSelection();
//      testerino();
    }

    private static void createNewUser() {
        Scanner in1 = new Scanner(System.in);
        System.out.println("input your first name");
        inputNewUser.setFirstName(in1.nextLine());
        System.out.println("input your second name");
        inputNewUser.setSecondName(in1.nextLine());
        System.out.println("input your user name");
        inputNewUser.setUserName(in1.nextLine());
        System.out.println("input your password");
        inputNewUser.setPassword(in1.nextLine());
        selectRole();
        inputNewUser.setUuid();
        inputNewUser.writeNewUserIntoStorage();
        System.out.println("user creation sucessful");
    }

    private static void selectRole() {
        Scanner in2 = new Scanner(System.in);
        System.out.println("select a role for this user");
        for (Roles r1 : Roles.values()) {
            System.out.println(r1.getRole() + ". " + r1);
        }

        String s1 = in2.nextLine();
        switch (Integer.valueOf(s1)) {
            case 1:
                inputNewUser.setRole(Roles.ADMIN.toString());
                break;
            case 2:
                inputNewUser.setRole(Roles.LECTURER.toString());
                break;
            case 3:
                inputNewUser.setRole(Roles.STUDENT.toString());
                break;
            default:
                System.out.println("incorrect selection, try again");
                selectRole();
                break;
        }
    }

    private static void adminSelection() {
        Scanner in3 = new Scanner(System.in);
        adminAction.printMenu();
        System.out.println("Select action to perform");
        String s2 = in3.nextLine();
        switch ((Integer.valueOf(s2))) {
            case 1:
                createNewUser();
                adminSelection();
                break;
            case 2:
                addCourse();
                adminSelection();
                break;
            case 3:
                editCourse();
                adminSelection();
                break;
            case 4:
                deleteCourse();
                adminSelection();
                break;
            case 5:
                viewCourseList();
                adminSelection();
                break;
            case 6:
                viewStudentList();
                adminSelection();
                break;
            case 7:
                System.out.println("Logged out");
                logIn();
                break;
            default:
                System.out.println("Invalid selection, try again");
                adminSelection();
                break;
        }
    }

//    private static void logIn(){
//        Scanner in4 = new Scanner(System.in);
//    }

    private static void addCourse() {
        Scanner in5 = new Scanner(System.in);
        System.out.println("Adding new course, a code will be automatically generated");
        addCourse.setCode();
        System.out.println("Input course name");
        addCourse.setTitle(in5.nextLine());
        System.out.println("Input course description");
        addCourse.setDescription(in5.nextLine());
        System.out.println("Input course starting date in yyyy-mm-dd format");
        addCourse.setStartdate(in5.nextLine());
        System.out.println("Input course credit ammount");
        addCourse.setCredits(in5.nextLine());
        System.out.println("Select a lecturer ID from the list");
        String idid = setLecturerIdForCourse();
        while (idid == null) {
            idid = setLecturerIdForCourse();
        }
        addCourse.setLecturerId(idid);
//        addCourse.setLecturerId(setLecturerIdForCourse());
        addCourse.writeNewCourseIntoStorage();
    }

    public static String setLecturerIdForCourse() {
        Scanner in6 = new Scanner(System.in);
        String idToSet = null;
        int counter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                counter++;
                String[] split = line.split(";");
                System.out.println(counter + ". " + split[1] + " " + split[2] + " " + split[5]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedReader br1 = new BufferedReader(new FileReader("users.txt"))) {
            String idSelection = in6.nextLine();
            String lineX = "";
            if (Integer.valueOf(idSelection) <= counter && Integer.valueOf(idSelection) >= 1) {
                for (int i = 1; i <= Integer.valueOf(idSelection); i++) {
                    lineX = br1.readLine();
                }
                String[] splitLineX = lineX.split(";");
                idToSet = splitLineX[5];
            } else {
                System.out.println("Incorrect selection, try again");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return idToSet;
    }

    public static void editCourse() {

        Scanner in7 = new Scanner(System.in);
        Integer counter = 0;
        Path coursePath = Paths.get("D:/IdeaProjects/SchoolManagementSystem", "courses.txt");
        try {
            List<String> courses = new ArrayList<String>(Files.readAllLines(coursePath, StandardCharsets.UTF_8));
            System.out.println("Select course to edit");
            for (String crs : courses) {
                counter++;
                String line = crs;
                String[] split = line.split(";");
                System.out.println(counter + ". " + split[1]);
            }
            System.out.println((counter + 1) + ". Done");
            String selection = in7.nextLine();
            Integer slc = Integer.valueOf(selection);
            slc -= 1;
            String oldLine, newLine;
            if (Integer.valueOf(selection) <= counter && Integer.valueOf(selection) >= 1) {
                oldLine = courses.get(slc);
                newLine = oldLine;
                newLine = partOfCourseToEdit(slc, oldLine);
                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).equals(oldLine)) {
                        courses.set(i, newLine);
                        break;
                    }
                }
                Files.write(coursePath, courses, StandardCharsets.UTF_8);
                editCourse();
            } else if (Integer.valueOf(selection) == (counter + 1)) {
                System.out.println("Done editing");
            } else {
                System.out.println("Incorrect selection, try again");
                editCourse();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String partOfCourseToEdit(int number, String givenLine) {
        Scanner in8 = new Scanner(System.in);
        String newLine1 = null;
        String line1 = givenLine;
        String[] split1;
        System.out.println("Choose which part of the course to edit");
        split1 = line1.split(";");
        System.out.println("1. Name: " + split1[1] +
                "\n2. Description: " + split1[2] +
                "\n3. Starting date: " + split1[3] +
                "\n4. Credit ammount: " + split1[4] +
                "\n5. Lecturer ID: " + split1[5] +
                "\n6. Done");
        String selection1 = in8.nextLine();
        switch (Integer.valueOf(selection1)) {
            case 1:
                System.out.println("Input new name for course");
                split1[1] = in8.nextLine();
//                newLine1 = split1[0] + ";" + split1[1] + ";" + split1[2] + ";" + split1[3] + ";" + split1[4] + ";" + split1[5];
                break;
            case 2:
                System.out.println("Input new description for course");
                split1[2] = in8.nextLine();
                newLine1 = split1[0] + ";" + split1[1] + ";" + split1[2] + ";" + split1[3] + ";" + split1[4] + ";" + split1[5];
                break;
            case 3:
                System.out.println("Input new starting date for course");
                split1[3] = in8.nextLine();
//                newLine1 = split1[0] + ";" + split1[1] + ";" + split1[2] + ";" + split1[3] + ";" + split1[4] + ";" + split1[5];
                break;
            case 4:
                System.out.println("Input new credit for course");
                split1[4] = in8.nextLine();
//                newLine1 = split1[0] + ";" + split1[1] + ";" + split1[2] + ";" + split1[3] + ";" + split1[4] + ";" + split1[5];
                break;
            case 5:
                System.out.println("Select a lecturer");
                String idid = setLecturerIdForCourse();
                while (idid == null) {
                    idid = setLecturerIdForCourse();
                }
                split1[5] = idid;
//                newLine1 = split1[0] + ";" + split1[1] + ";" + split1[2] + ";" + split1[3] + ";" + split1[4] + ";" + split1[5];
                break;
            case 6:
                System.out.println("Done editing");
                break;
            default:
                System.out.println("Incorrect selection");
                break;
        }
        newLine1 = split1[0] + ";" + split1[1] + ";" + split1[2] + ";" + split1[3] + ";" + split1[4] + ";" + split1[5];
        return newLine1;
    }

    private static void deleteCourse() {
        Scanner in9 = new Scanner(System.in);
        Path coursePath = Paths.get("D:/IdeaProjects/SchoolManagementSystem", "courses.txt");
        int counter = 0;
        try {
            List<String> courses = new ArrayList<String>(Files.readAllLines(coursePath, StandardCharsets.UTF_8));
            System.out.println("Select course delete");
            for (String crs : courses) {
                counter++;
                String line = crs;
                String[] split = line.split(";");
                System.out.println(counter + ". " + split[1]);
            }
            System.out.println((counter + 1) + ". Cancel");
            String selection = in9.nextLine();
            int slc = Integer.valueOf(selection) - 1;
            if (Integer.valueOf(selection) <= counter && Integer.valueOf(selection) >= 1) {
                courses.remove(slc);
                Files.write(coursePath, courses, StandardCharsets.UTF_8);
            } else if (Integer.valueOf(selection) == (counter + 1)) {
                System.out.println("Nothing deleted");
            } else {
                System.out.println("Incorrect selection, try again");
                deleteCourse();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void viewCourseList() {
        int counter = 0;
        Path coursePath = Paths.get("D:/IdeaProjects/SchoolManagementSystem", "courses.txt");
        try {
            List<String> courses = new ArrayList<String>(Files.readAllLines(coursePath, StandardCharsets.UTF_8));
            System.out.println("List of courses:");
            for (String crs : courses) {
                counter++;
                String line = crs;
                String[] split = line.split(";");
                System.out.println(counter + ". " + split[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void viewStudentList() {
        int counter = 0;
        Path studentPath = Paths.get("D:/IdeaProjects/SchoolManagementSystem", "users.txt");
        try {
            List<String> students = new ArrayList<String>(Files.readAllLines(studentPath, StandardCharsets.UTF_8));
            System.out.println("List of Students:");
            for (String crs : students) {
                String line = crs;
                String[] split = line.split(";");
                if (split[0].equals("STUDENT")) {
                    counter++;
                    System.out.println(counter + ". " + split[1] + " " + split[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void logIn() {
        Scanner in10 = new Scanner(System.in);
        String un, pw, pwcheck = null, uncheck, role = "";
        Path userPath = Paths.get("D:/IdeaProjects/SchoolManagementSystem", "users.txt");
        try {
            List<String> users = new ArrayList<String>(Files.readAllLines(userPath, StandardCharsets.UTF_8));
            System.out.println("Enter username:");
            un = in10.nextLine();
            if (un.equals(adminAction.getName())) {
                pwcheck = adminAction.getPw();
                role = "ADMIN";
            } else {
                for (String usr : users) {
                    String line = usr;
                    String[] split = line.split(";");
                    uncheck = split[3];
                    if (uncheck.equals(un)) {
                        pwcheck = split[4];
                        role = split[0];
                        break;
                    }
                }
            }
            System.out.println("Enter password:");
            pw = in10.nextLine();
            if (pwcheck == null) {
                System.out.println("incorrect username or password");
                logIn();
            } else if (pw.equals(pwcheck) && role.equals("STUDENT")) {
                System.out.println("Sucessfully loged in");
                studentSelection();
            } else if (pw.equals(pwcheck) && role.equals("LECTURER")) {
                System.out.println("Sucessfully loged in");
                lecturerSelection();
            } else if (pw.equals(pwcheck) && role.equals("ADMIN")) {
                System.out.println("Sucessfully loged in");
                adminSelection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void lecturerSelection() {
        System.out.println("Lecturer menu");
    }

    private static void studentSelection() {
        System.out.println("Student menu");
    }

//        private static void testerino() {
//            int counter = 1;
//            try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
//                String line;
//                while ((line = br.readLine()) != null) {
//                    String[] split = line.split(";");
//                    System.out.println(counter + ". " + split[1] + " " + split[2] + " " + split[5]);
//                    counter++;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
}


