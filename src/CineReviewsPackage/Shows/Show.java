package CineReviewsPackage.Shows;

import CineReviewsPackage.Exceptions.ShowException;
import CineReviewsPackage.Persons.Person;
import CineReviewsPackage.Shows.Reviews.Review;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface Show {
    String getCertification();

    int getYear();

    Person getCreator();

    Iterator<String> getGenres();

    Iterator<Map.Entry<String, Person>> getShowsPersons();

    int getSeasonsOrDuration();

    int addReview(String reviewText, String rating, Person reviewer) throws ShowException;

    Iterator<Review> getReviews() throws ShowException;

    int getAverageReviews();

    public boolean containsAllGenres(Set<String> toCheck);

    public String getTitle();
}
