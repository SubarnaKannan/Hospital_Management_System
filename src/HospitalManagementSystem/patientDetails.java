package HospitalManagementSystem;

public class patientDetails {
    private String name;
    private int age;
    private String gender;

    public patientDetails() {
    }

    public patientDetails(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }


    public String getGender() {
        return gender;
    }

    public boolean setGender(String gender) {
        if(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase(("others")))
        this.gender = gender;
        else{
            System.out.println("Enter a valid gender!!!") ;
            return false;
        }
        return true;
    }
    public boolean setAge(int age) {
         if(age>0 && age<100)
             this.age = age;
         else {
             System.out.println("Enter a valid age!!!");
             return false;
         }
         return true;
    }
}
