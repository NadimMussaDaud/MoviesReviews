public class AudienceClass extends PersonAbstract<Review>{
    public AudienceClass(String name) {
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
