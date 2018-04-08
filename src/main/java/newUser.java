import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class newUser implements newUserInterface {

    private String firstName, secondName, password, userName, uniqueId, role;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUuid() {
        this.uniqueId = UUID.randomUUID().toString();
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void writeNewUserIntoStorage() {
        try (BufferedWriter buwr = new BufferedWriter(new FileWriter("users.txt", true))) {
            buwr.write(role + ";" + firstName + ";" + secondName + ";" + userName + ";" + password + ";" + uniqueId + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}