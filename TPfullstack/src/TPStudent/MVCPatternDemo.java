package TPStudent;

public class MVCPatternDemo {
    public static void main(String[] args) {
        // Récupérer les données de l'étudiant (simulation d'une base de données)
        Student model = retrieveStudentFromDatabase();

        // Créer la vue
        StudentView view = new StudentView();

        // Créer le contrôleur
        StudentController controller = new StudentController(model, view);

        // Afficher les détails initiaux de l'étudiant
        controller.updateView();

        // Mettre à jour le nom de l'étudiant
        controller.setStudentName("John");

        // Afficher les détails mis à jour
        controller.updateView();
    }

    private static Student retrieveStudentFromDatabase() {
        Student student = new Student();
        student.setName("Robert");
        student.setRollNo("10");
        return student;
    }
}