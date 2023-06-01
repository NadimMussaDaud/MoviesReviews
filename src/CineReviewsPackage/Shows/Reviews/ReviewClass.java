package CineReviewsPackage.Shows.Reviews;

import CineReviewsPackage.Persons.Person;

import java.util.Objects;

public class ReviewClass implements Review{
    final Person reviewer;
    final String reviewText;
    final ReviewValue rating;

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
