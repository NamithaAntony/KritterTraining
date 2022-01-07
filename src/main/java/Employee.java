

public abstract class Employee {
    static public String companyName="Kritter";
    static public String companyLocation="Bangalore";
    static public int numberOfEmployees=50;

    public enum department
    {
        HR, OPERATIONS, TECH, MARKETING;
    }

    String name;
    int age;
    String gender;
    String place;
    int paymentPerHour;


    public Employee(String name, int age, String gender, String place, int paymentPerHour)
    {
        this.name=name;
        this.age=age;
        this.gender=gender;
        this.place=place;
        this.paymentPerHour=paymentPerHour;
    }

    public abstract int calculateSalary();

    public static void changeCompanyDetails(String newName, String newLocation, int newStrength)
    {
        companyName=newName;
        companyLocation=newLocation;
        numberOfEmployees=newStrength;
    }

}
