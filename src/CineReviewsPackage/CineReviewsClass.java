package CineReviewsPackage;

import CineReviewsPackage.Exceptions.*;
import CineReviewsPackage.Persons.*;
import CineReviewsPackage.Shows.MovieClass;
import CineReviewsPackage.Shows.SeriesClass;
import CineReviewsPackage.Shows.Show;

import java.util.*;

public class CineReviewsClass implements CineReviews{

    private static final String UNKNOWN_TYPE = "Unknown user type!";
    private static final String USER_EXISTS = "User %s already exists!";
    private static final String NO_USERS = "No users registered.";
    private static final String ADMIN_NOT_FOUND = "Admin %s does not exist!";
    private static final String SHOW_EXISTS = "Show %s already exists!";
    private SortedMap<String, Show> shows;
    private final SortedMap<String, Person> persons;

    public CineReviewsClass(){
        shows = new TreeMap<>();
        persons = new TreeMap<>();
    }

    @Override
    public boolean hasType(String type) {
        return type.equals("admin") || type.equals("audience") || type.equals("critic");
    }
    @Override
    public void register(String type, String name, String password) throws CineReviewsException{
        if(!hasType(type)) throw new CineReviewsException(UNKNOWN_TYPE);
        if(persons.containsKey(name)) throw new CineReviewsException(String.format(USER_EXISTS, name));

        if(password!=null){
            persons.put(name, new AdminClass(password));
        }
        else{
            if(type.equals("audience")){
                persons.put(name, new AudienceClass());
            }
            else{
                persons.put(name, new CriticClass());
            }
        }

    }

    @Override
    public Iterator<Map.Entry<String, Person>> getPersons() throws CineReviewsException{
        if(persons.isEmpty()) throw new CineReviewsException(NO_USERS);
        return persons.entrySet().iterator();
    }

    @Override
    public boolean isAdmin(String admin) throws CineReviewsException {
        if(!persons.containsKey(admin))
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, admin));
        if(!(persons.get(admin) instanceof AdminClass))
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, admin));

        return persons.get(admin) instanceof AdminClass;
    }

    public void authenticate(String name, String password) throws CineReviewsException, UserException{
        if(!persons.containsKey(name))
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, name));
        Person p = persons.get(name);
        if(!(p instanceof AdminClass))
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, name));
        p.authenticate(password);
    }

    @Override
    public boolean hasShow(String title) {
        return false;
    }

    @Override
    public int addShow(String title, String director, int durationOrSeasons, String certification, int year,
                        List<String> genres, List<String> cast, String type, String adminName) throws CineReviewsException {
        if(shows.containsKey(title)) throw new CineReviewsException(String.format(SHOW_EXISTS, title));

        int newArtistCount = handleArtists(cast, director);

        //tirar de users todos os artistas recém criados e guardar num array de users
        SortedMap<String, Person> castPersons = new TreeMap<>();
        for(String a : cast)
            castPersons.put(a, persons.get(a));
        Person directorPerson = persons.get(director);

        Show s = null;
        if(type.equals("movie"))
            s = new MovieClass(directorPerson,durationOrSeasons,certification,year,genres,castPersons);
        if(type.equals("series"))
            s = new SeriesClass(directorPerson,durationOrSeasons,certification,year,genres,castPersons);

        shows.put(title, s);
        for(Person p : castPersons.values())
            p.addMedia(s);

        persons.get(adminName).addMedia(s);

        return newArtistCount;
    }

    private int handleArtists(List<String> cast, String director){
        int count = 0;
        count += addArtistInfo(director,null,null);
        for(String c : cast){
            count += addArtistInfo(c,null,null);
        }
        return count;
    }

    private int addArtist(String name, String birthplace, String birthday) throws CineReviewsException{
        if(!persons.containsKey(name)){
            persons.put(name, new ArtistClass(birthplace, birthday));
            return 1;
        }
        else throw new CineReviewsException("");
    }

    public int addArtistInfo(String name, String birthplace, String birthday) {
        try {
            return addArtist(name, birthplace, birthday);
        } catch (CineReviewsException e) {
            if ((persons.get(name) instanceof ArtistClass) && birthplace != null && birthday != null)
                ((ArtistClass) persons.get(name)).addInfo(birthplace, birthday);
            return 0;
        }
    }

    public Iterator<Map.Entry<String, Show>> getShows() throws CineReviewsException{
        if(shows.isEmpty()) throw new CineReviewsException("No shows have been uploaded.");
        return shows.entrySet().iterator();
    }
}
