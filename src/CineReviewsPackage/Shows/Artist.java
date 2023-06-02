package CineReviewsPackage.Shows;

import java.util.Iterator;
import java.util.Map;

/**
 * This interface represents an Artist in the CineReviews project.
 * It provides methods for adding information about the artist,
 * adding shows the artist has worked on, getting information about
 * the artist and checking if the artist has worked with another artist.
 *
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public interface Artist {
    /**
     * Adds information about the artist's birthplace and birthday.
     * @param birthplace The birthplace of the artist.
     * @param birthday The birthday of the artist.
     */
    void addInfo(String birthplace, String birthday);

    /**
     * Adds a show that the artist has worked on and their role in it.
     * @param s The show that the artist has worked on.
     * @param role The role of the artist in the show.
     */
    void addShow(Show s, String role);

    /**
     * Gets the name of the artist.
     * @return The name of the artist.
     */
    String getName();

    /**
     * Gets the birthday of the artist.
     * @return The birthday of the artist.
     */
    String getBirthday();

    /**
     * Gets the birthplace of the artist.
     * @return The birthplace of the artist.
     */
    String getBirthplace();

    /**
     * Gets an iterator over the shows that the artist has worked on
     * and their roles in them.
     * @return An iterator over entries where each entry contains a show
     * and the role of the artist in it.
     */
    Iterator<Map.Entry<Show, String>> getWorkedShows();

    /**
     * Checks if this Artist has worked with another Artist with a given name
     *
     * @param name name to check
     *
     *@return true if this Artist has worked with another Artist with a given name
     */
    boolean hasWorkedWith(String name);
}
