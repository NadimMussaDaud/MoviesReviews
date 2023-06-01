package CineReviewsPackage.Shows.Reviews;

import CineReviewsPackage.Persons.Person;

public interface Review {
    String getReviewText();

    ReviewValue getRating();

    Person getReviewer();
}
