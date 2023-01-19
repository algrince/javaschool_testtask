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

        if (validate(statement) == false) {
            return null;
        }
        while (statement.contains("(") || statement.contains("*") || statement.contains("/")
            || statement.contains("+") || statement.contains("-")) {
            if (statement.contains("(")) {
                String newStatement = statement.substring(statement.indexOf("(") + 1, statement.indexOf(")"));
                String bracketsNumber = evaluate(newStatement);
                statement = statement.replaceFirst("\\(([^\\)]+)\\)", bracketsNumber);
            }
            if (statement.contains("*") && (statement.contains("/"))) {
                if (statement.indexOf("*") < statement.indexOf("/")) {
                    statement = multiply(statement);
                    statement = divide(statement);
                } else {
                    statement = divide(statement);
                    statement = multiply(statement);
                }
            } else if (statement.contains("*")) {
                statement = multiply(statement);
            } else if (statement.contains("/")) {
                statement = divide(statement);
            } else if (statement.contains("+") && statement.contains("-")) {
                if (statement.indexOf("+") < statement.indexOf("-")) {
                    statement = sum(statement);
                    statement = sub(statement);
                } else {
                    statement = sub(statement);
                    statement = sum(statement);
                }
            } else if (statement.contains("-")) {
                statement = sub(statement);
            } else if (statement.contains("+")) {
                statement = sum(statement);
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

    public String multiply(String statement) {
        String[] dividedStatement = statement.split("\\*");
        String left = dividedStatement[0];
        String leftNumber = getLeftNumber(left);
        String right = dividedStatement[1];
        String rightNumber = getRightNumber(right);
        double result = Double.parseDouble(leftNumber) * Double.parseDouble(rightNumber);
        String toReplace = leftNumber + "*" + rightNumber;
        statement = statement.replace(toReplace, Double.toString(result));
        return statement;
    }
    public String divide(String statement) {
        String[] dividedStatement = statement.split("\\/");
        String right = dividedStatement[1];
        String rightNumber = getRightNumber(right);
        if (Integer.parseInt(rightNumber, 0, 0, 0) == 0) {
            return null;
        } else {
            String left = dividedStatement[0];
            String leftNumber = getLeftNumber(left);
            double result = Double.parseDouble(leftNumber) / Double.parseDouble(rightNumber);
            String toReplace = leftNumber + "/" + rightNumber;
            statement = statement.replace(toReplace, Double.toString(result));
        }
        return statement;
    }
    public String sum(String statement) {
        String[] dividedStatement = statement.split("\\+");
        String left = dividedStatement[0];
        String leftNumber = getLeftNumber(left);
        String right = dividedStatement[1];
        String rightNumber = getRightNumber(right);
        double result = Double.parseDouble(leftNumber) + Double.parseDouble(rightNumber);
        String toReplace = leftNumber + "+" + rightNumber;
        statement = statement.replace(toReplace, Double.toString(result));
        return statement;
    }
    public String sub(String statement) {
        String[] dividedStatement = statement.split("\\-");
        String left = dividedStatement[0];
        String leftNumber = getLeftNumber(left);
        String right = dividedStatement[1];
        String rightNumber = getRightNumber(right);
        double result = Double.parseDouble(leftNumber) - Double.parseDouble(rightNumber);
        String toReplace = leftNumber + "-" + rightNumber;
        statement = statement.replace(toReplace, Double.toString(result));
        return statement;
    }

    public String getLeftNumber(String left) {
        String number = ""; 
        for (int i=left.length() - 1; i >=0 ; i--) {
            char character = left.charAt(i);
            if (Character.isDigit(character) || character == '.') {
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
        char openedBracket = '(';
        char closedBracket = ')';
        char coma = ',';
        boolean validated = true;
        boolean insideBracket = false;

        if (statement == null || statement.isEmpty() || 
        statement.matches(".*[^0-9]{2,}.*") || statement.indexOf(coma) != -1) {
            return false;
        }

        for (int i=0; i < statement.length(); i++) {
            if (statement.charAt(i) == openedBracket) {
                validated = false;
                insideBracket = true; 
            } else if (statement.charAt(i) == closedBracket) {
                if (insideBracket == false) {
                    validated = false;
                    break;
                } else {
                validated = true;
            }
        }
        }

        if (insideBracket == true) {
            validated = false;
        }
        return validated;
    }

}
