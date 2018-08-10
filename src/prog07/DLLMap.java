package prog07;
import java.util.*;

public class DLLMap <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  protected static class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {

    K key;
    V value;
    Node<K,V> next;
    Node<K,V> previous;
    
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
  
  protected Node<K, V> head, tail;
  protected int size;
  
  public boolean isEmpty () { return size == 0; }
  
  /** Find a node with a key starting from start.
      @param key The key we are seeking.
      @param start The node we should start from, null if we should
      start from head.
      @return The node with the key.  If there is no node with the
      key, the PREVIOUS node, or null if there is no previous node.
   */
  protected Node<K, V> find (K key, Node start) {
    // Start searching at start if it is not null or head if it is.
	  Node lastNode = null;
    for (Node<K, V> node = start == null ? head : (Node<K, V>) start;
         node != null; node = node.next) {
    	
    	int cmp = node.key.compareTo(key);
    	
    	if(cmp > 0){
    		if(node.previous != null){
    			return node.previous;
    		}else{
    			return null;
    		}
    		
    	}
    	if(node.key.equals(key)){
    		return(node);
    	}
    	lastNode = node;
      // EXERCISE 5

      // Return node if it has the right key.

      // Return the PREVIOUS node if the key is not there.

    }
    if(lastNode != null){
       int cmp = lastNode.key.compareTo(key);
       if(cmp < 0){
   		return lastNode;
       }
    }
   		
  
	
    //if(key.equals("Aaron")){
    	return null;
   // }else{
    //	return lastNode.previous;

    //}
  }
  
  
  public boolean containsKey (Object keyAsObject) {
    return containsKey((K) keyAsObject, null);
  }
  
  protected boolean containsKey (K key, Node<K,V> start) {
    Node<K,V> node = find(key, start);
    return node != null && node.key.equals(key);
  }
  
  public V get (Object keyAsObject) {
    return get((K) keyAsObject, null);
  }
  
  protected V get (K key, Node<K,V> start) {
    Node<K,V> node = find(key, start);
    if(node != null){
    	if(node.key.equals(key)){
    	    return node.value;
    	}
    }
    
    // EXERCISE 6

    return null;
  }
  
  public V remove (Object keyAsObject) {
    return remove((K) keyAsObject, null);
  }

  protected V remove (K key, Node<K,V> start) {
    Node<K,V> node = find(key, start);
    if(node == null){
    	return null;
    }
    if(node.key.equals(key)){
    	if(node.next != null){
    		node.next.previous = node.previous;
    		
    	}else{
    		tail = node.previous;
    	}
    	if(node.previous != null){
            node.previous.next = node.next;

    	}else{
    		head = node.next;
    	}
        return node.value;
    }
    
    // EXERCISE 7

    return null; // WRONG
  }      

  public V put (K key, V value) {
    return put(key, value, null);
  }      
  
  protected V put (K key, V value, Node<K,V> start) {
	  Node<K,V> node = find(key, start);
	  
	  ///NEW
	  	if(node!= null && node.key.equals(key)) {
	  		V oldValue = node.value;
	  		node.value = value;
	  		return oldValue;
	  	}
	  	
	  	Node<K,V> previous = node;
	  	Node<K,V> next = node == null ? head : node.next;
	  	
	  	node = new Node<K,V>(key,value);
	  	node.previous = previous;
	  	node.next = next;
	  	
	  	if(previous == null)
	  		head = node;
	  	else
	  		previous.next = node;
	  	
	  	if(next == null)
	  		tail = node;
	  	else
	  		next.previous = node;
	  	
	  	size++;
	  	return null;
	  //END NEW
	  /**
	  if(node == null){
		  Node<K,V> newNode = new Node<K,V>(key,value);
		  newNode.next = head;
		  if(head == null){
			  head = newNode;
		  }else{
			  head.previous = newNode;
		  }
		  head = newNode;
		  size++;
		  return null;
	  }
	  if(!node.key.equals(key)){
		  Node<K,V> newNode = new Node<K,V>(key,value);
		  newNode.previous = node;
		  newNode.next = node.next;
		  if(node.next != null){
			  node.next.previous = newNode;
		  }
		  node.next = newNode;
		  return null;
	  }else{
		  V oldValue = node.value;
		  node.value = value;
		  return oldValue;
	  }

	**/
	  // EXERCISE 8
  }      

  public String toString () {
    String s = "";
    for (Node<K,V> node = head; node != null; node = node.next)
      s += node.key + " ";
    s += "\n";
    if (head != null && !(head.value instanceof Node)) {
      for (Node<K,V> node = head; node != null; node = node.next)
        s += node.value + " ";
      s += "\n";
    }
    return s;
  }

  protected class Iter implements Iterator<Map.Entry<K, V>> {
    Node<K, V> next;
    
    Iter () {
      next = head;
    }
    
    public boolean hasNext () { return next != null; }
    
    public Map.Entry<K, V> next () {
      Map.Entry<K, V> ret = next;
      next = next.next;
      return ret;
    }
    
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
  
  protected class Setter extends AbstractSet<Map.Entry<K, V>> {
    public Iterator<Map.Entry<K, V>> iterator () {
      return new Iter();
    }
    
    public int size () { return size; }
  }
  
  public Set<Map.Entry<K, V>> entrySet () { return new Setter(); }
  
  protected void makeTestList () {
    for (int i = 2; i < 26; i += 2) {
      Node node = new Node("" + (char)('A' + i), i);
      if (size == 0)
        head = tail = node;
      else {
        tail.next = node;
        node.previous = tail;
        tail = node;
      }
      size++;
    }
  }

  public static void main (String[] args) {
    DLLMap<String, Integer> map = new DLLMap<String, Integer>();
    map.makeTestList();
    System.out.println(map);
    System.out.println("containsKey(A) = " + map.containsKey("A"));
    System.out.println("containsKey(C) = " + map.containsKey("C"));
    System.out.println("containsKey(L) = " + map.containsKey("L"));
    System.out.println("containsKey(M) = " + map.containsKey("M"));
    System.out.println("containsKey(Y) = " + map.containsKey("Y"));
    System.out.println("containsKey(Z) = " + map.containsKey("Z"));

    System.out.println("get(A) = " + map.get("A"));
    System.out.println("get(C) = " + map.get("C"));
    System.out.println("get(L) = " + map.get("L"));
    System.out.println("get(M) = " + map.get("M"));
    System.out.println("get(Y) = " + map.get("Y"));
    System.out.println("get(Z) = " + map.get("Z"));

    System.out.println("remove(A) = " + map.remove("A"));
    System.out.println("remove(C) = " + map.remove("C"));
    System.out.println("remove(L) = " + map.remove("L"));
    System.out.println("remove(M) = " + map.remove("M"));
    System.out.println("remove(Y) = " + map.remove("Y"));
    System.out.println("remove(Z) = " + map.remove("Z"));

    System.out.println(map);

    System.out.println("put(A,7) = " + map.put("A", 7));
    System.out.println("put(A,9) = " + map.put("A", 9));
    System.out.println("put(M,17) = " + map.put("M",17));
    System.out.println("put(M,19) = " + map.put("M",19));
    System.out.println("put(Z,3) = " + map.put("Z",3));
    System.out.println("put(Z,20) = " + map.put("Z",20));

    System.out.println(map);
  }
}
