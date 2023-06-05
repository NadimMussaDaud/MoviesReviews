package CineReviewsPackage.Persons;

import CineReviewsPackage.Shows.Reviews.Review;

/**
 * Represents an audience type of person.
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public class AudienceClass extends PersonAbstract<Review>{
    public AudienceClass(String name) {
        super(name);
    }
    public void addMedia(Object o){
        super.media.add((Review) o);
    }

}
