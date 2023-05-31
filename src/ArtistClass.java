public class ArtistClass extends PersonAbstract<Show> {
    private String name, birthday,birthplace;
    public ArtistClass(String name, String birthday, String birthplace) {
        super(name);
        this.birthday = birthday;
        this.birthplace = birthplace;
    }


    public void addInfo(String birthplace, String birthday) {
        this.birthday = birthday;
        this.birthplace = birthplace;
    }

    @Override
    public boolean isArtist() {
        return true;
    }

    @Override
    public boolean isAdministrator() {
        return false;
    }
}
