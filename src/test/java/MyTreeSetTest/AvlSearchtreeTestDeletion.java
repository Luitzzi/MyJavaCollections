package MyTreeSetTest;

import MySet.MyTreeSet.AvlTree.AvlSearchtree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AvlSearchtreeTestDeletion {
    private AvlSearchtree<Integer> tree;

    @BeforeEach
    public void setup() {
        tree = new AvlSearchtree<>();
    }

    // ********** Test without rotation Leaf *******************************************************

    @Test
    public void delete_Without_Rotations_Leaf() {
        Integer []treeElements = {6,3,8,1,4,7,10};
        tree.create_Tree(treeElements);
        tree.delete(10);

        String result = "( ( ( n , 1 , n ) , 3 , ( n , 4 , n ) ) , 6 , ( ( n , 7 , n ) , 8 , n ) )";
        assertEquals(tree.print_Inorder_As_String(), result);
    }

    // ********** Test without rotation Inside *******************************************************

    @Test
    public void delete_Without_Rotations_Inside_Left_Descendent() {
        Integer []treeElements = {6,3,8,1,4,7,10};
        tree.create_Tree(treeElements);
        tree.delete(10);
        tree.delete(8);

        String result = "( ( ( n , 1 , n ) , 3 , ( n , 4 , n ) ) , 6 , ( n , 7 , n ) )";
        assertEquals(tree.print_Inorder_As_String(), result);
    }

    @Test
    public void delete_Without_Rotations_Inside_Right_Descendent() {
        Integer []treeElements = {6,3,8,1,4,7,10};
        tree.create_Tree(treeElements);
        tree.delete(7);
        tree.delete(8);

        String result = "( ( ( n , 1 , n ) , 3 , ( n , 4 , n ) ) , 6 , ( n , 10 , n ) )";
        assertEquals(tree.print_Inorder_As_String(), result);
    }

    @Test
    public void delete_Without_Rotations_Inside_Two_Descendents() {
        Integer []treeElements = {5,4,8,3,7,9,6};
        tree.create_Tree(treeElements);
        tree.delete(5);

        // Left Branch
        assertEquals(3, tree.getRoot().getLeft().getLeft().getValue());
        assertEquals(4, tree.getRoot().getLeft().getValue());
        // Root
        assertEquals(6, tree.getRoot().getValue());
        // Right Branch
        assertEquals(8, tree.getRoot().getRight().getValue());
        assertEquals(7, tree.getRoot().getRight().getLeft().getValue());
        assertEquals(9, tree.getRoot().getRight().getRight().getValue());

        // Check Height
        assertEquals(2, tree.getRoot().getHeight(), "Old height: 3");
    }

    // ********** Test without rotation Root *******************************************************

    @Test
    public void delete_Without_Rotations_Root_No_Descendents() {
        Integer []treeElements = {2};
        tree.create_Tree(treeElements);
        tree.delete(2);

        assertNull(tree.getRoot());
    }

    @Test
    public void delete_Without_Rotations_Root_Left_Descendents() {
        Integer []treeElements = {2,1};
        tree.create_Tree(treeElements);
        tree.delete(2);

        assertEquals(1, tree.getRoot().getValue());
    }

    @Test
    public void delete_Without_Rotations_Root_Right_Descendents() {
        Integer []treeElements = {2,3};
        tree.create_Tree(treeElements);
        tree.delete(2);

        assertEquals(3, tree.getRoot().getValue());
    }

    @Test
    public void delete_Without_Rotations_Root_Two_Descendents() {
        Integer []treeElements = {2,1,3};
        tree.create_Tree(treeElements);
        tree.delete(2);

        assertEquals(3, tree.getRoot().getValue());
        assertEquals(1, tree.getRoot().getLeft().getValue());
    }

    // ********** Test with rotation ***********************************************************

    @Test
    public void delete_Left_Rotation() {
        Integer[] treeElements = {4,3,6,5,7};
        tree.create_Tree(treeElements);
        tree.delete(3);

        // Left Branch
        assertEquals(5, tree.getRoot().getLeft().getRight().getValue());
        assertEquals(4, tree.getRoot().getLeft().getValue());
        // Root
        assertEquals(6, tree.getRoot().getValue());
        // Right Branch
        assertEquals(7, tree.getRoot().getRight().getValue());
    }

    @Test
    public void delete_Right_Rotation() {
        Integer[] treeElements = {4,2,5,1,3};
        tree.create_Tree(treeElements);
        tree.delete(5);


        // Left Branch
        assertEquals(1, tree.getRoot().getLeft().getValue());
        // Root
        assertEquals(2, tree.getRoot().getValue());
        // Right Branch
        assertEquals(4, tree.getRoot().getRight().getValue());
        assertEquals(3, tree.getRoot().getRight().getLeft().getValue());
    }

    @Test
    public void delete_Right_Left_Rotation() {
        Integer[] treeElements = {3,2,7,1,5,8,6};
        tree.create_Tree(treeElements);
        tree.delete(1);

        // Left Branch
        assertEquals(2, tree.getRoot().getLeft().getLeft().getValue());
        assertEquals(3, tree.getRoot().getLeft().getValue());
        // Root
        assertEquals(5, tree.getRoot().getValue());
        // Right Branch
        assertEquals(7, tree.getRoot().getRight().getValue());
        assertEquals(6, tree.getRoot().getRight().getLeft().getValue());
        assertEquals(8, tree.getRoot().getRight().getRight().getValue());
    }

    @Test
    public void delete_Left_Right_Rotation() {
        Integer[] treeElements = {5,2,6,1,4,7,3};
        tree.create_Tree(treeElements);
        tree.delete(7);

        // Left Branch
        assertEquals(1, tree.getRoot().getLeft().getLeft().getValue());
        assertEquals(2, tree.getRoot().getLeft().getValue());
        assertEquals(3, tree.getRoot().getLeft().getRight().getValue());
        // Root
        assertEquals(4, tree.getRoot().getValue());
        // Right Branch
        assertEquals(5, tree.getRoot().getRight().getValue());
        assertEquals(6, tree.getRoot().getRight().getRight().getValue());
    }
}