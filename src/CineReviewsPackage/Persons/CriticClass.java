package CineReviewsPackage.Persons;

import CineReviewsPackage.Shows.Reviews.Review;

public class CriticClass extends PersonAbstract<Review>{
    public CriticClass(String name) {
        super(name);
    }

    public void addMedia(Object o){
        super.media.add((Review) o);
    }

}
