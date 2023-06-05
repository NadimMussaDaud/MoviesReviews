package CineReviewsPackage.Persons;

import CineReviewsPackage.Exceptions.UserException;

import java.util.LinkedList;
import java.util.List;

abstract class PersonAbstract<T> implements Person {
    private final String name; //storing the name is necessary due to the review sorting process only.
    protected final List<T> media;

    public PersonAbstract(String name) {
        media = new LinkedList<>();
        this.name = name;
    }

    public abstract void addMedia(Object o);
    public String getName(){return name;}
    public int numberUploads(){
        return media.size();
    }
    public void authenticate(String password) throws UserException {}
}
