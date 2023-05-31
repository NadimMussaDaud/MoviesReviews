import java.util.LinkedList;

abstract class PersonAbstract<T> implements Person {

    protected String name;
    protected LinkedList<T> media;
    public PersonAbstract(String name) {
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
