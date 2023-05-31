public class AudienceClass extends PersonAbstract{
    public AudienceClass(String name) {
        super(name);
    }

    @Override
    public boolean isAdministrator() {
        return false;
    }
}
