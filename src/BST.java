/*
    This file, BST.java, provides the class with the underlying logic for BSTAnimation. It implements a binary
    search tree which implements the Tree interface, with numerous methods for inserting, deleting, and traversing
    the tree.
*/

import java.util.*;

public class BST<E extends Comparable<E>> implements Tree<E> {
    protected TreeNode<E> root;
    protected int size = 0;

    /** Create a default binary tree */
    public BST() {
    }

    /** Create a binary tree from an array of objects */
    public BST(E[] objects) {
        for (int i = 0; i < objects.length; i++)
            add(objects[i]);
    }

    // Create height function to return the height of a tree, calls height recursively
    public int height() {
        if (size == 0) { // Empty tree, return -1
            return -1;
        } else if (size == 1) { // Tree with single node, return 0
            return 0;
        } else {
            return height(root); // Recursively find height
        }
    }

    // Recursive function to determine height of tree
    private int height(TreeNode<E> node) {
        if (node == null) { // Base case
            return 0;
        } else { // Recursively call height to find height of left and right subtrees
            int leftHeight = height(node.left);
            int rightHeight= height(node.right);

            // Take whichever is largest
            if (leftHeight > rightHeight) {
                return leftHeight + 1;
            } else {
                return rightHeight + 1;
            }
        }
    }

    @Override /** Returns true if the element is in the tree */
    public boolean search(E e) {
        TreeNode<E> current = root; // Start from the root

        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else
                return true; // e is found
        }

