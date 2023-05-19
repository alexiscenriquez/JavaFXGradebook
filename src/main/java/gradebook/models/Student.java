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

    public Student(Integer id, String fName, String lName) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
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
        return
                String.valueOf(id) +'\t'+
                 fName + ' ' +
                lName + '\t'


                ;
    }
}
