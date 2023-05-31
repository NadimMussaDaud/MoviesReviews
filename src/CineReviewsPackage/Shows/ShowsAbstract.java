package CineReviewsPackage.Shows;

import CineReviewsPackage.Persons.Person;

import java.util.*;

/**
 * This class is an abstract class that implements the CineReviewsPackage.Shows.Show interface.
 * It is used to create a Movie or a Series. We assume that a Director and a Creator are the same thing.
 */

abstract class ShowsAbstract implements Show {

    protected String certification;
    protected int year;
    protected Person creator;
    protected List<String> genres;
    protected SortedMap<String, Person> persons;


    public ShowsAbstract(Person creator, String certification, int year, List<String> genres, SortedMap<String, Person> cast) {
        this.certification = certification;
        this.year = year;
        this.creator = creator;
        this.genres = genres;
        persons = new TreeMap<>();
        persons.putAll(cast);
    }

    public String getCertification() {
        return certification;
    }

    public int getYear() {
        return year;
    }

    public Person getCreator(){
        return creator;
    }

    public Iterator<String> getGenres() {
        return genres.iterator();
    }

    public Iterator<Map.Entry<String, Person>> getShowsPersons() {
        return persons.entrySet().iterator();
    }

    public abstract int getSeasonsOrDuration();
}
