
package structures;

import exceptions.ElementNotFoundException;
import exceptions.EmptyCollectionException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedBinaryTree implements the BinaryTreeADT interface.
 * 
 * @author Lewis and Chase
 * @author Yaro Salo - formatted for TCSS 342
 * @version 4.1
 * 
 * @param <T> the generic data type
 */
public class LinkedBinaryTree<T> implements BinaryTreeADT<T>, Iterable<T> {
    
    /** LinkedBinaryTree string. */
    public static final String COLLECTION = "LinkedBinaryTree";
    /** The root node. */
    protected BinaryTreeNode<T> myRoot;
    /** The number of nodes. */
    protected int myModCount;
   
    /** Creates an empty binary tree. */
    public LinkedBinaryTree() {
        myRoot = null;
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param theElement the element that will become the root of the binary
     *            tree.
     */
    public LinkedBinaryTree(final T theElement) {
        myRoot = new BinaryTreeNode<T>(theElement);
    }

    /**
     * Creates a binary tree with the specified element as its root and the
     * given trees as its left child and right child.
     *
     * @param theElement the element that will become the root of the binary
     *            tree
     * @param theLeft the left subtree of this tree
     * @param theRight the right subtree of this tree
     */
    public LinkedBinaryTree(final T theElement, final LinkedBinaryTree<T> theLeft,
                            final LinkedBinaryTree<T> theRight) {
        myRoot = new BinaryTreeNode<T>(theElement);
       
        if (theLeft == null) {    
            myRoot.setLeft(null);
        } else {
            
            myRoot.setLeft(theLeft.myRoot);
        }
       
        if (theRight == null) {
            
            myRoot.setRight(null);
        } else {
            
            myRoot.setRight(theRight.myRoot);
        }
    }

    /**
     * Returns a reference to the element at the root.
     *
     * @return a reference to the specified target
     * @throws EmptyCollectionException if the tree is empty
     */
    public T getRootElement() throws EmptyCollectionException {
        return myRoot.getElement();
    }

    /**
     * Returns a reference to the node at the root.
     *
     * @return a reference to the specified node
     * @throws EmptyCollectionException if the tree is empty
     */
    protected BinaryTreeNode<T> getRootNode() throws EmptyCollectionException {
        return myRoot;
    }

    /**
     * Returns the left subtree of the root of this tree.
     *
     * @return a link to the left subtree for the tree
     */
    public LinkedBinaryTree<T> getLeft() {
        
        //Create an empty new linked binary tree.
        final LinkedBinaryTree<T> leftSubTree = new LinkedBinaryTree<>();
        //Grab the left child node from the root which now becomes the root 
        //node of the sub tree.
        leftSubTree.myRoot = myRoot.getLeft();
        return leftSubTree;
    }

    /**
     * Returns the right subtree of the root of this tree.
     *
     * @return a link to the right subtree of the tree
     */
    public LinkedBinaryTree<T> getRight() {
       
        final LinkedBinaryTree<T> rightSubTree = new LinkedBinaryTree<>();
        //Grab the right child node from the root which now becomes the root 
        //node of the sub tree.
        rightSubTree.myRoot = myRoot.getRight();
        return rightSubTree;
    }

    /**
     * Returns true if this binary tree is empty and false otherwise.
     *
     * @return true if this binary tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return myRoot == null;
    }

    /**
     * Returns the integer size of this tree.
     *
     * @return the integer size of the tree
     */
    public int size() {
        //the number of non null children plus the root is the size
        if (this.isEmpty()) {
            throw new EmptyCollectionException(COLLECTION);
        }
        return myRoot.numChildren() + 1; 
    }

    /**
     * Returns the height of this tree.  
     *
     * @return the height of the tree
     */
    public int getHeight() {
         //start the recursive process with the root.
        return height(myRoot);
    }

    /**
     * Returns the height of the specified node. If the trees is empty returns -1.
     *
     * @param theNode the node from which to calculate the height
     * @return the height of the tree
     */
    private int height(final BinaryTreeNode<T> theNode) {
        int result = 0;
        int heightLeft = 0;
        int heightRight = 0;
        
        if (theNode == null) {
          
            result = -1; //Indicates that the tree is empty
        } else {
            
            //compute the height of the left subtree. 
            heightLeft = 1 + height(theNode.myLeft); 
            
            //compute the height of the right subtree.
            heightRight = 1 + height(theNode.myRight); 
            
            if (heightLeft > heightRight) { //Find out which is greater
                result = heightLeft;
            } else {
                result = heightRight;
            }
        }
        return result; //return the greater height     
    }
    
    

    @Override
    public int countLeafNodes() {
        return countLeafsRecursive(myRoot);
    }
    
    /**
     * Recursive helper method to count the number of leafs 
     * in the tree.
     * 
     * @return the leaf count of the tree.
     * @param theNode is the node from which to start the search.
     */
    private int countLeafsRecursive(final BinaryTreeNode<T> theNode) {
        
        int count = 0;
        if (theNode == null) {
            count = 0;
        } else if (theNode.myLeft == null && theNode.myRight == null) { // leaf node
            count = 1;
        } else {
            //Go to the left
            final int leftCount = countLeafsRecursive(theNode.myLeft);
            //Go to the right
            final int rightCount = countLeafsRecursive(theNode.myRight);
            count = leftCount + rightCount;
        }
        return count;
       
        
    }
    
    @Override
    public int countOneChildNodes() {
        return countOneChildNodesRecursive(myRoot);
    }
    
    /**
     * Helper method to check if a node has only one child.
     * @param theNode the node to check
     * @return whether or not the node has one child.
     */
    private boolean onlyOneChild(final BinaryTreeNode<T> theNode) {
        int count = 0;
        if (theNode.myLeft != null) {
            count++;
        }
        if (theNode.myRight != null) {
            count++;
        }
        return count == 1;
    }
    /**
     * Helper method to count one child nodes.
     * 
     * @return the one child node count.
     * @param theNode is the node from where to begin.
     */
    private int countOneChildNodesRecursive(final BinaryTreeNode<T> theNode) {
        int count = 0;
        if (theNode == null) {
            count =  0;
        } else if (onlyOneChild(theNode)) { //If the node has one child count it.
            count =  1 + countOneChildNodesRecursive(theNode.myLeft) 
                       + countOneChildNodesRecursive(theNode.myRight);
        } else {
            //check the left and the right children.
            count = countOneChildNodesRecursive(theNode.myLeft) 
                            +  countOneChildNodesRecursive(theNode.myRight);
        }
        return count;
    }
    
    /**
     * Returns true if this tree contains an element that matches the specified
     * target element and false otherwise.
     *
     * @param theTargetElement the element being sought in this tree
     * @return true if the element in is this tree, false otherwise
     */
    public boolean contains(final T theTargetElement) {
        
        //findNode() returns null if the element was not found
        return findNode(theTargetElement, myRoot) != null;
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree. Throws a ElementNotFoundException if the specified
     * target element is not found in the binary tree.
     *
     * @param theTargetElement the element being sought in this tree
     * @return a reference to the specified target
     * @throws ElementNotFoundException if the element is not in the tree
     */
    public T find(final T theTargetElement) throws ElementNotFoundException {
        final BinaryTreeNode<T> current = findNode(theTargetElement, myRoot);

        if (current == null) {
            throw new ElementNotFoundException(COLLECTION);
        }
        return current.getElement();
    }

    /**
     * Returns a reference to the specified target element if it is found in
     * this binary tree.
     *
     * @param theTargetElement the element being sought in this tree
     * @param theNext the node to search in
     * @return a reference to the specified element.
     */
    private BinaryTreeNode<T> findNode(final T theTargetElement,
                                       final BinaryTreeNode<T> theNext) {
        if (theNext == null) {
            return null;
        }
        if (theNext.getElement().equals(theTargetElement)) {
            return theNext;
        }
        BinaryTreeNode<T> temp = findNode(theTargetElement, theNext.getLeft());

        if (temp == null) {
            temp = findNode(theTargetElement, theNext.getRight());
        }
        return temp;
    }

    /**
     * Returns a string representation of this binary tree showing the nodes in
     * an inorder fashion.
     *
     * @return a string representation of this binary tree
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final ArrayUnorderedList<T> tempList = new ArrayUnorderedList<>();
        inOrder(myRoot, tempList);
        
        final Iterator<T> itr = tempList.iterator();
        sb.append('[');
        while (itr.hasNext()) {
            sb.append(itr.next());
            
            if (itr.hasNext()) { //don't print a space after the last element.
                sb.append(' ');
            }
        }
        sb.append(']');
        return sb.toString();
        
    }

    /**
     * Returns an iterator over the elements in this tree using the
     * iteratorInOrder method.
     *
     * @return an in order iterator over this binary tree
     */
    public Iterator<T> iterator() {
        return iteratorInOrder();
    }

    /**
     * Performs an inorder traversal on this binary tree by calling an
     * overloaded, recursive inorder method that starts with the root.
     *
     * @return an in order iterator over this binary tree
     */
    public Iterator<T> iteratorInOrder() {
        final ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        inOrder(myRoot, tempList);
        return new TreeIterator(tempList.iterator());
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param theNode the node to be used as the root for this traversal
     * @param theTempList the temporary list for use in this traversal
     */
    protected void inOrder(final BinaryTreeNode<T> theNode,
                           final ArrayUnorderedList<T> theTempList) {
        if (theNode != null) {
            inOrder(theNode.getLeft(), theTempList);
            theTempList.addToRear(theNode.getElement());
            inOrder(theNode.getRight(), theTempList);
        }
    }

    /**
     * Performs an preorder traversal on this binary tree by calling an
     * overloaded, recursive preorder method that starts with the root.
     *
     * @return a pre order iterator over this tree
     */
    public Iterator<T> iteratorPreOrder() {
        final ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        preOrder(myRoot, tempList);

        return new TreeIterator(tempList.iterator());
    }

    /**
     * Performs a recursive preorder traversal.
     *
     * @param theNode the node to be used as the root for this traversal
     * @param theTempList the temporary list for use in this traversal
     */
    protected void preOrder(final BinaryTreeNode<T> theNode,
                            final ArrayUnorderedList<T> theTempList) {
        if (theNode != null) {
            theTempList.addToRear(theNode.getElement());
            preOrder(theNode.getLeft(), theTempList);
            preOrder(theNode.getRight(), theTempList);
        }
    }

    /**
     * Performs an postorder traversal on this binary tree by calling an
     * overloaded, recursive postorder method that starts with the root.
     *
     * @return a post order iterator over this tree
     */
    public Iterator<T> iteratorPostOrder() {
        final ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        postOrder(myRoot, tempList);

        return new TreeIterator(tempList.iterator());
    }

    /**
     * Performs a recursive postorder traversal.
     *
     * @param theNode the node to be used as the root for this traversal
     * @param theTempList the temporary list for use in this traversal
     */
    protected void postOrder(final BinaryTreeNode<T> theNode,
                             final ArrayUnorderedList<T> theTempList) {
        if (theNode != null) {
            postOrder(theNode.getLeft(), theTempList);
            postOrder(theNode.getRight(), theTempList);
            theTempList.addToRear(theNode.getElement());
        }
    }

    /**
     * Performs a levelorder traversal on this binary tree, using a templist.
     *
     * @return a levelorder iterator over this binary tree
     */
    public Iterator<T> iteratorLevelOrder() {
        final ArrayUnorderedList<BinaryTreeNode<T>> nodes =
                        new ArrayUnorderedList<BinaryTreeNode<T>>();
        final ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
        BinaryTreeNode<T> current;

        nodes.addToRear(myRoot);

        while (!nodes.isEmpty()) {
            current = nodes.removeFirst();

            if (current != null) {
                tempList.addToRear(current.getElement());
                if (current.getLeft() != null) {
                    nodes.addToRear(current.getLeft());
                }
                if (current.getRight() != null) {
                    nodes.addToRear(current.getRight());
                }
            } else {
                tempList.addToRear(null);
            }
        }

        return new TreeIterator(tempList.iterator());
    }


    /**
     * Inner class to represent an iterator over the elements of this tree.
     */
    private class TreeIterator implements Iterator<T> {
        /** The expected number of nodes. */
        private final int myExpectedModCount;

        /** The iterator. */
        private final Iterator<T> myIter;

        /**
         * Sets up this iterator using the specified iterator.
         *
         * @param theIter the list iterator created by a tree traversal.
         */
        TreeIterator(final Iterator<T> theIter) {
            this.myIter = theIter;
            myExpectedModCount = myModCount;
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
            if (!(myModCount == myExpectedModCount)) {
                throw new ConcurrentModificationException();
            }
            return myIter.hasNext();
        }

        /**
         * Returns the next element in the iteration. If there are no more
         * elements in this iteration, a NoSuchElementException is thrown.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iterator is empty
         */
        public T next() throws NoSuchElementException {
            if (hasNext()) {
                return myIter.next();
            } else {
                throw new NoSuchElementException();
            }
        }

        /**
         * The remove operation is not supported.
         * 
         * @throws UnsupportedOperationException if the remove operation is
         *             called
         */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
