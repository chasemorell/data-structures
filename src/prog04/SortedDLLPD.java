package prog04;

/** This is an implementation of the prog02.PhoneDirectory interface that uses
 *   a doubly linked list to store the data.
 *   @author vjm
 */
public class SortedDLLPD extends DLLBasedPD {
  /** Add an entry or change an existing entry.
      @param name The name of the person being added or changed.
      @param number The new number to be assigned.
      @return The old number or, if a new entry, null.
  */
	
	protected FindOutput find (String name) {
		// EXERCISE
		// For each entry in the directory.
		// If this is the entry you want
		// Return the appropriate FindOutput object.
		int cmp;
		for(DLLEntry entry = head; entry != null; entry = entry.getNext()){
			 cmp = name.compareTo(entry.getName());
			// System.out.println(entry.getName());
			if(cmp  == 0)
				return new FindOutput(true, entry); 
			if(cmp < 0) 
				return new FindOutput(false, entry);
			
		}
		
		return new FindOutput(false, null); // Name not found.
	}

	
  public String addOrChangeEntry (String name, String number) {
    String oldNumber = null;
    FindOutput fo = find(name);
    if (fo.found) {
      oldNumber = fo.entry.getNumber();
      fo.entry.setNumber(number);
    } else {
        // Create a new entry to insert.
        DLLEntry entry = new DLLEntry(name, number);
        // Declare and set the variable next.
  	  	DLLEntry next = fo.entry;
        // Declare the variable previous.
  	  	DLLEntry previous;
  	  	
  	    // Set it.
        // Oops that crashes if next==null.  What should it be then?
  	  	if(next == null)
  	  		previous = tail;
  	  	else
  	  		previous = next.getPrevious();
  	 
  	    // Set the next and previous of the new entry.
        entry.setNext(next);
  	    entry.setPrevious(previous);
  	    
  	    // Set the next of previous to the new entry.
        // Oops that crashes if previous==null.  What should you do then?
  	    if(previous == null) {
  	    		head = entry;
  	    }else {
  	    		previous.setNext(entry);
  	    }
  	    
  	    // Set the previous of next to the new entry.
        // Oops that crashes if next==null.  What should you do then?
  	    if(next == null)
  	    		tail = entry;
  	    else 
  	    		next.setPrevious(entry);

    }
    return oldNumber;
  }
}
