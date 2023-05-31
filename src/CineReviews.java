import Exceptions.NoUserException;
import Exceptions.NotAdministratorException;

import java.util.Iterator;

public interface CineReviews {
    boolean hasType(String type);

    boolean hasPerson(String name);

    void register(String type, String name, String password);

    boolean hasUsers();
    Iterator<User> getUsers();

    boolean isAdmin(String admin) throws NoUserException, NotAdministratorException;

    boolean correctPassword(String admin, String password);

    boolean hasShow(String title);

    void addMovie(String title, String director, int duration, String certification, int year, String[] genres, String[] cast);
}
