package gradebook.models;

public class Student {
    private String fName;
    private String lName;
    private Integer id;
    private Double grade;

    public Student(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
    }

    public Student(String fName, String lName, Integer id, Double grade) {
        this.fName = fName;
        this.lName = lName;
        this.id = id;
        this.grade = grade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", id=" + id +
                ", grade=" + grade +
                '}';
    }
}
