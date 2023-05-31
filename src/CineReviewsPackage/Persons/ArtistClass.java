package CineReviewsPackage.Persons;

import CineReviewsPackage.Shows.Show;

public class ArtistClass extends PersonAbstract<Show> {
    private String birthday,birthplace;
    public ArtistClass(String name,String birthday, String birthplace) {
        super(name);
        this.birthday = birthday;
        this.birthplace = birthplace;
    }


    public void addInfo(String birthplace, String birthday) {
        this.birthday = birthday;
        this.birthplace = birthplace;
    }

    public void addMedia(Object o){
        super.media.add((Show) o);
    }
}
