package CineReviewsPackage.Persons;

import CineReviewsPackage.Shows.Reviews.Review;

/**
 * Represents a critic type of person, where its reviews are worth more.
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public class CriticClass extends PersonAbstract<Review>{
    public CriticClass(String name) {
        super(name);
    }
    public void addMedia(Object o){
        super.media.add((Review) o);
    }

}
