import Exceptions.*;

import java.util.*;

public class CineReviewsClass implements CineReviews{

    private static final String UNKNOWN_TYPE = "Unknown user type!";
    private static final String USER_EXISTS = "User %s already exists!";
    private static final String NO_USERS = "No users registered.";
    private static final String ADMIN_NOT_FOUND = "Admin %s does not exist!";
    private static final String SHOW_EXISTS = "Show %s already exists!";
    private LinkedList<Show> shows;
    private final SortedMap<String, Person> persons;

    public CineReviewsClass(){
        shows = new LinkedList<>();
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
        return persons.size()==0;
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
    public void addMovie(String title, String director, int duration, String certification, int year, String[] genres, String[] cast) {
        addArtistInfo(director,null,null);
        for(String c : cast){
           addArtistInfo(c,null,null);
        }
        //tirar de users todos os artistas recém criados e guardar num array de users
        LinkedList<Person> cast2 = new LinkedList<>();
        for(String a : cast){
            cast2.add(persons.get(a));
        }
        Person director2 = persons.get(director);
        shows.add(new MovieClass(title,director2,duration,certification,year,genres,cast2));
    }

    private void addArtist(String name, String birthplace, String birthday) throws NoUserException{
        if(!persons.containsKey(name))
            persons.put(name, new ArtistClass(birthplace,birthday));
        else throw new NoUserException();
    }


    public void addArtistInfo(String name, String birthplace, String birthday) {
        try {
            addArtist(name, birthplace, birthday);
        } catch (NoUserException e) {
            if (persons.get(name).isArtist())
                ((ArtistClass) persons.get(name)).addInfo(birthplace, birthday);
        }
    }
}
