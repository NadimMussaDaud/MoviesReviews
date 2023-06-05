package CineReviewsPackage.Shows;

import java.util.*;

/**
 * Implements the Artist Interface.
 * @author Lucas Andrade, 64583
 * @author Nadim Daud, 63529
 */
public class ArtistClass implements Artist {
    private final String name;
    private final SortedMap<Show, String> workedShows; //this map's value indicates its role.
    private String birthday,birthplace;

    /**
     * Constructs a new ArtistClass object with the given parameters.
     * @param name The name of the artist.
     * @param birthday The birthday of the artist.
     * @param birthplace The birthplace of the artist.
     */
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

    public SortedSet<String> collaboratedArtists(){
        SortedSet<String> artists = new TreeSet<>();

        for(Show s : workedShows.keySet()){
            Iterator<Artist> it = s.getShowsPersons();

            while(it.hasNext()){
                Artist a = it.next();
                if(!a.getName().equals(this.getName()))
                    artists.add(a.getName());
            }
        }

        return artists;
    }
}
