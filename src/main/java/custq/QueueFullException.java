package custq;

public class QueueFullException extends Exception {
    public QueueFullException() {
        super("The queue is now full");
    }}
