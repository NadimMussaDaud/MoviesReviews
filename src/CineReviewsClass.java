import Exceptions.*;

import java.util.*;

public class CineReviewsClass implements CineReviews{

    private static final String UNKNOWN_TYPE = "Unknown user type!";
    private static final String USER_EXISTS = "User %s already exists!";
    private static final String NO_USERS = "No users registered.";
    private static final String ADMIN_NOT_FOUND = "Admin %s does not exist!";
    private static final String SHOW_EXISTS = "Show %s already exists!";
    private LinkedList<Person> users;
    private LinkedList<Show> shows;
    private final SortedMap<String, User> users;

    public CineReviewsClass(){
        shows = new LinkedList<>();
        users = new TreeMap<>();
    }

    @Override
    public boolean hasType(String type) {
        return type.equals("admin") || type.equals("audience") || type.equals("critic");
    }
    @Override
    public void register(String type, String name, String password) throws CineReviewsException{
        if(!hasType(type)) throw new CineReviewsException(UNKNOWN_TYPE);
        if(hasPerson(name)) throw new CineReviewsException(String.format(USER_EXISTS, name));

        if(password!=null){
            users.put(name, new AdminClass(password));
        }
        else{
            if(type.equals("audience")){
                users.put(name, new AudienceClass());
            }
            else{
                users.put(name, new CriticClass());
            }
        }

    }

    @Override
    public boolean hasUsers() {
        return users.size()==0;
    }

    @Override
    public Iterator<Map.Entry<String, User>> getUsers() throws CineReviewsException{
        if(!hasUsers()) throw new CineReviewsException(NO_USERS);
        return users.entrySet().iterator();
    }

    @Override
    /**
     * @param admin the name of the user
     * @return true if the user is an administrator
     * @throws NoUserException if the user does not exist
     * @throws NotAdministratorException if the user is not an administrator
     */
    public boolean isAdmin(String admin) throws CineReviewsException {
        if(!users.containsKey(admin))
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, admin));
        if(!users.get(admin).isAdministrator())
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, admin));

        return users.get(admin).isAdministrator();
    }

    public void authenticate(String name, String password) throws CineReviewsException, UserException{
        if(!hasPerson(name))
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, name));
        User u = users.get(name);
        if(!u.isAdministrator())
            throw new CineReviewsException(String.format(ADMIN_NOT_FOUND, name));
        u.authenticate(password);
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
            cast2.add(getUser(a));
        }
        Person director2 = getUser(director);
        shows.add(new MovieClass(title,director2,duration,certification,year,genres,cast2));
    }
    private void addArtist(String name, String birthplace, String birthday) throws NoUserException{
        if(!hasPerson(name))
            users.add(new ArtistClass(name,birthplace,birthday));
        else throw new NoUserException();
    }

    /**
     * @param name
     * @param birthplace
     * @param birthday
     */
    public void addArtistInfo(String name, String birthplace, String birthday) {
        try {
            addArtist(name, birthplace, birthday);
        } catch (NoUserException e) {
            if (getUser(name).isArtist())
                ((ArtistClass) getUser(name)).addInfo(birthplace, birthday);
        }
    }

        private Person getUser(String name){
        for(Person u : users){
            if(u.getName().equals(name)){
                return u;
            }
        }
        return null;
    }
}
