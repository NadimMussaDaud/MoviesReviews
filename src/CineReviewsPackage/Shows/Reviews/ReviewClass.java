package CineReviewsPackage.Shows.Reviews;

import CineReviewsPackage.Persons.Person;

/**
 * Class implementing the Review interface.
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public class ReviewClass implements Review{
    private final Person reviewer;
    private final String reviewText;
    private final ReviewValue rating;

    /**
     * Constructs a new ReviewClass object with the given review text, rating, and reviewer.
     * @param reviewText The text of the review.
     * @param rating The rating of the review as a String.
     * @param reviewer The Person who wrote the review.
     */
    public ReviewClass(String reviewText, String rating, Person reviewer) {
        this.reviewText = reviewText;
        this.rating = ReviewValue.fromText(rating);
        this.reviewer = reviewer;
    }

    public String getReviewText() {
        return reviewText;
    }

    public ReviewValue getRating() {
        return rating;
    }

    public Person getReviewer() {
        return reviewer;
    }
}
