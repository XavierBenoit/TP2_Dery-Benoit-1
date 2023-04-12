package client.model;

import java.net.UnknownHostException;
import java.util.Scanner;
import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


public class Client {

    private Socket clientSocket;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public void client() {
        System.out.println("Bienvenue au portail d'inscription de l'UDEM");
        chargerListe();

    }
    public void chargerListe(){
        try{
            clientSocket = new Socket("127.0.0.1", 1337);
            System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste de cours (entrez le nombre correspondant au choix):");
            System.out.println("1. Automne/n" +
                    "2. Hiver/n" +
                    "3. Ete/n");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Choix: ");
            String sessionChoisi = null;
            OutputStreamWriter choiceA = new OutputStreamWriter(
                    clientSocket.getOutputStream()
            );
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                switch (line) {
                    case "1" -> {
                        sessionChoisi = "Automne";
                    }
                    case "2" -> {
                        sessionChoisi = "Hiver";
                    }
                    case "3" -> {
                        sessionChoisi = "Ete";
                    }

                }
            }
            scanner.close();
                CommandObject command= new CommandObject("CHARGER", sessionChoisi);
                System.out.println("Les cours offerts durant la session d'" + sessionChoisi + "sont:");

                objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectOutputStream.writeObject(command);

                ArrayList<Course> courseList = (ArrayList<Course>) objectInputStream.readObject();

                int count = 0;
                while (courseList.size() > count) {
                    Course tempCourse = courseList.get(count);
                    String code = tempCourse.getCode();
                    String name = tempCourse.getName();
                    int orderCount = count + 1;
                    System.out.println(orderCount + "." + code + "/t" + name);
                    count++;
            }
                System.out.println("Choix: /n" +
                                    "1. Consulter les cours offerts pour une autre session /n" +
                                    "2. Inscription à un cours");
            Scanner scanner2 = new Scanner(System.in);
            System.out.print("Choix: ");
            OutputStreamWriter choiceB = new OutputStreamWriter(
                    clientSocket.getOutputStream()
            );
            while (scanner2.hasNext()) {
                String line = scanner.nextLine();
                switch (line) {
                    case "1" -> {
                        chargerListe();
                    }
                    case "2" -> {
                        envoyerInscription();
                    }
                }
            }
            scanner2.close();
    } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
public void envoyerInscription(){

    }
}
