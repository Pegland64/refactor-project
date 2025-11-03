package org.iut.refactoring;

public class CalculateurSalaire {

    public double calculateSalary(Employee employee) {
        double salary = employee.getBaseSalary();

        switch (employee.getType()) {
            case "DEVELOPPEUR":
                salary = calculateDeveloperSalary(employee);
                break;
            case "CHEF DE PROJET":
                salary = calculateProjectManagerSalary(employee);
                break;
            case "STAGIAIRE":
                salary = calculateInternSalary(employee);
                break;
        }

        return salary;
    }

    private double calculateDeveloperSalary(Employee employee) {
        double salary = employee.getBaseSalary() * 1.2;
        if (employee.getExperience() > 5) {
            salary *= 1.15;
        }
        if (employee.getExperience() > 10) {
            salary *= 1.05;
        }
        return salary;
    }

    private double calculateProjectManagerSalary(Employee employee) {
        double salary = employee.getBaseSalary() * 1.5;
        if (employee.getExperience() > 3) {
            salary *= 1.1;
        }
        salary += 5000;
        return salary;
    }

    private double calculateInternSalary(Employee employee) {
        return employee.getBaseSalary() * 0.6;
    }

    public double calculateAnnualBonus(Employee employee) {
        double bonus = 0;

        switch (employee.getType()) {
            case "DEVELOPPEUR":
                bonus = employee.getBaseSalary() * 0.1;
                if (employee.getExperience() > 5) {
                    bonus *= 1.5;
                }
                break;
            case "CHEF DE PROJET":
                bonus = employee.getBaseSalary() * 0.2;
                if (employee.getExperience() > 3) {
                    bonus *= 1.3;
                }
                break;
        }

        return bonus;
    }
}
