package CineReviewsPackage.Persons;

import CineReviewsPackage.Exceptions.UserException;

public interface Person {

    boolean isAdministrator();

    public void addMedia(Object e);

    int numberUploads();

    void authenticate(String password) throws UserException;

    boolean isArtist();
}
