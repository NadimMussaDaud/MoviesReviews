package CineReviewsPackage.Exceptions;

/**
 * This class represents a custom exception for the CineReviews project.
 * It extends the Exception class and provides a constructor that takes a message as an argument.
 *
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public class UserException extends Exception{
    /**
     * Constructor for the UserException class.
     * @param message The message to be passed to the superclass constructor.
     */
    public UserException(String message){
        super(message);
    }
}
