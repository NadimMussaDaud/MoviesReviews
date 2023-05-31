package CineReviewsPackage.Persons;

import CineReviewsPackage.Review;

public class AudienceClass extends PersonAbstract<Review>{
    public AudienceClass() {
    }

    @Override
    public boolean isAdministrator() {
        return false;
    }

    @Override
    public boolean isArtist() {
        return false;
    }
}
