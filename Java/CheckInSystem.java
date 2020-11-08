import java.util.HashMap;
import java.util.Map;

public class CheckInSystem {
    private Map<String, String> checkInRecords = new HashMap<>();

    public boolean checkIn(String fingerprint){
        Employee employee = EmployeeFactory.query(fingerprint);
        String record = employee.getRecord();
//        int type = employee.getType();
//        switch (type) {
//            case Employee.ENGINEER:
//                record = "I am an Engineer, My Name is" + employee.getName();
//                break;
//            case Employee.SALESMAN:
//                record = "I am a Salesman, My Name is" + employee.getName();
//                break;
//            case Employee.MANAGER:
//                record = "I am a Manager, My Name is" + employee.getName();
//                break;
//            default:
//                record = "";
//        }
        if (checkInRecords.isEmpty()) {
            return false;
        }
        checkInRecords.put(fingerprint, record);

        return true;
    }

    public int payAmount(Employee employee) {
        return employee.getSalary();
//        int type = employee.getType();
//        switch (type) {
//            case ENGINEER:
//                return monthlySalary;
//            case SALESMAN:
//                return monthlySalary + commission;
//            case MANAGER:
//                return monthlySalary + bonus;
//            default:
//                throw new RuntimeException("Invalid employee");
//        }
    }
}

abstract class Employee {
    int monthlySalary = 100;
    int commission = 200;
    int bonus = 300;

    abstract String getRecord();
    abstract int getSalary();
}

class Engineer extends Employee {
    private String name;

    public Engineer(String name) {
        this.name = name;
    }

    @Override
    int getSalary() {
        return monthlySalary;
    }

    @Override
    String getRecord() {
        return "I am an Engineer, My Name is" + name;
    }
}

class Salesman extends Employee {
    private String name;

    public Salesman (String name) {
        this.name = name;
    }

    @Override
    int getSalary() {
        return monthlySalary + commission;
    }

    @Override
    String getRecord() {
        return "I am a Salesman, My Name is" + name;
    }
}

class Manager extends Employee {
    private String name;

    public Manager (String name) {
        this.name = name;
    }

    @Override
    int getSalary() {
        return monthlySalary + commission + bonus;
    }

    @Override
    String getRecord() {
        return "I am a manager, My Name is" + name;
    }
}
