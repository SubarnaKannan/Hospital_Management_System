package HospitalManagementSystem;

public class Gender {

    private String gender;

    public void setGender(String gender,Patient patient) {
        if(gender.equalsIgnoreCase("male")||gender.equalsIgnoreCase("female")){
        this.gender = gender;}
        else{
            System.out.println("Enter a valid Gender");
            patient.addPatient();
            System.exit(200);
            }
    }

    public String getGender() {
        return gender;
    }
}
