package CineReviewsPackage.Shows;

import CineReviewsPackage.Exceptions.ShowException;
import CineReviewsPackage.Persons.Person;
import CineReviewsPackage.Shows.Reviews.Review;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Show {
    String getCertification();

    int getYear();

    Artist getCreator();

    Iterator<String> getGenres();

    Iterator<Artist> getShowsPersons();

    int getSeasonsOrDuration();

    int addReview(String reviewText, String rating, Person reviewer) throws ShowException;

    Iterator<Review> getReviews() throws ShowException;

    double getAverageReviews();

    public boolean containsAllGenres(List<String> toCheck);

    public String getTitle();

    public boolean hasArtist(String name);
}
