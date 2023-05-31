package CineReviewsPackage.Shows;

import CineReviewsPackage.Persons.Person;

import java.util.List;
import java.util.SortedMap;

public class SeriesClass extends ShowsAbstract {
    private int seasons;

    public SeriesClass(Person director, int seasons, String certification, int year, List<String> genres, SortedMap<String, Person> cast){
        super(director, certification, year, genres, cast);
        this.seasons = seasons;
    }

    public int getSeasonsOrDuration(){
        return seasons;
    }
}
