package CineReviewsPackage.Persons;

import CineReviewsPackage.Review;

public class CriticClass extends PersonAbstract<Review>{
    public CriticClass() {
        super();
    }

    public void addMedia(Object o){
        super.media.add((Review) o);
    }

}
