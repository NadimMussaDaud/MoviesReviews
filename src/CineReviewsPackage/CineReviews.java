package CineReviewsPackage;

import CineReviewsPackage.Exceptions.CineReviewsException;
import CineReviewsPackage.Exceptions.UserException;
import CineReviewsPackage.Persons.Person;
import CineReviewsPackage.Shows.Show;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface CineReviews {
    boolean hasType(String type);

    void register(String type, String name, String password) throws CineReviewsException;

    Iterator<Map.Entry<String, Person>> getPersons() throws CineReviewsException;

    boolean isAdmin(String admin) throws CineReviewsException ;

    public void authenticate(String name, String password) throws CineReviewsException, UserException;

    boolean hasShow(String title);

    int addShow(String title, String director, int duration, String certification, int year,
                 List<String> genres, List<String> cast, String type, String adminName) throws CineReviewsException;

    public Iterator<Map.Entry<String, Show>> getShows() throws CineReviewsException;
}
