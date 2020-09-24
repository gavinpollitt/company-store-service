package custq;

public class QueueEmptyException extends Exception {
    public QueueEmptyException() {
        super("There are no entries available to get");
    }
}

