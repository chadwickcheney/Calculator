
/**
 * Calculator class executes mathematical expressions in the form a string and returns a double
 *
 * @author Chadwick Cheney
 *
 */
public class Calculator {

	private static final String ACCEPTED_CHARACTERS= "1234567890%^*()-+./<p";

	/**
	 * Ensures that the string set is valid, only numbers and accepted characters.
	 * Throws an ArithematicException when failed test.
	 * 
	 * @param expr is the user input expression as a String
	 * @return no return value
	 */
	public void validateAcceptedCharacters(String expr) {
		System.out.println("set: "+expr);
		for (int i = 0; i < expr.length(); ++i) {
			boolean isFound = ACCEPTED_CHARACTERS.indexOf(expr.charAt(i)) !=-1? true : false;
			if (!isFound) {
				throw new ArithmeticException("Invalid Characters");
			}
		}
	}

	/**
	 * Ensures that the string set has matching open and close parenthesis count.
	 * Throws an ArithematicException when failed test.
	 *
	 * @param expr is the user input expression as a Strings
	 * @return no return value
	 */
	public void validateNestedParenthesis(String expr) {
		int parenthesisCount = 0;
		for (int i = 0; i < expr.length(); ++i) {
			if (expr.charAt(i) == '(') {
				++parenthesisCount;
			}
			else if (expr.charAt(i) == ')') {
				--parenthesisCount;
			}
		}
		if (parenthesisCount != 0) {
			throw new ArithmeticException("Invalid Parenthesis Count");
		}
	}

	/**
	 * Check to see if character is an operand by comparing ascii codes
	 *
	 * @param asciiCode is the ascii code of the character
	 * @return true or false
	 */
	public boolean isOperand(int asciiCode) {
		if (asciiCode == 42 ||  asciiCode == 43 || asciiCode == 45 || asciiCode == 47 || asciiCode == 64 || asciiCode == 94 || asciiCode == 60 || asciiCode == 37) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Check to see if the ascii code is a number or decimal by comparing ascii codes.
	 *
	 * @param asciiCode is the ascii code of the character
	 * @return true or false
	 */
	public boolean isNumberOrDecimal(int asciiCode) {
		if (asciiCode >= 48 && asciiCode <= 57 || asciiCode == 46) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Calculate computes the expressions in the form of a
	 * String and returns a double.
	 *
	 * @param expr is the user input expression as a Strings
	 * @see #isOperand(int)
	 * @see #isNumberOrDecimal(int)
	 * @return answer in double
	 */
	public double calculate(String expr) {
		
		while (expr.contains("(")) { //compute all mathematical expressions within parenthesis and replace parenthesis with valid characters
			//check if nested parenthesis
			int indexOfFirstOpenParenthesis = expr.indexOf("(");	//get index of the first occurrence of opening parenthesis
			String substringAfterFirstOpenParenthesis =
					expr.substring(indexOfFirstOpenParenthesis); //mathematical expression following the first open parenthesis
			int indexOfNextClosingParenthesis = -1; 			//index of last close parenthesis in the current parenthesis nest
			int parenthesisCount = 0; 							//count parenthesis to get enclosed parenthesis blocks

			for (int index = 0; index < substringAfterFirstOpenParenthesis.length(); index++) { //capture enclosing parenthesis of indexOfFirstOpenParenthesis by getting the index of it
				char character = substringAfterFirstOpenParenthesis.charAt(index);
				if (character == '(')
					parenthesisCount += 1;
				else if (character == ')')
					parenthesisCount -= 1;
				if (parenthesisCount == 0 && character == ')') {
					indexOfNextClosingParenthesis = index;
					break;
				}
			}

			int indexOfRemainingSet = indexOfNextClosingParenthesis+
					indexOfFirstOpenParenthesis+1; 							//index of set not yet scanned
			String subset = expr.substring(indexOfFirstOpenParenthesis+1,
					indexOfRemainingSet-1); 								//subset set of set not yet scanned
			String setA = expr.substring(0, indexOfFirstOpenParenthesis); 	//set before first parenthesis
			String setB = Double.toString(calculate(subset)); 				//set within parenthesis, calls calculate recursive for nested parenthesis
			String setC = expr.substring(indexOfRemainingSet); 				//set after parenthesis

			if (setA.length() > 0) {
				int lastCharAsciiCode = (int) setA.charAt(setA.length()-1);
				if (!isOperand(lastCharAsciiCode)) {
					setA += "*";
				}
			}

			expr = setA+setB+setC; //combine all set modified substrings back to full set string
		}

		expr += '@'; 				//special character for calculation marker
		String temp = ""; 			//temporary variable for calculation placeholder
		double calculations = 0.0; 	//temporary variable for calculation placeholder
		double result = 0; 			//result variable to be returned
		char action = '+'; 			//operand variable to update at every operand scanned

		int firstAscii = (int) expr.charAt(0);
		if (isOperand(firstAscii)) {
			temp = Double.toString(calculations);
		}

		for (int i = 0; i < expr.length(); i++) {
			int characterAsciiCode = (int) expr.charAt(i);
			if (isNumberOrDecimal(characterAsciiCode)) {
				temp += expr.charAt(i);
			}
			else if (isOperand(characterAsciiCode)) {
				switch(action) {
					case'+':
						result = result + Double.parseDouble(temp);
						break;
					case'-':
						result = result - Double.parseDouble(temp);
						break;
					case'*':
						result = result * Double.parseDouble(temp);
						break;
					case'/':
						result = result / Double.parseDouble(temp);
						break;
					case'^':
						result = Math.pow(result, Double.parseDouble(temp));
						break;
					case'<':
						double num = Double.parseDouble(temp);
						double root = result;
						result = Math.pow(Math.E, Math.log(num)/root);
						break;
					case'%':
						result = result % Double.parseDouble(temp);
						break;
				}
				temp = ""; //reset temporary placeholder
				action = (char) expr.charAt(i); //get next operand
			}
		}
		return result;
	}

	/**
	 * Run executes the calculation methods and is called from outside this class
	 *
	 * @param expr is the user input expression as a String.
	 * @see #validateAcceptedCharacters(String)
	 * @see #validateNestedParenthesis(String)
	 * @return answer in double if the tests pass double.
	 */
	public Double run(String expr) {
		
		try {
			expr = expr.replace("_/", "<"); //replace nth root characters for execution 
			expr = expr.replace("pi", Double.toString(Math.PI)); //replace Pi input with full pi value

			validateAcceptedCharacters(expr); 	//validate string set to make ensure only accepted characters and operands.
			validateNestedParenthesis(expr);		//validate parenthesis count is correct.			
			double answer = calculate(expr);
			return answer;
		} catch (ArithmeticException ae) {
			ae.printStackTrace();
			System.out.println("...please try agian...");
		}
		
		return null;

	}
}
