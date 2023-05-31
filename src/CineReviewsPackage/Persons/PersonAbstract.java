package CineReviewsPackage.Persons;

import CineReviewsPackage.Exceptions.UserException;

import java.util.LinkedList;

abstract class PersonAbstract<T> implements Person {
    protected LinkedList<T> media;

    public PersonAbstract() {
        media = new LinkedList<>();
    }

    public abstract void addMedia(Object o);

    public int numberUploads(){
        return media.size();
    }
    public void authenticate(String password) throws UserException {}
}
