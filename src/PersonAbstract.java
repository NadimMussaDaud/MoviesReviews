import Exceptions.UserException;

import java.util.LinkedList;

abstract class PersonAbstract<T> implements Person {

    protected String name;
    protected LinkedList<T> media;

    public PersonAbstract() {
        media = new LinkedList<>();
    }
    public int numberUploads(){
        return media.size();
    }

    public abstract boolean isAdministrator();



    public void authenticate(String password) throws UserException {}
}
