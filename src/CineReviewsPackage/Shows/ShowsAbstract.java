package CineReviewsPackage.Shows;

import CineReviewsPackage.Persons.Person;

import java.util.*;

/**
 * This class is an abstract class that implements the CineReviewsPackage.Shows.Show interface.
 * It is used to create a Movie or a Series. We assume that a Director and a Creator are the same thing.
 */

abstract class ShowsAbstract implements Show {

    private static final String DIRECTOR = "Director"; //Director or Creator are considered the same thing
    private static final String ACTOR = "Actor";
    protected String certification;
    protected int year;
    protected List<String> genres;
    protected SortedMap<String, Person> persons; //String is the role of the person


    public ShowsAbstract(Person director, String certification, int year, List<String> genres, List<Person> cast) {
        this.certification = certification;
        this.year = year;
        this.genres = genres;
        persons = new TreeMap<>();
        initializeCrew(director,cast);
    }

    private void initializeCrew(Person director, List<Person> cast) {
        persons.put(DIRECTOR, director);
        for (Person o : cast)
            persons.put(ACTOR, o);
    }

}
