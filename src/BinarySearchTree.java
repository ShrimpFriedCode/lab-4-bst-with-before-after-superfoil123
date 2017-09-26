import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.function.BiPredicate;

/**
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
         * <p>
         * Constructs a new node with the given values for fields.
         */
        public Node(K data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
            height = 0;
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

        protected int setHeight(Node n){
            if (n != null){ //if not null, add 1

                int l = setHeight(n.left);
                int r = setHeight(n.right);

                if(l > r){ //if  get max of either the left or right, to find the true height
                    return l +1;
                }
                else{
                    return r + 1;
                }
            }
            else { //else it DNE, so 0
                return 0;
            }
        }

        protected boolean updateHeight() {
            boolean ret = false;
            int hCheck = setHeight(this) - 1;

            if(this.height != hCheck){
                ret = true;
                height =  hCheck;
            }
            return ret;
        }

        public Node getBefore() {
            Node n = this;
            Node ret = this;

            if(n.left != null){
                ret = n.left;
                ret = ret.largest();
            }
            else{
                ret = n.parent;
                while(ret != null && n == ret.left){
                    n = ret;
                    ret = ret.parent;
                }
            }
            return ret;
        }

        public Node getAfter() {
            Node n = this;
            Node ret = n;

            if(n.right != null){
                ret = n.right;
                ret = ret.smallest();
            }
            else {
                ret = n.parent;
                while (ret != null && n == ret.right) {
                    n = ret;
                    ret = ret.parent;
                }
            }
            return ret;
        }

        private Node smallerAncestor(Node q) {
            Node ret = q.parent;
            Node n = q;

            while(ret != null &&  n == ret.left){
                n = ret;
                ret = ret.parent;
            }

            return ret;
        }

        private Node greaterAncestor(Node q) {
            Node ret = q.parent;
            Node n = q;

            while (ret != null && n == ret.right) {
                n = ret;
                ret = ret.parent;
            }

            return ret;
        }

        protected Node smallest() {
            Node ret = this;
            while(ret.left != null){
                ret = ret.left;
            }
            return ret;
        }

        private Node largest() {
            Node ret = this;
            while(ret.right != null){
                ret = ret.right;
            }
            return ret;
        }

        public String toString() {
            return toStringPreorder(this);
        }

    }

    protected Node root;
    protected int numNodes = 0;
    protected BiPredicate<K, K> lessThan;

    /**
     * Constructs an empty BST, where the data is to be organized according to
     * the lessThan relation.
     */
    public BinarySearchTree(BiPredicate<K, K> lessThan) {
        this.lessThan = lessThan;
    }

    public Node searchH(Node n, K key){

        if(n == null){
            return null;
        }
        else if(lessThan.test(key, n.get())){
            return searchH(n.left, key);
        }
        else if(lessThan.test(n.get(), key)){
            return searchH(n.right, key);
        }
        else{
            return n;
        }

    }

    public Node search(K key) {
        return searchH(root, key);
    }

    /**
     * Returns the height of this tree. Runs in O(1) time!
     */
    public int height() {

        if(root == null){
            return -1;
        }
        else {
            return get_height(root);
        }
    }

    protected int get_height(Node n) {
        if(n != null){
            return n.height;
        }
        return 0;
    }

    /**
     * <p>
     * Clears all the keys from this tree. Runs in O(1) time!
     */
    public void clear()
    {
        numNodes = 0;
        root = null;
    }

    /**
     * Returns the number of keys in this tree.
     */
    public int size() {
        return numNodes;
    }

    /**
     * <p>
     * Inserts the given key into this BST, as a leaf, where the path
     * to the leaf is determined by the predicate provided to the tree
     * at construction time.
     *
     * The parent pointer of the new node and
     * the heights in all node along the path to the root are adjusted
     * accordingly.
     *
     *
     * <p>
     * Note: we assume that all keys are unique. Thus, if the given
     * key is already present in the tree, nothing happens.
     * <p>
     * Returns the location where the insert occurred (i.e., the leaf
     * node containing the key), or null if the key is already present.
     *
     */

    private Node insertH(Node n, Node p,  K key){

        if(n == null){//if null insert
           n = new Node(key);
           n.parent = p;
           numNodes += 1;
           n.updateHeight();
           return n;
        }

        if(lessThan.test(key, n.get())){//if key is less than, go left
            n.left =  insertH(n.left, n, key);
        }
        else if(lessThan.test(n.get(), key)){//else must be right
            n.right =  insertH(n.right, n, key);
        }
        n.updateHeight();
        return n;
    }

    public Node insert(K key) {
        root = insertH(root, root, key);
        return search(key);
    }

        /**
         * Returns true iff the given key is in this BST.
         */
    public boolean contains(K key) {
        Node p = search(key);
        return p != null;
    }

    /**
     * <p>
     * Removes the key from this BST. If the key is not in the tree,
     * nothing happens.
     */
    public Node removeH(Node n, K key){

        if(n == null){
            return n;
        }
        else if(lessThan.test(key, n.get())){
            n.left = removeH(n.left, key);
            return n;
        }
        else if(lessThan.test(n.get(), key)){
            n.right = removeH(n.right, key);
            return n;
        }
        else{
            if(n.left == null){
                if(n.right != null) {
                    n.right.parent = n.parent;
                }
                return n.right;
            }
            else if(n.right == null){
                if(n.left != null) {
                    n.left.parent = n.parent;
                }
                return n.left;
            }
            else {
                n.data = n.right.smallest().get();
                n.right = removeH(n.right, n.data);
                return n;
            }
        }
    }

    public void remove(K key) {
        if(search(key) != null) {
            root = removeH(root, key);
            numNodes -= 1;
        }
    }

    /**
     * TODO
     * <p>
     * Returns a sorted list of all the keys in this tree.
     */

    public List<K> listH(Node n){
        List<K> keys = new ArrayList<K>();

        if(n != null){
            keys.addAll(listH(n.left));
            keys.add(n.get());
            keys.addAll(listH(n.right));
        }
        return keys;
    }

    public List<K> keys() {
        return listH(root);
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
