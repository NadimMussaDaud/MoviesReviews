package CineReviewsPackage.Shows;

import CineReviewsPackage.Exceptions.ShowException;
import CineReviewsPackage.Persons.CriticClass;
import CineReviewsPackage.Persons.Person;
import CineReviewsPackage.Shows.Reviews.Review;
import CineReviewsPackage.Shows.Reviews.ReviewClass;
import CineReviewsPackage.Shows.Reviews.ReviewComparator;

import javax.naming.NameNotFoundException;
import java.util.*;

abstract class ShowsAbstract implements Show {
    protected final String title; //name is stored only to facilitate the Comparator.
    protected final String certification;
    protected final int year;
    protected final Artist creator;
    protected List<String> genres;
    protected final List<Artist> cast;
    protected final SortedSet<Review> reviews;


    public ShowsAbstract(Artist creator, String title, String certification, int year, List<String> genres, List<Artist> cast) {
        this.title = title;
        this.certification = certification;
        this.year = year;
        this.creator = creator;
        this.genres = genres;  //Insertion order must be preserved.
        this.cast = new LinkedList<>(); //Insertion order must be preserved.
        this.cast.addAll(cast);
        reviews = new TreeSet<>(new ReviewComparator());
    }

    public String getCertification() {
        return certification;
    }

    public int getYear() {
        return year;
    }

    public Artist getCreator(){
        return creator;
    }

    public Iterator<String> getGenres() {
        return genres.iterator();
    }

    public Iterator<Artist> getShowsPersons() {
        return cast.iterator();
    }

    public abstract int getSeasonsOrDuration();

    public int addReview(String reviewText, String rating, Person reviewer) throws ShowException {
        Review r = new ReviewClass(reviewText, rating, reviewer);
        for(Review tmp : reviews)
            if(reviewer.getName().equals(tmp.getReviewer().getName())) throw new ShowException();

        reviews.add(r);
        reviewer.addMedia(r);
        return reviews.size();
    }

    public Iterator<Review> getReviews() throws ShowException{
        if(reviews.isEmpty()) throw new ShowException();
        return reviews.iterator();
    }

    public double getAverageReviews(){
        double count = 0;
        double divider = 0;

        for(Review r : reviews){
            if(r.getReviewer() instanceof  CriticClass){
                count += 5*r.getRating().getNumber();
                divider += 5;
            } else{
                count += r.getRating().getNumber();
                divider++;
            }
        }

        if(count == 0) return 0; //stops it from returning NaN and messing up the comparator.
        return count/divider;
    }

    public boolean containsAllGenres(List<String> toCheck){
        return new HashSet<>(genres).containsAll(toCheck);
    }

    public String getTitle() {
        return title;
    }

    public boolean hasArtist(String name){
        if(name.equals(creator.getName())) return true;

        for (Artist a : cast)
            if(name.equals(a.getName()))
                return true;

        return false;
    }
}
