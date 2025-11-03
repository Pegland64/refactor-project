package org.iut.refactoring;

public class Employee {
    private final String id;
    private String type;
    private final String name;
    private final double baseSalary;
    private final int experience;
    private final String department;

    public Employee(String id, String type, String name, double baseSalary, int experience, String department) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.baseSalary = baseSalary;
        this.experience = experience;
        this.department = department;
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getName() { return name; }
    public double getBaseSalary() { return baseSalary; }
    public int getExperience() { return experience; }
    public String getDepartment() { return department; }
}
