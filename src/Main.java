/**
 * Autores: Lucas e Nadim Mussá Daud
 */

import CineReviewsPackage.CineReviews;
import CineReviewsPackage.CineReviewsClass;
import CineReviewsPackage.Persons.AdminClass;
import CineReviewsPackage.Persons.Person;
import CineReviewsPackage.Exceptions.CineReviewsException;
import CineReviewsPackage.Shows.Show;

import java.awt.event.WindowStateListener;
import java.util.*;

public class Main {

    //MESSAGES
    private static final String EXIT_MESSAGE = "Bye!";
    private static final String UNKNOWN_COMMAND_FORMAT = "%s %s\n";
    private static final String USER_REGISTERED = "User %s was registered as %s.\n";
    private static final String USERS_HEADER = "All registered users:";
    private static final String USERS_ADMIN_FORMAT = "Admin %s has uploaded %d shows\n";
    private static final String USERS_FORMAT = "User %s has posted %d reviews\n";
    private static final String ADD_MOVIE_SUCCESS = "%s %s (%d) was uploaded [%d new artists were created].\n";
    private static final String SHOWS_HEADER = "All shows:";
    private static final String SHOWS_FORMAT = "%s; %s; %d; %s; %d; %s";
    private static final String SHOWS_CAST_FORMAT = "; %s";

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
                case MOVIE -> show(in, system, "Movie");
                case SERIES -> show(in, system, "Series");
                case SHOWS -> shows(system);

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
            Iterator<Map.Entry<String, Person>> it = system.getPersons();
            System.out.println(USERS_HEADER);
            while (it.hasNext()) {
                Map.Entry<String, Person> m = it.next();

                if (m.getValue() instanceof AdminClass)
                    System.out.printf(USERS_ADMIN_FORMAT, m.getKey(), m.getValue().numberUploads());
                else
                    System.out.printf(USERS_FORMAT, m.getKey(), m.getValue().numberUploads());
            }
        } catch (CineReviewsException c) {
            System.out.println(c.getMessage());
        }
    }

    /**
     * Registers a new movie
     */
    private static void show(Scanner in, CineReviews system, String type){
        String admin = in.next();
        String password = in.nextLine().trim();
        String title = in.nextLine().trim();
        String director = in.nextLine().trim();
        int durationOrSeasons = in.nextInt();
        in.nextLine();
        String certification = in.nextLine().trim();
        int year = in.nextInt();
        in.nextLine();

        List<String> genres = readStringArray(in);
        List<String> cast = readStringArray(in);

        try {
            system.authenticate(admin, password);
            int newArtistCount = system.addShow(title, director, durationOrSeasons, certification,
                    year, genres, cast, type, admin);
            System.out.printf(ADD_MOVIE_SUCCESS, type, title, year, newArtistCount);
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

    private static void shows(CineReviews system){
        try {
            Iterator<Map.Entry<String, Show>> it = system.getShows();
            System.out.println(SHOWS_HEADER);
            while (it.hasNext())
                printShow(it.next());

        }catch (CineReviewsException c){
            System.out.println(c.getMessage());
        }
    }

    private static void printShow(Map.Entry<String, Show> showEntry){
        Show s = showEntry.getValue();
        System.out.printf(SHOWS_FORMAT, showEntry.getKey(), s.getCreator(), s.getSeasonsOrDuration(),
                s.getCertification(), s.getYear(), s.getGenres().next());

        Iterator<Map.Entry<String, Person>> it = s.getShowsPersons();
        while(it.hasNext())
            System.out.printf(SHOWS_CAST_FORMAT, it.next().getKey());

        System.out.println();
    }

}














