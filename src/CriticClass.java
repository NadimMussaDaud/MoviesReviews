public class CriticClass extends PersonAbstract<Review>{
    public CriticClass(String name) {
        super(name);
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
