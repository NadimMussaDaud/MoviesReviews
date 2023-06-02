package CineReviewsPackage;

import CineReviewsPackage.Exceptions.CineReviewsException;
import CineReviewsPackage.Exceptions.UserException;
import CineReviewsPackage.Persons.Person;
import CineReviewsPackage.Shows.Artist;
import CineReviewsPackage.Shows.Reviews.Review;
import CineReviewsPackage.Shows.Show;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicInteger;

public interface CineReviews {
    boolean hasType(String type);

    void register(String type, String name, String password) throws CineReviewsException;

    Iterator<Map.Entry<String, Person>> getPersons() throws CineReviewsException;

    void authenticate(String name, String password) throws CineReviewsException, UserException;


    int addShow(String title, String director, int duration, String certification, int year,
                List<String> genres, List<String> cast, String type, String adminName) throws CineReviewsException;

    int addArtistInfo(String name, String birthplace, String birthday) throws CineReviewsException;

    Artist getArtist(String name) throws CineReviewsException;

    Iterator<Map.Entry<String, Show>> getShows() throws CineReviewsException;

    int addReview(String userName, String title, String review, String classification) throws CineReviewsException;

    Iterator<Review> getReviews(String showName) throws CineReviewsException;

    double getAverageRating(String showName);

    Iterator<Show> getShowsFromGenres(List<String> genres) throws CineReviewsException;

    Iterator<Show> getShowsFromYear(int year) throws CineReviewsException;
    Iterator<SortedSet<Artist>> getNoCollaborationSets(AtomicInteger size) throws CineReviewsException;

    Iterator<String> getMostFrequentCollaborators(AtomicInteger maxCollab) throws CineReviewsException;
}
