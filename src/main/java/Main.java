import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

    private static newUser inputNewUser = new newUser();
    private static Admin adminAction = new Admin();
    private static newCourse addCourse = new newCourse();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("This is a first time log in");
        System.out.println("Input username and password");
        if (adminAction.adminLogIn(in.nextLine(), in.nextLine()) == false) {
            System.out.println("Input username and password");
            adminAction.adminLogIn(in.nextLine(), in.nextLine());
        }
//      logIn();
        adminSelection();
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
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                System.out.println("Not yet implemented");
                adminSelection();
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
        setLecturerIdForCourse();
        addCourse.writeNewCourseIntoStorage();
    }

    public static void setLecturerIdForCourse() {
        Scanner in6 = new Scanner(System.in);
        int counter = 1;
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(";");
                System.out.println(counter + ". " + split[1] + " " + split[2] + " " + split[5]);
                counter++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedReader br1 = new BufferedReader(new FileReader("users.txt"))) {
            String idSelection = in6.nextLine();
            String lineX = "";
            if (Integer.valueOf(idSelection) <= counter && Integer.valueOf(idSelection) >= 1) {
                for (int i = 1; i <= Integer.valueOf(idSelection); i++) {
                    lineX=br1.readLine();
                }
                String[] splitLineX = lineX.split(";");
                addCourse.setLecturerId(splitLineX[5]);
            } else {
                System.out.println("Incorrect selection, try again");
                setLecturerIdForCourse();
            }

        } catch (Exception e) {
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


