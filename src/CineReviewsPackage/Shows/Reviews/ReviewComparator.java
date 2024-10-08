package CineReviewsPackage.Shows.Reviews;

import CineReviewsPackage.Persons.CriticClass;
import CineReviewsPackage.Persons.Person;

import java.util.Comparator;

/**
 * This class implements a comparator for comparing Review objects.
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public class ReviewComparator implements Comparator<Review> {
    /**
     * Compares two Review objects.
     * @param o1 The first Review object to compare.
     * @param o2 The second Review object to compare.
     * @return A negative integer, zero or a positive integer if the first Review object is less than,
     * equal to or greater than the second Review object, respectively.
     */
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
