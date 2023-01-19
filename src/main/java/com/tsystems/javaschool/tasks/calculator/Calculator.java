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
        return "";
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
