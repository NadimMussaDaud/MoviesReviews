package CineReviewsPackage.Persons;

import CineReviewsPackage.Exceptions.UserException;

public interface Person {

    String getName();

    void addMedia(Object o);

    int numberUploads();

    void authenticate(String password) throws UserException;
}
