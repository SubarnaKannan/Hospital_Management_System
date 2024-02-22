package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Patient {
    private Scanner scanner;
    private static Connection connection;
    static {
        connection = DBConnection.getConnection();
    }
    public Patient( Scanner scanner) {

        this.scanner=scanner;
    }

    public void addPatient() {
        boolean genderResult = false;
        boolean ageResult = false;
        patientDetails patient = new patientDetails();
        while(!genderResult || !ageResult){
            System.out.print("Enter Patient Name: ");
            String name = scanner.next();
            System.out.print("Enter Patient Age: ");
            int age = scanner.nextInt();
            System.out.print("Enter Patient Gender (Male/Female/Others): ");
            String gender=scanner.next();

            patient.setName(name);
            ageResult = patient.setAge(age);
            genderResult = patient.setGender(gender);
        }


        try {
            String query = "INSERT INTO Patients(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, patient.getName());
            preparedStatement.setInt(2, patient.getAge());
            preparedStatement.setString(3, patient.getGender());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Added Successfully!!");
            } else {
                System.out.println("Failed to add Patient!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatients() {
        String query = "select * from Patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+--------------------+----------+---------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender        |");
            System.out.println("+------------+--------------------+----------+---------------+");
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-13s |\n",id, name, age, gender);
                System.out.println("+------------+--------------------+----------+---------------+");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id) {
        String query = "select * from Patients where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
