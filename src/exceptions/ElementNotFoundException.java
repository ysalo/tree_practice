
package exceptions;

/**
 * ElementNotFoundException represents the situation in which a target element
 * is not present in a collection.
 *
 * @author Lewis and Chase
 * @author Yaro Salo - formatted for TCSS 342
 * @version 4.1
 */
public class ElementNotFoundException extends RuntimeException {
    /** Generated serialization ID for this class. */
    private static final long serialVersionUID = -8985982736375193844L;

    /**
     * Sets up this exception with an appropriate message.
     * 
     * @param theCollection is the collection in which to look.
     */
    public ElementNotFoundException(final String theCollection) {
        super("The target element is not in this " + theCollection);
    }
}
