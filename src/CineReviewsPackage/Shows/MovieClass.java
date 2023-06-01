package CineReviewsPackage.Shows;

import CineReviewsPackage.Persons.Person;
import java.util.List;

import java.util.LinkedList;
import java.util.SortedMap;

public class MovieClass extends ShowsAbstract {

    private final int duration;
    public MovieClass(Person director, String title, int duration, String certification, int year, List<String> genres, SortedMap<String, Person> cast) {
        super(director, title, certification, year, genres, cast);
        this.duration = duration;
    }

    public int getSeasonsOrDuration(){
        return duration;
    }
}
