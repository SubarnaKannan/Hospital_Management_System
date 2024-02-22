package HospitalManagementSystem;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/ hospitalmanagementsystem";
    private static final String username = "root";
    private static final String password = "123456789";
    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(scanner);
            Doctor doctor = new Doctor();
            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient ");
                System.out.println("2. View Patients ");
                System.out.println("3. View Doctors ");
                System.out.println("4. Book Appointments ");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        //Add Patients
                        patient.addPatient();
                        return;

                    case 2:
                        //View Patients
                        patient.viewPatients();
                        return;

                    case 3:
                        //View Doctors
                        doctor.viewDoctors();
                        return;

                    case 4:
                        //Book Appointments

                        bookAppointment(patient, doctor, connection, scanner);
                        return;

                    case 5:
                        return;

                    default:
                        System.out.println("Enter a valid choice!!");
                        break;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        boolean docAvail = false;
        patient.viewPatients();
        doctor.viewDoctors();
        while(!docAvail) {
            System.out.print("Enter Patient Id: ");
            int patientId = scanner.nextInt();
            System.out.print("Enter Doctor Id: ");
            int doctorId = scanner.nextInt();
            System.out.print("Enter appointment date(YYYY-MM-DD): ");
            String appointmentDate = scanner.next();

            if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
                if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
                    String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                        preparedStatement.setInt(1, patientId);
                        preparedStatement.setInt(2, doctorId);
                        preparedStatement.setString(3, appointmentDate);
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Appointment Booked!!");
                            docAvail = true;
                            continue;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                 else {
                    System.out.println("Either Doctor or Patient doesn't exist!!!");
                    System.out.println("Please try again with valid data!!");
                }
            } else {
                System.out.println("Doctor is not available on this date!!");
                System.out.println("Please try again with valid data!!");

            }
        }
    }
    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                int count = resultSet.getInt(1);
                if(count==0) {
                    return true;
                }
                else  {
                    return  false;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}