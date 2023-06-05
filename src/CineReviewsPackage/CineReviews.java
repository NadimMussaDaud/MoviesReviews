package CineReviewsPackage;

import CineReviewsPackage.Exceptions.CineReviewsException;
import CineReviewsPackage.Exceptions.UserException;
import CineReviewsPackage.Persons.Person;
import CineReviewsPackage.Shows.Artist;
import CineReviewsPackage.Shows.Reviews.Review;
import CineReviewsPackage.Shows.Show;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This interface represents the CineReviews system.
 * It provides methods for registering and authenticating users,
 * adding shows and artists, getting information about shows and artists,
 * adding reviews and getting statistics about the system.
 *
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public interface CineReviews {

    /**
     * Registers a new user in the system.
     * @param type The type of user to register.
     * @param name The name of the user to register.
     * @param password The password of the user to register.
     * @throws CineReviewsException If the given type does not exist.
     */
    void register(String type, String name, String password) throws CineReviewsException;

    /**
     * Gets an iterator over the persons registered in the system.
     * @return An iterator over entries where each entry contains a person's name
     * and their corresponding Person object.
     * @throws CineReviewsException If there are no users on the system.
     */
    Iterator<Map.Entry<String, Person>> getPersons() throws CineReviewsException;

    /**
     * Authenticates a user in the system using their name and password.
     * @param name The name of the user to authenticate.
     * @param password The password of the user to authenticate.
     * @throws CineReviewsException If the user does not exist or is not an admin.
     * @throws UserException If authentication fails due to incorrect credentials.
     */
    void authenticate(String name, String password) throws CineReviewsException, UserException;

    /**
     * Adds a new show to the system.
     *
     * @param title         title of show
     * @param director      director of show
     * @param duration      duration or number of seasons
     * @param certification certification of show
     * @param year          year show was released
     * @param genres        list with genres
     * @param cast          list with cast members
     * @param type          type of show (movie or series)
     *
     *@return number of new actors registered
     *@throws CineReviewsException if the show already exists.
     */
    int addShow(String title, String director, int duration, String certification, int year,
                List<String> genres, List<String> cast, String type, String adminName) throws CineReviewsException;

    /**
     * Adds information about an artist in the system.
     *
     *@param name       name of artist
     *@param birthplace birthplace of artist
     *@param birthday   birthday of artist
     *
     *@return 1 if a new artist was registered, 0 if its info was updated.
     *@throws CineReviewsException if the artist already has information stored.
     */
    int addArtistInfo(String name, String birthplace, String birthday) throws CineReviewsException;

    /**
     * Gets an Artist object for an artist with a given name.
     *
     *@param name name of artist to get
     *
     *@return Artist object for artist with given name
     *@throws CineReviewsException no artist is found.
     */
    Artist getArtist(String name) throws CineReviewsException;

    /**
     * Gets an iterator over the shows in the system.
     *
     *@return iterator over entries where each entry contains a show's title and its corresponding Show object
     *@throws CineReviewsException if there are no shows.
     */
    Iterator<Map.Entry<String, Show>> getShows() throws CineReviewsException;

    /**
     * Adds a review for a show with a given title by a user with a given name.
     *
     *@param userName       name of user adding review
     *@param title          title of show being reviewed
     *@param review         text of review
     *@param classification classification given in review
     *
     *@return the amount of reviews for this show.
     *@throws CineReviewsException if adding review fails
     */
    int addReview(String userName, String title, String review, String classification) throws CineReviewsException;

    /**
     * Gets an iterator over reviews for a show with a given title.
     *
     *@param showName title of show to get reviews for
     *
     *@return iterator over reviews for show with given title
     *@throws CineReviewsException if there are no reviews.
     */
    Iterator<Review> getReviews(String showName) throws CineReviewsException;

    /**
     * Gets average rating for reviews for a show with a given title.
     *
     *@param showName title of show to get average rating for reviews for
     *
     *@return average rating for reviews for show with given title
     */
    double getAverageRating(String showName);

    /**
     * Gets an iterator over shows that contain all genres in a given list.
     *
     *@param genres list with genres to check for in shows
     *
     *@return iterator over shows that contain all genres in given list
     *@throws CineReviewsException if no shows with the given genres were found.
     */
    Iterator<Show> getShowsFromGenres(List<String> genres) throws CineReviewsException;

    /**
     * Gets an iterator over shows that were released in a given year.
     *
     *@param year year to check for in shows' release years
     *
     *@return iterator over shows that were released in given year
     *@throws CineReviewsException if no shows released on the given year were found.
     */
    Iterator<Show> getShowsFromYear(int year) throws CineReviewsException;

    /**
     * Gets an iterator over sets containing artists that have never collaborated with each other.
     *
     *@param size size is set to size of largest set returned by this method (output parameter)
     *
     *@return iterator over sets containing artists that have never collaborated with each other. Sets are sorted alphabetically.
     *@throws CineReviewsException if there are no artists registered, or if there are no artists that have not collaborated with anyone.
     */
    Iterator<SortedSet<Artist>> getNoCollaborationSets(AtomicInteger size) throws CineReviewsException;

    /**
     * Gets an iterator over names of most frequent collaborators.
     *
     *@param maxCollab maxCollab is set to maximum number of collaborations found (output parameter)
     *
     *@return iterator over names of most frequent collaborators. Names are sorted alphabetically.
     *@throws CineReviewsException if there are no artists or if there are no collaborations between artists.
     */
    Iterator<String> getMostFrequentCollaborators(AtomicInteger maxCollab) throws CineReviewsException;
}
