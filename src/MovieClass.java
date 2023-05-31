public class MovieClass extends ShowsAbstract {

    private int duration;
    public MovieClass(String title, String director, int duration, String certification, int year, String[] genres, String[] cast) {
        super(title, director,certification, year, genres, cast);
        this.duration = duration;
    }
}
