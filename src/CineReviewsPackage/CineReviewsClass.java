package CineReviewsPackage;

import CineReviewsPackage.Exceptions.*;
import CineReviewsPackage.Persons.*;
import CineReviewsPackage.Shows.*;
import CineReviewsPackage.Shows.Reviews.Review;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

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

        List<SortedSet<Artist>> result = new ArrayList<>();
        int maxSize = 0;
        for (Artist artist : artists.values()) {
            List<SortedSet<Artist>> newResult = new ArrayList<>(result);
            for (SortedSet<Artist> resultSet : result) {
                if (!hasOverlap(artist, resultSet)) {
                    SortedSet<Artist> newSet = new TreeSet<>(Comparator.comparing(Artist::getName));
                    newSet.addAll(resultSet);
                    newSet.add(artist);
                    if (newSet.size() > maxSize) {
                        maxSize = newSet.size();
                        newResult.clear();
                    }
                    if (newSet.size() == maxSize) {
                        newResult.add(newSet);
                    }
                }
            }
            SortedSet<Artist> singleSet = new TreeSet<>(Comparator.comparing(Artist::getName));
            singleSet.add(artist);
            if (singleSet.size() > maxSize) {
                maxSize = singleSet.size();
                newResult.clear();
            }
            if (singleSet.size() == maxSize) {
                newResult.add(singleSet);
            }
            result = newResult;
        }
        if (result.isEmpty() || maxSize < 2) throw new CineReviewsException(NO_AVOIDERS);
        size.set(maxSize);
        return result.iterator();
    }

    // This method checks if an artist has any shows in common with a set of artists
    private boolean hasOverlap(Artist artist, Set<Artist> artistSet) {
        // Iterate over each artist in the set of artists
        for (Artist s : artistSet) {
            if(artist.hasWorkedWith(s.getName()))
                return true;
        }
        return false;
    }

    public Iterator<String> getMostFrequentCollaborators(AtomicInteger maxCollab) throws CineReviewsException {
        if (artists.isEmpty()) throw new CineReviewsException(NO_ARTISTS);

        Map<String[], Integer> collaborationCount = getCollaborationCounts();

        int maxCollaborations = Collections.max(collaborationCount.values());
        if (maxCollaborations == 0) throw new CineReviewsException(NO_COLLABORATIONS);

        List<String> mostFrequentCollaborators = new ArrayList<>();
        for (Map.Entry<String[], Integer> entry : collaborationCount.entrySet()) {
            if (entry.getValue() == maxCollaborations) {
                String[] pair = entry.getKey();
                mostFrequentCollaborators.add(pair[1] + "and" + pair[2]);
            }
        }

        maxCollab.set(maxCollaborations);
        Collections.sort(mostFrequentCollaborators);
        return mostFrequentCollaborators.iterator();
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
