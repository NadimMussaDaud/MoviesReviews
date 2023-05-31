import java.util.LinkedList;

abstract class UserAbstract<T> implements User {

    protected String name;
    protected LinkedList<T> media;
    public UserAbstract(String name) {
        this.name = name;
        media = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public int numberUploads(){
        return media.size();
    }

    public boolean isAdministrator(){
        return false;
    }

}
