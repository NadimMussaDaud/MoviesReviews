package CineReviewsPackage.Shows;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class ArtistClass implements Artist {
    private final String name;
    private final SortedMap<Show, String> workedShows; //this map's value indicates its role.
    private String birthday,birthplace;
    public ArtistClass(String name, String birthday, String birthplace) {
        this.name = name;
        workedShows = new TreeMap<>(new ShowComparator());
        this.birthday = birthday;
        this.birthplace = birthplace;
    }


    @Override
    public void addInfo(String birthplace, String birthday) {
        this.birthday = birthday;
        this.birthplace = birthplace;
    }

    @Override
    public void addShow(Show s, String role){
        workedShows.put(s, role);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getBirthday() {
        return birthday;
    }

    @Override
    public String getBirthplace() {
        return birthplace;
    }

    @Override
    public Iterator<Map.Entry<Show, String>> getWorkedShows(){
        return workedShows.entrySet().iterator();
    }

    @Override
    public boolean hasWorkedWith(String name){
        for(Show s : workedShows.keySet()){
            if(s.hasArtist(name))
                return true;
        }
        return false;
    }
}
