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
        boolean hasParentheses = findParentheses(statement, actionOrder);
        System.out.println(hasParentheses);
        System.out.println(actionOrder);
        String output = unpack(actionOrder, dollarToEuroRate);
        System.out.println(output);
        return output;
    }
    
    public boolean findParentheses(String statement, Stack actionOrder) {
        int indexOpened = statement.indexOf("(");
        int indexClosed = statement.lastIndexOf(")");
        if (indexOpened != -1 && indexClosed != -1) {
            actionOrder.push(statement.substring(0, indexOpened));
            findOperations(statement.substring(indexOpened + 1, indexClosed), actionOrder);
            if (String.valueOf(actionOrder.peek()).contains("(")) {
                boolean innerParentheses = findParentheses(String.valueOf(actionOrder.pop()), actionOrder);
            }
            return true;
        } 
        findOperations(statement, actionOrder);;
        return false;
    }
    

    public void findOperations(String statement, Stack actionOrder) {
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
                } else if (nextElement.equals("+")) {
                    String secondOperand = String.valueOf(actionOrder.pop());
                    if (secondOperand.contains("convertToDollar") || secondOperand.contains("convertToEuro")) {
                        secondOperand = convert(dollarToEuroRate, secondOperand);
                    }
                    String secondCurrency = checkCurrencyType(secondOperand);
                    if (currency != secondCurrency) {
                        throw new CannotConvertCurrencyException();
                    }
                    double secondNumber = trimNumberFromCurrency(secondOperand, secondCurrency);
                    actionOrder.push(uniteNumberWithCurrency(number + secondNumber, secondCurrency));
                } else if (nextElement.equals("-")) {
                    String secondOperand = String.valueOf(actionOrder.pop());
                    if (secondOperand.contains("convertToDollar") || secondOperand.contains("convertToEuro")) {
                        secondOperand = convert(dollarToEuroRate, secondOperand);
                    }
                    String secondCurrency = checkCurrencyType(secondOperand);
                    if (currency != secondCurrency) {
                        throw new CannotConvertCurrencyException();
                    }
                    double secondNumber = trimNumberFromCurrency(secondOperand, secondCurrency);
                    actionOrder.push(uniteNumberWithCurrency(secondNumber - number, secondCurrency));
                }
            }
        return "";
    }
    
    public String checkType(String element) {
        if (element.equals("convertToDollar")) {
            return "convert dollar";
        } else if (element.equals("convertToEuro")){
            return "convert euro";
        } else if (element.contains("+")) {
            return "suma";
        } else if (element.contains("-")) {
            return "substraction";
        } else {
            return "simple number";
        }
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
                System.out.println("$" + format(number));
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
