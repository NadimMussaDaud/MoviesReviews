public class AdminClass extends PersonAbstract{

    private String password;
    public AdminClass(String name, String password) {
        super(name);
        this.password = password;
    }

    @Override
    public boolean isAdministrator() {
        return true;
    }

    public int numberMovies(){
        return super.media.size();
    }

    @SuppressWarnings("unchecked")
    public void addMovie(Show show){
        super.media.add(show);
    }
}
