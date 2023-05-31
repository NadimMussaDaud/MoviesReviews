package Exceptions;

public class NotAdministratorException extends Throwable{
    public NotAdministratorException() {
        super("Person is not an administrator");
    }
}
