import Exceptions.UserException;

public interface User {
    boolean isAdministrator();
    int numberUploads();

    void authenticate(String password) throws UserException;
}
