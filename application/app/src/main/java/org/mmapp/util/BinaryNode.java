package org.mmapp.util;

import org.w3c.dom.Node;

/**
 * MIT License (http://choosealicense.com/licenses/mit/)
 * <p><br>
 * <b>BinaryNode</b><br>
 * Creates a binary node.
 * </p><br>
 *
 * @author Erik Roemmelt
 */
public class BinaryNode<T> {

    private BinaryNode<T> left, right;
    private T value;

    public BinaryNode(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public T getValue() {
        return this.value;
    }
    public BinaryNode<T> getLeft() {
        return this.left;
    }
    public BinaryNode<T> getRight() {
        return this.right;
    }

    public void setValue(T value) {
        this.value = value;
    }
    public void setLeft(BinaryNode<T> left) {
        this.left = left;
    }
    public void setRight(BinaryNode<T> right) {
        this.right = right;
    }

    public boolean isInternal() {
        return (this.left != null) || (this.right != null);
    }
}
