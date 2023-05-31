package CineReviewsPackage.Shows.Reviews;

import CineReviewsPackage.Persons.Person;

public class ReviewClass implements Review{
    Person reviewer;
    String reviewText;
    ReviewValue rating;

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
