package CineReviewsPackage.Shows;

import CineReviewsPackage.Persons.Person;

import java.util.Iterator;
import java.util.Map;

public interface Show {
    public String getCertification();

    public int getYear();

    public Person getCreator();

    public Iterator<String> getGenres();

    public Iterator<Map.Entry<String, Person>> getShowsPersons();

    public int getSeasonsOrDuration();
}
