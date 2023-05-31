package CineReviewsPackage.Shows.Reviews;

public enum ReviewValue {
    TERRIBLE("terrible", 1),
    POOR("poor", 2),
    AVERAGE("average", 3),
    GOOD("good", 4),
    EXCELLENT("excellent", 5);

    private final String text;
    private final int number;

    ReviewValue(String text, int number) {
        this.text = text;
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public int getNumber() {
        return number;
    }

    public static ReviewValue fromText(String reviewText) {
        for (ReviewValue review : ReviewValue.values()) {
            if (review.getText().equalsIgnoreCase(reviewText)) {
                return review;
            }
        }
        return null;
    }

    public static ReviewValue fromNumber(int reviewNumber) {
        for (ReviewValue review : ReviewValue.values()) {
            if (review.getNumber() == reviewNumber) {
                return review;
            }
        }
        return null;
    }
}
