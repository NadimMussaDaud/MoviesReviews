package CineReviewsPackage.Shows;

import java.util.Comparator;

public class ShowComparator implements Comparator<Show> {
    @Override
    public int compare(Show show1, Show show2) {
        double scoreComparison = show1.getAverageReviews() - show2.getAverageReviews();
        if (scoreComparison != 0) {
            return (int)scoreComparison;
        }

        int yearComparison = Integer.compare(show1.getYear(), show2.getYear());
        if (yearComparison != 0) {
            return yearComparison;
        }

        return show1.getTitle().compareTo(show2.getTitle());
    }
}