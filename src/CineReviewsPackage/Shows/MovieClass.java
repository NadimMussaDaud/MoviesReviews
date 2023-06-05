package CineReviewsPackage.Shows;

import java.util.List;

/**
 * Class representing a movie, a type of Show with a fixed duration.
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public class MovieClass extends ShowsAbstract {
    private final int duration;

    /**
     * Constructs a new ShowsAbstract object with the given parameters.
     * @param director The creator of the show.
     * @param title The title of the show.
     * @param duration is the length of the movie.
     * @param certification The certification of the show.
     * @param year The year the show was released.
     * @param genres A list of genres for the show.
     * @param cast A list of cast members for the show.
     */
    public MovieClass(Artist director, String title, int duration, String certification, int year, List<String> genres, List<Artist> cast) {
        super(director, title, certification, year, genres, cast);
        this.duration = duration;
    }

    public int getSeasonsOrDuration(){
        return duration;
    }
}
