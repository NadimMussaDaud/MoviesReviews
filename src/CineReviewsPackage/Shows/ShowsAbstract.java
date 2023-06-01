package CineReviewsPackage.Shows;

import CineReviewsPackage.Exceptions.ShowException;
import CineReviewsPackage.Persons.CriticClass;
import CineReviewsPackage.Persons.Person;
import CineReviewsPackage.Shows.Reviews.Review;
import CineReviewsPackage.Shows.Reviews.ReviewClass;
import CineReviewsPackage.Shows.Reviews.ReviewComparator;

import java.util.*;

/**
 * This class is an abstract class that implements the CineReviewsPackage.Shows.Show interface.
 * It is used to create a Movie or a Series. We assume that a Director and a Creator are the same thing.
 */

abstract class ShowsAbstract implements Show {
    protected final String title; //name is stored only to facilitate de Comparator.
    protected final String certification;
    protected final int year;
    protected final Person creator;
    protected final Set<String> genres;
    protected final SortedMap<String, Person> persons;
    protected final SortedSet<Review> reviews;


    public ShowsAbstract(Person creator, String title, String certification, int year, List<String> genres, SortedMap<String, Person> cast) {
        this.title = title;
        this.certification = certification;
        this.year = year;
        this.creator = creator;
        this.genres = new HashSet<>(genres);
        persons = new TreeMap<>();
        persons.putAll(cast);
        reviews = new TreeSet<>(new ReviewComparator());
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

    public int addReview(String reviewText, String rating, Person reviewer) throws ShowException {
        Review r = new ReviewClass(reviewText, rating, reviewer);
        if(reviews.contains(r)) throw new ShowException();
        reviews.add(r);
        reviewer.addMedia(r);
        return reviews.size();
    }

    public Iterator<Review> getReviews() throws ShowException{
        if(reviews.isEmpty()) throw new ShowException();
        return reviews.iterator();
    }

    public int getAverageReviews(){
        int count = 0;

        for(Review r : reviews)
            count += (r.getReviewer() instanceof CriticClass)? 5*r.getRating().getNumber() : r.getRating().getNumber();

        return count/reviews.size();
    }

    public boolean containsAllGenres(Set<String> toCheck){
        return genres.containsAll(toCheck);
    }

    public String getTitle() {
        return title;
    }
}
