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
    private static final String GENRES_NO_SHOW_FOUND = "No show was found within the criteria.";
    private static final String GENRES_FORMAT = "%s %s by %s released on %d [%.1f]\n";
    private static final String GENRES_HEADER = "Search by genre:";

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
                case ARTIST -> artist(in, system);
                case CREDITS -> credits(in, system);
                case REVIEW -> review(in, system);
                case REVIEWS -> reviews(in, system);
                case GENRE -> genre(in, system);

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

    private static void artist(Scanner in, CineReviews system){
        try{
            String artistName = in.nextLine().trim();
            String actionTaken = (system.addArtistInfo(artistName, in.nextLine(), in.nextLine()) == 0)? "updated" : "created";
            System.out.printf(ARTIST_FORMAT, artistName, actionTaken);
        }catch (CineReviewsException c){
            System.out.println(c.getMessage());
        }
    }

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

    private static void printArtistShows(Artist a){
        Iterator<Map.Entry<Show, String>> it = a.getWorkedShows();

        while(it.hasNext()){
            Map.Entry<Show, String> m = it.next();
            Show s = m.getKey();

            String showType = (s instanceof MovieClass)? "movie" : "series";
            System.out.printf(CREDITS_FORMAT, s.getTitle(), s.getYear(), m.getValue(), showType);
        }
    }


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

    private static void genre(Scanner in, CineReviews system){
        in.nextLine();
        Iterator<Show> matchesIt = system.getShowsFromGenres(new ArrayList<>(readStringArray(in)));

        if(!matchesIt.hasNext())
            System.out.println(GENRES_NO_SHOW_FOUND);
        else
            System.out.println(GENRES_HEADER);

        while(matchesIt.hasNext()){
            Show s = matchesIt.next();
            String showType = (s instanceof MovieClass)? "Movie" : "Series";

            System.out.printf(GENRES_FORMAT, showType, s.getTitle(), s.getCreator(), s.getYear(), s.getAverageReviews());
        }
    }
}














