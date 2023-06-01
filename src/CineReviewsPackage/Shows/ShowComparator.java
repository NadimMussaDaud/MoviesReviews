package CineReviewsPackage.Shows;

import java.util.Comparator;

public class ShowComparator implements Comparator<Show> {
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