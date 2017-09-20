import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * TODO: This is your first major task.
 * <p>
 * This class implements a generic unbalanced binary search tree (BST).
 */

public class BinarySearchTree<K> implements Tree<K> {

    /**
     * A Node is a Location (defined in Tree.java), which means that it can be the return value
     * of a search on the tree.
     */

    class Node implements Location<K> {

        protected K data;
        protected Node left, right;
        protected Node parent;
        protected int height;

        /**
         * Constructs a leaf node with the given key.
         */
        public Node(K key) {
            this(key, null, null);
        }

        /**
         * TODO
         * <p>
         * Constructs a new node with the given values for fields.
         */
        public Node(K data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        /*
         * Provide the get() method required by the Location interface.
         */
        @Override
        public K get() {
            return data;
        }

        /**
         * Return true iff this node is a leaf in the tree.
         */
        protected boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * TODO
         * <p>
         * Performs a local update on the height of this node. Assumes that the
         * heights in the child nodes are correct. Returns true iff the height
         * actually changed. This function *must* run in O(1) time.
         */
        protected boolean updateHeight() {
            throw new UnsupportedOperationException();
        }

        /**
         * TODO
         * <p>
         * Returns the location of the node containing the inorder predecessor
         * of this node.
         */
        public Node getBefore() {
            throw new UnsupportedOperationException();
        }

        /**
         * TODO
         * <p>
         * Returns the location of the node containing the inorder successor
         * of this node.
         */
        public Node getAfter() {
            throw new UnsupportedOperationException();
        }

        /**
         * TODO
         * <p>
         * This method should return the closest ancestor of node q
         * whose key is less than q's key. It is not necessary to
         * to perform key comparisons to implement this method.
         */
        private Node smallerAncestor(Node q) {
            throw new UnsupportedOperationException();
        }

        /**
         * TODO
         * <p>
         * This method should return the closest ancestor of node q
         * whose key is greater than q's key. It is not necessary to
         * to perform key comparisons to implement this method.
         */
        private Node greaterAncestor(Node q) {
            throw new UnsupportedOperationException();
        }

        /*
         * TODO
         * This method should return the node in the subtree rooted at 'this'
         * that has the smallest key.
         */
        protected Node smallest() {
            throw new UnsupportedOperationException();
        }

        /*
         * TODO
         * This method should return the node in the subtree rooted at 'this'
         * that has the largest key.
         */
        private Node largest() {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            return toStringPreorder(this);
        }

    }

    protected Node root;
    protected int numNodes;
    protected BiPredicate<K, K> lessThan;

    /**
     * Constructs an empty BST, where the data is to be organized according to
     * the lessThan relation.
     */
    public BinarySearchTree(BiPredicate<K, K> lessThan) {
        this.lessThan = lessThan;
    }

    /**
     * TODO
     * <p>
     * Looks up the key in this tree and, if found, returns the
     * location containing the key.
     */
    public Node search(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the height of this tree. Runs in O(1) time!
     */
    public int height() {
        return get_height(root);
    }

    /**
     * TODO
     * The get_height method returns the height of the Node n, which may be null.
     */
    protected int get_height(Node n) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     * <p>
     * Clears all the keys from this tree. Runs in O(1) time!
     */
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the number of keys in this tree.
     */
    public int size() {
        return numNodes;
    }

    /**
     * TODO
     * <p>
     * Inserts the given key into this BST, as a leaf, where the path
     * to the leaf is determined by the predicate provided to the tree
     * at construction time. The parent pointer of the new node and
     * the heights in all node along the path to the root are adjusted
     * accordingly.
     * <p>
     * Note: we assume that all keys are unique. Thus, if the given
     * key is already present in the tree, nothing happens.
     * <p>
     * Returns the location where the insert occurred (i.e., the leaf
     * node containing the key), or null if the key is already present.
     */
    public Node insert(K key) {
        throw new UnsupportedOperationException();
    }

        /**
         * Returns true iff the given key is in this BST.
         */
    public boolean contains(K key) {
        Node p = search(key);
        return p != null;
    }

    /**
     * TODO
     * <p>
     * Removes the key from this BST. If the key is not in the tree,
     * nothing happens.
     */
    public void remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     * <p>
     * Returns a sorted list of all the keys in this tree.
     */
    public List<K> keys() {
        throw new UnsupportedOperationException();
    }

    private String toStringInorder(Node p) {
        if (p == null)
            return ".";
        String left = toStringInorder(p.left);
        if (left.length() != 0) left = left + " ";
        String right = toStringInorder(p.right);
        if (right.length() != 0) right = " " + right;
        String data = p.data.toString();
        return "(" + left + data + right + ")";
    }

    private String toStringPreorder(Node p) {
        if (p == null)
            return ".";
        String left = toStringPreorder(p.left);
        if (left.length() != 0) left = " " + left;
        String right = toStringPreorder(p.right);
        if (right.length() != 0) right = " " + right;
        String data = p.data.toString();
        return "(" + data + "[" + p.height + "]" + left + right + ")";
    }

    /**
     * Returns a textual representation of this BST.
     */
    public String toString() {
        return toStringPreorder(root);
    }
}
