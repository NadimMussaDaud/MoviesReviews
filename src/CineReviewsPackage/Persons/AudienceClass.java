package CineReviewsPackage.Persons;

import CineReviewsPackage.Review;

public class AudienceClass extends PersonAbstract<Review>{
    public AudienceClass() {
        super();
    }

    public void addMedia(Object o){
        super.media.add((Review) o);
    }

}
