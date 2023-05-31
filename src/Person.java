import Exceptions.UserException;

public interface Person {
    String getName();

    boolean isAdministrator();

    int numberUploads();

    void authenticate(String password) throws UserException;

    boolean isArtist();
}
