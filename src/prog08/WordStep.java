package prog08;

import prog02.GUI;
import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Comparator;
import java.util.PriorityQueue;

public class WordStep {
  private static class Node {
    String word;
    Node previous;
    
    Node (String word) {
      this.word = word;
    }
    int distance(){
    		Node node = this;
    		if(node.previous != null) {
    			return 1 + node.previous.distance();
    		}
    		return 1;
    }
  }
  
  private static class NodeComparator implements Comparator<Node>{
	 
	private String target;
	NodeComparator(String target){
		this.target = target;
	}
	
	public int value(Node node){
		return (node.distance() + differenceLetters(node.word,target));
	}
	
	
	@Override
	public int compare(Node o1, Node o2) {
		// TODO Auto-generated method stub		
		return value(o1) - value(o2);
	}
	  
	
  }
  
  static GUI ui = new GUI();
  
  public static void main (String[] args) {
	  
    WordStep game = new WordStep();
    String fn = null;
    do {
      fn = ui.getInfo("Enter dictionary file:");
      if (fn == null)
        return;
    } while (!game.loadDictionary("src/prog06/"+fn));
    
    String start = ui.getInfo("Enter starting word:");
    if (start == null)
      return;
    while (game.find(start) == null) {
      ui.sendMessage(start + " is not a word.");
      start = ui.getInfo("Enter starting word:");
      if (start == null)
        return;
    }
    String target = ui.getInfo("Enter target word:");
    if (target == null)
      return;
    while (game.find(target) == null) {
      ui.sendMessage(target + " is not a word.");
      target = ui.getInfo("Enter target word:");
      if (target == null)
        return;
    }
    
    String[] commands = { "Human plays.", "Computer plays." };
    int c = ui.getCommand(commands);
    
    if (c == 0)
      game.play(start, target);
    else
      game.solve(start, target);
  }
  
  void play (String start, String target) {
    while (true) {
      ui.sendMessage("Current word: " + start + "\n" +
                     "Target word: " + target);
      String word = ui.getInfo("What is your next word?");
      if (word == null)
        return;
      if (find(word) == null)
        ui.sendMessage(word + " is not in the dictionary.");
      else if (!oneLetter(start, word))
        ui.sendMessage("Sorry, but " + word +
                       " differs by more than one letter from " + start);
      else if (word.equals(target)) {
        ui.sendMessage("You win!");
        return;
      }
      else
        start = word;
    }
  }    
  
  static boolean oneLetter (String snow, String slow) {
    if (snow.length() != slow.length())
      return false;
    int count = 0;
    for (int i = 0; i < snow.length(); i++)
      if (snow.charAt(i) != slow.charAt(i))
        count++;
    return count == 1;
  }

  List<Node> nodes = new ArrayList<Node>();
  
  boolean loadDictionary (String file) {
    try {
      Scanner in = new Scanner(new File(file));
      while (in.hasNextLine()) {
        String word = in.nextLine();
        Node node = new Node(word);
        nodes.add(node);
      }
    } catch (Exception e) {
      ui.sendMessage("Uh oh: " + e);
      return false;
    }
    return true;
  }
  
  Node find (String word) {
    for (int i = 0; i < nodes.size(); i++)
      if (word.equals(nodes.get(i).word))
        return nodes.get(i);
    return null;
  }
  
  void clearAllPrevious () {
    for (int i = 0; i < nodes.size(); i++)
      nodes.get(i).previous = null;
  }
  
  static int differenceLetters(String a,String b) {
	  int maxLength;
	  int numDiff = 0;
	  if(a.length() > b.length())
		  maxLength = a.length();
	  else
		  maxLength = b.length();
	  
	  for(int i = 0;i < maxLength;i++) {
		  if(a.charAt(i) != b.charAt(i))
			  numDiff++;
	  }
	  return numDiff;
  }
  
  void solve (String start, String target) {
    clearAllPrevious();
    
    //Queue<Node> queue = new LinkedList<Node>();
	//Queue<Node> queue = new PriorityQueue<Node>(1000,new NodeComparator(target));
	Queue<Node> queue = new Heap<Node>(new NodeComparator(target));

    int numPolled = 0;
    Node startNode = find(start);
    queue.offer(startNode);
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      numPolled++;
      System.out.println("DEQUEUE: " + node.word);
      System.out.print("ENQUEUE:");
      
      for (int i = 0; i < nodes.size(); i++) {
        Node next = nodes.get(i);
         //if (next != startNode && next.previous == null && oneLetter(node.word, next.word)) 
        if(next.previous != null && node.distance()+1 < next.distance() && oneLetter(node.word,next.word)) {
     			queue.remove(next);
     			next.previous = node;
     			queue.add(next);
     	}
        
        if (next != startNode && next.previous == null && oneLetter(node.word, next.word)) { 
          next.previous = node;
          queue.offer(next);
          System.out.print(" " + next.word);
          if (next.word.equals(target)) {
            ui.sendMessage("Got to " + target + " from " + node.word);
            String s = node.word + "\n" + target;
            ui.sendMessage("Number of Times Polled: " + (numPolled) + ", steps: " + node.distance());

            while (node != startNode) {
              node = node.previous;
              s = node.word + "\n" + s;
            }
            ui.sendMessage(s);
            return;
          }
        }
        
     
        
      }
      System.out.println();
    }
  }
}
