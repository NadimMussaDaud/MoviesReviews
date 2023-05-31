import Exceptions.UserException;

import java.util.LinkedList;

abstract class UserAbstract<T> implements User {
    protected LinkedList<T> media;
    public UserAbstract() {
        media = new LinkedList<>();
    }
    public int numberUploads(){
        return media.size();
    }

    public boolean isAdministrator(){
        return false;
    }

    public void authenticate(String password) throws UserException {}
}
