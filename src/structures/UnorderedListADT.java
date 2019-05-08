
package structures;

/**
 * UnorderedListADT defines the interface to an unordered list collection.
 * Elements are stored in any order the user desires.
 *
 * @author Lewis and Chase
 * @author Yaro Salo - formatted for TCSS 342
 * @version 4.1
 * 
 * @param <T> the generic data type
 */
public interface UnorderedListADT<T> extends ListADT<T> {
    /**
     * Adds the specified element to the front of this list.
     *
     * @param theElement the element to be added to the front of this list
     */
    void addToFront(T theElement);

    /**
     * Adds the specified element to the rear of this list.
     *
     * @param theElement the element to be added to the rear of this list
     */
    void addToRear(T theElement);

    /**
     * Adds the specified element after the specified target.
     *
     * @param theElement the element to be added after the target
     * @param theTarget the target is the item that the element will be added
     *            after
     */
    void addAfter(T theElement, T theTarget);
}
