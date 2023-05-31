/**
 * Autores: Lucas e Nadim Mussá Daud
 */

import CineReviewsPackage.CineReviews;
import CineReviewsPackage.CineReviewsClass;
import CineReviewsPackage.Persons.Person;
import CineReviewsPackage.Exceptions.CineReviewsException;

import java.util.*;

public class Main {

    //MESSAGES
    private static final String EXIT_MESSAGE = "Bye!";
    private static final String UNKNOWN_COMMAND_FORMAT = "%s %s\n";
    private static final String UNKNOWN_TYPE = "Unknown user type!";
    private static final String USER_EXISTS = "CineReviewsPackage.Persons.Person %s already exists!\n";
    private static final String USER_REGISTERED = "CineReviewsPackage.Persons.Person %s was registered as %s.\n";
    private static final String USERS_CMD_HEADER = "All registered users:";
    private static final String USERS_CMD_ADMIN_FORMAT = "Admin %s has uploaded %d shows\n";
    private static final String USERS_CMD_USER_FORMAT = "User %s has posted %d reviews\n";
    private static final String ADD_MOVIE_SUCCESS = "Show %s was added by %s.\n";

    public static void main(String[] args){
        commands();
    }

    /**
     * @returns the command with the given name or UNKNOWN if the command does not exist
     * @param command the name of the command
     */
    private static Commands getCommand(String command) {
        for (Commands c : Commands.values()) {
            if (c.getName().equals(command)) {
                return c;
            }
        }
        return Commands.UNKNOWN;
    }

    /**
     * Main command, reads command input and calls the appropriate method.
     */
    private static void commands() {
        Scanner in = new Scanner(System.in);
        CineReviews system = new CineReviewsClass();

        Commands command;
        do {
            command = getCommand(in.next().toLowerCase());
            switch (command) {
                case HELP -> help();
                case UNKNOWN -> System.out.printf(UNKNOWN_COMMAND_FORMAT, command.getName(), command.getDescription());
                case REGISTER -> register(in, system);
                case USERS -> users(system);
                case MOVIE -> movie(in, system);

            }
        } while (command != Commands.EXIT);

        exit();
        in.close();
    }


    /**
     * Prints the help message
     */
    private static void help() {
        for (Commands c : Commands.values()) {
            if (c != Commands.UNKNOWN) {
                System.out.println(c.getName() + " - " + c.getDescription());
            }
        }
    }

    /**
     * Prints the exit message
     */
    private static void exit() {
        System.out.println(EXIT_MESSAGE);
    }

    private static void register(Scanner in, CineReviews system) {
        String type = in.next().toLowerCase();
        String name = in.next();
        String password = null;

        if (type.equals("admin")) {
            password = in.nextLine().trim();
        } else {
            in.nextLine();
        }

        try {
            system.register(type, name, password);
            System.out.printf(USER_REGISTERED, name, type);
        } catch (CineReviewsException c) {
            System.out.println(c.getMessage());
        }

    }

    /**
     * Prints the users
     */
    private static void users(CineReviews system) {
        try {
            System.out.println(USERS_CMD_HEADER);
            Iterator<Map.Entry<String, Person>> it = system.getPersons();
            while (it.hasNext()) {
                Map.Entry<String, Person> m = it.next();

                if (m.getValue().isAdministrator())
                    System.out.printf(USERS_CMD_ADMIN_FORMAT, m.getKey(), m.getValue().numberUploads());
                else
                    System.out.printf(USERS_CMD_USER_FORMAT, m.getKey(), m.getValue().numberUploads());
            }
        } catch (CineReviewsException c) {
            System.out.println(c.getMessage());
        }
    }

    /**
     * Registers a new movie
     */
    private static void movie(Scanner in, CineReviews system){
        String admin = in.next();
        String password = in.nextLine().trim();
        String title = in.nextLine().trim();
        String director = in.nextLine().trim();
        int duration = in.nextInt();
        in.nextLine();
        String certification = in.nextLine().trim();
        int year = in.nextInt();
        in.nextLine();

        List<String> genres = readStringArray(in);
        List<String> cast = readStringArray(in);

        try {
            system.authenticate(admin, password);
            system.addMovie(title, director, duration, certification, year, genres, cast);
            System.out.printf(ADD_MOVIE_SUCCESS, title, admin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<String> readStringArray(Scanner in) {
        int amountToRead = in.nextInt();
        in.nextLine();
        List<String> array = new LinkedList<>();
        for (int i = 0; i < amountToRead; i++) {
            array.add(in.nextLine().trim());
        }
        return array;
    }

    

}














