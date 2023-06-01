package CineReviewsPackage.Shows.Reviews;

import CineReviewsPackage.Persons.CriticClass;
import CineReviewsPackage.Persons.Person;

import java.util.Comparator;

public class ReviewComparator implements Comparator<Review> {
    @Override
    public int compare(Review o1, Review o2) {
        Person reviewer1 = o1.getReviewer();
        Person reviewer2 = o2.getReviewer();

        if (reviewer1 instanceof CriticClass && !(reviewer2 instanceof CriticClass)) {
            return -1;
        } else if (!(reviewer1 instanceof CriticClass) && reviewer2 instanceof CriticClass) {
            return 1;
        }

        int ratingComparison = o2.getRating().compareTo(o1.getRating());
        if (ratingComparison != 0) {
            return ratingComparison;
        }

        return reviewer1.getName().compareTo(reviewer2.getName());
    }
}
