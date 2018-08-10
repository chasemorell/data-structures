package prog06;

import prog02.GUI;
import prog02.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class WordStep {
	public static UserInterface ui = new GUI();
    
	public List<Node> nodes = new ArrayList<Node>();

	public WordStep(){
		
	}
	
	public void cleanNodes() { //Sets previous of every node equal to null
		for(Node n:nodes) {
			n.previous = null;
		}
	}
	//grapes to snakes
	public void solve(String start,String end) {
		Queue<Node> wordList = new ArrayQueue<Node>();
		Node startNode = find(start);
		wordList.offer(startNode);
		while(!wordList.isEmpty()) {
			Node theNode = wordList.poll();
			for(Node n:nodes) {
				if(n.previous == null && oneLetter(n.item,theNode.item)) {
					
					Node nextNode = n;
					nextNode.previous = theNode;
					wordList.offer(nextNode);
					//System.out.println(n.item + ", I: " + wordList.size());
					if(nextNode.item.equals(end)) {
						String s = "";
       						while(nextNode.previous != null && !nextNode.previous.item.equals(start)) {
							s =  nextNode.previous.item + "\n" + s; 
							nextNode = nextNode.previous;
						}
						s = start + "\n" + s + end;
						cleanNodes();
						ui.sendMessage("Got from " + start + " to " + end);
						ui.sendMessage(s);
						return;
					}
				}
			}
		}
		cleanNodes();
		System.out.println("No solution found");
		
	}
	
	private static class Node {
        private String item;
        private Node previous;
        
        private Node (String item) {
          this.item = item;
          previous = null;
        }
        private Node (String item, Node previous) {
          this.item = item;
          this.previous = previous;
        }
	} 
	
	public void play(String startWord,String targetWord){
		String inputWord;
		String currentWord = startWord;
		while (currentWord.compareTo(targetWord) != 0){
			inputWord = ui.getInfo("Enter a word");
			if(find(inputWord) != null) {

				if(oneLetter(inputWord,currentWord)){
					currentWord = inputWord;
				}else{
					ui.sendMessage("This word differs by than more than one letter from " + currentWord);
				}
			}else {
				ui.sendMessage("Word is not in dictionary");
			}

		}
		ui.sendMessage("You win!");
		return;
	}
	
	public int score(){
		return 0;
	}
	
	public static boolean oneLetter(String a,String b){
		int diff = 0;
		if(a.length() != b.length()){
			return false;
		}
		for(int i = 0; i < a.length();i ++){
			if(a.charAt(i) != b.charAt(i)){
				diff++;
			}
		}

		if(diff <= 1){
			return true;
		}
		
		return false;
	}
	

	
	public void loadDictionary(String fileName){
		System.out.println(fileName);

		try {
			File file = new File("src/prog06/" + fileName);
			Scanner in = new Scanner(file);

			
			while(in.hasNextLine()) {
				Node nToAdd = new Node(in.nextLine());
				nodes.add(nToAdd);
			}
			in.close();
		}catch(FileNotFoundException e) {
			System.out.println("File Not found");
			ui.sendMessage("Uh oh. File was not found");
			loadDictionary(ui.getInfo("Enter the Dictionary Name:"));

		}
	}
	
	public Node find(String word) {
		for(int i = 0; i < nodes.size();i++) {
			if(nodes.get(i).item.equals(word)) {
				return nodes.get(i);
			}
		}
		return null;
	}
	
	public static void main(String[] args){
		System.out.println("INIT");

		WordStep game = new WordStep();
		game.loadDictionary(ui.getInfo("Enter the Dictionary Name:"));

		while (true){
			String startWord = ui.getInfo("Enter the Starting Word:");
			String targetWord = ui.getInfo("Enter the target Word:");
			
			String[] commands = { "Human plays.", "Computer plays." };
			int c = ui.getCommand(commands);
			switch(c){
				case -1: return;
				case 0: game.play(startWord,targetWord); break;
				case 1: game.solve(startWord, targetWord);
			}
		}
		
		
	}
}
