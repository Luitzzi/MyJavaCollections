package MySet.MyTreeSet.SearchTree;

import MySet.MyTreeSet.AbstractSearchTree;

public class Searchtree<T extends Comparable<T>> extends AbstractSearchTree<T, BasicTreeNode<T>> {

    @Override
    protected BasicTreeNode<T> create_Node(T value) {
        return new BasicTreeNode<>(value);
    }

    @Override
    protected void insert_Recursion(BasicTreeNode<T> currentRoot, BasicTreeNode<T> nodeToInsert) {
        T rootValue = currentRoot.getValue();
        T insertValue = nodeToInsert.getValue();
        // Check which branch the recursion needs to follow to get to the insertion place.
        if (insertValue.compareTo(rootValue) < 0) {
            if (currentRoot.getLeft() == null) {
                // Base case: Insert as left element.
                currentRoot.setLeft(nodeToInsert);
            }
            else {
                insert_Recursion(currentRoot.getLeft(), nodeToInsert);
            }
        } else {
            if (currentRoot.getRight() == null) {
                // Base case: Insert as right element.
                currentRoot.setRight(nodeToInsert);
            }
            else {
                insert_Recursion(currentRoot.getRight(), nodeToInsert);
            }
        }
    }

    public void insert_Iterative(T value) {
        BasicTreeNode<T> currentRoot = root;
        BasicTreeNode<T> nodeToInsert = new BasicTreeNode<>(value);
        boolean gotInserted = false;

        if (root == null) {
            setRoot(nodeToInsert);
        }
        else {
            while (!gotInserted) {
                T rootValue = currentRoot.getValue();
                T insertValue = nodeToInsert.getValue();
                // Check which branch the recursion needs to follow to get to the insertion place.
                if (insertValue.compareTo(rootValue) < 0) {
                    if (currentRoot.getLeft() == null) {
                        // Base case: Insert as left element.
                        currentRoot.setLeft(nodeToInsert);
                        gotInserted = true;
                    }
                    else {
                        currentRoot = currentRoot.getLeft();
                    }
                } else {
                    if (currentRoot.getRight() == null) {
                        // Base case: Insert as right element.
                        currentRoot.setRight(nodeToInsert);
                        gotInserted = true;
                    }
                    else {
                        currentRoot = currentRoot.getRight();
                    }
                }
            }
        }
    }

    @Override
    protected boolean contains(BasicTreeNode<T> currentRoot, T value) {
        if (currentRoot == null) {
            System.out.println("The tree doesn't contain the value " + value + ".");
            return false;
        }
        else if (currentRoot.getValue().compareTo(value) == 0) {
            // Element found.
            System.out.println("The tree contains the value " + value + ".");
            return true;
        }
        else {
            // Check in which branch the element should be.
            if (value.compareTo(currentRoot.getValue()) < 0) {
                return contains(currentRoot.getLeft(), value);
            }
            else return contains(currentRoot.getRight(), value);
        }
    }

    @SuppressWarnings("unused")
    public void delete_Iterative(T value) {
        BasicTreeNode<T> currentNode = root;
        BasicTreeNode<T> previousNode = null;
        boolean gotDeleted = false;

        while (!gotDeleted) {
            if (currentNode == null) {
                System.out.println("The value " + value + " doesn't exist!");
                gotDeleted = true;
            }
            else {
                if (value.compareTo(currentNode.getValue()) < 0) {
                    previousNode = currentNode;
                    currentNode = currentNode.getLeft();
                } else if (value.compareTo(currentNode.getValue()) > 0) {
                    previousNode = currentNode;
                    currentNode = currentNode.getRight();
                } else {
                    if (previousNode == null) {
                        deleteRoot();
                    } else {
                        deleteNode(previousNode, currentNode);
                    }
                    gotDeleted = true;
                }
            }
        }

    }

    protected void delete(BasicTreeNode<T> previousNode, BasicTreeNode<T> currentNode, T value) {
        // Base case: Value is not in the tree.
        if (currentNode == null) {
            System.out.println("The value " + value + " doesn't exist!");
        }
        else {
            // Check in which branch the value should be.
            if (value.compareTo(currentNode.getValue()) < 0) {
                delete(currentNode, currentNode.getLeft(), value);
            } else if (value.compareTo(currentNode.getValue()) > 0) {
                delete(currentNode, currentNode.getRight(), value);
            } else {
                if (previousNode == null) {
                    // Base case: Value is at the root.
                    deleteRoot();
                } else {
                    // Base case: Value is inside the tree, or a leaf.
                    deleteNode(previousNode, currentNode);
                }
            }
        }
    }

    private void deleteRoot() {
        boolean hasLeftDescendant = root.getLeft() != null;
        boolean hasRightDescendant = root.getRight() != null;

        if (hasLeftDescendant && hasRightDescendant) {
            delete_With_Two_Descendants(root);
        } else if (hasLeftDescendant) {
            setRoot(root.getLeft());
        } else if (hasRightDescendant) {
            setRoot(root.getRight());
        } else {
            setRoot(null);
        }
    }

    // Delete the node in the left, or right branch considering the cases:
    // - Has two descendants
    // - Has only a left/ right descendants
    // - Has no descendants
    private void deleteNode(BasicTreeNode<T> previousNode, BasicTreeNode<T> nodeToDelete) {
        boolean isLeftNode = previousNode.getLeft() == nodeToDelete;
        boolean hasLeftDescendant = nodeToDelete.getLeft() != null;
        boolean hasRightDescendant = nodeToDelete.getRight() != null;

        if (isLeftNode) {
            if (hasLeftDescendant && hasRightDescendant) {
                delete_With_Two_Descendants(nodeToDelete);
            } else if (hasLeftDescendant) {
                previousNode.setLeft(nodeToDelete.getLeft());
            } else if (hasRightDescendant) {
                previousNode.setLeft(nodeToDelete.getRight());
            } else {
                previousNode.setLeft(null);
            }
        }
        else {
            if (hasLeftDescendant && hasRightDescendant) {
                delete_With_Two_Descendants(nodeToDelete);
            } else if (hasLeftDescendant) {
                previousNode.setRight(nodeToDelete.getLeft());
            } else if (hasRightDescendant) {
                previousNode.setRight(nodeToDelete.getRight());
            } else {
                previousNode.setRight(null);
            }
        }
    }

    private void delete_With_Two_Descendants(BasicTreeNode<T> nodeToDelete) {
        T nextGreaterNumber;
        if (nodeToDelete.getRight().getLeft() == null) {
            nextGreaterNumber = nodeToDelete.getRight().getValue();
            nodeToDelete.setRight(nodeToDelete.getRight().getRight());
        }
        else {
            nextGreaterNumber = get_Inorder_Successor(nodeToDelete.getRight(), nodeToDelete.getRight().getLeft());
        }
        nodeToDelete.setValue(nextGreaterNumber);
    }

    // For case: "Delete with two descendants"
    // -> Switch the value of the nodeToDelete with the next greater value in the tree and delete this node
    protected T get_Inorder_Successor(BasicTreeNode<T> previousNode, BasicTreeNode<T> currentNode) {
        if (currentNode.getLeft() == null) {
            previousNode.setLeft(null);
            return currentNode.getValue();
        }
        else return get_Inorder_Successor(currentNode, currentNode.getLeft());
    }
}
