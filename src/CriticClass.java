public class CriticClass extends UserAbstract{
    public CriticClass(String name) {
        super(name);
    }

    @Override
    public boolean isAdministrator() {
        return false;
    }
}
