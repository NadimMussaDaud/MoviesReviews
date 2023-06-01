package CineReviewsPackage.Shows;

import CineReviewsPackage.Shows.Show;
import CineReviewsPackage.Shows.ShowComparator;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Artist{
    private final String name;
    private final SortedMap<Show, String> workedShows; //this map's value indicates its role.
    private String birthday,birthplace;
    public Artist(String name, String birthday, String birthplace) {
        this.name = name;
        workedShows = new TreeMap<>(new ShowComparator());
        this.birthday = birthday;
        this.birthplace = birthplace;
    }


    public void addInfo(String birthplace, String birthday) {
        this.birthday = birthday;
        this.birthplace = birthplace;
    }

    public void addShow(Show s, String role){
        workedShows.put(s, role);
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public Iterator<Map.Entry<Show, String>> getWorkedShows(){
        return workedShows.entrySet().iterator();
    }
}
