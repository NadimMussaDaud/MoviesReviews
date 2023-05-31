package CineReviewsPackage.Shows.Reviews;

import CineReviewsPackage.Persons.Person;

public interface Review {
    public String getReviewText();

    public ReviewValue getRating();

    public Person getReviewer();
}
