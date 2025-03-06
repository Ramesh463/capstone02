package expd.week12.capstone02.Exception;

public class ArtistNotFoundException extends Exception {
    public ArtistNotFoundException(Long id) {
        super("Artist not found with id: " + id);
    }

    public ArtistNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
