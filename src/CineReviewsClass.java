import Exceptions.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class CineReviewsClass implements CineReviews{

    private LinkedList<Person> users;
    private LinkedList<Show> shows;
    public CineReviewsClass(){
        users = new LinkedList<>();
        shows = new LinkedList<>();
    }

    @Override
    public boolean hasType(String type) {
        return type.equals("admin") || type.equals("audience") || type.equals("critic");
    }

    @Override
    public boolean hasPerson(String name) {
        return getUser(name)!=null;
    }

    @Override
    public void register(String type, String name, String password) {
        if(password!=null){
            users.add(new AdminClass(name,password));
        }
        else{
            if(type.equals("audience")){
                users.add(new AudienceClass(name));
            }
            else{
                users.add(new CriticClass(name));
            }
        }

    }

    @Override
    public boolean hasUsers() {
        return users.size()==0;
    }

    @Override
    public Iterator<Person> getUsers() {
        users.sort(Comparator.comparing(Person::getName));
        return users.iterator();
    }

    @Override
    /**
     * @param admin the name of the user
     * @return true if the user is an administrator
     * @throws NoUserException if the user does not exist
     * @throws NotAdministratorException if the user is not an administrator
     */
    public boolean isAdmin(String admin) throws NoUserException, NotAdministratorException {
        if(!hasPerson(admin))
            throw new NoUserException();
        if(!getUser(admin).isAdministrator())
            throw new NotAdministratorException();

        return getUser(admin).isAdministrator();
    }

    @Override
    public boolean correctPassword(String admin, String password) {
        return false;
    }

    @Override
    public boolean hasShow(String title) {
        return false;
    }

    @Override
    public void addMovie(String title, String director, int duration, String certification, int year, String[] genres, String[] cast) {

        //shows.add(new MovieClass(title,director,duration,certification,year,genres,cast));
    }

    private void addArtistInfo(String name, String birthplace, String birthday){


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
