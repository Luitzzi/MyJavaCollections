package MySet.MyTreeSet;

public interface TreeNode<T extends Comparable<T>, N extends TreeNode<T,N>> {
    T getValue();
    void setValue(T value);
    N getLeft();
    void setLeft(N left);
    N getRight();
    void setRight(N right);
}
