package CineReviewsPackage.Shows.Reviews;

import CineReviewsPackage.Persons.Person;

/**
 * This interface represents a Review in the CineReviews project.
 * It provides methods for getting information about the review,
 * such as the text of the review, its rating and the person who wrote it.
 *
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public interface Review {
    /**
     * Gets the text of the review.
     * @return The text of the review.
     */
    String getReviewText();

    /**
     * Gets the rating of the review.
     * @return The rating of the review.
     */
    ReviewValue getRating();

    /**
     * Gets the person who wrote the review.
     * @return The person who wrote the review.
     */
    Person getReviewer();
}
