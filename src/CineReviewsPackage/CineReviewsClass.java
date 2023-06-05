package CineReviewsPackage;

import CineReviewsPackage.Exceptions.*;
import CineReviewsPackage.Persons.*;
import CineReviewsPackage.Shows.*;
import CineReviewsPackage.Shows.Reviews.Review;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 *
 */
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
    private static final String NO_SHOW_WITH_CRITERIA = "No show was found within the criteria.";
    private static final String NO_ARTISTS = "No artists yet!";
    private static final String NO_COLLABORATIONS = "No collaborations yet!";
    private static final String NO_AVOIDERS = "It is a small world!";

    private final SortedMap<String, Show> shows;
    private final SortedMap<String, Person> persons;
    private final SortedMap<String, Artist> artists;

    /**
     * Constructs a new CineReviewsClass object with empty TreeMaps for shows, persons, and artists.
     */
    public CineReviewsClass() {
        shows = new TreeMap<>();
        persons = new TreeMap<>();
        artists = new TreeMap<>();
    }

    /**
     * Checks if a given type is part of the supported user types.
     *
     * @param type is the type to check
     * @return true if the type is supported, false otherwise.
     */
    private boolean hasType(String type) {
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

        List<Artist> castArtists = new LinkedList<>();
        for (String a : cast)
            castArtists.add(artists.get(a));
        Artist directorArtist = artists.get(director);

        Show s = null;
        if (type.equals("Movie")) {
            s = new MovieClass(directorArtist, title, durationOrSeasons, certification, year, genres, castArtists);
            directorArtist.addShow(s, "director");
        }
        if (type.equals("Series")) {
            s = new SeriesClass(directorArtist, title, durationOrSeasons, certification, year, genres, castArtists);
            directorArtist.addShow(s, "creator");
        }
        shows.put(title, s);
        for (Artist a : castArtists)
            a.addShow(s, "actor");


        persons.get(adminName).addMedia(s);

        return newArtistCount;
    }

    /**
     * Adds information about all artists in the show to the system.
     *
     * @param cast     A list of cast members to add information for.
     * @param director The director to add information for.
     * @return The number of new artists added to the system.
     */
    private int handleArtists(List<String> cast, String director) {
        int count = 0;
        try {
            count += addArtistInfo(director, null, null);
        } catch (CineReviewsException ignored) {
        }
        //If artist is already defined, ignore it.
        for (String c : cast) {
            try {
                count += addArtistInfo(c, null, null);
            } catch (CineReviewsException ignored) {
            }
        }
        return count;
    }

    public int addArtistInfo(String name, String birthday, String birthplace) throws CineReviewsException {
        if (!artists.containsKey(name)) {
            artists.put(name, new ArtistClass(name, birthday, birthplace));
            return 1;
        } else {
            Artist a = artists.get(name);

            if (a.getBirthplace() != null || a.getBirthday() != null)
                throw new CineReviewsException(String.format(ARTIST_BIO_EXISTS, name));

            a.addInfo(birthplace, birthday);
            return 0;
        }
    }

    public Artist getArtist(String name) throws CineReviewsException {
        if (!artists.containsKey(name)) throw new CineReviewsException(String.format(ARTIST_NOT_FOUND, name));
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

    public Iterator<Show> getShowsFromGenres(List<String> genres) throws CineReviewsException {
        //This was my first time using predicates and lambda expressions, I have little Idea what this does.
        Predicate<Show> condition = show -> show.containsAllGenres(genres);
        return getShowsFromCondition(condition);
    }

    public Iterator<Show> getShowsFromYear(int year) throws CineReviewsException {
        return getShowsFromCondition(show -> show.getYear() == year);
    }

    /**
     * Gets an iterator over shows that match a certain condition.
     *
     * @param condition The condition to check for in shows.
     * @return An iterator over shows that match the given condition.
     * @throws CineReviewsException If no show matches the given condition.
     */
    private Iterator<Show> getShowsFromCondition(Predicate<Show> condition) throws CineReviewsException {
        List<Show> toReturn = new ArrayList<>();

        for (Show s : shows.values()) {
            if (condition.test(s))
                toReturn.add(s);
        }

        if (toReturn.isEmpty()) throw new CineReviewsException(NO_SHOW_WITH_CRITERIA);
        toReturn.sort(new ShowComparator());
        return toReturn.iterator();
    }

    public Iterator<SortedSet<Artist>> getNoCollaborationSets(AtomicInteger size) throws CineReviewsException {
        if (artists.isEmpty()) throw new CineReviewsException(NO_ARTISTS);

        SortedSet<SortedSet<Artist>> powerSet = artistsPowerSet();
        Map<String, SortedSet<String>> collabMatrix = createCollaborationMatrix();
        SortedSet<SortedSet<Artist>> candidates = new TreeSet<>(new SetComparator());

        for (SortedSet<Artist> subset : powerSet) {
            boolean hasCollaborated = false;
            for (Artist artist1 : subset) {
                for (Artist artist2 : subset) {
                    if (!artist1.equals(artist2)) {
                        SortedSet<String> collaborations = collabMatrix.get(artist1.getName());
                        if (collaborations.contains(artist2.getName())) {
                            hasCollaborated = true;
                            break;
                        }
                    }
                }
                if (hasCollaborated) {
                    break;
                }
            }
            if (!hasCollaborated) {
                candidates.add(subset);
            }
        }

        return isolateLargestSets(candidates, size).iterator();
    }

    private SortedSet<SortedSet<Artist>> isolateLargestSets(SortedSet<SortedSet<Artist>> candidates, AtomicInteger size) throws CineReviewsException {
        int max = 0;
        SortedSet<SortedSet<Artist>> finalSet = new TreeSet<>(new SetComparator());

        for (SortedSet<Artist> artistSet : candidates) {
            if (artistSet.size() > max) {
                max = artistSet.size();
                finalSet.clear();
            }
            if (artistSet.size() == max) {
                finalSet.add(artistSet);
            }
        }
        if (max < 2) throw new CineReviewsException(NO_AVOIDERS);
        size.set(max);
        return finalSet;
    }

    private SortedSet<SortedSet<Artist>> artistsPowerSet() {
        SortedSet<SortedSet<Artist>> subsets = new TreeSet<>(new SetComparator());
        subsets.add(new TreeSet<>(Comparator.comparing(Artist::getName)));

        for (Artist artist : artists.values()) {
            SortedSet<SortedSet<Artist>> current = new TreeSet<>(new SetComparator());
            for (SortedSet<Artist> set : subsets) {
                SortedSet<Artist> newSet = new TreeSet<>(set);
                newSet.add(artist);
                current.add(newSet);
            }
            subsets.addAll(current);
        }
        return subsets;
    }

    private Map<String, SortedSet<String>> createCollaborationMatrix() {
        Map<String, SortedSet<String>> result = new TreeMap<>();

        for (Map.Entry<String, Artist> m : artists.entrySet())
            result.put(m.getKey(), m.getValue().collaboratedArtists());

        return result;
    }

    public Iterator<String> getMostFrequentCollaborators(AtomicInteger maxCollab) throws CineReviewsException {
        if (artists.isEmpty()) throw new CineReviewsException(NO_ARTISTS);

        Map<String[], Integer> collaborationCount = getCollaborationCounts();

        try {
            int maxCollaborations = Collections.max(collaborationCount.values());


            List<String> mostFrequentCollaborators = new ArrayList<>();
            for (Map.Entry<String[], Integer> entry : collaborationCount.entrySet()) {
                if (entry.getValue() == maxCollaborations) {
                    String[] pair = entry.getKey();
                    mostFrequentCollaborators.add(pair[0] + " and " + pair[1]);
                }
            }

            maxCollab.set(maxCollaborations);
            Collections.sort(mostFrequentCollaborators);
            return mostFrequentCollaborators.iterator();

        } catch (NoSuchElementException n) {
            throw new CineReviewsException(NO_COLLABORATIONS);
        }
    }

    private Map<String[], Integer> getCollaborationCounts() {
        Map<String[], Integer> collaborationCount = new HashMap<>();
        List<Artist> artistList = new ArrayList<>(artists.values());
        for (int i = 0; i < artistList.size(); i++) {
            for (int j = i + 1; j < artistList.size(); j++) {
                Artist artist1 = artistList.get(i);
                Artist artist2 = artistList.get(j);
                int count = 0;
                Iterator<Map.Entry<Show, String>> artist1Shows = artist1.getWorkedShows();
                while (artist1Shows.hasNext()) {
                    Show show = artist1Shows.next().getKey();
                    if (show.hasArtist(artist2.getName())) {
                        count++;
                    }
                }
                String[] pair = {artist1.getName(), artist2.getName()};
                collaborationCount.put(pair, count);
            }
        }
        return collaborationCount;
    }
}
