import java.util.LinkedList;

public class MovieClass extends ShowsAbstract {

    private int duration;
    public MovieClass(String title, Person director, int duration, String certification, int year, String[] genres, LinkedList<Person> cast) {
        super(title, director,certification, year, genres, cast);
        this.duration = duration;
    }
}
