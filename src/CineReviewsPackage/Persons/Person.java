package CineReviewsPackage.Persons;

import CineReviewsPackage.Exceptions.UserException;

/**
 * This interface represents a Person in the CineReviews project.
 * It provides methods for getting the name of the person, adding media,
 * getting the number of uploads and authenticating the person.
 *
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public interface Person {

    /**
     * Gets the name of the person.
     * @return The name of the person.
     */
    String getName();

    /**
     * Adds media to the person's uploads.
     * @param o The media to be added.
     */
    void addMedia(Object o);

    /**
     * Gets the number of uploads by the person.
     * @return The number of uploads by the person.
     */
    int numberUploads();

    /**
     * Authenticates the person using a password. Only used by the Admin subclass.
     * @param password The password to be used for authentication.
     * @throws UserException If authentication fails.
     */
    void authenticate(String password) throws UserException;
}
