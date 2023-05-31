package CineReviewsPackage.Persons;

import CineReviewsPackage.Shows.Reviews.Review;

public class AudienceClass extends PersonAbstract<Review>{
    public AudienceClass(String name) {
        super(name);
    }

    public void addMedia(Object o){
        super.media.add((Review) o);
    }

}
