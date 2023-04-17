package server;

import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Un serveur qui reçoit des requêtes pour visualiser certains cours du fichier cours.txt
 * ou pour écrire des inscriptions dans le fichier inscription.txt.
 */
public class Server {

    /**
     * la <code>String</code> associée à la commande d'inscription
     *
     * @see Server#handleEvents
     */
    public final static String REGISTER_COMMAND = "INSCRIRE";
    /**
     * la <code>String</code> associée à la commande de chargement
     *
     * @see Server#handleEvents
     */
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * un constructeur de la classe <code>Server</code>. Il initialise l'attribut <code>server</code>
     * avec le paramètre <code>port</code>, pour un seul client à la fois, ainsi que l'attribut
     * <code>handlers</code>, une liste de manipulateurs d'événements.
     *
     * @param port le port avec lequel l'attribut <code>server</code> de cette classe sera initié
     * @throws IOException Si une exception d'entrée ou de sortie (input/output) se produit
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * Ajoute un manipulateur d'événement dans l'attribut <code>handlers</code> de cette classe.
     *
     * @param h le manipulateur d'événement à ajouter
     * @see EventHandler
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * Appelle la méthode <code>handle</code> de chacun des manipulateurs d'événements de ce
     * serveur, avec une certaine commande et une certaine ligne d'arguments.
     *
     * @param cmd la <code>String</code> de commande qui //TODO
     * @param arg
     */
    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * Laisse un client se connecter,
     *
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Attend une ligne de commande de l'attribut de stream d'entrée, puis la gère.
     *
     * @throws IOException Si une exception d'entrée ou de sortie (input/output) se produit
     * @throws ClassNotFoundException Si l'objet provenant du stream d'entrée ne correspond pas
     * à une ligne de commande
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * Sépare une ligne de commande en une commande et une ligne d'arguments.
     *
     * @param line la <code>String</code> de la ligne de commande
     * @return une <code>Pair</code> de <code>String</code> contenant la commande
     * et la ligne d'arguments
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * Déconnecte les attributs de streams d'entrée et de sortie ainsi que l'attribut client
     * de ce serveur.
     *
     * @throws IOException Si une exception d'entrée ou de sortie (input/output) se produit
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * Gère une commande si elle correspond à l'un des deux attributs de commande de ce serveur,
     * c'est-à-dire <code>REGISTER_COMMAND</code> ou <code>LOAD_COMMAND</code>.
     *
     * @param cmd la <code>String</code> de la commande à comparer
     * @param arg la <code>String</code> de la ligne d'arguments
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transformer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     La méthode gère les exceptions si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     */
    public void handleLoadCourses(String arg) {
        try {
            File courses = new File("IFT1025-TP2-server-main/src/main/java/server/data/cours.txt");
            FileReader readCourses = new FileReader(courses);
            BufferedReader buffCourses = new BufferedReader(readCourses);
            String line;
            ArrayList<Course> courseList = new ArrayList<Course>();
            while ((line = buffCourses.readLine()) != null) {
                String code = line.split(" ")[0];
                String name = line.split(" ")[1];
                String session = line.split(" ")[2];
                if (session.equals(arg)) {
                    courseList.add(new Course(code, name, session));
                }
            }
            System.out.println(courseList);
            readCourses.close();
            objectOutputStream.writeObject(courseList);
            System.out.println("Liste de cours pour la session demandée a été envoyée");
        } catch (FileNotFoundException e) {
            System.out.println("Lecture du fichier impossible");
        } catch (IOException e) {
            System.out.println("Exportation dans le flux échouée");
        }
    }

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     La méthode gére les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        try {
            RegistrationForm registrationForm = (RegistrationForm) objectInputStream.readObject();
            String prenom = registrationForm.getPrenom();
            String nom = registrationForm.getNom();
            String email = registrationForm.getEmail();
            String matricule = registrationForm.getMatricule();

            Course course = registrationForm.getCourse();
            String session = course.getSession();
            String code = course.getCode();

            FileWriter registration = new FileWriter("server/data/inscription.txt");
            registration.write(session + " " + code + " " + matricule + "   " + prenom + " " + nom + " " + email);
            registration.close();
            System.out.println("Inscription enregistrée avec succès");

            // TODO: Il manque message de confirmation envoyé au client
            } catch (FileNotFoundException e) {
                System.out.println("Impossible d'écrire dans le fichier (inscription.txt)");
            } catch (IOException e) {
            System.out.println("Erreur lors de la réception du formulaire");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur dans la lecture de l'objet (formulaire) reçu");
        }

    }
    }

