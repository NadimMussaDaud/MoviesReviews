import Exceptions.UserException;

public interface Person {

    boolean isAdministrator();

    int numberUploads();

    void authenticate(String password) throws UserException;

    boolean isArtist();
}
