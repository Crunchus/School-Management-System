import javax.rmi.CORBA.Util;
import java.io.*;
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
        String role2 = null, userName, firstName, secondName;
        System.out.println("input your first name");
        firstName=UtilScanner.scan.nextLine();
        inputNewUser.setFirstName(firstName);
        System.out.println("input your second name");
        secondName = UtilScanner.scan.nextLine();
        inputNewUser.setSecondName(secondName);
        System.out.println("input your user name");
        userName=UtilScanner.scan.nextLine();
        inputNewUser.setUserName(userName);
        System.out.println("input your password");
        inputNewUser.setPassword(UtilScanner.scan.nextLine());
        while(role2 == null){
            role2 = selectRole();
        }
        inputNewUser.setRole(role2);
        inputNewUser.setUuid();
        inputNewUser.writeNewUserIntoStorage();
        if(role2.equals("STUDENT")){
            writeStudentInfo(userName, firstName, secondName);
        }else if(role2.equals("LECTURER")){
            writeLecturerInfo(userName, firstName, secondName);
        }
        System.out.println("user creation sucessful");
    }

    private static void writeStudentInfo(String userName, String firstName, String secondName){
        try (BufferedWriter buwr = new BufferedWriter(new FileWriter("studentinfo.txt", true))) {
            buwr.write(userName + ";" + firstName + ";" + secondName + ";-;-;-;-;-;-" + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeLecturerInfo(String userName, String firstName, String secondName){
        try (BufferedWriter buwr = new BufferedWriter(new FileWriter("lecturerinfo.txt", true))) {
            buwr.write(userName + ";" + firstName + ";" + secondName + ";-;-;-;-;-;-;-" + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String selectRole() {
        String role1 = null;
        System.out.println("select a role for this user");
        for (Roles r1 : Roles.values()) {
            System.out.println(r1.getRole() + ". " + r1);
        }

        String s1 = UtilScanner.scan.nextLine();
        switch (Integer.valueOf(s1)) {
            case 1:
//                inputNewUser.setRole(Roles.ADMIN.toString());
                role1 = Roles.ADMIN.toString();
                break;
            case 2:
//                inputNewUser.setRole(Roles.LECTURER.toString());
                role1 = Roles.LECTURER.toString();
                break;
            case 3:
//                inputNewUser.setRole(Roles.STUDENT.toString());
                role1 = Roles.STUDENT.toString();
                break;
            default:
                System.out.println("incorrect selection, try again");
                break;
        }
        return role1;
    }

    private static void adminSelection(String user) {
        String usr = user;
        adminAction.printMenu();
        System.out.println("Select action to perform");
        String s2 = UtilScanner.scan.nextLine();
        switch ((Integer.valueOf(s2))) {
            case 1:
                createNewUser();
                adminSelection(usr);
                break;
            case 2:
                addCourse();
                adminSelection(usr);
                break;
            case 3:
                editCourse();
                adminSelection(usr);
                break;
            case 4:
                deleteCourse();
                adminSelection(usr);
                break;
            case 5:
                viewCourseList();
                adminSelection(usr);
                break;
            case 6:
                viewStudentList();
                adminSelection(usr);
                break;
            case 7:
                System.out.println("Logged out");
                logIn();
                break;
            default:
                System.out.println("Invalid selection, try again");
                adminSelection(usr);
                break;
        }
    }

//    private static void logIn(){
//        Scanner in4 = new Scanner(System.in);
//    }

    private static void addCourse() {
        String courseName;
        System.out.println("Adding new course, a code will be automatically generated");
        addCourse.setCode();
        System.out.println("Input course name");
        courseName = UtilScanner.scan.nextLine();
        addCourse.setTitle(courseName);
        System.out.println("Input course description");
        addCourse.setDescription(UtilScanner.scan.nextLine());
        System.out.println("Input course starting date in yyyy-mm-dd format");
        addCourse.setStartdate(UtilScanner.scan.nextLine());
        System.out.println("Input course credit ammount");
        addCourse.setCredits(UtilScanner.scan.nextLine());
        System.out.println("Select a lecturer ID from the list");
        String idid = setLecturerIdForCourse();
        while (idid == null) {
            idid = setLecturerIdForCourse();
        }
        String[] split = idid.split(";");
        addCourse.setLecturerId(split[0]);
//        addCourse.setLecturerId(setLecturerIdForCourse());
//        Path lecturerPath = Paths.get("D:/IdeaProjects/SchoolManagementSystem", "lecturerinfo.txt");
//        try {
//            String oldLine= "", newLine= "", line1;
//            String[] split1;
//            List<String> lecturerInfo = new ArrayList<String>(Files.readAllLines(lecturerPath, StandardCharsets.UTF_8));
//            for (String ltr : lecturerInfo) {
//                line1 = ltr;
//                oldLine = ltr;
//                split1 = line1.split(";");
//                if(split[1] == split1[0]){
//                    if(split1[9] == "-"){
//                        split1[9]=courseName;
//                    } else {
//                        split1[9] = split1[9] + "," + courseName;
//                    }
//                    newLine = split1[0] + ";" + split1[1] + ";" + split1[2] + ";" +split1[3] + ";" +split1[4] + ";" +split1[5] + ";" +split1[6] + ";" +split1[7] + ";" +split1[8] + ";" +split1[9];
//                    break;
//                }
//            }
//            for (int i = 0; i < lecturerInfo.size(); i++) {
//                if (lecturerInfo.get(i).equals(oldLine)) {
//                    lecturerInfo.set(i, newLine);
//                    break;
//                }
//            }
//            Files.write(lecturerPath, lecturerInfo, StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        addCourse.writeNewCourseIntoStorage();
    }

    public static String setLecturerIdForCourse() {
        String idToSet = null;
        int counter = 0;
        Path userPath = Paths.get("D:/IdeaProjects/SchoolManagementSystem", "users.txt");
        try {
            List<String> users = new ArrayList<String>(Files.readAllLines(userPath, StandardCharsets.UTF_8));
            List<String> lecturers = new ArrayList<String>();
            System.out.println("Select a lecturer from the list");
            for (String usr : users) {
                String line = usr;
                String[] split = line.split(";");
                if (split[0].equals("LECTURER")) {
                    counter++;
                    lecturers.add(split[5] + ";" + split[3]);
                    System.out.println(counter + ". " + split[1] + " " + split[2]);
                }
            }
            String select = UtilScanner.scan.nextLine();
            Integer sl = (Integer.valueOf(select) - 1);
            if(Integer.valueOf(select) <= counter && Integer.valueOf(select) >= 1){
                idToSet = lecturers.get(sl);
            } else {
                System.out.println("Incorrect selection");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return idToSet;
    }

    public static void editCourse() {
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
            String selection = UtilScanner.scan.nextLine();
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
        String selection1 = UtilScanner.scan.nextLine();
        switch (Integer.valueOf(selection1)) {
            case 1:
                System.out.println("Input new name for course");
                split1[1] = UtilScanner.scan.nextLine();
//                newLine1 = split1[0] + ";" + split1[1] + ";" + split1[2] + ";" + split1[3] + ";" + split1[4] + ";" + split1[5];
                break;
            case 2:
                System.out.println("Input new description for course");
                split1[2] = UtilScanner.scan.nextLine();
                newLine1 = split1[0] + ";" + split1[1] + ";" + split1[2] + ";" + split1[3] + ";" + split1[4] + ";" + split1[5];
                break;
            case 3:
                System.out.println("Input new starting date for course");
                split1[3] = UtilScanner.scan.nextLine();
//                newLine1 = split1[0] + ";" + split1[1] + ";" + split1[2] + ";" + split1[3] + ";" + split1[4] + ";" + split1[5];
                break;
            case 4:
                System.out.println("Input new credit for course");
                split1[4] = UtilScanner.scan.nextLine();
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
            String selection = UtilScanner.scan.nextLine();
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
        String un, pw, pwcheck = null, uncheck, role = "";
        Path userPath = Paths.get("D:/IdeaProjects/SchoolManagementSystem", "users.txt");
        try {
            List<String> users = new ArrayList<String>(Files.readAllLines(userPath, StandardCharsets.UTF_8));
            System.out.println("Enter username:");
            un = UtilScanner.scan.nextLine();
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
            pw = UtilScanner.scan.nextLine();
            if (pwcheck == null) {
                System.out.println("incorrect username or password");
                logIn();
            } else if (pw.equals(pwcheck) && role.equals("STUDENT")) {
                System.out.println("Sucessfully loged in");
                studentSelection(un);
            } else if (pw.equals(pwcheck) && role.equals("LECTURER")) {
                System.out.println("Sucessfully loged in");
                lecturerSelection(un);
            } else if (pw.equals(pwcheck) && role.equals("ADMIN")) {
                System.out.println("Sucessfully loged in");
                adminSelection(un);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void lecturerSelection(String user) {
        String usr = user;
        System.out.println("Select action to perform");
        System.out.println("Select action to perform");
    }

    private static void studentSelection(String user) {
        String usr = user;
        System.out.println("Select action to perform");
        System.out.println("1. Edit personal information" +
                "\n2. View course list" +
                "\n3. View courses I am registered for" +
                "\n4. Register for a course" +
                "\n5. Log out");

        String select = UtilScanner.scan.nextLine();

        switch ((Integer.valueOf(select))) {
            case 1:
                editInfoStudent();
                studentSelection(usr);
                break;
            case 2:
                viewCourseList();
                studentSelection(usr);
                break;
            case 3:
//                registeredCourses();
                studentSelection(usr);
                break;
            case 4:
//                registerForACourse();
                studentSelection(usr);
                break;
            case 5:
                System.out.println("Logged out");
                logIn();
                break;
            default:
                System.out.println("Invalid selection, try again");
                studentSelection(usr);
                break;
        }


    }

    private static void editInfoStudent() {
        Integer counter = 0;
        Path studentPath = Paths.get("D:/IdeaProjects/SchoolManagementSystem", "courses.txt");
        try {
            List<String> studentInfo = new ArrayList<String>(Files.readAllLines(studentPath, StandardCharsets.UTF_8));
            System.out.println("Select field to edit");
            for (String std : studentInfo) {
                counter++;
                String line = std;
                String[] split = line.split(";");
                System.out.println(counter + ". " + split[1]);
            }
            System.out.println((counter + 1) + ". Done");
            String selection = UtilScanner.scan.nextLine();
            Integer slc = Integer.valueOf(selection);
            slc -= 1;
            String oldLine, newLine;
            if (Integer.valueOf(selection) <= counter && Integer.valueOf(selection) >= 1) {
                oldLine = studentInfo.get(slc);
                newLine = oldLine;
                newLine = partOfCourseToEdit(slc, oldLine);
                for (int i = 0; i < studentInfo.size(); i++) {
                    if (studentInfo.get(i).equals(oldLine)) {
                        studentInfo.set(i, newLine);
                        break;
                    }
                }
                Files.write(studentPath, studentInfo, StandardCharsets.UTF_8);
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


