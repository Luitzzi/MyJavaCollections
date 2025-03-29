package MySet.MyTreeSet.AvlTree;

import MySet.MyTreeSet.AbstractSearchTree;

public class AvlSearchtree<T extends Comparable<T>> extends AbstractSearchTree<T, AvlTreeNode<T>> {
    private int treeHeight;

    private int get_Height(AvlTreeNode<T> node) {
        if (node == null) {
            return -1;
        }
        else {
            return node.getHeight();
        }
    }

    public void update_Height(AvlTreeNode<T> node) {
        int heightLeftTree = get_Height(node.getLeft());
        int heightRightTree = get_Height(node.getRight());
        node.setHeight(1 + Math.max(heightLeftTree, heightRightTree));
    }

    @Override
    protected AvlTreeNode<T> create_Node(T value) {
        return new AvlTreeNode<>(value);
    }

    @Override
    protected void insert_Recursion(AvlTreeNode<T> currentRoot, AvlTreeNode<T> nodeToInsert) {
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
            check_Rotation(currentRoot, true);
        } else {
            if (currentRoot.getRight() == null) {
                // Base case: Insert as right element.
                currentRoot.setRight(nodeToInsert);
            }
            else {
                insert_Recursion(currentRoot.getRight(), nodeToInsert);
            }
            check_Rotation(currentRoot, false);
        }
    }

    private void check_Rotation(AvlTreeNode<T> currentRoot, boolean rightDegrationPossible) {
        int heightLeftSubtree = get_Height(currentRoot.getLeft());
        int heightRightSubtree = get_Height(currentRoot.getRight());
        if (Math.abs(heightLeftSubtree - heightRightSubtree) > 1) {
            // Check if the inner or outer Branch is the problem
            if (rightDegrationPossible) {
                check_Rotation_Right(currentRoot);
            }
            else {
                check_Rotation_Left(currentRoot);
            }
        }
        else {
            update_Height(currentRoot);
        }
    }

    private void check_Rotation_Right(AvlTreeNode<T> currentRoot) {
        int heightOuterBranch = get_Height(currentRoot.getLeft().getLeft());
        int heightInnerBranch = get_Height(currentRoot.getLeft().getRight());

        if (heightOuterBranch >= heightInnerBranch) {
            rotation_Right(currentRoot);
        }
        else {
            double_Rotation_Right(currentRoot);
        }
    }

    private void check_Rotation_Left(AvlTreeNode<T> currentRoot) {
        int heightOuterBranch = get_Height(currentRoot.getRight().getRight());
        int heightInnerBranch = get_Height(currentRoot.getRight().getLeft());

        if (heightOuterBranch >= heightInnerBranch) {
            rotation_Left(currentRoot);
        }
        else {
            double_Rotation_Left(currentRoot);
        }
    }

    protected void rotation_Right(AvlTreeNode<T> currentRoot) {
        AvlTreeNode<T> leftNode = currentRoot.getLeft();
        AvlTreeNode<T> newRightNode = new AvlTreeNode<>(currentRoot.getValue());

        // Copy the currentRoot into the newLeftNode
        newRightNode.setRight(currentRoot.getRight());
        newRightNode.setLeft(leftNode.getRight());
        currentRoot.setRight(newRightNode);

        // Swap the insides of the leftNode with the currentRoot and delete the leftNode
        currentRoot.setValue(leftNode.getValue());
        currentRoot.setLeft(leftNode.getLeft());

        // Update heights
        update_Height(newRightNode);
        update_Height(currentRoot);
    }

    protected void rotation_Left(AvlTreeNode<T> currentRoot) {
        AvlTreeNode<T> rightNode = currentRoot.getRight();
        AvlTreeNode<T> newLeftNode = new AvlTreeNode<>(currentRoot.getValue());

        // Copy the currentRoot into the newLeftNode
        newLeftNode.setLeft(currentRoot.getLeft());
        newLeftNode.setRight(rightNode.getLeft());
        currentRoot.setLeft(newLeftNode);

        // Swap the insides of the rightNode with the currentRoot and delete the rightNode
        currentRoot.setValue(rightNode.getValue());
        currentRoot.setRight(rightNode.getRight());

        // Update heights
        update_Height(newLeftNode);
        update_Height(currentRoot);
    }

    protected void double_Rotation_Right(AvlTreeNode<T> currentRoot) {
        rotation_Left(currentRoot.getLeft());
        rotation_Right(currentRoot);
    }

    protected void double_Rotation_Left(AvlTreeNode<T> currentRoot) {
        rotation_Right(currentRoot.getRight());
        rotation_Left(currentRoot);
    }

    @Override
    protected boolean contains(AvlTreeNode<T> currentRoot, T value) {
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

    @Override
    protected void delete(AvlTreeNode<T> previousNode, AvlTreeNode<T> currentNode, T value) {
        // Base case: Value is not in the tree.
        if (currentNode == null) {
            System.out.println("The value " + value + " doesn't exist!");
        }
        else {
            // Check in which branch the value should be.
            if (value.compareTo(currentNode.getValue()) < 0) {
                delete(currentNode, currentNode.getLeft(), value);
                check_Rotation(currentNode,false);
            } else if (value.compareTo(currentNode.getValue()) > 0) {
                delete(currentNode, currentNode.getRight(), value);
                check_Rotation(currentNode,true);
            } else {
                if (previousNode == null) {
                    // Base case: Value is at the root.
                    deleteRoot();
                } else {
                    // Base case: Value is inside the tree, or a leaf.
                    delete_Node(previousNode, currentNode);
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
    private void delete_Node(AvlTreeNode<T> previousNode, AvlTreeNode<T> nodeToDelete) {
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

    private void delete_With_Two_Descendants(AvlTreeNode<T> nodeToDelete) {
        T nextGreaterNumber;
        if (nodeToDelete.getRight().getLeft() == null) {
            nextGreaterNumber = nodeToDelete.getRight().getValue();
            nodeToDelete.setRight(nodeToDelete.getRight().getRight());
            update_Height(nodeToDelete);
        }
        else {
            nextGreaterNumber = get_Inorder_Successor(nodeToDelete.getRight(), nodeToDelete.getRight().getLeft());
            update_Height(nodeToDelete.getRight());
            update_Height(nodeToDelete);
        }
        nodeToDelete.setValue(nextGreaterNumber);
    }

    // For case: "Delete with two descendants"
    // -> Switch the value of the nodeToDelete with the next greater value in the tree and delete this node
    protected T get_Inorder_Successor(AvlTreeNode<T> previousNode, AvlTreeNode<T> currentNode) {
        if (currentNode.getLeft() == null) {
            previousNode.setLeft(null);
            return currentNode.getValue();
        }
        else {
            T nextGreaterNumber = get_Inorder_Successor(currentNode, currentNode.getLeft());
            update_Height(currentNode);
            return nextGreaterNumber;
        }
    }
}
