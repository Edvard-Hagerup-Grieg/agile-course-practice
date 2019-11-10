package ru.unn.agile.binaryTree.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BinaryTreeManipulationTests {
    private BinaryTree binaryTree;

    private int     simpleKey  = 42;
    private String  simpleData = "SimpleText";

    @Before
    public void setUp() {
        binaryTree = new BinaryTree();
        binaryTree.add(simpleKey, simpleData);
    }

    @Test
    public void canFindElement() {
        final String result = binaryTree.find(simpleKey);

        assertEquals(simpleData, result);
    }

    @Test
    public void canRemoveNode() {
        binaryTree.remove(simpleKey);
        final String result = binaryTree.find(simpleKey);

        assertNull(result);
    }

    @Test
    public void noErrorForFindNotExistsKey() {
        final int notExistsKey = 666;

        assertNull(binaryTree.find(notExistsKey));
    }

    @Test
    public void noErrorForRemoveNotExistsKey() {
        final int notExistsKey = 666;

        assertFalse(binaryTree.remove(notExistsKey));
    }
}
