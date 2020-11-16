package fi.hyperlearner;

import java.util.List;

public class Person {
    private int id;
    private String name;
    private double salary;
    private EmploymentType employmentType;
    private List<Shifts> shifts;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", employmentType=" + employmentType +
                ", shifts=" + shifts +
                '}';
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }


    public List<Shifts> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shifts> shifts) {
        this.shifts = shifts;
    }
}
