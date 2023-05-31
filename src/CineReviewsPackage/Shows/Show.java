package CineReviewsPackage.Shows;

import CineReviewsPackage.Exceptions.ShowException;
import CineReviewsPackage.Persons.Person;
import CineReviewsPackage.Shows.Reviews.Review;

import java.util.Iterator;
import java.util.Map;

public interface Show {
    public String getCertification();

    public int getYear();

    public Person getCreator();

    public Iterator<String> getGenres();

    public Iterator<Map.Entry<String, Person>> getShowsPersons();

    public int getSeasonsOrDuration();

    public int addReview(String reviewText, String rating, Person reviewer) throws ShowException;

    public Iterator<Review> getReviews() throws ShowException;

    public int getAverageReviews();
}
