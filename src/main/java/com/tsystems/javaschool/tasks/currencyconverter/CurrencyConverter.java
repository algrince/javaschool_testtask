package com.tsystems.javaschool.tasks.currencyconverter;

import java.util.Stack;
import java.text.DecimalFormat;

public class CurrencyConverter {

    /**
     * Currency converter which converts the input statement and returns the result value.
     *
     * @param dollarToEuroRate 1 Dollar equals XX Euro
     * @param statement input statement to convert
     * @return converted value
     */
    public String convert(double dollarToEuroRate, String statement) {
        Stack<String> actionOrder = new Stack<String>();
        findParentheses(statement, actionOrder);
        return unpack(actionOrder, dollarToEuroRate);
    }
    
    public void findParentheses(String statement, Stack actionOrder) {
        //Find covertion by looking for parentheses
        int indexOpened = statement.indexOf("(");
        int indexClosed = statement.lastIndexOf(")");

        if (indexOpened != -1 && indexClosed != -1) {
            actionOrder.push(statement.substring(0, indexOpened));
            findOperations(statement.substring(indexOpened + 1, indexClosed), actionOrder);

            // Check if the number to convert contains convertion
            if (String.valueOf(actionOrder.peek()).contains("(")) {
                findParentheses(String.valueOf(actionOrder.pop()), actionOrder);
            }
            return;
        }

        findOperations(statement, actionOrder);
        return;
    }
    

    public void findOperations(String statement, Stack actionOrder) {
        // Find operations between numbers and/or convertions
        int indexPlus = statement.indexOf("+");
        int indexMinus = statement.indexOf("-");
        
        if (indexPlus != -1) {
            actionOrder.push(statement.substring(0, indexPlus));
            actionOrder.push("+");
            actionOrder.push(statement.substring(indexPlus + 1));
        } else if (indexMinus != -1) {
            actionOrder.push(statement.substring(0, indexMinus));
            actionOrder.push("-");
            actionOrder.push(statement.substring(indexMinus + 1));
        } else {
            actionOrder.push(statement);
        }
    }

    public String unpack(Stack actionOrder, double dollarToEuroRate) {
        while (!actionOrder.isEmpty()) {
            String element = String.valueOf(actionOrder.pop());
            String currency = checkCurrencyType(element);
            double number = trimNumberFromCurrency(element, currency);
            String nextElement = "";

            if (actionOrder.size() >= 1) {
                nextElement = String.valueOf(actionOrder.pop());
            } else {
                return uniteNumberWithCurrency(number, currency);
            }

            if (nextElement.equals("convertToDollar") && currency == "euro") {
                actionOrder.push(uniteNumberWithCurrency(number / dollarToEuroRate, "dollar"));
            } else if (nextElement.equals("convertToEuro") && currency == "dollar") {
                actionOrder.push(uniteNumberWithCurrency(number * dollarToEuroRate, "euro"));
            } else if (nextElement.equals("convertToDollar") || nextElement.equals("convertToEuro")) {
                throw new CannotConvertCurrencyException();
        
            } else if (nextElement.equals("+") || nextElement.equals("-")) {
                String secondOperand = String.valueOf(actionOrder.pop());
                if (secondOperand.contains("convertToDollar") || secondOperand.contains("convertToEuro")) {
                    secondOperand = convert(dollarToEuroRate, secondOperand);
                }
                String secondCurrency = checkCurrencyType(secondOperand);
                if (currency != secondCurrency) {
                    throw new CannotConvertCurrencyException();
                }
                double secondNumber = trimNumberFromCurrency(secondOperand, secondCurrency);
                actionOrder.push(uniteNumberWithCurrency(
                    operate(number, secondNumber, nextElement), secondCurrency));
                }
            }
        return "";
    }

    public double operate(double firstNumber, double secondNumber, String operator) {
        switch (operator) {
            case "+":
                return secondNumber + firstNumber;
            case "-":
                return secondNumber - firstNumber;
        }
        return 0.0;
    }

    public String checkCurrencyType(String currency) {
        char dollarSign = '$';
        char euroLastSign = 'o';
        if (currency.charAt(0) == dollarSign) {
            return "dollar";
        } else if (currency.charAt(currency.length() - 1) == euroLastSign) {
            return "euro";
        }
        throw new CannotConvertCurrencyException();
    }

    public double trimNumberFromCurrency(String operand, String currency) {
        switch (currency) {
            case "dollar":
                return Double.parseDouble(operand.substring(1));
            case "euro":
                return Double.parseDouble(operand.substring(0, operand.length() - 4));
        }
        throw new CannotConvertCurrencyException();
    }

    public String uniteNumberWithCurrency(double number, String currency) {
        switch (currency) {
            case "dollar":
                return "$" + format(number);
            case "euro":
                return format(number) + "euro";
        }
        throw new CannotConvertCurrencyException();
    }

    public String format(double result) {
        // Formates a resulting number
        DecimalFormat whole = new DecimalFormat("#");
        DecimalFormat decimal = new DecimalFormat("#.##");
        if (result % 1 == 0) {
            return whole.format(result);
        } else {
            return decimal.format(result);
        }
    }
}
