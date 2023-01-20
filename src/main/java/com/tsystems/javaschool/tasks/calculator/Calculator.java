package com.tsystems.javaschool.tasks.calculator;

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
        String plusSign = "+";
        String minusSign = "-";
        String timesSign = "*";
        String divisionSign = "/";

        if (validate(statement) == false) {
            return null;
        }
        while (statement.contains("(") || statement.contains(timesSign) || statement.contains(divisionSign)
            || statement.contains(plusSign) || statement.contains(minusSign)) {
            if (statement.contains("(")) {
                String newStatement = statement.substring(statement.indexOf("(") + 1, statement.indexOf(")"));
                String bracketsNumber = evaluate(newStatement);
                statement = statement.replaceFirst("\\(([^\\)]+)\\)", bracketsNumber);
            }
            if (statement.contains(timesSign) && (statement.contains(divisionSign))) {
                if (statement.indexOf(timesSign) < statement.indexOf(divisionSign)) {
                    statement = operate(statement, timesSign);
                    statement = operate(statement, divisionSign);
                } else {
                    statement = operate(statement, divisionSign);
                    statement = operate(statement, timesSign);
                }
            } else if (statement.contains(timesSign)) {
                statement = operate(statement, timesSign);
            } else if (statement.contains(divisionSign)) {
                statement = operate(statement, divisionSign);
            } else if (statement.contains(plusSign) && statement.contains(minusSign)) {
                if (statement.indexOf(plusSign) < statement.indexOf(minusSign)) {
                    statement = operate(statement, plusSign);
                    statement = operate(statement, minusSign);
                } else {
                    statement = operate(statement, minusSign);
                    statement = operate(statement, plusSign);
                }
            } else if (statement.contains(minusSign)) {
                statement = operate(statement, minusSign);
            } else if (statement.contains(plusSign)) {
                statement = operate(statement, plusSign);
            }
        }
        double result = Double.parseDouble(statement);
        int roundRes = (int) Math.round(result);
        return Integer.toString(roundRes);
    }

    public String getRightNumber(String right) {
        String number = ""; 
        for (int i=0; i < right.length(); i++) {
            char character = right.charAt(i);
            if (Character.isDigit(character) || character == '.') {
                number = number + character;
            } else {
                break;
            }
        }
        return number;
    }

    public String operate(String statement, String operator) {
        // Find numbers around the operator 
        String regexOperator = "\\" + operator;
        String[] dividedStatement = statement.split(regexOperator);
        String left = dividedStatement[0];
        String leftNumber = getLeftNumber(left);
        String right = dividedStatement[1];
        String rightNumber = getRightNumber(right);

        // Make an operation and replace the expression with result
        double result = 0.0;
        String toReplace = "";

        switch (operator) {
            case "+":
                result = Double.parseDouble(leftNumber) + Double.parseDouble(rightNumber);
                toReplace = leftNumber + "+" + rightNumber;
                break;
            case "-":
                result = Double.parseDouble(leftNumber) - Double.parseDouble(rightNumber);
                toReplace = leftNumber + "-" + rightNumber;
                break;
            case "*":
                result = Double.parseDouble(leftNumber) * Double.parseDouble(rightNumber);
                toReplace = leftNumber + "*" + rightNumber;
                break;
            case "/":
                if (Integer.parseInt(rightNumber, 0, 0, 0) == 0) {
                    return null;
                } else {
                    result = Double.parseDouble(leftNumber) / Double.parseDouble(rightNumber);
                    toReplace = leftNumber + "/" + rightNumber;
                }
        }
        statement = statement.replace(toReplace, Double.toString(result));
        return statement;
    }

    public String getLeftNumber(String left) {
        String number = ""; 
        for (int i=left.length() - 1; i >=0 ; i--) {
            char character = left.charAt(i);
            if (character == '-') {
                number = number + character;
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
        return leftNumber;
    }

    public boolean validate(String statement) {
        char coma = ',';

        if (statement == null || statement.isEmpty() || 
        statement.matches(".*[^0-9()]{2,}.*") || statement.indexOf(coma) != -1) {
            return false;
        }

        char openedBracket = '(';
        int openedBracketCounter = 0;
        int closedBracketCounter = 0;
        char closedBracket = ')';
        boolean validated = true;
        boolean insideBracket = false;
        

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

        if (insideBracket == true || openedBracketCounter != closedBracketCounter) {
            validated = false;
        }
        return validated;
    }

}
