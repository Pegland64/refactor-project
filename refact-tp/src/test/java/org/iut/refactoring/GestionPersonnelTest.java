package org.iut.refactoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionPersonnelTest {

    private GestionPersonnel gestionPersonnel;
    private String developerId;
    private String managerId;
    private String internId;

    @BeforeEach
    void setUp() {
        gestionPersonnel = new GestionPersonnel();
        gestionPersonnel.ajouteSalarie("DEVELOPPEUR", "Alice", 50000, 6, "IT");
        gestionPersonnel.ajouteSalarie("CHEF DE PROJET", "Bob", 60000, 4, "RH");
        gestionPersonnel.ajouteSalarie("STAGIAIRE", "Charlie", 20000, 0, "IT");

        // Récupération des IDs (via reflection ou méthode getter à ajouter)
        // Pour l'instant, on simule
        List<Employee> employees = gestionPersonnel.getEmployesParDivision("IT");
        developerId = employees.get(0).getId();
        internId = employees.get(1).getId();

        employees = gestionPersonnel.getEmployesParDivision("RH");
        managerId = employees.get(0).getId();
    }

    @Test
    void testAjouteSalarie() {
        gestionPersonnel.ajouteSalarie("DEVELOPPEUR", "Dan", 55000, 12, "IT");
        List<Employee> itEmployees = gestionPersonnel.getEmployesParDivision("IT");
        assertEquals(3, itEmployees.size());
    }

    @Test
    void testCalculSalaireDeveloppeur() {
        double salaire = gestionPersonnel.calculSalaire(developerId);
        // 50000 * 1.2 * 1.15 = 69000
        assertEquals(69000, salaire, 0.01);
    }

    @Test
    void testCalculSalaireChefDeProjet() {
        double salaire = gestionPersonnel.calculSalaire(managerId);
        // (60000 * 1.5 * 1.1) + 5000 = 104000
        assertEquals(104000, salaire, 0.01);
    }

    @Test
    void testCalculSalaireStagiaire() {
        double salaire = gestionPersonnel.calculSalaire(internId);
        // 20000 * 0.6 = 12000
        assertEquals(12000, salaire, 0.01);
    }

    @Test
    void testCalculBonusAnnuelDeveloppeur() {
        double bonus = gestionPersonnel.calculBonusAnnuel(developerId);
        // 50000 * 0.1 * 1.5 = 7500
        assertEquals(7500, bonus, 0.01);
    }

    @Test
    void testCalculBonusAnnuelChefDeProjet() {
        double bonus = gestionPersonnel.calculBonusAnnuel(managerId);
        // 60000 * 0.2 * 1.3 = 15600
        assertEquals(15600, bonus, 0.01);
    }

    @Test
    void testCalculBonusAnnuelStagiaire() {
        double bonus = gestionPersonnel.calculBonusAnnuel(internId);
        assertEquals(0, bonus, 0.01);
    }

    @Test
    void testAvancementEmploye() {
        gestionPersonnel.avancementEmploye(developerId, "CHEF DE PROJET");
        double nouveauSalaire = gestionPersonnel.calculSalaire(developerId);
        // Nouveau calcul en tant que chef de projet
        assertTrue(nouveauSalaire > 69000);
    }

    @Test
    void testGetEmployesParDivision() {
        List<Employee> itEmployees = gestionPersonnel.getEmployesParDivision("IT");
        assertEquals(2, itEmployees.size());

        List<Employee> rhEmployees = gestionPersonnel.getEmployesParDivision("RH");
        assertEquals(1, rhEmployees.size());
    }

    @Test
    void testCalculSalaireEmployeInexistant() {
        double salaire = gestionPersonnel.calculSalaire("id-inexistant");
        assertEquals(0, salaire);
    }

    @Test
    void testCalculBonusEmployeInexistant() {
        double bonus = gestionPersonnel.calculBonusAnnuel("id-inexistant");
        assertEquals(0, bonus);
    }
}
