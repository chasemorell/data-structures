package prog08;
import java.util.*;

public class Tree <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  private class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {
    K key;
    V value;
    Node left, right;
    
    Node (K key, V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }
  }
  
  private Node root;
  private int size;

  public int size () { return size; }

  /**
   * Find the node with the given key.
   * @param key The key to be found.
   * @return The node with that key.
   */
  private Node<K, V> find (K key, Node<K,V> root) {
    // EXERCISE:
	if(root == null)
		return null;

	int cmp = key.compareTo(root.key);
	if(cmp == 0)
		return root;
	if(cmp < 0)
		return find(key,root.left);
	if(cmp > 0)
		return find(key,root.right);

    return null;
  }    

  public boolean containsKey (Object key) {
    return find((K) key, root) != null;
  }
  
  public V get (Object key) {
    Node<K, V> node = find((K) key, root);
    if (node != null)
      return node.value;
    return null;
  }
  
  public boolean isEmpty () { return size == 0; }
  
  /**
   * Add key,value pair to tree rooted at root.
   * Return root of modified tree.
   */
  private Node<K,V> add (K key, V value, Node<K,V> root) {
    // EXERCISE:
	  Node<K,V> node = find(key,root);
	  if(node != null){
		  node.value = value;
		  return root;
	  }
	  if(root == null){
		  return new Node<K,V>(key,value);
	  }
	 
	  int cmp = key.compareTo(root.key);
	  if(cmp < 0)
		  root.left = add(key,value,root.left);
	  if(cmp > 0)
		  root.right = add(key,value,root.right);

    return root;
  }
  
  int depth (Node root) {
    if (root == null)
      return -1;
    return 1 + Math.max(depth(root.left), depth(root.right));
  }

  public V put (K key, V value) {
    // EXERCISE:
	  Node<K,V> node = find(key,root);
	  if(node == null){
		  root = add(key,value,root);
		  size++;
		  return null;
	  }
	  V oldValue = node.value;
	  node.setValue(value);
    return oldValue;
  }      
  
  public V remove (Object keyAsObject) {
    // EXERCISE:  Delete these lines and implement remove.
	  K key = (K)keyAsObject;
	  //System.out.println("removing " + key);
	  Node<K,V> node = find(key,root);
	  
	  if(node == null) {
		  //System.out.println("none found");
		  return null;
	  }
	  
	  root = remove(node.key,root);
	  size--;
	  return node.value;
 }

  private Node<K,V> remove (K key, Node<K,V> node) {
    // EXERCISE:
	  int cmp = key.compareTo( node.key );
	  if( node == null )
          return null;   
	  
      if( cmp < 0 )
          node.left = remove( key, node.left );
      else if( cmp > 0 )
          node.right = remove( key, node.right );
      else if( node.left != null && node.right != null ) 
      {
    	  	  Node min = moveMinToRoot( node.right );
          node.key = (K) min.key;
          node.value = (V) min.value;
          node.right = remove( node.key, node.right );
      }
      else {
    	  	if(node.left == null)
    		   node = node.right;
    	  	else
    		   node = node.left;
      }
      return node;
  }

  /**
   * Remove root of tree rooted at root.
   * Return root of BST of remaining nodes.
   */
  private Node removeRoot (Node root) {
    // IMPLEMENT    
	  /*left subtree is empty?  return right subtree
		right subtree is empty?  return left subtree
	    Use moveMinToRoot to move the minimum in the right subtree
	    to the root of the right subtree.
	    Put the left subtree (of the root) to the left of the right subtree.
	    Return the right subtree. */
	 System.out.println("Root in RemoveRoot = " + root.key);
	  if(root.left == null)
		  return root.right;
	  if(root.right == null)
		  return root.left;
	 
	  Node newRoot = moveMinToRoot(root.right);
	  root.value = newRoot.value;
	  root.key = newRoot.key;
	  
	  return root;
	  
  }
  

  /**
   * Move the minimum key (leftmost) node to the root.
   * Return the new root.
   */
  private Node getSmallestInSubtree(Node root) {
	  if(root.left == null) {
		  //System.out.println("min = " + root.key);
		  return root;
	  }
	  return getSmallestInSubtree(root.left);
  }
  
  
  private Node moveMinToRoot (Node root) {

	  Node min = getSmallestInSubtree(root);
	  return min;
	 // root.value = min.value;
	 // root.key = min.key;
	//  removeSmallest(root);
	 // return root;
	  
    // IMPLEMENT
	//min.right = root.right;
	
	  
  }

  public Set<Map.Entry<K, V>> entrySet () { return null; }
  
  public String toString () {
    return toString(root, 0);
  }
  
  private String toString (Node root, int indent) {
    if (root == null)
      return "";
    String ret = toString(root.right, indent + 2);
    for (int i = 0; i < indent; i++)
      ret = ret + "  ";
    ret = ret + root.key + " " + root.value + "\n";
    ret = ret + toString(root.left, indent + 2);
    return ret;
  }

  public static void main (String[] args) {
    Tree<Character, Integer> tree = new Tree<Character, Integer>();
    String s = "balanced";
    
    for (int i = 0; i < s.length(); i++) {
      tree.put(s.charAt(i), i);
      System.out.print(tree);
      System.out.println("-------------");
    }
   

    for (int i = 0; i < s.length(); i++) {
      tree.remove(s.charAt(i));
      System.out.print(tree);
      System.out.println("-------------");
    }
  }
}
