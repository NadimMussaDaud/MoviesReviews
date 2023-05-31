package CineReviewsPackage.Persons;

import CineReviewsPackage.Shows.Show;

public class ArtistClass extends PersonAbstract<Show> {
    private String name, birthday,birthplace;
    public ArtistClass(String birthday, String birthplace) {
        super();
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
