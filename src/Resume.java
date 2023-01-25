/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    String uuid;


    public Resume(final String uuid) {
        this.uuid = uuid;

    }

    @Override
    public String toString() {
        return uuid;
    }
}
