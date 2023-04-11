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

public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

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

    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

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
        // TODO: implémenter cette méthode
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
            System.out.println("Exportation dans le flux échoué");
        }
    }

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     La méthode gére les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        // TODO: implémenter cette méthode
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
            System.out.println("Inscription enregistré avec succès");

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

