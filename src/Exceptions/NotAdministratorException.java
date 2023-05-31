package Exceptions;

public class NotAdministratorException extends Throwable{
    public NotAdministratorException() {
        super("User is not an administrator");
    }
}
