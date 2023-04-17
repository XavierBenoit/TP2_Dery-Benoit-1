package server.models;

import java.io.Serializable;

/**
 * Formulaire d'inscription d'un étudiant à un cours, avec son prénom et nom de famille, son
 * adresse courriel et son matricule. Un étudiant peut s'inscrire à un cours avec ce
 * formulaire via le serveur.
 */
public class RegistrationForm implements Serializable {
    private String prenom;
    private String nom;
    private String email;
    private String matricule;
    private Course course;

    /**
     * un constructeur de la classe <code>RegistrationForm</code>
     *
     * @param prenom la <code>String</code> du prénom de l'étudiant
     * @param nom la <code>String</code> du nom de famille de l'étudiant
     * @param email la <code>String</code> de l'adresse courriel de l'étudiant
     * @param matricule la <code>String</code> du matricule de l'étudiant
     * @param course le <code>Course</code> auquel l'étudiant s'inscrit
     */
    public RegistrationForm(String prenom, String nom, String email, String matricule, Course course) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.matricule = matricule;
        this.course = course;
    }

    /**
     * Getter du prénom de l'étudiant.
     *
     * @return la <code>String</code> du prénom de l'étudiant
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter du prénom de l'étudiant.
     *
     * @param prenom la <code>String</code> du nouveau prénom de l'étudiant
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Getter du nom de l'étudiant.
     *
     * @return la <code>String</code> du nom de l'étudiant
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter du nom de l'étudiant.
     *
     * @param nom la <code>String</code> du nouveau nom de l'étudiant
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter de l'adresse courriel de l'étudiant.
     *
     * @return la <code>String</code> de l'adresse courriel de l'étudiant
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter de l'adresse courriel de l'étudiant.
     *
     * @param email la <code>String</code> de la nouvelle adresse courriel de l'étudiant
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter du matricule de l'étudiant.
     *
     * @return la <code>String</code> du matricule de l'étudiant
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * Setter du matricule de l'étudiant.
     *
     * @param matricule la <code>String</code> du nouveau matricule de l'étudiant
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * Getter du cours auquel l'étudiant s'inscrit.
     *
     * @return le <code>Course</code> auquel l'étudiant s'inscrit
     * @see Course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Setter du cours auquel l'étudiant s'inscrit.
     *
     * @param course le nouveau <code>Course</code> auquel l'étudiant s'inscrit
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Retourne une <code>String</code> avec le nom de la classe et le contenu
     * de chacun des champs de cette classe.
     *
     * @return une <code>String</code> avec le nom de la classe et le contenu de
     * chacun des champs de cette classe
     */
    @Override
    public String toString() {
        return "InscriptionForm{" + "prenom='" + prenom + '\'' + ", nom='" + nom + '\'' + ", email='" + email + '\'' + ", matricule='" + matricule + '\'' + ", course='" + course + '\'' + '}';
    }
}
