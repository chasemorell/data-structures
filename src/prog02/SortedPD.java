package prog02;

import java.io.*;

/**
 *
 * @author vjm
 */
public class SortedPD extends ArrayBasedPD {
	/** Add an entry or change an existing entry.
    @param name The name of the person being added or changed
    @param number The new number to be assigned
    @return The old number or, if a new entry, null
*/
	///**
	public String addOrChangeEntry (String name, String number) {
 	    String oldNumber = null;

	    FindOutput fo = find(name);
	    //System.out.println("***** Name " + name + ", index to enter it : "+ fo.index);

	    if (fo.found) {
	      oldNumber = theDirectory[fo.index].getNumber();
	      theDirectory[fo.index].setNumber(number);
	    } else {
	    		int insertionIndex = fo.index;
	    		//insertionIndex = 0;
	    		System.out.println("Insertion Index = " + insertionIndex);
			//int insertionIndex = 0;
		    //System.out.println("Size " + size);
		    //System.out.println("theDirectory Length " + theDirectory.length);
		    //System.out.println("Insertion Index " + fo.index);
		    if (size >= theDirectory.length)
		        reallocate();
		    
			for(int i = theDirectory.length - 2;i >= insertionIndex;i--) {
				theDirectory[i+1] = theDirectory[i];
			}
			theDirectory[insertionIndex] = new DirectoryEntry(name, number);
			size++;                  
			 return null;
	    }
	    modified = true;
	    return oldNumber;
	  }

	//*/
	  /** Find an entry in the directory.
    @param name The name to be found
    @return A FindOutput object containing the result of the search.
*/
	

	
	protected FindOutput find (String name) {
		int first = 0;
		
		int last = size-1;
		int middle = ((first+last)/2);
		//System.out.println("Name: "+ name + " Size: " + size + " First: " + first + " Last: " + last + " Middle: " + middle);
		while(last >= first ) {
			middle = (int)Math.ceil(((first+last)/2));
			int cmp = name.compareTo(theDirectory[middle].getName());
			//System.out.println(name + " differs from " + theDirectory[middle].getName() + " by " + cmp);
			if(cmp >0) {
				 first = middle+1;
				middle =  (int)Math.ceil(((first+last)/2));
				//System.out.println("New Middle = " + middle);
				
			}else if(cmp<0) {
				last = middle-1;
				middle =  (int)Math.ceil(((first+last)/2));
				//System.out.println("New Middle = " + middle);
			}else if(cmp==0) {
				//System.out.println("Found!");
		        return new FindOutput(true, middle);
			}
		}
		middle =  (int)Math.ceil((((double)first+(double)last)/2));
		//System.out.println("FINAL: Size: " + size + " First: " + first + " Last: " + last + " Middle: " + middle+ "\n" );
	
		return new FindOutput(false, middle);
	
	}
	
	
	/** Remove an entry from the directory.
    @param name The name of the person to be removed
    @return The current number. If not in directory, null is
    returned
*/
	public String removeEntry (String name) {
		
		System.out.println("Array: ");

 	    for(int i = 0;i <= size - 1;i++) {
 	    		System.out.print(theDirectory[i].getName() + " , ");
 	    }
 		System.out.println("\n Array End ");
 		
 		
		FindOutput fo = find(name);
		if (!fo.found)
			return null;
		
		System.out.println("Current length = " + theDirectory.length + " Number to move : "+ (theDirectory.length - fo.index));
		DirectoryEntry entry = theDirectory[fo.index];

		for(int i = fo.index;i < (theDirectory.length - fo.index)-1;i++) {
			theDirectory[i] = theDirectory[i+1];
		}
		
		size--;
		modified = true;
		return entry.getNumber();
	}

}
