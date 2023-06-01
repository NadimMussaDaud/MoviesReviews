package CineReviewsPackage.Shows;

import CineReviewsPackage.Persons.Person;

import java.util.List;
import java.util.SortedMap;

public class SeriesClass extends ShowsAbstract {
    private final int seasons;

    public SeriesClass(Person director, String title, int seasons, String certification, int year, List<String> genres, SortedMap<String, Person> cast){
        super(director, title, certification, year, genres, cast);
        this.seasons = seasons;
    }

    public int getSeasonsOrDuration(){
        return seasons;
    }
}
