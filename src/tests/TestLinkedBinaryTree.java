package tests;

import static org.junit.Assert.*;

import exceptions.EmptyCollectionException;
import org.junit.Before;
import org.junit.Test;


import structures.LinkedBinaryTree;

/**
 * Test the implemented methods from the class LinkedBinaryTree.
 * @author Yaro Salo
 * @version 1.0
 *
 */
public class TestLinkedBinaryTree {

    /**
     * The element at the root of my fixture.
     */
    private static final int ROOT_ELM = 61;
    
    /**
     * Represent 10.
     */
    private static final int TEN = 10;
    /**
     * Represent 20.
     */
    private static final int TWENTY = 20;
    /**
     * Represent 40.
     */
    private static final int FORTY = 40;
    
    /**
     * Expected size.
     */
    private static final int EXPECTED_SIZE = 6;
    
    /** The data structure to represent a balanced tree. */
    private LinkedBinaryTree<Integer> myNormalTree;
    
    /** The data structure to represent a tree that is like a linked list. */
    private LinkedBinaryTree<Integer> myLineTree;
    
    /** The data structure to represent a tree that zig-zags. */
    private LinkedBinaryTree<Integer> myZigZagTree;
    
    /** The data structure to represent a tree that has a shape of a pyramid. */
    private LinkedBinaryTree<Integer> myPyramidTree;
     
    /**
     * Method to initialize test fixtures.
     */
    @Before
    public void setUp() {
    
        final LinkedBinaryTree<Integer> tree1 = new LinkedBinaryTree<>(TWENTY);
        final LinkedBinaryTree<Integer> tree2 = new LinkedBinaryTree<>(FORTY);
        final LinkedBinaryTree<Integer> tree3 = new LinkedBinaryTree<>(TWENTY);
        final LinkedBinaryTree<Integer> tree4 = new LinkedBinaryTree<>(TEN, tree1, tree2);
        final LinkedBinaryTree<Integer> tree5 = new LinkedBinaryTree<>(TWENTY, null, tree3);
        myNormalTree = new LinkedBinaryTree<>(ROOT_ELM, tree4, tree5);
        
        
        final LinkedBinaryTree<Integer> tree6 = new LinkedBinaryTree<>(FORTY);
        final LinkedBinaryTree<Integer> tree7 = new LinkedBinaryTree<>(TWENTY, tree6 , null);
        final LinkedBinaryTree<Integer> tree8 = new LinkedBinaryTree<>(TEN, tree7, null);
        myLineTree = new LinkedBinaryTree<>(ROOT_ELM, tree8, null);
        
        final LinkedBinaryTree<Integer> tree9 = new LinkedBinaryTree<>(TEN);
        final LinkedBinaryTree<Integer> tree10 = new LinkedBinaryTree<>(TWENTY, tree9, null);
        final LinkedBinaryTree<Integer> tree11 = new LinkedBinaryTree<>(FORTY, null, tree10);
        myZigZagTree = new LinkedBinaryTree<>(TEN, tree11, null);
        
        final LinkedBinaryTree<Integer> tree12 = new LinkedBinaryTree<>(TEN);
        final LinkedBinaryTree<Integer> tree13 = new LinkedBinaryTree<>(TWENTY);
        final LinkedBinaryTree<Integer> tree14 = new LinkedBinaryTree<>(FORTY, tree12, null);
        final LinkedBinaryTree<Integer> tree15 = new LinkedBinaryTree<>(TEN, null, tree13);
        myPyramidTree = new LinkedBinaryTree<>(ROOT_ELM, tree14, tree15);
    
    }

    /**
     * Test the getRootElement() method.
     */
    @Test
    public void testGetRootElement() {
        assertTrue("Same root", myNormalTree.getRootElement().equals(ROOT_ELM));
        assertFalse("Different root.", myNormalTree.getRootElement().equals(FORTY));
        
    }


    /**
     *  Test the size() method.
     */
    @Test
    public void testSize() {
        assertEquals("Unexpected size", myNormalTree.size(), EXPECTED_SIZE);
    }
    
    /**
     *  Test the size() method empty.
     *  @throws EmptyCollectionException because the collection is empty.
     */
    @Test(expected = EmptyCollectionException.class)
    public void testSizeEmpty() {
        final LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        tree.size();
    }

    /**
     * Test the getHeight() method.
     */
    @Test
    public void testGetHeight() {
        assertEquals("Equals.", myNormalTree.getHeight(), 2);
        assertNotEquals("Not Equals.", myNormalTree.getHeight(), 0);

        final int heightL = 3;
        assertEquals("Equal.", myLineTree.getHeight(), heightL);
        assertNotEquals("Not Equal.", myLineTree.getHeight(), 2);
        
        final int heighZ = 3;
        assertEquals("Zig Equals.", myZigZagTree.getHeight(), heighZ);
        assertNotEquals("Zig Not Equal.", myZigZagTree.getHeight(), 2);
              
    }

    /**
     * Test the countLeafNodes() method.
     */
    @Test
    public void testCountLeafNodes() {
        final int leafN = 3;
        assertEquals(myNormalTree.countLeafNodes(), leafN);

        final int leafL = 1;
        assertEquals(myLineTree.countLeafNodes(), leafL);
        
        final int leafZ = 1;
        assertEquals("Zig Equal.", myZigZagTree.countLeafNodes(), leafZ);
        
        final int leafP = 2;
        assertEquals(myPyramidTree.countLeafNodes(), leafP);
    }

    /**
     * Test the countOneChildNodes() method.
     */
    @Test
    public void testCountOneChildNodes() {
        final int childN = 1;
        assertEquals(myNormalTree.countOneChildNodes(), childN);

        final int childL = 3;
        assertEquals(myLineTree.countOneChildNodes(), childL);
        
        final int childZ = 3;
        assertEquals(myZigZagTree.countOneChildNodes(), childZ);
        
        
        final int childP = 2;
        assertEquals(myPyramidTree.countOneChildNodes(), childP);
    }

    /**
     * Test the contains method.
     */
    @Test
    public void testContains() {
        assertTrue("The tree contains 10", myNormalTree.contains(TEN));
        assertTrue("The tree contains 40", myNormalTree.contains(FORTY));
        assertFalse("The tree does not contain 0", myNormalTree.contains(0));
        
    }
    
    /**
     * Test the contains method.
     */
    @Test
    public void testContainsEmpty() {
        final LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        assertFalse("Empty collection", tree.contains(ROOT_ELM));
        
    }

    /**
     * Test the toString() method.
     */
    @Test
    public void testToString() {
        assertEquals("toString() prints unexpected result", 
                     myNormalTree.toString(), "[20 10 40 61 20 20]");
    }
}
