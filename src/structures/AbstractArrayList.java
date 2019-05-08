
package structures;

import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ArrayList represents an array implementation of a list. The front of the list
 * is kept at array index 0. This class will be extended to create a specific
 * kind of list.
 *
 * @author Lewis and Chase
 * @author Yaro Salo - formatted for TCSS 342
 * @version 4.1
 * 
 * @param <T> the generic data type
 */
public abstract class AbstractArrayList<T> implements ListADT<T>, Iterable<T> {
    /** The default capacity. */
    private static final int DEFAULT_CAPACITY = 100;

    /** Integer indicating that an element was not found. */
    private static final int NOT_FOUND = -1;

    /** A string to indicate an array list. */
    private static final String LIST = "Array List";

    /** The size of the list and a reference to the next available element spot. */
    protected int myRear;

    /** The list. */
    protected T[] myList;

    /** Counts the number of times a list has been modified. */
    protected int myModCount;

    /**
     * Creates an empty list using the default capacity.
     */
    public AbstractArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an empty list using the specified capacity.
     *
     * @param theInitialCapacity the integer value of the size of the array list
     */
    public AbstractArrayList(final int theInitialCapacity) {
        myRear = 0;
        myList = (T[]) (new Object[theInitialCapacity]);
        myModCount = 0;
    }

    /**
     * Creates a new array to store the contents of this list with twice the
     * capacity of the old one. Called by descendant classes that add elements
     * to the list.
     */
    protected void expandCapacity() {
        myList = Arrays.copyOf(myList, myList.length * 2);
    }

    /**
     * Removes and returns the last element in this list.
     *
     * @return the last element in the list
     * @throws EmptyCollectionException if the element is not in the list
     */
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException(LIST);
        }
        final T result;
        myRear--;
        result = myList[myRear];
        myList[myRear] = null;
        myModCount++;

        return result;
    }

    /**
     * Removes and returns the first element in this list.
     *
     * @return the first element in the list
     * @throws EmptyCollectionException if the element is not in the list
     */
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException(LIST);
        }
        final T result = myList[0];
        myRear--;
        /** shift the elements */
        for (int scan = 0; scan < myRear; scan++) {
            myList[scan] = myList[scan + 1];
        }
        myList[myRear] = null;
        myModCount++;

        return result;
    }

    /**
     * Removes and returns the specified element.
     *
     * @param theElement the element to be removed and returned from the list
     * @return the removed element
     * @throws ElementNotFoundException if the element is not in the list
     */
    public T remove(final T theElement) {
        final T result;
        final int index = find(theElement);

        if (index == NOT_FOUND) {
            throw new ElementNotFoundException(LIST);
        }
        result = myList[index];
        myRear--;

        // shift the appropriate elements
        for (int scan = index; scan < myRear; scan++) {
            myList[scan] = myList[scan + 1];
        }
        myList[myRear] = null;
        myModCount++;

        return result;
    }

    /**
     * Returns a reference to the element at the front of this list. The element
     * is not removed from the list. Throws an EmptyCollectionException if the
     * list is empty.
     *
     * @return a reference to the first element in the list
     * @throws EmptyCollectionException if the list is empty
     */
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException(LIST);
        }
        return myList[0];
    }

    /**
     * Returns a reference to the element at the rear of this list. The element
     * is not removed from the list. Throws an EmptyCollectionException if the
     * list is empty.
     *
     * @return a reference to the last element of this list
     * @throws EmptyCollectionException if the list is empty
     */
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException(LIST);
        }
        return myList[myRear - 1];
    }

    /**
     * Returns true if this list contains the specified element.
     *
     * @param theTarget the target element
     * @return true if the target is in the list, false otherwise
     */
    public boolean contains(final T theTarget) {
        return find(theTarget) != NOT_FOUND;
    }

    /**
     * Returns the array index of the specified element, or the constant
     * NOT_FOUND if it is not found.
     *
     * @param theTarget the target element
     * @return the index of the target element, or the NOT_FOUND constant
     */
    private int find(final T theTarget) {
        int scan = 0;
        int result = NOT_FOUND;

        if (!isEmpty()) {
            while (result == NOT_FOUND && scan < myRear) {
                if (theTarget.equals(myList[scan])) {
                    result = scan;
                } else {
                    scan++;
                }
            }
        }
        return result;
    }

    /**
     * Returns true if this list is empty and false otherwise.
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return myRear == 0;
    }

    /**
     * Returns the number of elements currently in this list.
     *
     * @return the number of elements in the list
     */
    public int size() {
        return myRear;
    }

    /**
     * Returns a string representation of this list.
     * 
     * @return the string representation of the list
     */
    public String toString() {
        String result = "";

        for (int scan = 0; scan < myRear; scan++) {
            result = result + myList[scan] + "\n";
        }
        return result;
    }

    /**
     * Returns an iterator for the elements currently in this list.
     * 
     * @return an iterator for the elements in the list
     */
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    /**
     * ArrayListIterator iterator over the elements of an ArrayList.
     */
    private class ArrayListIterator implements Iterator<T> {
        /**
         * The modification counter to check if the collection has been
         * modified.
         */
        protected int myIteratorModCount;

        /** Is the index of the current element. */
        protected int myCurrent;

        /**
         * Sets up this iterator using the specified modCount.
         */
        ArrayListIterator() {
            myIteratorModCount = myModCount;
            myCurrent = 0;
        }

        /**
         * Returns true if this iterator has at least one more element to
         * deliver in the iteration.
         *
         * @return true if this iterator has at least one more element to
         *         deliver in the iteration
         * @throws ConcurrentModificationException if the collection has changed
         *             while the iterator is in use
         */
        public boolean hasNext() throws ConcurrentModificationException {
            if (myIteratorModCount != myModCount) {
                throw new ConcurrentModificationException();
            }
            return myCurrent < myRear;
        }

        /**
         * Returns the next element in the iteration. If there are no more
         * elements in this iteration, a NoSuchElementException is thrown.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if an element not found exception
         *             occurs
         * @throws ConcurrentModificationException if the collection has changed
         */
        public T next() throws ConcurrentModificationException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            myCurrent++;

            return myList[myCurrent - 1];
        }

        /**
         * The remove operation is not supported in this collection.
         * 
         * @throws UnsupportedOperationException if the remove method is called
         */
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }
    }
}
