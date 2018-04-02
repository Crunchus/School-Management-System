import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class newCourse implements newCourseInterface {

    private String courseCode, courseTitle, courseDescription, courseStartDate, courseCredits, courseLecturerId;


    @Override
    public void setCode() {
        this.courseCode = UUID.randomUUID().toString();
    }

    @Override
    public void setTitle(String title) {
        this.courseTitle = title;
    }

    @Override
    public void setDescription(String description) {
        this.courseDescription = description;
    }

    @Override
    public void setStartdate(String startdate) {
        this.courseStartDate = startdate;
    }

    @Override
    public void setCredits(String credits) {
        this.courseCredits = credits;
    }

    @Override
    public void setLecturerId(String lecturerId) {
        this.courseLecturerId = lecturerId;
    }

    public void writeNewCourseIntoStorage(){
        try (BufferedWriter buwr = new BufferedWriter(new FileWriter("courses.txt", true))) {
            buwr.write(courseCode+";"+ courseTitle+";"+ courseDescription+";"+ courseStartDate+";"+ courseCredits +";"+ courseLecturerId +"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
