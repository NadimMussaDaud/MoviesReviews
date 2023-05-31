/**
 * Autores: Lucas e Nadim Mussá Daud
 */

import Exceptions.CineReviewsException;
import Exceptions.NoUserException;
import Exceptions.NotAdministratorException;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Main {

    //MESSAGES
    private static final String EXIT_MESSAGE = "Bye!";
    private static final String UNKNOWN_COMMAND_FORMAT = "%s %s\n";
    private static final String UNKNOWN_TYPE = "Unknown user type!";
    private static final String USER_EXISTS = "Person %s already exists!\n";
    private static final String USER_REGISTERED = "Person %s was registered as %s.\n";
    private static final String USER_EXISTS = "User %s already exists!\n";
    private static final String USER_REGISTERED = "User %s was registered as %s.\n";
    private static final String USERS_CMD_HEADER = "All registered users:";
    private static final String USERS_CMD_ADMIN_FORMAT = "Admin %s has uploaded %d shows\n";
    private static final String USERS_CMD_USER_FORMAT = "User %s has posted %d reviews\n";
    private static final String ADD_MOVIE_SUCCESS = "Show %s was added by %s.\n";

    public static void main(String[] args) throws NoUserException, NotAdministratorException {
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
    private static void commands() throws NoUserException, NotAdministratorException {
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
            Iterator<Map.Entry<String, User>> it = system.getUsers();
            while (it.hasNext()) {
                Map.Entry<String, User> m = it.next();

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
    private static void movie(Scanner in, CineReviews system) throws NoUserException, NotAdministratorException {
        String admin = in.next();
        String password = in.nextLine().trim();
        String title = in.nextLine().trim();
        String director = in.nextLine().trim();
        int duration = in.nextInt();
        in.nextLine();
        String certification = in.nextLine().trim();
        int year = in.nextInt();
        in.nextLine();

        String[] genres = readStringArray(in);
        String[] cast = readStringArray(in);

        try {
            system.authenticate(admin, password);
            system.addMovie(title, director, duration, certification, year, genres, cast);
            System.out.printf("Show %s was added by %s.\n", title, admin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String[] readStringArray(Scanner in) {
        int amountToRead = in.nextInt();
        in.nextLine();
        String[] array = new String[amountToRead];
        for (int i = 0; i < amountToRead; i++) {
            array[i] = in.nextLine().trim();
        }
        return array;
    }


}














