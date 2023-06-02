import CineReviewsPackage.CineReviews;
import CineReviewsPackage.CineReviewsClass;
import CineReviewsPackage.Persons.AdminClass;
import CineReviewsPackage.Persons.CriticClass;
import CineReviewsPackage.Persons.Person;
import CineReviewsPackage.Exceptions.CineReviewsException;
import CineReviewsPackage.Shows.Artist;
import CineReviewsPackage.Shows.MovieClass;
import CineReviewsPackage.Shows.Reviews.Review;
import CineReviewsPackage.Shows.Show;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class contains the main method for running the CineReviews project.
 * It provides methods for handling user input and executing commands.
 *
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
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
    private static final String ARTIST_FORMAT = "%s bio was %s.\n";
    private static final String CREDITS_FORMAT = "%s; %d; %s [%s]\n";
    private static final String REVIEW_ADDED = "Review for %s was registered [%d reviews].\n";
    private static final String REVIEWS_HEADER = "Reviews of %s [%.1f]:\n";
    private static final String REVIEWS_FORMAT = "Review of %s (%s): %s [%s]\n";
    private static final String CONDITION_NO_SHOW_FOUND = "No show was found within the criteria.";
    private static final String CONDITION_FORMAT = "%s %s by %s released on %d [%.1f]\n";
    private static final String GENRES_HEADER = "Search by genre:";
    private static final String RELEASED_HEADER = "Shows released on %d:";
    private static final String FRIENDS_HEADER = "These artists have worked on %d projects together:\n";
    private static final String AVOIDERS_HEADER = "These %s artists never worked together:\n";

    public static void main(String[] args){
        commands();
    }

    /**
     * @return the command with the given name or UNKNOWN if the command does not exist
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
     * Handles user input and executes commands.
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
                case ARTIST -> artist(in, system);
                case CREDITS -> credits(in, system);
                case REVIEW -> review(in, system);
                case REVIEWS -> reviews(in, system);
                case GENRE -> genre(in, system);
                case RELEASED -> released(in, system);
                case AVOIDERS -> avoiders(system);
                case FRIENDS -> friends(system);

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

    /**
     * Registers a new user in the system.
     * @param in The scanner to read user input from.
     * @param system The CineReviews system to register the user in.
     */
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
     * Prints the users registered in the system.
     * @param system The CineReviews system to get users from.
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
     * Registers a new show in the system.
     * @param in The scanner to read user input from.
     * @param system The CineReviews system to add the show to.
     * @param type The type of show to add ("Movie" or "Series").
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

    /**
     * Reads a list of strings from user input.
     * @param in The scanner to read user input from.
     * @return A list of strings read from user input.
     */
    private static List<String> readStringArray(Scanner in) {
        int amountToRead = in.nextInt();
        in.nextLine();
        List<String> array = new LinkedList<>();
        for (int i = 0; i < amountToRead; i++) {
            array.add(in.nextLine().trim());
        }
        return array;
    }

    /**
     * Prints the shows registered in the system.
     * @param system The CineReviews system to get shows from.
     */
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

    /**
     * Prints information about a show.
     * @param showEntry An entry containing a show's title and its corresponding Show object.
     */
    private static void printShow(Map.Entry<String, Show> showEntry){
        Show s = showEntry.getValue();
        System.out.printf(SHOWS_FORMAT, showEntry.getKey(), s.getCreator().getName(), s.getSeasonsOrDuration(),
                s.getCertification(), s.getYear(), s.getGenres().next());

        Iterator<Artist> it = s.getShowsPersons();
        int i = 0;
        while(it.hasNext() && i < 3) {
            System.out.printf(SHOWS_CAST_FORMAT, it.next().getName());
            i++;
        }

        System.out.println();
    }

    /**
     * Adds information about an artist in the system.
     * @param in The scanner to read user input from.
     * @param system The CineReviews system to add artist information to.
     */
    private static void artist(Scanner in, CineReviews system){
        try{
            String artistName = in.nextLine().trim();
            String actionTaken = (system.addArtistInfo(artistName, in.nextLine(), in.nextLine()) == 0)? "updated" : "created";
            System.out.printf(ARTIST_FORMAT, artistName, actionTaken);
        }catch (CineReviewsException c){
            System.out.println(c.getMessage());
        }
    }

    /**
     * Prints information about an artist's shows.
     * @param in The scanner to read user input from.
     * @param system The CineReviews system to get artist information from.
     */
    private static void credits(Scanner in, CineReviews system){
        try{
            String artistName = in.nextLine().trim();
            Artist a = system.getArtist(artistName);

            if(a.getBirthday() != null)
               System.out.println(a.getBirthday());
            if(a.getBirthplace() != null)
              System.out.println(a.getBirthplace());

            printArtistShows(a);
        } catch(CineReviewsException c){
            System.out.println(c.getMessage());
        }
    }

    /**
     * Prints information about an artist's shows.
     * @param a The artist to print shows for.
     */
    private static void printArtistShows(Artist a){
        Iterator<Map.Entry<Show, String>> it = a.getWorkedShows();

        while(it.hasNext()){
            Map.Entry<Show, String> m = it.next();
            Show s = m.getKey();

            String showType = (s instanceof MovieClass)? "movie" : "series";
            System.out.printf(CREDITS_FORMAT, s.getTitle(), s.getYear(), m.getValue(), showType);
        }
    }

    /**
     * Adds a review for a show by a user.
     * @param in The scanner to read user input from.
     * @param system The CineReviews system to add the review to.
     */
    private static void review(Scanner in, CineReviews system){
        try{
            String userName = in.next();
            String showName = in.nextLine().trim();
            int reviewCount = system.addReview(userName, showName, in.nextLine(), in.nextLine());
            System.out.printf(REVIEW_ADDED, showName, reviewCount);
        } catch(CineReviewsException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints reviews for a show with a given title.
     * @param in The scanner to read user input from.
     * @param system The CineReviews system to get reviews from.
     */
    private static void reviews(Scanner in, CineReviews system){
        String show = in.nextLine().trim();

        try{
            Iterator<Review> it = system.getReviews(show);
            System.out.printf(REVIEWS_HEADER, show, system.getAverageRating(show));
            while (it.hasNext()){
                Review tmp = it.next();
                Person p = tmp.getReviewer();
                String userType = (p instanceof CriticClass)? "critic" : "audience";
                System.out.printf(REVIEWS_FORMAT, p.getName(), userType,tmp.getReviewText(),tmp.getRating().getText());
            }
        } catch (CineReviewsException c){
            System.out.println(c.getMessage());
        }
    }

    /**
     * Prints shows that contain all genres in a given list.
     * @param in The scanner to read user input from.
     * @param system The CineReviews system to get shows from.
     */
    private static void genre(Scanner in, CineReviews system){
        try {
            in.nextLine();
            Iterator<Show> matchesIt = system.getShowsFromGenres(new ArrayList<>(readStringArray(in)));

            printShowsFromCondition(matchesIt, GENRES_HEADER);
        } catch (CineReviewsException c){
            System.out.println(c.getMessage());
        }
    }

    /**
     * Prints shows that were released in a given year.
     * @param in The scanner to read user input from.
     * @param system The CineReviews system to get shows from.
     */
    private static void released(Scanner in, CineReviews system){
        try {
            in.nextLine();
            int year = in.nextInt();
            Iterator<Show> it = system.getShowsFromYear(year);
            in.nextLine();

            printShowsFromCondition(it, String.format(RELEASED_HEADER, year));
        } catch (CineReviewsException c){
            System.out.println(c.getMessage());
        }
    }

    /**
     * Prints shows that match a certain condition.
     * @param it An iterator over the shows that match the condition.
     * @param header The header to print before printing the shows.
     */
    private static void printShowsFromCondition(Iterator<Show> it, String header){
        if (!it.hasNext())
            System.out.println(CONDITION_NO_SHOW_FOUND);
        else
            System.out.println(header);

        while (it.hasNext()) {
            Show s = it.next();
            String showType = (s instanceof MovieClass) ? "Movie" : "Series";
            System.out.printf(CONDITION_FORMAT, showType, s.getTitle(), s.getCreator().getName(), s.getYear(), s.getAverageReviews());
        }
    }

    /**
     * Prints sets of artists that have never collaborated with each other.
     * @param system The CineReviews system to get artist sets from.
     */
    private static void avoiders(CineReviews system){
        try{
            AtomicInteger size = new AtomicInteger();
            Iterator<SortedSet<Artist>> setIt = system.getNoCollaborationSets(size);

            System.out.printf(AVOIDERS_HEADER, size);
            while (setIt.hasNext()){
                Iterator<Artist> it = setIt.next().iterator();

                while (it.hasNext()){
                    System.out.print(it.next().getName() + ((it.hasNext())? ", " : "\n"));
                }
            }
        } catch (CineReviewsException c){
            System.out.println(c.getMessage());
        }
    }

    /**
     * Prints names of the most frequent collaborators.
     * @param system The CineReviews system to get collaborator names from.
     */
    private static void friends(CineReviews system){
        try{
            AtomicInteger maxCollaborations = new AtomicInteger();
            Iterator<String> it = system.getMostFrequentCollaborators(maxCollaborations);

            System.out.printf(FRIENDS_HEADER, maxCollaborations.get());
            while(it.hasNext()){
                System.out.println(it.next());
            }
        } catch (CineReviewsException c){
            System.out.println(c.getMessage());
        }
    }

}














