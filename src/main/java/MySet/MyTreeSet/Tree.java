package MySet.MyTreeSet;

import MySet.MyTreeSet.SearchTree.BasicTreeNode;

import java.lang.reflect.Array;

public class Tree<T extends Comparable<T>>{
    private BasicTreeNode<T> root;
    private Class<T> clazz;

    public void build_Tree(T[] inorder, T[] preorder, Class<T> clazz) {
        this.clazz = clazz;
        root = new BasicTreeNode<>(null);
        BasicTreeNode<T> newRoot = build_Tree(inorder, preorder, root);
        root.setValue(newRoot.getValue());
    }

    public BasicTreeNode<T> build_Tree(T[] inorder, T[] preorder, BasicTreeNode<T> prevNode) {
        if (inorder.length != preorder.length) {
            throw new IllegalArgumentException("Arrays have not the same length");
        }

        if (inorder.length == 1) {
            return new BasicTreeNode<>(inorder[0]);
        }
        else {
            T root = preorder[0];
            int rootIndexInorder = -1;
            for (int i = 0; i < inorder.length; i++) {
                if (inorder[i] == root) {
                    rootIndexInorder = i;
                    break;
                }
            }
            if (rootIndexInorder == -1) {
                throw new IllegalArgumentException("Wrong input!");
            }
            T[] newInorderLeft = (T[]) Array.newInstance(clazz, rootIndexInorder);
            T[] newInorderRight = (T[]) Array.newInstance(clazz, rootIndexInorder);
            for (int i = 0; i < rootIndexInorder; i++) {
                newInorderLeft[i] = inorder[i];
            }
            for (int i = rootIndexInorder + 1; i < inorder.length; i++) {
                newInorderRight[i - rootIndexInorder + 1] = inorder[i];
            }

            T[] newPreorderLeft = (T[]) Array.newInstance(clazz, rootIndexInorder);
            T[] newPreorderRight = (T[]) Array.newInstance(clazz, rootIndexInorder);

            for (int i = 0; i < rootIndexInorder; i++) {
                newPreorderLeft[i] = preorder[i];
            }
            for (int i = rootIndexInorder + 1; i < inorder.length; i++) {
                newPreorderRight[i - rootIndexInorder + 1] = preorder[i];
            }

            BasicTreeNode<T> newRoot = new BasicTreeNode<>(root);
            prevNode.setLeft(build_Tree(newInorderLeft, newPreorderLeft, newRoot));
            prevNode.setRight(build_Tree(newInorderRight, newPreorderRight, newRoot));
            return newRoot;
        }
    }
}
