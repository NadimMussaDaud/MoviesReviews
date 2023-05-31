import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class is an abstract class that implements the Show interface.
 * It is used to create a Movie or a Series. We assume that a Director and a Creator are the same thing.
 */

abstract class ShowsAbstract implements Show {

    private static final String DIRECTOR = "Director"; //Director or Creator are considered the same thing
    private static final String ACTOR = "Actor";
    protected String title, certification;
    protected int year;
    protected String[] genres;
    protected HashMap<String, Person> persons; //String is the role of the person


    public ShowsAbstract(String title, Person director, String certification, int year, String[] genres, LinkedList cast) {
        this.title = title;
        this.certification = certification;
        this.year = year;
        this.genres = genres;
        persons = new HashMap<>();
        initializeCrew(director,cast);
    }

    private void initializeCrew(Person director ,LinkedList cast) {
        persons.put(DIRECTOR, director);
        for (Object o : cast) {
            Person p = (Person) o;
            persons.put(ACTOR, p);
        }
    }

}
