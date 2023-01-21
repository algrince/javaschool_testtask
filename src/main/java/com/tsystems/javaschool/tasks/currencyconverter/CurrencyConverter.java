package com.tsystems.javaschool.tasks.currencyconverter;

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
        String convDollar = "convertToDollar";
        String convEuro = "convertToEuro";
        String[] splitDollar = statement.split(convDollar, 2);
        String[] splitEuro = statement.split(convEuro, 2);
        String change = "";
        String operation = "";

        if (splitDollar.length != 1) {
            change = "dollar";
            System.out.print(splitDollar[1]);
            String moneyToChange = splitDollar[1].substring(1, splitDollar[1].indexOf(")"));
            System.out.println("TO CHANGE: " + moneyToChange);
            String money = convert(dollarToEuroRate, moneyToChange);
            String currency = checkCurrencyType(money);
            if (currency.equals("dollar")) {
                double trimmedNumber = trimNumberFromCurrency(money, currency);
                double result = trimmedNumber / dollarToEuroRate;
                return uniteNumberWithCurrency(result, "euro");
            }
            else {
                throw new CannotConvertCurrencyException();
            }
            
        } else if (splitEuro.length != 1) {
            change = "euro";
            String moneyToChange = splitEuro[1].substring(1, splitEuro[1].indexOf(")"));
            String money = convert(dollarToEuroRate, moneyToChange);
            String currency = checkCurrencyType(money);
            if (currency.equals("euro")) {
                double trimmedNumber = trimNumberFromCurrency(money, currency);
                double result = trimmedNumber * dollarToEuroRate;
                return uniteNumberWithCurrency(result, "dollar");
            }
            else {
                throw new CannotConvertCurrencyException();
            }
        } else {
            operation = hasOperations(statement);
            switch (operation) {
                case "sum":
                    String[] sumOperands = statement.split("\\+");
                    if (checkOperandsType(sumOperands)) {
                        String currency = checkCurrencyType(sumOperands[0]);
                        double trimmedSumOperand1 = trimNumberFromCurrency(sumOperands[0], currency);
                        double trimmedSumOperand2 = trimNumberFromCurrency(sumOperands[1], currency);
                        double result = trimmedSumOperand1 + trimmedSumOperand2;
                        return uniteNumberWithCurrency(result, currency);
                    } else {
                        throw new CannotConvertCurrencyException();
                    }
                case "substraction":
                    String[] subOperands = statement.split("\\-");
                    if (checkOperandsType(subOperands)) {
                        String currency = checkCurrencyType(subOperands[1]);
                        double trimmedSubOperand1 = 0;
                        double trimmedSubOperand2 = 0;
                        if (subOperands.length == 2) {
                            trimmedSubOperand1 = trimNumberFromCurrency(subOperands[0], currency);
                            trimmedSubOperand2 = trimNumberFromCurrency(subOperands[1], currency);
                            trimmedSubOperand2 *= -1; 
                        } else if (subOperands.length == 3) {
                            trimmedSubOperand1 = trimNumberFromCurrency(subOperands[1], currency);
                            trimmedSubOperand1 *= -1;
                            trimmedSubOperand2 = trimNumberFromCurrency(subOperands[2], currency);
                            trimmedSubOperand2 *= -1;
                        }
                        double result = trimmedSubOperand1 + trimmedSubOperand2;
                        return uniteNumberWithCurrency(result, currency);
                    } else {
                        throw new CannotConvertCurrencyException();
                    }
                case "no operation":
                    String currency = checkCurrencyType(statement);
                    if (currency.equals("dollar") || currency.equals("euro")) {
                        return uniteNumberWithCurrency(trimNumberFromCurrency(statement, currency), currency);
                    } else {
                        throw new CannotConvertCurrencyException();
                    }
            }
        }
        return null;
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

    public static boolean checkOperandsType(String[] operands) {
        return checkCurrencyType(operands[0]) == checkCurrencyType(operands[1]);
    }

    public static String checkCurrencyType(String currency) {
        char dollarSign = '$';
        System.out.println(currency);
        if (currency.charAt(0) == dollarSign) {
            return "dollar";
        } else if (currency.substring(currency.length() - 4).equals("euro")) {
            return "euro";
        }
        throw new CannotConvertCurrencyException();
    }

    public static String hasOperations(String expression) {
        int idxMinus = expression.indexOf('-');
        int idxPlus = expression.indexOf('+');
        if (idxPlus != -1) {
            return "sum";
        }
        else if (idxMinus != -1) {
            if (idxMinus == 0 || expression.charAt(idxMinus - 1) == '(' || idxMinus == 1) {
                return "no operation";
            }
            return "substraction";
        }
        return "no operation";
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
