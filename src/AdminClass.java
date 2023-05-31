public class AdminClass extends PersonAbstract<Show>{

    private String password;
    public AdminClass(String name, String password) {
        super(name);
        this.password = password;
    }

    @Override
    public boolean isAdministrator() {
        return true;
    }

    @Override
    public boolean isArtist() {
        return false;
    }

    public int numberMovies(){
        return super.media.size();
    }

    @SuppressWarnings("unchecked")
    public void addMovie(Show show){
        super.media.add(show);
    }
}
