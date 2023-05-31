abstract class ShowsAbstract implements Show {
    protected String title, certification,director;
    protected int year;
    protected String[] genres, cast;

    public ShowsAbstract(String title, String director, String certification, int year, String[] genres, String[] cast) {
        this.title = title;
        this.director = director;
        this.certification = certification;
        this.year = year;
        this.genres = genres;
        this.cast = cast;
    }

}
