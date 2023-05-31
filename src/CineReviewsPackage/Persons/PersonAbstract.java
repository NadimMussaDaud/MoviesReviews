package CineReviewsPackage.Persons;

import CineReviewsPackage.Exceptions.UserException;

import java.util.LinkedList;

abstract class PersonAbstract<T> implements Person {
    protected LinkedList<T> media;

    public PersonAbstract() {
        media = new LinkedList<>();
    }

    public void addMedia(Object e){
        media.add((T)e);
    }

    public int numberUploads(){
        return media.size();
    }

    public abstract boolean isAdministrator();

    public void authenticate(String password) throws UserException {}
}
