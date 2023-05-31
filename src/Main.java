/**
 * Autores: Lucas e Nadim Mussá Daud
 */

import Exceptions.NoUserException;
import Exceptions.NotAdministratorException;

import java.util.Iterator;
import java.util.Scanner;

public class Main {

    //MESSAGES
    private static final  String  EXIT_MESSAGE = "Bye!";
    private static final String UNKNOWN_COMMAND_FORMAT = "%s %s\n";
    private static final String UNKNOWN_TYPE = "Unknown user type!";
    private static final String USER_EXISTS = "Person %s already exists!\n";
    private static final String USER_REGISTERED = "Person %s was registered as %s.\n";

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
            switch(command){
                case HELP -> help();
                case UNKNOWN -> System.out.printf(UNKNOWN_COMMAND_FORMAT,command.getName(), command.getDescription());
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

        if (!system.hasType(type)) {
            System.out.println(UNKNOWN_TYPE);
        } else if (system.hasPerson(name)) {
            System.out.printf(USER_EXISTS, name);
        } else{
            system.register(type, name, password);
            System.out.printf(USER_REGISTERED,name,type);
        }

    }

    /**
     * Prints the users
     */
    private static void users(CineReviews system) {

        if (system.hasUsers()) {
            System.out.println("No users registered.");
        } else {
            System.out.println("All registered users:");
            Iterator<Person> it = system.getUsers();
            while (it.hasNext()) {
                Person user = it.next();
                if (user.isAdministrator())
                    System.out.printf("Admin %s has uploaded %d shows\n", user.getName(), user.numberUploads());
                else
                    System.out.printf("Person %s has posted %d reviews\n", user.getName(), user.numberUploads());
            }
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

        int noGenres = in.nextInt();
        in.nextLine();
        String[] genres = new String[noGenres];
        for (int i = 0; i < noGenres; i++) {
            genres[i] = in.nextLine().trim();
        }

        int noCast = in.nextInt();
        in.nextLine();
        String[] cast = new String[noCast];
        for (int i = 0; i < noCast; i++) {
            cast[i] = in.nextLine().trim();
        }

        try {
            if (!(system.hasPerson(admin) && system.isAdmin(admin))) {
                System.out.printf("Admin %s does not exist!\n", admin);
            } else if (!system.correctPassword(admin, password)) {
                System.out.println("Invalid authentication!");
            } else if (system.hasShow(title)) {
                System.out.printf("Show %s already exists!\n", title);
            } else {
                system.addMovie(title, director, duration, certification, year, genres, cast);
                System.out.printf("Show %s was added by %s.\n", title, admin);
            }
        } catch (NoUserException | NotAdministratorException e) {
            System.out.println(e.getMessage());
        }
    }


}