        return false;
    }

    @Override /** Insert element o into the binary tree
     * Return true if the element is inserted successfully */
    public boolean insert(E e) {
        if (root == null)
            root = createNewNode(e); // Create a new root
            //root = new TreeNode<>(e);	// Question:  Why not do it this way?  It would work.
        else {
            // Locate the parent node
            TreeNode<E> parent = null;
            TreeNode<E> current = root;
            while (current != null)
                if (e.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                }
                else if (e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                }
                else
                    return false; // Duplicate node not inserted

            // Create the new node and attach it to the parent node
            if (e.compareTo(parent.element) < 0)
                parent.left = createNewNode(e);
                //parent.left = new TreeNode<>(e);
            else
                parent.right = createNewNode(e);
        }

        size++;
        return true; // Element inserted successfully
    }

    protected TreeNode<E> createNewNode(E e) {  // Factory method design pattern
        return new TreeNode<>(e);
    }

    // Define variable to hold current ordered list
    java.util.ArrayList<E> orderedList = new ArrayList<E>();

    // Define helper method to add elements to ordered list in inorder fashion
    private void inorderList(TreeNode<E> root, List<E> list) {
        if (root == null) return;
        inorderList(root.left, list);
        list.add(root.element);
        inorderList(root.right, list);
    }

    // Define method to return list of inorder elements by calling helper method
    public java.util.List<E> inorderList() {
        orderedList.clear(); // Clear previous list as we are reordering it
        inorderList(root, orderedList); // call helper method
        return orderedList;
    }

    // Define helper method to add elements to ordered list in preorder fashion
    private void preorderList(TreeNode<E> root, List<E> list) {
        if (root == null) return;
        list.add(root.element);
        preorderList(root.left, list);
        preorderList(root.right, list);
    }

    // Define method to return list of preorder elements by calling helper method
    public java.util.List<E> preorderList() {
        orderedList.clear();
        preorderList(root, orderedList);
        return orderedList;
    }

    // Define helper method to add elements to ordered list in postorder fashion
    private void postorderList(TreeNode<E> root, List<E> list) {
        if (root == null) return;
        postorderList(root.left, list);
        postorderList(root.right, list);
        list.add(root.element);
    }


    // Define method to return list of postorder elements by calling helper method
    public java.util.List<E> postorderList() {
        orderedList.clear();
        postorderList(root, orderedList);
        return orderedList;
    }

    // Define helper method to add elements to ordered list in breadth-first fashion
    private void breadthFirstOrderList(TreeNode<E> root, List<E> list) {
        Queue<TreeNode<E>> queue = new LinkedList<>(); // Instantiate new empty queue
        if (root == null) return; // Base case
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode<E> node = queue.remove();
            list.add(node.element);
            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
    }

    // Define method to return list of breadth-first elements by calling helper method
    public java.util.List<E> breadthFirstOrderList() {
        orderedList.clear();
        breadthFirstOrderList(root, orderedList);
        return orderedList;
    }

    /** This inner class is static, because it does not access
     any instance members defined in its outer class */
    public static class TreeNode<E> {
        protected E element;
        protected TreeNode<E> left;
        protected TreeNode<E> right;

        public TreeNode(E e) {
            element = e;
        }
    }

    @Override /** Get the number of nodes in the tree */
    public int getSize() {
        return size;
    }

    /** Returns the root of the tree */
    public TreeNode<E> getRoot() {
        return root;
    }

    /** Returns a path from the root leading to the specified element */
    public java.util.ArrayList<TreeNode<E>> path(E e) {
        java.util.ArrayList<TreeNode<E>> list =
                new java.util.ArrayList<>();
        TreeNode<E> current = root; // Start from the root

        while (current != null) {
            list.add(current); // Add the node to the list
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else
                break;
        }

        return list; // Return an array list of nodes
    }

    @Override /** Delete an element from the binary tree.
     * Return true if the element is deleted successfully
     * Return false if the element is not in the tree */
    public boolean delete(E e) {
        // Locate the node to be deleted and also locate its parent node
        TreeNode<E> parent = null;
        TreeNode<E> current = root;
        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            }
            else
                break; // Element is in the tree pointed at by current
        }

        if (current == null)
            return false; // Element is not in the tree

        // Case 1: current has no left child
        if (current.left == null) {
            // Connect the parent with the right child of the current node
            if (parent == null) {
                root = current.right;
            }
            else {
                if (e.compareTo(parent.element) < 0)
                    parent.left = current.right;
                else
                    parent.right = current.right;
            }
        }
        else {
            // Case 2: The current node has a left child
            // Locate the rightmost node in the left subtree of
            // the current node and also its parent
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; // Keep going to the right
            }

            // Replace the element in current by the element in rightMost
            current.element = rightMost.element;

            // Eliminate rightmost node
            if (parentOfRightMost.right == rightMost)
                parentOfRightMost.right = rightMost.left;
            else
                // Special case: parentOfRightMost.left == rightMost, happens if parentOfRightMost is current
                parentOfRightMost.left = rightMost.left;
        }

        size--;
        return true; // Element deleted successfully
    }

    @Override /** Obtain an iterator. Use inorder. */
    public java.util.Iterator<E> iterator() {
        return new InorderIterator();
    }

    // Inner class InorderIterator
    private class InorderIterator implements java.util.Iterator<E> {
        // Store the elements in a list
        private java.util.ArrayList<E> list =
                new java.util.ArrayList<>();
        private int current = 0; // Index of next element in the iteration
        private boolean canRemove = false;

        public InorderIterator() {
            inorder(); // Traverse binary tree and store elements in list
        }

        /** Inorder traversal from the root*/
        private void inorder() {
            inorder(root);
        }

        /** Inorder traversal from a subtree */
        private void inorder(TreeNode<E> root) {
            if (root == null) return;
            inorder(root.left);
            this.list.add(root.element);
            inorder(root.right);
        }

        @Override /** More elements for traversing? */
        public boolean hasNext() {
            return current < list.size();
        }

        @Override /** Get the current element and move to the next */
        public E next() {
            if (hasNext())
                canRemove = true;
            else
                throw new java.util.NoSuchElementException();
            return list.get(current++);
        }

        @Override /** Remove the element most recently returned */
        public void remove() {
            if (!canRemove)
                throw new IllegalStateException();
            BST.this.delete(list.get(--current));
            InorderIterator.this.list.remove(current);
            canRemove = false;
        }
    }

    @Override /** Remove all elements from the tree */
    public void clear() {
        root = null;
        size = 0;
    }
}
