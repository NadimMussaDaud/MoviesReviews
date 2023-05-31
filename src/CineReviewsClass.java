import Exceptions.*;

import java.util.*;

public class CineReviewsClass implements CineReviews{
    private static final String UNKNOWN_TYPE = "Unknown user type!";
    private static final String USER_EXISTS = "User %s already exists!";
    private static final String NO_USERS = "No users registered.";
    private static final String ADMIN_NOT_FOUND = "Admin %s does not exist!";
    private static final String SHOW_EXISTS = "Show %s already exists!";
    private final SortedMap<String, User> users;
    public CineReviewsClass(){
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
    public void addMovie(String title, String director, int duration, String certification, int year,
                         String[] genres, String[] cast) throws CineReviewsException{
        if(hasShow(title)) throw new CineReviewsException(String.format(SHOW_EXISTS, title));


    }
}
