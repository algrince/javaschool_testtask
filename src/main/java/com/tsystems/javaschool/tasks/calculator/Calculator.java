package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {

        // End the method if statement is invalid
        if (validate(statement) == false) {
            return null;
        }
        String operationResult = divideIntoOperations(statement);
        if (operationResult.isEmpty()) {
            return null;
        } else {
            return format(operationResult);
        }
    }

    public String divideIntoOperations(String statement){
        String plusSign = "+";
        String minusSign = "-";
        String timesSign = "*";
        String divisionSign = "/";
        String[] numbers = {};
        String result = "";
        String toReplace = "";
        // Make math operations while there are sings in statement
        if (statement.contains("(")) {
            String newStatement = statement.substring(statement.indexOf("(") + 1, statement.indexOf(")"));
            String bracketsNumber = divideIntoOperations(newStatement);
            statement = statement.replaceFirst("\\(([^\\)]+)\\)", bracketsNumber);
        }

        int timesIndex = statement.indexOf(timesSign);
        int divisionIndex = statement.indexOf(divisionSign);
        int plusIndex = statement.indexOf(plusSign);
        int minusIndex = statement.indexOf(minusSign);
        if ((timesIndex < divisionIndex && timesIndex != -1 && divisionIndex != -1) 
            || (divisionIndex == -1 && timesIndex != -1)) {
            numbers = divideIntoOperands(statement, timesSign, false);
            result = operate(statement, timesSign, numbers);
            toReplace = numbers[0] + timesIndex + numbers[1];

        } else if ((divisionIndex < timesIndex && divisionIndex != -1 && timesIndex != -1)
            || (timesIndex == -1 && divisionIndex != -1)){
            numbers = divideIntoOperands(statement, divisionSign, false);
            result = operate(statement, divisionSign, numbers);
            toReplace = numbers[0] + divisionIndex + numbers[1];

        } else if ((plusIndex < minusIndex && plusIndex != -1 && minusIndex != -1)
            || (minusIndex == -1 && plusIndex != -1)) {
            numbers = divideIntoOperands(statement, plusSign, false);
            result = operate(statement, plusSign, numbers);
            toReplace = numbers[0] + plusIndex + numbers[1];

        } else if ((minusIndex < plusIndex && minusIndex != -1 && plusIndex != -1)
            || (plusIndex == -1 && minusIndex != -1)) {
            numbers = divideIntoOperands(statement, minusSign, false);
            if (numbers.length == 0) {
                return statement;
            }
            result = operate(statement, minusSign, numbers);
            toReplace = numbers[0] + plusIndex + numbers[1];
        }
        
        statement = statement.replace(toReplace, result);
        minusIndex = statement.indexOf(minusIndex);
        if (minusIndex == 0) {
            statement = handleNegative(statement);
        } else if (statement.contains(plusSign) || (statement.contains(minusSign) && minusIndex != 0)
        || statement.contains(timesSign) || statement.contains(divisionSign)) {
            return divideIntoOperations(statement);
        }
        return statement;
    }

    public String handleNegative(String statement) {
        String[] numbers = {};
        String result = "";
        String toReplace = "";
        for (int i = 1; i < statement.length(); i++) {
            char ch = statement.charAt(i);
            switch (ch) {
                case '+':
                    numbers = divideIntoOperands(statement, "+", true);
                    String[] numbersForSum = {numbers[1], numbers[0]};
                    result = operate(statement, "-", numbersForSum);
                    toReplace = "-" + numbers[0] + "+" + numbers[1];
                    break;
                case '-':
                    numbers = divideIntoOperands(statement, "-", true);
                    result = "-" + operate(statement, "+", numbers);
                    toReplace = "-" + numbers[0] + "-" + numbers[1];
                    break;
                case '/':
                    numbers = divideIntoOperands(statement, "/", true);
                    result = "-" + operate(statement, "/", numbers);
                    if (result.equals("-")) {
                        return null;
                    }
                    toReplace = "-" + numbers[0] + "/" + numbers[1];
                    break;
                case '*':
                    numbers = divideIntoOperands(statement, "/", true);
                    result = "-" + operate(statement, "/", numbers);
                    toReplace = "-" + numbers[0] +  "*" + numbers[1];
                default:
                    continue;
            }
        }
        return statement.replace(toReplace, result);
    }


    public String getRightNumber(String right) {
        // Separate a number on the right from the sign in the statement
        String number = "";
        for (int i=0; i < right.length(); i++) {
            char character = right.charAt(i);
            if (character == '-' && i == 0) {
                number = number + character;
            }
            if (Character.isDigit(character) || character == '.') {
                number = number + character;
            } else {
                break;
            }
        }
        return number;
    }

    public String getLeftNumber(String left) {
        // Separate a number on the right from the sign in the statement
        String number = "";
        boolean negative = false; 
        for (int i = left.length() - 1; i >= 0 ; i--) {
            char character = left.charAt(i);
            // Check if the number is negative
            if (character == '-') {
                negative = true;
                break;
            } else if (Character.isDigit(character) || character == '.') {
                number = number + character;
            } else {
                break;
            }
        }
        StringBuilder reverseNumber = new StringBuilder();
        reverseNumber.append(number);
        reverseNumber.reverse();
        String leftNumber = reverseNumber.toString();
        if (negative) {
            return "-" + leftNumber;
        } else {
            return leftNumber;
        }
    }

    public String[] divideIntoOperands(String statement, String operator, boolean leftNegative) {
        // Find numbers around the operator 
        String regexOperator = "\\" + operator;
        String[] dividedStatement = statement.split(regexOperator);
        String left = dividedStatement[0];
        // If "left" is empty, a negative number was passed instead of an expression
        if (left.isEmpty()) {
            String[] isNegativeNumber = {};
            return isNegativeNumber;
        }
        String leftNumber = getLeftNumber(left);
        String right = dividedStatement[1];
        String rightNumber = getRightNumber(right);
        if (leftNegative) {
            leftNumber = leftNumber.substring(1);
        }
        String[] numbers = {leftNumber, rightNumber};
        return numbers;
    } 

    public String operate(String statement, String operator, String[] numbers) {
        if (numbers.length == 0) {
            return statement;
        } 

        String leftNumber = numbers[0];
        String rightNumber = numbers[1];

        // Make an operation
        double result = 0.0;

        switch (operator) {
            case "+":
                result = Double.parseDouble(leftNumber) + Double.parseDouble(rightNumber);
                break;
            case "-":
                result = Double.parseDouble(leftNumber) - Double.parseDouble(rightNumber);
                break;
            case "*":
                result = Double.parseDouble(leftNumber) * Double.parseDouble(rightNumber);
                break;
            case "/":
                // Handle division by 0
                if (Double.parseDouble(rightNumber) == 0.0) {
                    return "";
                } else {
                    result = Double.parseDouble(leftNumber) / Double.parseDouble(rightNumber);
                }
                break;
        }
        return Double.toString(result);
    }



    public boolean validate(String statement) {
        String coma = ",";

        // Check null/empty statements and ones that cointain comas/repeated symbols
        if (statement == null || statement.isEmpty() || 
        statement.matches(".*[^0-9()]{2,}.*") || statement.contains(coma)) {
            return false;
        }

        char openedBracket = '(';
        int openedBracketCounter = 0;
        int closedBracketCounter = 0;
        char closedBracket = ')';
        boolean validated = true;
        boolean insideBracket = false;
        
        // Check parenthesis pairings 
        for (int i=0; i < statement.length(); i++) {
            if (statement.charAt(i) == openedBracket) {
                validated = false;
                insideBracket = true; 
                openedBracketCounter++;
            } else if (statement.charAt(i) == closedBracket) {
                if (insideBracket == false) {
                    validated = false;
                    break;
                } else {
                validated = true;
                insideBracket = false;
                closedBracketCounter++;
            }
        }
        }

        // Statement is invalid if at the end parenthesis are open or the number of opened/closed does not match
        if (insideBracket == true || openedBracketCounter != closedBracketCounter) {
            validated = false;
        }
        return validated;
    }

    public String format(String finalStatement) {
        // Formates a resulting number
        double result = Double.parseDouble(finalStatement);
        DecimalFormat whole = new DecimalFormat("#");
        DecimalFormat decimal = new DecimalFormat("#.####");
        if (result % 1 == 0) {
            return whole.format(result);
        } else {
            return decimal.format(result);
        }
    }

}
