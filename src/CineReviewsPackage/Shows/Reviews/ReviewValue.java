package CineReviewsPackage.Shows.Reviews;

/**
 * This enum represents the possible values for a review in the CineReviews project.
 * Each value has a corresponding text and number representation.
 * The enum also provides methods for getting a ReviewValue from its text or number representation.
 *
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public enum ReviewValue {
    TERRIBLE("terrible", 1),
    POOR("poor", 2),
    AVERAGE("average", 3),
    GOOD("good", 4),
    EXCELLENT("excellent", 5);

    private final String text;
    private final int number;

    /**
     * Constructor for the ReviewValue enum.
     * @param text The text representation of the review value.
     * @param number The number representation of the review value.
     */
    ReviewValue(String text, int number) {
        this.text = text;
        this.number = number;
    }

    /**
     * Gets the text representation of the review value.
     * @return The text representation of the review value.
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the number representation of the review value.
     * @return The number representation of the review value.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Gets a ReviewValue from its text representation.
     * @param reviewText The text representation of the review value to get.
     * @return The ReviewValue corresponding to the given text representation,
     * or null if no such ReviewValue exists.
     */
    public static ReviewValue fromText(String reviewText) {
        for (ReviewValue review : ReviewValue.values()) {
            if (review.getText().equalsIgnoreCase(reviewText)) {
                return review;
            }
        }
        return null;
    }
}
