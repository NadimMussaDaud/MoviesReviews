package CineReviewsPackage.Shows;

import java.util.List;

/**
 * Class representing a series, a type of show with seasons.
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public class SeriesClass extends ShowsAbstract {
    private final int seasons;

    /**
     * Constructs a new ShowsAbstract object with the given parameters.
     * @param director The director of the show.
     * @param title The title of the show.
     * @param seasons is the amount of seasons the show has.
     * @param certification The certification of the show.
     * @param year The year the show was released.
     * @param genres A list of genres for the show.
     * @param cast A list of cast members for the show.
     */
    public SeriesClass(Artist director, String title, int seasons, String certification, int year, List<String> genres, List<Artist> cast){
        super(director, title, certification, year, genres, cast);
        this.seasons = seasons;
    }

    public int getSeasonsOrDuration(){
        return seasons;
    }
}
