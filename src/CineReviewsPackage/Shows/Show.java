package CineReviewsPackage.Shows;

import CineReviewsPackage.Exceptions.ShowException;
import CineReviewsPackage.Persons.Person;
import CineReviewsPackage.Shows.Reviews.Review;

import java.util.Iterator;
import java.util.List;

/**
 * This interface represents a Show in the CineReviews project.
 * It provides methods for getting information about the show,
 * adding reviews, getting reviews and checking if the show contains
 * certain genres or artists.
 *
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public interface Show {
    /**
     * Gets the certification of the show.
     * @return The certification of the show.
     */
    String getCertification();

    /**
     * Gets the year of the show.
     * @return The year of the show.
     */
    int getYear();

    /**
     * Gets the creator of the show.
     * @return The creator of the show.
     */
    Artist getCreator();

    /**
     * Gets an iterator over the genres of the show.
     * @return An iterator over the genres of the show.
     */
    Iterator<String> getGenres();

    /**
     * Gets an iterator over the persons involved in the show.
     * @return An iterator over the persons involved in the show.
     */
    Iterator<Artist> getShowsPersons();

    /**
     * Gets the number of seasons or duration of the show.
     * @return The number of seasons or duration of the show.
     */
    int getSeasonsOrDuration();

    /**
     * Adds a review to the show.
     * @param reviewText The text of the review.
     * @param rating The rating of the review.
     * @param reviewer The person who wrote the review.
     * @return The id of the added review.
     * @throws ShowException If the person reviewing has already done so before.
     */
    int addReview(String reviewText, String rating, Person reviewer) throws ShowException;

    /**
     * Gets an iterator over the reviews of the show.
     * @return An iterator over the reviews of the show.
     * @throws ShowException If there are no reviews.
     */
    Iterator<Review> getReviews() throws ShowException;

    /**
     * Gets the average rating of reviews for this show
     *
     * @return average rating
     */
    double getAverageReviews();

    /**
     * Checks if this show contains all genres in a given list
     *
     * @param toCheck list with genres to check
     *
     * @return true if all genres are present in this show
     */
    boolean containsAllGenres(List<String> toCheck);

    /**
     * Returns title of this Show
     *
     * @return title
     */
    String getTitle();

    /**
     * Checks if this Show has an artist with a given name
     *
     * @param name name to check
     *
     *@return true if artist is present in this Show
     */
    boolean hasArtist(String name);
}
