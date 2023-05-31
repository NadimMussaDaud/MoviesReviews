package CineReviewsPackage.Shows.Reviews;

import CineReviewsPackage.Persons.CriticClass;
import CineReviewsPackage.Persons.Person;

import java.util.Comparator;

public class ReviewComparator implements Comparator<Review> {
    @Override
    public int compare(Review o1, Review o2) {
        Person reviewer1 = o1.getReviewer();
        Person reviewer2 = o2.getReviewer();

        // Sort by reviewer's class type (CriticClass first)
        if (reviewer1 instanceof CriticClass && !(reviewer2 instanceof CriticClass)) {
            return -1;
        } else if (!(reviewer1 instanceof CriticClass) && reviewer2 instanceof CriticClass) {
            return 1;
        }

        // Sort by rating (highest to lowest)
        int ratingComparison = o2.getRating().compareTo(o1.getRating());
        if (ratingComparison != 0) {
            return ratingComparison;
        }

        // Sort alphabetically by reviewer's username
        return reviewer1.getName().compareTo(reviewer2.getName());
    }
}
