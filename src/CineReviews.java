import Exceptions.CineReviewsException;
import Exceptions.NoUserException;
import Exceptions.NotAdministratorException;
import Exceptions.UserException;

import java.util.Iterator;
import java.util.Map;

public interface CineReviews {
    boolean hasType(String type);

    void register(String type, String name, String password) throws CineReviewsException;

    boolean hasUsers();
    Iterator<Map.Entry<String, Person>> getPersons() throws CineReviewsException;

    boolean isAdmin(String admin) throws CineReviewsException ;

    public void authenticate(String name, String password) throws CineReviewsException, UserException;

    boolean hasShow(String title);

    void addMovie(String title, String director, int duration, String certification, int year,
                  String[] genres, String[] cast) throws CineReviewsException;
}
