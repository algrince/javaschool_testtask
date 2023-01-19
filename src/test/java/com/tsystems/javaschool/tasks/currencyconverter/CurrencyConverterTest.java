package com.tsystems.javaschool.tasks.currencyconverter;

import org.junit.Assert;
import org.junit.Test;

public class CurrencyConverterTest {
    CurrencyConverter currencyConverter = new CurrencyConverter();

    @Test
    public void convert0() {
        String result = currencyConverter.convert(0.8, "convertToDollar(2euro)");

        // assert
        Assert.assertEquals("$2.5", result);
    }

    @Test
    public void convert1() {
        String result = currencyConverter.convert(0.8, "convertToEuro($2)");

        // assert
        Assert.assertEquals("1.6euro", result);
    }

    @Test
    public void convert2() {
        String result = currencyConverter.convert(0.8, "convertToDollar(convertToEuro($2))");

        // assert
        Assert.assertEquals("$2", result);
    }

    @Test
    public void convert3() {
        String result = currencyConverter.convert(0.8, "convertToDollar(convertToEuro($2)+2euro)");

        // assert
        Assert.assertEquals("$4.5", result);
    }

    @Test
    public void convert4() {
        String result = currencyConverter.convert(0.8, "convertToDollar(convertToEuro($2)-1euro)");

        // assert
        Assert.assertEquals("$0.75", result);
    }

    @Test
    public void convert5() {
        String result = currencyConverter.convert(0.8, "convertToDollar(convertToEuro($10.00)+5euro)");

        // assert
        Assert.assertEquals("$16.25", result);
    }

    @Test(expected = CannotConvertCurrencyException.class)
    public void convert6() {
        String result = currencyConverter.convert(0.8, "convertToDollar($1)");

        // assert(Exception)
    }

    @Test(expected = CannotConvertCurrencyException.class)
    public void convert7() {
        String result = currencyConverter.convert(0.8, "convertToEuro(1euro)");

        // assert(Exception)
    }

    @Test(expected = CannotConvertCurrencyException.class)
    public void convert8() {
        String result = currencyConverter.convert(0.8, "convertToEuro(1)");

        // assert(Exception)
    }

    @Test(expected = CannotConvertCurrencyException.class)
    public void convert9() {
        String result = currencyConverter.convert(0.8, "convertToEuro(euro1)");

        // assert(Exception)
    }

    @Test(expected = CannotConvertCurrencyException.class)
    public void convert10() {
        String result = currencyConverter.convert(0.8, "convertToEuro($1+1euro)");

        // assert(Exception)
    }

    @Test(expected = CannotConvertCurrencyException.class)
    public void convert11() {
        String result = currencyConverter.convert(0.8, "$1+1euro");

        // assert(Exception)
    }

    @Test
    public void convert12() {
        String result = currencyConverter.convert(0.8, "$1+$1");

        // assert
        Assert.assertEquals("$2", result);
    }

    @Test
    public void convert13() {
        String result = currencyConverter.convert(0.8, "$1");

        // assert
        Assert.assertEquals("$1", result);
    }

    @Test
    public void convert14() {
        String result = currencyConverter.convert(0.8, "$1.00");

        // assert
        Assert.assertEquals("$1", result);
    }
}