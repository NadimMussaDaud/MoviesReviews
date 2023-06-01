package CineReviewsPackage;

import CineReviewsPackage.Exceptions.*;
import CineReviewsPackage.Persons.*;
import CineReviewsPackage.Shows.*;
import CineReviewsPackage.Shows.Reviews.Review;

import java.util.*;

public class CineReviewsClass implements CineReviews {

    private static final String UNKNOWN_TYPE = "Unknown user type!";
    private static final String USER_EXISTS = "User %s already exists!";
    private static final String USER_NOT_FOUND = "User %s does not exist!";
    private static final String NO_USERS = "No users registered.";
    private static final String ADMIN_NOT_FOUND = "Admin %s does not exist!";
    private static final String ADMIN_CANNOT_REVIEW = "Admin %s cannot review shows!";
    private static final String SHOW_EXISTS = "Show %s already exists!";
    private static final String NO_SHOWS = "No shows have been uploaded.";
    private static final String SHOW_NOT_FOUND = "Show %s does not exist!";
    private static final String REVIEW_EXISTS = "%s has already reviewed %s!";
    private static final String SHOW_HAS_NO_REVIEWS = "Show %s has no reviews.";
    private static final String ARTIST_BIO_EXISTS = "Bio of %s is already available!";
    private static final String ARTIST_NOT_FOUND = "No information about %s!";

    private final SortedMap<String, Show> shows;
    private final SortedMap<String, Person> persons;
    private final SortedMap<String, Artist> artists;

    public CineReviewsClass() {
        shows = new TreeMap<>();
        persons = new TreeMap<>();
        artists = new TreeMap<>();
    }

    @Override
    public boolean hasType(String type) {
        return type.equals("admin") || type.equals("audience") || type.equals("critic");
    }

    @Override
    public void register(String type, String name, String password) throws CineReviewsException {
        if (!hasType(type)) throw new CineReviewsException(UNKNOWN_TYPE);
        if (persons.containsKey(name)) throw new CineReviewsException(String.format(USER_EXISTS, name));

        if (password != null) {
            persons.put(name, new AdminClass(name, password));
        } else {
            if (type.equals("audience")) {
                persons.put(name, new AudienceClass(name));
            } else {
                persons.put(name, new CriticClass(name));
            }
        }
    }

    @Override
    public Iterator<Map.Entry<String, Person>> getPersons() throws CineReviewsException {
        if (persons.isEmpty()) throw new CineReviewsException(NO_USERS);
        return persons.entrySet().iterator();
    }

    public void authenticate(String name, String password) throws CineReviewsException, UserException {
        if (!persons.containsKey(name))
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, name));
        Person p = persons.get(name);
        if (!(p instanceof AdminClass))
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, name));
        p.authenticate(password);
    }

    @Override
    public int addShow(String title, String director, int durationOrSeasons, String certification, int year,
                       List<String> genres, List<String> cast, String type, String adminName) throws CineReviewsException {
        if (shows.containsKey(title)) throw new CineReviewsException(String.format(SHOW_EXISTS, title));

        int newArtistCount = handleArtists(cast, director);

        //tirar de users todos os artistas recém criados e guardar num array de users
        List<Artist> castArtists = new LinkedList<>();
        for (String a : cast)
            castArtists.add(artists.get(a));
        Artist directorArtist = artists.get(director);

        Show s = null;
        if (type.equals("Movie")) {
            s = new MovieClass(directorArtist, title, durationOrSeasons, certification, year, genres, castArtists);
            directorArtist.addShow(s, "director");
        }
        if (type.equals("Series")){
            s = new SeriesClass(directorArtist, title, durationOrSeasons, certification, year, genres, castArtists);
            directorArtist.addShow(s, "creator");
        }
        shows.put(title, s);
        for (Artist a : castArtists)
            a.addShow(s, "actor");


        persons.get(adminName).addMedia(s);

        return newArtistCount;
    }

    private int handleArtists(List<String> cast, String director) {
        int count = 0;
        try {
            count += addArtistInfo(director, null, null);
        } catch (CineReviewsException ignored){}
            //If artist is already defined, ignore it.
        for (String c : cast) {
            try {
                count += addArtistInfo(c, null, null);
            }catch (CineReviewsException ignored){}
        }
        return count;
    }

    public int addArtistInfo(String name,String birthday, String birthplace) throws CineReviewsException{
        if (!artists.containsKey(name)) {
            artists.put(name, new Artist(name, birthday, birthplace));
            return 1;
        } else {
            Artist a = artists.get(name);

            if (a.getBirthplace() != null || a.getBirthday() != null)
                throw new CineReviewsException(String.format(ARTIST_BIO_EXISTS, name));

            a.addInfo(birthplace, birthday);
            return 0;
        }
    }

    public Artist getArtist(String name) throws CineReviewsException{
        if(!artists.containsKey(name)) throw new CineReviewsException(String.format(ARTIST_NOT_FOUND, name));
        return artists.get(name);
    }

    public Iterator<Map.Entry<String, Show>> getShows() throws CineReviewsException {
        if (shows.isEmpty()) throw new CineReviewsException(NO_SHOWS);
        return shows.entrySet().iterator();
    }

    public int addReview(String userName, String title, String review, String classification) throws CineReviewsException {
        if (!persons.containsKey(userName)) throw new CineReviewsException(String.format(USER_NOT_FOUND, userName));

        Person p = persons.get(userName);
        if (p instanceof AdminClass) throw new CineReviewsException(String.format(ADMIN_CANNOT_REVIEW, userName));

        if (!shows.containsKey(title)) throw new CineReviewsException(String.format(SHOW_NOT_FOUND, title));
        try {
            return shows.get(title).addReview(review, classification, p);
        } catch (ShowException s) {
            //Exception was not created inside the addReview method due to the need of knowing the username.
            throw new CineReviewsException(String.format(REVIEW_EXISTS, userName, title));
        }
    }

    public Iterator<Review> getReviews(String showName) throws CineReviewsException {
        if (!shows.containsKey(showName)) throw new CineReviewsException(String.format(SHOW_NOT_FOUND, showName));

        try {
            return shows.get(showName).getReviews();
        } catch (ShowException s) {
            //Exception was not created inside the getReviews method due to the need of knowing the show name.
            throw new CineReviewsException(String.format(SHOW_HAS_NO_REVIEWS, showName));
        }
    }

    public double getAverageRating(String showName) {
        return shows.get(showName).getAverageReviews();
    }

    public Iterator<Show> getShowsFromGenres(List<String> genres) {
        List<Show> toReturn = new ArrayList<>();

        for (Show s : shows.values()) {
            if (s.containsAllGenres(genres))
                toReturn.add(s);
        }

        toReturn.sort(new ShowComparator());
        return toReturn.iterator();
    }
}
