package prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;

public class Calculator {
  Object[] tokenize (String s) {
    input = s;
    index = 0;
    Object[] out = new Object[s.length()];
    int n = 0;
    
    while (!atEndOfInput ()) {
      if (isNumber())
        out[n++] = getNumber();
      else
        out[n++] = s.charAt(index++);
    }

    Object[] out2 = new Object[n];
    System.arraycopy(out, 0, out2, 0, n);
    return out2;
  }

  String input = null;
  int index = 0;

  boolean atEndOfInput () {
    while (index < input.length() &&
           Character.isWhitespace(input.charAt(index)))
      index++;
    return index == input.length();
  }

  boolean isNumber () {
    return Character.isDigit(input.charAt(index));
  }

  double getNumber () {
    int index2 = index;
    while (index2 < input.length() &&
           (Character.isDigit(input.charAt(index2)) ||
            input.charAt(index2) == '.'))
      index2++;
    double d = 0;
    try {
      d = Double.parseDouble(input.substring(index, index2));
    } catch (Exception e) {
      System.out.println(e);
    }
    index = index2;
    return d;
  }

  char getOperator () {
    char op = input.charAt(index++);
    if (OPERATORS.indexOf(op) == -1)
      System.out.println("Operator " + op + " not recognized.");
    return op;
  }

  Stack<Double> numberStack = new Stack<Double>();
  Stack<Character> operatorStack = new Stack<Character>();

  String numberStackToString () {
    String s = "numberStack: ";
    Stack<Double> helperStack = new Stack<Double>();
    // EXERCISE
    // Put every element of numberStack into helperStack
    // You will need to use a loop.  What kind?
    // What condition? When can you stop moving elements out of numberStack?
    // What method do you use to take an element out of numberStack?
    // What method do you use to put that element into helperStack.
    while(numberStack.empty() == false){
    	helperStack.push(numberStack.pop());
    }

    // Now put them back, but also add each one to s:
    // s = s + " " + number;
    while(helperStack.empty() == false){
    	s = s + " " + helperStack.peek();
    	numberStack.push(helperStack.pop());
    }
    System.out.println(s);
    return s;
  }

  String operatorStackToString () {
    String s = "operatorStack: ";
    Stack<Character> helperStack = new Stack<Character>();
    // EXERCISE
    // Put every element of numberStack into helperStack
    // You will need to use a loop.  What kind?
    // What condition? When can you stop moving elements out of numberStack?
    // What method do you use to take an element out of numberStack?
    // What method do you use to put that element into helperStack.
    while(operatorStack.empty() == false){
    	helperStack.push(operatorStack.pop());
    }

    // Now put them back, but also add each one to s:
    // s = s + " " + number;
    while(helperStack.empty() == false){
    	s = s + " " + helperStack.peek();
    	operatorStack.push(helperStack.pop());
    }
    System.out.println(s);

    return s;
  }

  static UserInterface ui = new GUI();

  void displayStacks () {
    ui.sendMessage(numberStackToString() + "\n" +
                   operatorStackToString());
  }

  double evaluate (String expr) {
    // Clean up any previous bad input.
    while (!operatorStack.empty()) operatorStack.pop();
    while (!numberStack.empty()) numberStack.pop();

    // Read the new input.
    Object[] inputs = tokenize(expr);
    String previous = "i"; //i for initial 
    for (int i = 0; i < inputs.length; i++) {
      if (inputs[i] instanceof Double) {
        double x = (Double) inputs[i];
        numberStack.push(x);
        displayStacks();
        previous = "number";
      }
      else {
        char o = (Character) inputs[i];
        if(o == '-') {
        		if(previous == ")" || previous == "number") {        			
        		}else {
        			o = 'u';
        		}
        }
        
        processOperator(o);
        //operatorStack.push(o);
        displayStacks();
        previous = Character.toString(o);

      }
    }
   while(operatorStack.empty() == false) {
	   if(operatorStack.peek() == 'u'){
		   numberStack.push(-1*numberStack.pop());
		   operatorStack.pop();
	   }else {
	    evaluateOperator();
	   }
   }
    return numberStack.pop();
  }
  
  double evaluateOperator (double a, char op, double b){
	  double result = 0;
	  switch(op){
	  case '+':
		  return(a + b);
	  case '-':
		  return(b - a);
		  
	  case '*':
		  return(a * b);
		  
	  case '/':
		  return(b/a);

	  case '^':
		  return(Math.pow(b,a));
	  case 'u':
		  return(a*(-1));
	  }
	  return result;
	  
  }
  
  void evaluateOperator (){
	  if(operatorStack.empty() == false) {
		  if(operatorStack.peek() == 'u'){
			  numberStack.push(-1*numberStack.pop());
			  operatorStack.pop();
			  return;
		  }else {
			  numberStack.push(evaluateOperator(numberStack.pop(),operatorStack.pop(),numberStack.pop()));
			  displayStacks();
		  }
	  }
  }
  
  /*void processOperator (char op) {
	  int precedenceTop = -4;
	  if(!operatorStack.empty()) precedenceTop = precedence(operatorStack.peek());
	  while(precedenceTop >= precedence(op) && !operatorStack.empty()){
		  evaluateOperator();
		  if(!operatorStack.empty()) precedenceTop = precedence(operatorStack.peek());
	  }
	  operatorStack.push(op);
  }*/
  
  void processOperator (char op) {
	  int precedenceTop = -4;
	  if(op == 'u') {
		  operatorStack.push(op);
		  return;
	  }
	  
	  if(!operatorStack.empty()) precedenceTop = precedence(operatorStack.peek());
	  
	  if(op == ')') {
		  while(operatorStack.peek() != '(' && !operatorStack.empty()){
			  
				  evaluateOperator();
			  
		  }
		 if(!operatorStack.isEmpty()) operatorStack.pop();
		 return;
	  }
	  
	  if(op == '(') {
		  operatorStack.push(op);
		  return;
	  }
	 

	  while(precedenceTop >= precedence(op) && !operatorStack.empty()){ 
			  displayStacks();
			  if(operatorStack.peek() == '(') {
				  //operatorStack.pop();
				  operatorStack.push(op);
				  return;
			  }
			  evaluateOperator();
			  if(!operatorStack.empty()) precedenceTop = precedence(operatorStack.peek());
		  
	  }
	  operatorStack.push(op);
  }
  
  static final String OPERATORS = "()+-*/^u";
  static final int[] PRECEDENCE = { -1, -1, 1, 1, 2, 2, 3,2 };
  
  int precedence (char op) {
    return PRECEDENCE[OPERATORS.indexOf(op)];
  }

  public static void main (String[] args) {
    Calculator parser = new Calculator();
    while (true) {
      String line = ui.getInfo("Enter arithmetic expression or cancel.");
      if (line == null)
         return;

      try {
        double result = parser.evaluate(line);
        ui.sendMessage(line + " = " + result);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }
}
