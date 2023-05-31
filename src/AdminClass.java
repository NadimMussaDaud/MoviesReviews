import Exceptions.UserException;

public class AdminClass extends UserAbstract{
    private static final String INVALID_AUTH = "Invalid authentication!";

    private final String password;
    public AdminClass(String password) {
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

    @Override
    public void authenticate(String password) throws UserException {
        if(!this.password.equals(password)) throw new UserException(INVALID_AUTH);
    }
}
