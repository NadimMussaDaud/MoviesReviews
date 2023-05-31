package Exceptions;

public class NoUserException extends Throwable {
    public NoUserException() {
        super("No such user exists");
    }
}
