package CineReviewsPackage.Shows;

import java.util.List;

import java.util.SortedMap;

public class MovieClass extends ShowsAbstract {

    private final int duration;
    public MovieClass(Artist director, String title, int duration, String certification, int year, List<String> genres, List<Artist> cast) {
        super(director, title, certification, year, genres, cast);
        this.duration = duration;
    }

    public int getSeasonsOrDuration(){
        return duration;
    }
}
