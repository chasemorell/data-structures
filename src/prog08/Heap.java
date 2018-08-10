package prog08;
import java.util.*;

public class Heap <E> extends AbstractQueue<E> {
  private List<E> theData = new ArrayList<E>();

  public Heap () {}
  
  /** An optional reference to a Comparator object. */
  Comparator < E > comparator = null;

  /** Creates a heap-based priority queue with that orders its
       elements according to the specified comparator.
       @param comp The comparator used to order this priority queue
   */
  public Heap (Comparator < E > comp) {
    comparator = comp;
  }

  public int size () { return theData.size(); }

  /** Compare the items with index i and index j in theData using
      either a Comparator object's compare method or their natural
      ordering using method compareTo.
      @param i index of first item in theData
      @param j index of second item in theData
      @return Negative int if first less than second,
              0 if first equals second,
              positive int if first > second
      @throws ClassCastException if items are not Comparable
   */
  private int compare (int i, int j) {
    if (comparator == null)
      return ((Comparable<E>) theData.get(i)).compareTo(theData.get(j));
    else
      return comparator.compare(theData.get(i), theData.get(j));
  }

  /** Swap the items with index i and index j in theData.
      @param i index of first item in theData
      @param j index of second item in theData
   */
  private void swap (int i, int j) {
    E temp = theData.get(i);
    theData.set(i, theData.get(j));
    theData.set(j, temp);
  }
  
  private int getLeft (int i) {
    return 2 * i + 1;
  }
  
  private int getRight (int i) {
    return 2 * i + 2;
  }
  
  private int getParent (int i) {
    return (i - 1) / 2;
  }

  private boolean isRoot (int i) {
    return i == 0;
  }

  private boolean isNull (int i) {
    return i >= size();
  }

  /** Insert an item into the priority queue.
      pre:  theData is in heap order.
      post: The item is in the priority queue and
            theData is in heap order.
      @param item The item to be inserted
      @throws NullPointerException if the item to be inserted is null.
   */
  public boolean offer (E item) {
    if (item == null)
      throw new NullPointerException();
    
    theData.add(item);
    int index = theData.size() - 1;
    while (!isRoot(index) && compare(index, getParent(index)) < 0) {
      swap(index, getParent(index));
      index = getParent(index);
    }

    return true;
}

    /**
     * Returns the item at the front of the queue without removing it.
     * @return The item at the front of the queue if successful;
     * return null if the queue is empty
     */
    @Override
    public E peek () {
      if (size() == 0)
        return null;
      return theData.get(0);
    }

  /** Remove an item from the priority queue
      pre: The ArrayList theData is in heap order.
      post: Removed smallest item, theData is in heap order.
      @return The item with the smallest priority value or null if empty.
   */
  public E poll() {
    // IMPLEMENT

    // Return null if queue is empty.
	 if(theData.isEmpty())
		 return null;
	 

    // If only one item then remove it and return it.
	 if(theData.size() == 1){
		 E result = theData.get(0);
		 theData.remove(0);
		 return result;
	 }

    // Save the top of the heap.
    E result = theData.get(0);

    /* Remove the last item from the ArrayList and place it into
       the first position. */
    theData.remove(0);
    theData.add(0, theData.remove(theData.size()-1));

		int index = 0;
		while ((getLeft(index) < size() && compare(index, getLeft(index)) > 0)
				|| (getRight(index) < size() && compare(index, getRight(index)) > 0)) {
			int left = getLeft(index);
			int right = getRight(index);
			int parent = index;
			if (left < size() && right < size()) {
				if (compare(left, right) > 0) {
					swap(right, parent);
					// index = right;
				} else
					swap(left, parent);
				// index = left;
			}
			if (left < size() && compare(parent, left) > 0) {
				swap(parent, left);
				// index = left;
			}
			if (right < size() && compare(parent, right) > 0) {
				swap(parent, right);
				// index = right;
			}
			index++;

		}
    // While the item at index is greater than one of its children...
    // (Use, getLeft, getRight, isNull, and compare.)


      // If the right child is there and is smaller than the left child...

        // Swap with right child and set index.

        // Else swap with left child and set index.


    return result;
  }

  public boolean remove (Object o) {
	  int index = theData.indexOf(o);
	    if (index == -1 || isNull(index))
	      return false;

	    //if there is only one, just remove it
		if (index == theData.size() - 1) {
			theData.remove(index);
			return true;
		}
		
		if(size() == 1) {
			theData.remove(index);
			return true;
		}

	    // Remove the item at size-1 and place it at index.
	    theData.set(index, theData.remove(size()-1) );
	    
	    
	    // If the item at index is better than its parent, swap it upward
	    // as in offer.  Otherwise, swap it downward as in poll().
	    while(compare(index,getParent(index)) < 0 && index > 0) {
	    		swap(getParent(index), index);
	    		index = getParent(index);
	    }
	    
	     
		while (getRight(index) < size() && compare(index, getLeft(index)) > 0
				|| getRight(index) < size() && compare(index, getRight(index)) > 0) {
			if (getRight(index) < size() && getLeft(index) < size()) {
				if (compare(getLeft(index), getRight(index)) > 0) {
					swap(index, getRight(index));
				} else {
					swap(index, getLeft(index));
				}
			} else {
				if (getLeft(index) < size() && compare(index, getLeft(index)) > 0)
					swap(index, getLeft(index));
				if (getRight(index) < size() && compare(index, getRight(index)) > 0)
					swap(index, getRight(index));
			}
			index++;
		}
		return true;

  }

  public Iterator<E> iterator () { return theData.iterator(); }

  public String toString () {
    return toString(0, 0);
  }
  
  private String toString (int root, int indent) {
    if (root >= size())
      return "";
    String ret = toString(2 * root + 2, indent + 2);
    for (int i = 0; i < indent; i++)
      ret = ret + "  ";
    ret = ret + theData.get(root) + "\n";
    ret = ret + toString(2 * root + 1, indent + 2);
    return ret;
  }

  public static void main (String[] args) {
    int[] data = { 3, 1, 4, 1, 5, 9, 2, 6 };
    Heap<Integer> queue = new Heap<Integer>();
    
    for (int i = 0; i < data.length; i++) {
      queue.offer(data[i]);
      System.out.println(queue);
      System.out.println("----------------");
      System.out.println();
    }
    System.out.println("polling");
    for (int i = 0; i < data.length; i++) {
      queue.poll();
      System.out.println(queue);
      System.out.println("----------------");
      System.out.println();
    }
  }
}

