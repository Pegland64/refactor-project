package org.iut.refactoring;

import java.util.*;
import java.time.*;

public class GestionPersonnel {

    private final List<Employee> employees = new ArrayList<>();
    private final Map<String, Double> employeeSalaries = new HashMap<>();
    private final List<String> logs = new ArrayList<>();
    private final CalculateurSalaire CalculateurSalaire = new CalculateurSalaire();

    public void ajouteSalarie(String type, String nom, double salaireDeBase, int experience, String equipe) {
        String id = UUID.randomUUID().toString();
        Employee employee = new Employee(id, type, nom, salaireDeBase, experience, equipe);
        employees.add(employee);

        double finalSalary = CalculateurSalaire.calculateSalary(employee);
        employeeSalaries.put(id, finalSalary);

        addLog("Ajout de l'employé: " + nom);
    }

    public double calculSalaire(String employeId) {
        Employee employee = findEmployeeById(employeId);
        if (employee == null) {
            System.out.println("ERREUR: impossible de trouver l'employé");
            return 0;
        }
        return CalculateurSalaire.calculateSalary(employee);
    }

    public double calculBonusAnnuel(String employeId) {
        Employee employee = findEmployeeById(employeId);
        if (employee == null) return 0;
        return CalculateurSalaire.calculateAnnualBonus(employee);
    }

    public void avancementEmploye(String employeId, String newType) {
        Employee employee = findEmployeeById(employeId);
        if (employee == null) {
            System.out.println("ERREUR: impossible de trouver l'employé");
            return;
        }

        employee.setType(newType);
        double nouveauSalaire = CalculateurSalaire.calculateSalary(employee);
        employeeSalaries.put(employeId, nouveauSalaire);

        addLog("Employé promu: " + employee.getName());
        System.out.println("Employé promu avec succès!");
    }

    public void generationRapport(String typeRapport, String filtre) {
        System.out.println("=== RAPPORT: " + typeRapport + " ===");

        switch (typeRapport) {
            case "SALAIRE":
                generateSalaryReport(filtre);
                break;
            case "EXPERIENCE":
                generateExperienceReport(filtre);
                break;
            case "DIVISION":
                generateDivisionReport();
                break;
        }

        addLog("Rapport généré: " + typeRapport);
    }

    private void generateSalaryReport(String filter) {
        employees.stream()
                .filter(emp -> filter == null || filter.isEmpty() || emp.getDepartment().equals(filter))
                .forEach(emp -> {
                    double salary = calculSalaire(emp.getId());
                    System.out.println(emp.getName() + ": " + salary + " €");
                });
    }

    private void generateExperienceReport(String filter) {
        employees.stream()
                .filter(emp -> filter == null || filter.isEmpty() || emp.getDepartment().equals(filter))
                .forEach(emp -> System.out.println(emp.getName() + ": " + emp.getExperience() + " années"));
    }

    private void generateDivisionReport() {
        Map<String, Long> departmentCounts = new HashMap<>();
        employees.forEach(emp -> {
            String dept = emp.getDepartment();
            departmentCounts.put(dept, departmentCounts.getOrDefault(dept, 0L) + 1);
        });

        departmentCounts.forEach((dept, count) ->
                System.out.println(dept + ": " + count + " employés"));
    }

    public List<Employee> getEmployesParDivision(String division) {
        List<Employee> result = new ArrayList<>();
        employees.stream()
                .filter(emp -> emp.getDepartment().equals(division))
                .forEach(result::add);
        return result;
    }

    public void printLogs() {
        System.out.println("=== LOGS ===");
        logs.forEach(System.out::println);
    }

    private Employee findEmployeeById(String employeId) {
        return employees.stream()
                .filter(emp -> emp.getId().equals(employeId))
                .findFirst()
                .orElse(null);
    }

    private void addLog(String message) {
        logs.add(LocalDateTime.now() + " - " + message);
    }

    // Pour compatibilité temporaire avec GestionApp
    public ArrayList<Object[]> employes = new ArrayList<>();
}
