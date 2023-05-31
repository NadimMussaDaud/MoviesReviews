package CineReviewsPackage;

import CineReviewsPackage.Exceptions.*;
import CineReviewsPackage.Persons.*;
import CineReviewsPackage.Shows.MovieClass;
import CineReviewsPackage.Shows.Show;

import java.util.*;

public class CineReviewsClass implements CineReviews{

    private static final String UNKNOWN_TYPE = "Unknown user type!";
    private static final String USER_EXISTS = "User %s already exists!";
    private static final String NO_USERS = "No users registered.";
    private static final String ADMIN_NOT_FOUND = "Admin %s does not exist!";
    private static final String SHOW_EXISTS = "CineReviewsPackage.Shows.Show %s already exists!";
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
    public boolean hasUsers() {
        return persons.isEmpty();
    }

    @Override
    public Iterator<Map.Entry<String, Person>> getPersons() throws CineReviewsException{
        if(!hasUsers()) throw new CineReviewsException(NO_USERS);
        return persons.entrySet().iterator();
    }

    @Override
    public boolean isAdmin(String admin) throws CineReviewsException {
        if(!persons.containsKey(admin))
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, admin));
        if(!persons.get(admin).isAdministrator())
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, admin));

        return persons.get(admin).isAdministrator();
    }

    public void authenticate(String name, String password) throws CineReviewsException, UserException{
        if(!persons.containsKey(name))
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, name));
        Person p = persons.get(name);
        if(!p.isAdministrator())
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, name));
        p.authenticate(password);
    }

    @Override
    public boolean hasShow(String title) {
        return false;
    }

    @Override
    public void addMovie(String title, String director, int duration, String certification, int year, List<String> genres, List<String> cast) throws CineReviewsException {
        addArtistInfo(director,null,null);
        for(String c : cast){
           addArtistInfo(c,null,null);
        }
        //tirar de users todos os artistas recém criados e guardar num array de users
        List<Person> castPersons = new LinkedList<>();
        for(String a : cast){
            castPersons.add(persons.get(a));
        }
        Person directorPerson = persons.get(director);

        Show s = new MovieClass(directorPerson,duration,certification,year,genres,castPersons);
        shows.put(title, s);

        for(Person p : castPersons)
            p.addMedia(s);

    }

    private void addArtist(String name, String birthplace, String birthday) throws CineReviewsException{
        if(!persons.containsKey(name))
            persons.put(name, new ArtistClass(birthplace,birthday));
        else throw new CineReviewsException("");
    }


    public void addArtistInfo(String name, String birthplace, String birthday) {
        try {
            addArtist(name, birthplace, birthday);
        } catch (CineReviewsException e) {
            if (persons.get(name) instanceof ArtistClass)
                ((ArtistClass) persons.get(name)).addInfo(birthplace, birthday);
        }
    }
}
