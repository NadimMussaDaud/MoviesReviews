package CineReviewsPackage.Shows;

import java.util.Comparator;

/**
 * This class implements a comparator for Show objects.
 * It compares shows based on their average review score,
 * the year they were released, and their title.
 */
public class ShowComparator implements Comparator<Show> {

    /**
     * Compares two Show objects to determine their order.
     * @param show1 The first Show object to compare.
     * @param show2 The second Show object to compare.
     * @return A negative integer if show1 should come before show2,
     *         a positive integer if show1 should come after show2,
     *         or 0 if they are equal.
     */
    @Override
    public int compare(Show show1, Show show2) {
        int scoreComparison = Double.compare(show2.getAverageReviews(), show1.getAverageReviews());
        if (scoreComparison != 0) {
            return scoreComparison;
        }

        int yearComparison = show2.getYear() - show1.getYear();
        if (yearComparison != 0) {
            return yearComparison;
        }

        return show1.getTitle().compareTo(show2.getTitle());
    }
}
