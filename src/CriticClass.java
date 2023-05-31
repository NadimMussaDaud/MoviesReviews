public class CriticClass extends PersonAbstract{
    public CriticClass(String name) {
        super(name);
    }

    @Override
    public boolean isAdministrator() {
        return false;
    }
}
