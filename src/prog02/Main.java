package prog02;

/**
 *
 * @author vjm
 */
public class Main {

	/** Processes user's commands on a phone directory.
      @param fn The file containing the phone directory.
      @param ui The UserInterface object to use
             to talk to the user.
      @param pd The PhoneDirectory object to use
             to process the phone directory.
	 */
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = {
				"Add/Change Entry",
				"Look Up Entry",
				"Remove Entry",
				"Save Directory",
		"Exit"};

		String name, number, oldNumber;

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case -1:
				ui.sendMessage("You clicked the red x, restarting.");
				break;
			case 0:
				name = ui.getInfo("Enter a name");
				if(name == null){
					ui.sendMessage("No name entered");
					break;
				}
				if(name.length() == 0){
					ui.sendMessage("No blank names please");
					break;
				}
				number = ui.getInfo("Enter the number");
				if(number == null){
					ui.sendMessage("No number entered");
					break;
				}
				oldNumber = pd.addOrChangeEntry(name, number);
				if(oldNumber == null){
					if(number.length() == 0){
						ui.sendMessage("Number for " + name + " is now blank");

					}else{
						ui.sendMessage("Number for " + name + " is " + number);

					}
				}else{
					ui.sendMessage("Number for " + name + " has been changed from " + oldNumber + " to "+ number);
				}
				
				// Ask for the name.
				// !!! Check for null (cancel) or "" (blank)
				// Ask for the number.
				// !!! Check for cancel.  Blank is o.k.
				// Call addOrChangeEntry
				// Report the result
				break;
			case 1:
				name = ui.getInfo("Enter the name");
				if(name == null){
					break;
				}
				if(name.length() == 0){
					ui.sendMessage("No blank names plase");
					break;
				}
				number = pd.lookupEntry(name);
				if(number == null){
					ui.sendMessage(name + " is not listed");
				}else{
					ui.sendMessage(name + " has number " + number);
				}
				// implement
				break;
			case 2:
				name = ui.getInfo("Enter name to remove");
				if(name == null){
					ui.sendMessage("No name entered");
					break;
				}
				oldNumber = pd.removeEntry(name);
				if(oldNumber == null){
					ui.sendMessage("Not in directory");
					break;
				}
				if(oldNumber.length() == 0){
					ui.sendMessage("Entry removed, but there was no number associated");
					break;
				}
				ui.sendMessage("Entry removed: " + name + ", " + oldNumber);
				// implement
				break;
			case 3:
				pd.save();
				ui.sendMessage("Saved");
				// implement
				break;
			case 4:
				// implement
				
				return;
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String fn = "csc220.txt";
		PhoneDirectory pd = new SortedPD();
		UserInterface ui = new GUI();
		processCommands(fn, ui, pd);
	}
}
