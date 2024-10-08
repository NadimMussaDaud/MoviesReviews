package CineReviewsPackage.Persons;

import CineReviewsPackage.Exceptions.UserException;
import CineReviewsPackage.Shows.Show;

/**
 * Represents an admin type of person, storing login credentials and able to authenticate.
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public class AdminClass extends PersonAbstract<Show>{
    private static final String INVALID_AUTH = "Invalid authentication!";
    private final String password;

    public AdminClass(String name, String password) {
        super(name);
        this.password = password;
    }

    public void addMedia(Object o){
        super.media.add((Show)o);
    }

    @Override
    public void authenticate(String password) throws UserException {
        if(!this.password.equals(password)) throw new UserException(INVALID_AUTH);
    }
}
