package com.tsystems.javaschool.tasks.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        if (statement == null ||
                statement.isEmpty() ||
                statement.matches(".*[+]{2,}.*|.*[-]{2,}.*|.*[*]{2,}.*|.*[/]{2,}.*|.*[.]{2,}.*|.*[^0-9/*\\-+.()].*|[^(]*[)].*|.*[(][^(]*[)]")) {
            return null;
        }


        StringBuilder incomeString = new StringBuilder(statement);

        Pattern p = Pattern.compile("[(].{3,}[)]");
        Matcher m = p.matcher(incomeString);

        while (m.find(0)) {
            int start = m.start();
            int end = m.end();
            String rawResult = evaluate(incomeString.substring(start + 1, end - 1));
            incomeString.replace(start, end, rawResult);
        }

        p = Pattern.compile("[-]?[1-9][0-9]*[.]*[0-9]*([*]|[/])[-]?[1-9][0-9]*[.]*[0-9]*");
        m = p.matcher(incomeString);

        while (m.find(0)) {
            int start = m.start();
            int end = m.end();
            String rawResult = priorityOperation(incomeString.substring(start, end));
            if (rawResult.equals("")) return null;
            incomeString.replace(start, end, rawResult);
        }

        p = Pattern.compile("[-]?[1-9][0-9]*([+]|[-])[-]?[1-9][0-9]*");
        m = p.matcher(incomeString);

        while (m.find(0)) {
            int start = m.start();
            int end = m.end();
            incomeString.replace(start, end, plusMinusOperation(incomeString.substring(start, end)));
        }

        return incomeString.toString();
    }

    private String priorityOperation(String str) {

        if (str.indexOf("*") > 0) {
            double firstValue = Double.parseDouble(str.substring(0, str.indexOf("*")));
            double secondValue = Double.parseDouble(str.substring(str.indexOf("*") + 1));
            if (str.matches("[^.]+")) {
                return String.valueOf((int) firstValue * (int) secondValue);
            }
            else {
                return String.valueOf(firstValue * secondValue);
            }
        }
        else {
            if (str.matches("[^.]+")) {
                int firstValue = Integer.parseInt(str.substring(0, str.indexOf("/")));
                int secondValue = Integer.parseInt(str.substring(str.indexOf("/") + 1));
                if (secondValue == 0) return "";
                if (firstValue % secondValue > 0) return priorityOperation(str + ".0000");
                return String.valueOf(firstValue / secondValue);
            }
            else {
                double firstValue = Double.parseDouble(str.substring(0, str.indexOf("/")));
                double secondValue = Double.parseDouble(str.substring(str.indexOf("/") + 1));
                if (secondValue == 0)
                    return "";
                return String.valueOf(firstValue / secondValue);
            }
        }
    }

    private String plusMinusOperation(String str) {

        if (str.indexOf("+") > 0) {
            double firstValue = Double.parseDouble(str.substring(0, str.indexOf("+")));
            double secondValue = Double.parseDouble(str.substring(str.indexOf("+") + 1));
            if (str.matches("[^.]+")) {
                return String.valueOf((int) firstValue + (int) secondValue);
            }
            else {
                return String.valueOf(firstValue + secondValue);
            }
        }
        else {
            if (str.matches("[^.]+")) {
                int firstValue = Integer.parseInt(str.substring(0, str.indexOf("-")));
                int secondValue = Integer.parseInt(str.substring(str.indexOf("-") + 1));
                return String.valueOf(firstValue - secondValue);
            }
            else {
                double firstValue = Double.parseDouble(str.substring(0, str.indexOf("-")));
                double secondValue = Double.parseDouble(str.substring(str.indexOf("-") + 1));
                return String.valueOf(firstValue - secondValue);
            }
        }
    }

}
