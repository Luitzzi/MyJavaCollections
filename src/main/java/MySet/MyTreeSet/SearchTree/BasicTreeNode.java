package MySet.MyTreeSet.SearchTree;

import MySet.MyTreeSet.TreeNode;

public class BasicTreeNode<T extends Comparable<T>> implements TreeNode<T,BasicTreeNode<T>> {
    private T value;
    private BasicTreeNode<T> left;
    private BasicTreeNode<T> right;

    public BasicTreeNode(T value) {
        this.value = value;
        left = null;
        right = null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public BasicTreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(BasicTreeNode<T> left) {
        this.left = left;
    }

    public BasicTreeNode<T> getRight() {
        return right;
    }

    public void setRight(BasicTreeNode<T> right) {
        this.right = right;
    }
}