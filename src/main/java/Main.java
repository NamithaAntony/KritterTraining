import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;



import com.opencsv.*;
import org.apache.log4j.*;

public class Main {

    public static void main(String[] args) {

        Logger logger=Logger.getLogger(Main.class.getName());
        String logpath=System.getProperty("user.dir")+"/log4j.properties";
        PropertyConfigurator.configure(logpath);

        ArrayList<ContractEmployee> ContractEmployeeList = new ArrayList<ContractEmployee>();
        ArrayList<FullTimeEmployee> FullTimeEmployeeList = new ArrayList<FullTimeEmployee>();
        Scanner sc = new Scanner(System.in);


        boolean cont=true;

        while (cont) {
            try {
                File file=new File("Records.csv");
                FileWriter write = new FileWriter(file);
                CSVWriter wr=new CSVWriter(write);


                System.out.println("1. Add Details of Employees\n2. View Details of Employees\n3. View Company Details\n4. Edit Company Details\n5. Print Salary Bill");
                System.out.print("Select option: ");
                char op = sc.next().charAt(0);
                if (op == '1') {
                    System.out.println("1. Contract Employee\n2. Full Time Employee");
                    System.out.print("Select the type of Employee: ");
                    char option = sc.next().charAt(0);
                    System.out.print("Enter the name: ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    System.out.print("Enter the age: ");
                    int age = sc.nextInt();
                    System.out.print("Enter the gender: ");
                    sc.nextLine();
                    String gender = sc.nextLine();
                    System.out.print("Enter the place: ");
                    String place = sc.nextLine();
                    System.out.print("Enter the payment per hour: ");
                    int paymentPerHour = sc.nextInt();


                    if (option == '1') {
                        System.out.print("Enter the Number of Hours: ");
                        int workingHours = sc.nextInt();
                        ContractEmployee emp = new ContractEmployee(name, age, gender, place, paymentPerHour, workingHours);
                        ContractEmployeeList.add(emp);
                        logger.info("Contract Employee object Created");
                        emp.saveToDatabase(emp);
                        String[] data={"Contract Employee",name,String.valueOf(age),gender,place,String.valueOf(paymentPerHour),Employee.companyName,Employee.department.HR.name()};
                        wr.writeNext(data);
                        logger.info("Contract employee details saved to database");
                    }

                    else if (option == '2') {
                        FullTimeEmployee emp = new FullTimeEmployee(name, age, gender, place, paymentPerHour);
                        FullTimeEmployeeList.add(emp);
                        logger.info("Full time Employee object Created");
                        emp.saveToDatabase(emp);
                        String[] data={"Full Time Employee",name,String.valueOf(age),gender,place,String.valueOf(paymentPerHour),Employee.companyName,Employee.department.HR.name()};
                        wr.writeNext(data);
                        logger.info("Full time employee details saved to database");
                    } else
                        logger.error("Invalid entry!!!!");

                    wr.close();
                }
                else if (op == '2') {
                    System.out.println("1. Contract Employee\n2. Full Time Employee\n3. All Employees");
                    System.out.print("Select the type of Employee: ");
                    char option1 = sc.next().charAt(0);
                    if (option1 == '1') {
                        for (ContractEmployee i : ContractEmployeeList)
                            i.printDetails();
                    } else if (option1 == '2') {
                        for (FullTimeEmployee i : FullTimeEmployeeList)
                            i.printDetails();
                    }
                    else if (option1=='3'){
                        CSVReader csvRead=new CSVReader(new FileReader(file));
                        String[] nextRecord;

                        while ((nextRecord = csvRead.readNext()) != null) {
                            for (String cell : nextRecord) {
                                System.out.print(cell + "\t");
                            }
                            System.out.println();
                        }
                    }
                    else
                        System.out.println("Invalid Entry");
                }
                else if (op == '3') {
                    System.out.println("Company Name: " + Employee.companyName);
                    System.out.println("Company Location: " + Employee.companyLocation);
                    System.out.println("Number of Employees: " + Employee.numberOfEmployees);
                }
                else if (op == '4') {
                    System.out.print("Enter the new name: ");
                    sc.nextLine();
                    String newName = sc.nextLine();
                    System.out.print("Enter the new location: ");
                    String newLocation = sc.nextLine();
                    System.out.print("Enter the new Strength: ");
                    int newStrength = sc.nextInt();
                    Employee.changeCompanyDetails(newName, newLocation, newStrength);
                }
                else if (op == '5') {
                    String MySQLURL = "jdbc:mysql://localhost:3306/employee?useSSL=false";
                    String databseUserName = "root";
                    String databasePassword = "kritter";
                    Connection con = null;

                    try {
                        con = DriverManager.getConnection(MySQLURL, databseUserName, databasePassword);
                        if (con != null) {
                            Statement stmt=con.createStatement();
                            System.out.print("1. Contract Employee\n2. Full Time Employee\nEnter your choice: ");
                            char choice=sc.next().charAt(0);
                            System.out.print("Enter the name of employee: ");
                            sc.nextLine();
                            String nameOfEmployee=sc.nextLine();
                            File filename=new File(nameOfEmployee+".txt");
                            if (choice=='1') {
                                PreparedStatement statement = con.prepareStatement("select * from contractemployee where name = ?");
                                statement.setString(1, nameOfEmployee);
                                ResultSet rs = statement.executeQuery();
                                FileWriter writer = new FileWriter(filename);
                                while (rs.next())
                                    writer.write("You have worked for "+rs.getInt(5)+" with salary "+rs.getInt(6));
                                writer.close();

                            }
                            else if (choice=='2'){
                                PreparedStatement statement = con.prepareStatement("select * from fulltimeemployee where name = ?");
                                statement.setString(1, nameOfEmployee);
                                ResultSet rs = statement.executeQuery();
                                FileWriter writer = new FileWriter(filename);
                                while (rs.next())
                                    writer.write("You have worked for "+rs.getInt(5)+" with salary "+rs.getInt(6));
                                writer.close();
                            }
                            else
                                System.out.println("Invalid Entry");
                            Scanner myReader = new Scanner(filename);
                            while (myReader.hasNextLine()) {
                                String data = myReader.nextLine();
                                System.out.println(data);
                            }
                            myReader.close();
                            con.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                    System.out.println("Invalid Entry");
            }
            catch (Exception E){
                E.printStackTrace();
            }
            finally {
                System.out.print("Do you want to continue?(y/n): ");
                char flag = sc.next().charAt(0);
                if ((flag != 'y') && (flag != 'Y')) {
                    cont = false;
                }
            }
        }
    }
}