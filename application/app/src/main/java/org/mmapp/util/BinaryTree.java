package org.mmapp.util;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>BinaryTree</b><br>
 * Creates a binary tree.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class BinaryTree<T> {

    private BinaryNode<T> root;

    public BinaryTree(BinaryNode<T> rootNode) {
        this.root = rootNode;
    }

    public void setRoot(BinaryNode<T> rootNode) {
        this.root = rootNode;
    }
    public BinaryNode<T> getRoot() {
        return this.root;
    }
    // getSize()
}
