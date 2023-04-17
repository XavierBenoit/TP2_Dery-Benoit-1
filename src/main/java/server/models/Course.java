package server.models;

import java.io.Serializable;

/**
 * Un cours ayant un nom, un code et une session à laquelle il est donné. Un étudiant peut
 * regarder une liste de cours, ou s'inscrire à un cours via un <code>RegistrationForm</code>,
 * à l'aide du serveur.
 */
public class Course implements Serializable {

    private String name;
    private String code;
    private String session;

    /**
     * un constructeur de la classe Course
     *
     * @param name    la <code>String</code> du nom du cours
     * @param code    la <code>String</code> du code du cours
     * @param session la <code>String</code> de la session à laquelle le cours est donné
     */
    public Course(String name, String code, String session) {
        this.name = name;
        this.code = code;
        this.session = session;
    }

    /**
     * Getter du nom du cours.
     *
     * @return la <code>String</code> du nom du cours
     */
    public String getName() {
        return name;
    }

    /**
     * Setter du nom du cours.
     *
     * @param name la <code>String</code> du nouveau nom du cours
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter du code du cours.
     *
     * @return la <code>String</code> du code du cours
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter du code du cours.
     *
     * @param code la <code>String</code> du nouveau code du cours
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter de la session à laquelle le cours est donné.
     *
     * @return la <code>String</code> de la session à laquelle le cours est donné
     */
    public String getSession() {
        return session;
    }

    /**
     * Setter de la session à laquelle le cours est donné.
     *
     * @param session la <code>String</code> de la nouvelle session à laquelle le cours est donné
     */
    public void setSession(String session) {
        this.session = session;
    }

    /**
     * Retourne une <code>String</code> avec le nom de la classe et le contenu de chacun des
     * champs de cette classe.
     *
     * @return une <code>String</code> avec le nom de la classe et le contenu de chacun des
     * champs de cette classe.
     */
    @Override
    public String toString() {
        return "Course{" +
                "name=" + name +
                ", code=" + code +
                ", session=" + session +
                '}';
    }
}
