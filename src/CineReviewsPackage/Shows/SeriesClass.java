package CineReviewsPackage.Shows;

import CineReviewsPackage.Persons.Person;

import java.util.List;

public class SeriesClass extends ShowsAbstract {
    private int seasons;

    public SeriesClass(Person director, String certification, int year, List<String> genres, List<Person> cast, int seasons){
        super(director, certification, year, genres, cast);
        this.seasons = seasons;
    }

}
