package CineReviewsPackage.Shows;

import CineReviewsPackage.Persons.Person;
import java.util.List;

import java.util.LinkedList;

public class MovieClass extends ShowsAbstract {

    private int duration;
    public MovieClass(Person director, int duration, String certification, int year, List<String> genres, List<Person> cast) {
        super(director,certification, year, genres, cast);
        this.duration = duration;
    }
}
