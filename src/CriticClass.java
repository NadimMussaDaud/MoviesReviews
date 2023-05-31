
public class CriticClass extends PersonAbstract<Review>{
    public CriticClass() {
        super();
    }

    @Override
    public boolean isAdministrator() {
        return false;
    }

    @Override
    public boolean isArtist() {
        return false;
    }
}
