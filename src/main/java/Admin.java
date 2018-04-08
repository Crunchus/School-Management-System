public class Admin {
    private final String name = "admin";
    private final String pw = "admin";

    public boolean adminLogIn(String username, String password) {
        if (username.equals(this.name) && password.equals(this.pw)) {
            System.out.println("Loged in as admin");
            return true;
        } else {
            System.out.println("Incorrect username or password, try again");
            return false;
        }
    }

    public void printMenu() {
        System.out.println("Select your action");
        System.out.println("1. Add new user\n2. Add course\n3. Edit course\n4. Delete course\n5. View course list\n6. View student list\n7. Log out");
    }

    public String getName() {
        return name;
    }

    public String getPw() {
        return pw;
    }
}
