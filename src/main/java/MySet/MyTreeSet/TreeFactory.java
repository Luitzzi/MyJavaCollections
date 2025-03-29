package MySet.MyTreeSet;

import MySet.MyTreeSet.AvlTree.AvlSearchtree;
import MySet.MyTreeSet.AvlTree.AvlTreeNode;

import java.util.ArrayList;

/**
 * Recreate a tree or searchtree from one or two arrays sorted in a specific way.
 * Methods:
 *  - build_tree -> Recreates an exact tree using two arrays in In- and Preorder.
 *  - build_searchtree -> Recreates it only from an array in Preorder.
 *  Alternatively a tree can be recreated from Inorder and Postorder
 *  and a searchtree from one of the three variations - In-, Post-, Preorder.
 */
public class TreeFactory {

    public static AvlSearchtree<Integer> build_Tree(Integer []inorder, Integer []preorder) {
        AvlSearchtree<Integer> tree = new AvlSearchtree<>();
        AvlTreeNode<Integer> dummyNode = new AvlTreeNode<>();
        dummyNode.setLeft(build_Tree(inorder,preorder,dummyNode));
        tree.setRoot(dummyNode.getLeft());
        return tree;
    }

    public static AvlTreeNode<Integer> build_Tree(Integer []inorder, Integer []preorder, AvlTreeNode<Integer> previousNode) {
        if (inorder.length == 0) {
            return null;
        }
        else if (inorder.length == 1) {
            return new AvlTreeNode<>(inorder[0]);
        }
        else {
            int rootValue = preorder[0];
            ArrayList<Integer> leftInorder = new ArrayList<>();
            ArrayList<Integer> rightInorder = new ArrayList<>();
            boolean passedRoot = false;
            for (Integer value : inorder) {
                if (value != rootValue) {
                    if (!passedRoot) {
                        leftInorder.add(value);
                    } else {
                        rightInorder.add(value);
                    }
                }
                else {
                    passedRoot = true;
                }
            }

            ArrayList<Integer> leftPreorder = new ArrayList<>();
            ArrayList<Integer> rightPreorder = new ArrayList<>();
            for (int i = 1; i <= leftInorder.size(); i++) {
                leftPreorder.add(preorder[i]);
            }
            for (int i = leftInorder.size() + 1; i < preorder.length; i++) {
                rightPreorder.add(preorder[i]);
            }

            AvlTreeNode<Integer> currentRoot = new AvlTreeNode<>(rootValue);
            currentRoot.setLeft(build_Tree(leftInorder.toArray(new Integer[0]), leftPreorder.toArray(new Integer[0]), currentRoot));
            currentRoot.setRight(build_Tree(rightInorder.toArray(new Integer[0]), rightPreorder.toArray(new Integer[0]), currentRoot));
            return currentRoot;
        }
    }

    public static AvlSearchtree<Integer> build_Searchtree(Integer []preorder) {
        AvlSearchtree<Integer> tree = new AvlSearchtree<>();
        AvlTreeNode<Integer> dummy = new AvlTreeNode<>();
        dummy.setLeft(build_Searchtree(preorder, dummy));
        tree.setRoot(dummy.getLeft());
        return tree;
    }

    public static AvlTreeNode<Integer> build_Searchtree(Integer []preorder, AvlTreeNode<Integer> previousNode) {
        if (preorder.length == 0) {
            return null;
        }
        else if (preorder.length == 1) {
            return new AvlTreeNode<>(preorder[0]);
        }
        else {
            Integer rootValue = preorder[0];
            AvlTreeNode<Integer> currentRoot = new AvlTreeNode<>(rootValue);
            ArrayList<Integer> leftPreorder = new ArrayList<>();
            ArrayList<Integer> rightPreorder = new ArrayList<>();
            for (int i = 1; i < preorder.length; i++) {
                if (preorder[i] < rootValue) {
                    leftPreorder.add(preorder[i]);
                } else {
                    rightPreorder.add(preorder[i]);
                }
            }
            currentRoot.setLeft(build_Searchtree(leftPreorder.toArray(new Integer[0]), currentRoot));
            currentRoot.setRight(build_Searchtree(rightPreorder.toArray(new Integer[0]), currentRoot));
            return currentRoot;
        }
    }
}
