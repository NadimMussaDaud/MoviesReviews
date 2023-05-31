package CineReviewsPackage.Exceptions;

public class NotAdministratorException extends Throwable{
    public NotAdministratorException() {
        super("CineReviewsPackage.Persons.Person is not an administrator");
    }
}
