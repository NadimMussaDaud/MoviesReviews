public class AudienceClass extends UserAbstract{
    public AudienceClass(String name) {
        super(name);
    }

    @Override
    public boolean isAdministrator() {
        return false;
    }
}
