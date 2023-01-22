package com.tsystems.javaschool.tasks.spreadsheet;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Ignore;

import java.util.Arrays;
import java.util.List;

@Ignore
public class SpreadsheetTest {

    Spreadsheet spreadsheet = new Spreadsheet();

    @Test
    public void process0() {
        String[] inputData = {"10 =A1 7 'Java"};

        List<String> result = spreadsheet.process(Arrays.asList(inputData));

        Assert.assertEquals("10 10 7 Java", result.get(0));
    }

    @Test
    public void process1() {
        String[] inputData = {"10 =A3 7 'Java"};

        List<String> result = spreadsheet.process(Arrays.asList(inputData));

        Assert.assertEquals("10 7 7 Java", result.get(0));
    }

    @Test
    public void process2() {
        String[] inputData = {"1"};

        List<String> result = spreadsheet.process(Arrays.asList(inputData));

        Assert.assertEquals("1", result.get(0));
    }

    @Test
    public void process3() {
        String[] inputData = {"1 2"};

        List<String> result = spreadsheet.process(Arrays.asList(inputData));

        Assert.assertEquals("1 2", result.get(0));
    }

    @Test
    public void process4() {
        String[] inputData = {"=A2 2"};

        List<String> result = spreadsheet.process(Arrays.asList(inputData));

        Assert.assertEquals("2 2", result.get(0));
    }

    @Test
    public void process5() {
        String[] inputData = {"2 2 =A1+A2"};

        List<String> result = spreadsheet.process(Arrays.asList(inputData));

        Assert.assertEquals("2 2 4", result.get(0));
    }

    @Test
    public void process6() {
        String[] inputData = {"2 2 =A1-A2"};

        List<String> result = spreadsheet.process(Arrays.asList(inputData));

        Assert.assertEquals("2 2 0", result.get(0));
    }

    @Test
    public void process7() {
        String[] inputData = {"2 3 =A1*A2"};

        List<String> result = spreadsheet.process(Arrays.asList(inputData));

        Assert.assertEquals("2 3 6", result.get(0));
    }

    @Test
    public void process8() {
        String[] inputData = {"3 3 =A1/A2"};

        List<String> result = spreadsheet.process(Arrays.asList(inputData));

        Assert.assertEquals("3 3 1", result.get(0));
    }

    @Test
    public void process9() {
        String[] inputData = {"1 3 5", "=B2 =A1+A2 =B1"};

        List<String> result = spreadsheet.process(Arrays.asList(inputData));

        Assert.assertEquals("1 3 5", result.get(0));
        Assert.assertEquals("4 4 4", result.get(1));
    }

    @Test
    public void process10() {
        String[] inputData = {"1 3 5", "=B2+1 =A1+A2 =B1+3"};

        List<String> result = spreadsheet.process(Arrays.asList(inputData));

        Assert.assertEquals("1 3 5", result.get(0));
        Assert.assertEquals("5 4 8", result.get(1));
    }

    @Test
    public void process11() {
        String[] inputData = {""};

        List<String> result = spreadsheet.process(Arrays.asList(inputData));

        Assert.assertEquals("", result.get(0));
    }

    @Test
    public void process12() {
        String[] inputData = {"1 2 3", "4 5 'Java"};

        List<String> result = spreadsheet.process(Arrays.asList(inputData));

        Assert.assertEquals("1 2 3", result.get(0));
        Assert.assertEquals("4 5 Java", result.get(1));
    }
}