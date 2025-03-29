package MyTreeSetTest;

import MySet.MyTreeSet.AvlTree.AvlSearchtree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AvlSearchtreeTestInsertion {
    private AvlSearchtree<Integer> tree;

    @BeforeEach
    public void setup() {
        tree = new AvlSearchtree<>();
    }

    @Test
    public void insert_Without_Rotations() {
        Integer []treeElements = {6,3,8,1,4,7,10};
        tree.create_Tree(treeElements);

        String result = "( ( ( n , 1 , n ) , 3 , ( n , 4 , n ) ) , 6 , ( ( n , 7 , n ) , 8 , ( n , 10 , n ) ) )";
        assertEquals(tree.print_Inorder_As_String(), result);
    }

    @Test
    public void insert_Left_Rotation() {
        Integer[] treeElements = {15, 10, 20, 17, 25, 26};
        tree.create_Tree(treeElements);

        // Left Branch
        assertEquals(10, tree.getRoot().getLeft().getLeft().getValue());
        assertEquals(15, tree.getRoot().getLeft().getValue());
        assertEquals(17, tree.getRoot().getLeft().getRight().getValue());
        // Root
        assertEquals(20, tree.getRoot().getValue());
        // Right Branch
        assertEquals(25, tree.getRoot().getRight().getValue());
        assertEquals(26, tree.getRoot().getRight().getRight().getValue());
    }

    @Test
    public void insert_Right_Rotation() {
        Integer[] treeElements = {20,15,25,10,17,9};
        tree.create_Tree(treeElements);

        // Left Branch
        assertEquals(9, tree.getRoot().getLeft().getLeft().getValue());
        assertEquals(10, tree.getRoot().getLeft().getValue());
        // Root
        assertEquals(15, tree.getRoot().getValue());
        // Right Branch
        assertEquals(20, tree.getRoot().getRight().getValue());
        assertEquals(17, tree.getRoot().getRight().getLeft().getValue());
        assertEquals(25, tree.getRoot().getRight().getRight().getValue());
    }

    @Test
    public void insert_Right_Left_Rotation() {
        Integer[] treeElements = {15,10,20,17,25,16};
        tree.create_Tree(treeElements);

        // Left Branch
        assertEquals(10, tree.getRoot().getLeft().getLeft().getValue());
        assertEquals(15, tree.getRoot().getLeft().getValue());
        assertEquals(16, tree.getRoot().getLeft().getRight().getValue());
        // Root
        assertEquals(17, tree.getRoot().getValue());
        // Right Branch
        assertEquals(20, tree.getRoot().getRight().getValue());
        assertEquals(25, tree.getRoot().getRight().getRight().getValue());
    }

    @Test
    public void insert_Left_Right_Rotation() {
        Integer[] treeElements = {20,15,25,10,17,18};
        tree.create_Tree(treeElements);

        // Left Branch
        assertEquals(10, tree.getRoot().getLeft().getLeft().getValue());
        assertEquals(15, tree.getRoot().getLeft().getValue());
        // Root
        assertEquals(17, tree.getRoot().getValue());
        // Right Branch
        assertEquals(20, tree.getRoot().getRight().getValue());
        assertEquals(18, tree.getRoot().getRight().getLeft().getValue());
        assertEquals(25, tree.getRoot().getRight().getRight().getValue());
    }
}