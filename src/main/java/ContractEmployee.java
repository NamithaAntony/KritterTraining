import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class ContractEmployee extends Employee {
    int workingHours;

    public ContractEmployee(String name, int age, String gender, String place, int paymentPerHour, int workingHours) {
        super(name, age, gender, place, paymentPerHour);
        this.workingHours = workingHours;
    }

    public int calculateSalary() {
        return paymentPerHour * workingHours;
    }

    public void printDetails() {
        System.out.println("Type of the Employee: Contract Based Employee");
        System.out.println("Name of the Employee: " + name);
        System.out.println("Age of the Employee: " + age);
        System.out.println("Gender of the Employee: " + gender);
        System.out.println("Place of the Employee: " + place);
        System.out.println("Payment per hour: " + paymentPerHour);
        System.out.println("Salary: " + calculateSalary());
    }

    public void saveToDatabase(ContractEmployee emp) {
        String MySQLURL = "jdbc:mysql://localhost:3306/employee?useSSL=false";
        String databseUserName = "root";
        String databasePassword = "kritter";
        Connection con = null;
        try {
            con = DriverManager.getConnection(MySQLURL, databseUserName, databasePassword);
            if (con != null) {
                System.out.println("Database connection is successful !!!!");
                String query = " insert into contractemployee (name, age, gender, place, paymentperhour, salary, companyname, department)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, emp.name);
                preparedStmt.setInt(2, emp.age);
                preparedStmt.setString(3, emp.gender);
                preparedStmt.setString(4, emp.place);
                preparedStmt.setInt(5, emp.paymentPerHour);
                preparedStmt.setInt(6, emp.calculateSalary());
                preparedStmt.setString(7, Employee.companyName);
                preparedStmt.setString(8, department.HR.name());
                preparedStmt.execute();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}



