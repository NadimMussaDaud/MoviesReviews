package CineReviewsPackage.Shows;

import CineReviewsPackage.Persons.Person;
import java.util.List;

import java.util.LinkedList;
import java.util.SortedMap;

public class MovieClass extends ShowsAbstract {

    private int duration;
    public MovieClass(Person director, int duration, String certification, int year, List<String> genres, SortedMap<String, Person> cast) {
        super(director,certification, year, genres, cast);
        this.duration = duration;
    }

    public int getSeasonsOrDuration(){
        return duration;
    }
}
