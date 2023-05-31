public class SerieClass extends ShowsAbstract {
    private int seasons;

    public SerieClass(String title, String director, String certification, int year, String[] genres, String[] cast, int seasons){
        super(title, director, certification, year, genres, cast);
        this.seasons = seasons;
    }


}
