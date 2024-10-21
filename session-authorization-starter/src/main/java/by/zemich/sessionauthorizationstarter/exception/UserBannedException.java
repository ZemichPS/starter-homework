package by.zemich.sessionauthorizationstarter.exception;

public class UserBannedException extends RuntimeException {
    public UserBannedException(String message) {
        super(message);
    }
}
