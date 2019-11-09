package ru.unn.agile.QuadraticEquasion.model;

public final class Formatter {

    private Formatter() { }

    private static Integer getTwoDigitsAfterPoint(final double value) {
        final int hundred = 100;
        double val = value * hundred;
        return (int) val % hundred;
    }

    public static String formatPositiveDouble(final double value) {
        if (value < 0) {
            throw new IllegalArgumentException();
        }

        Integer i = (int) value;
        StringBuilder formattedString = new StringBuilder();
        formattedString.append(i.toString());

        final int ten = 10;

        i = getTwoDigitsAfterPoint(value);
        formattedString.append(".");
        if (i == 0) {
            formattedString.append("0");
        } else if (i < ten) {
            formattedString.append("0");
            formattedString.append(i.toString());
        } else {
            if (i % ten == 0) {
                i /= ten;
            }
            formattedString.append(i.toString());
        }

        return formattedString.toString();
    }

    public static String getFormatted(final QuadraticEquasion qe) {
        StringBuilder formattedString = new StringBuilder();
        String a = formatPositiveDouble(Math.abs(qe.getACoeff()));
        String b = formatPositiveDouble(Math.abs(qe.getBCoeff()));
        String c = formatPositiveDouble(Math.abs(qe.getCCoeff()));
        formattedString.append((qe.getACoeff() < 0) && (Math.abs(qe.getACoeff()) != 0) ? "- " : "")
            .append((Math.abs(qe.getACoeff()) != 1) && (Math.abs(qe.getACoeff()) != 0) ? a : "")
            .append(Math.abs(qe.getACoeff()) != 0 ? "(x^2)" : "")
            .append(qe.getBCoeff() < 0  ? " - " : (Math.abs(qe.getBCoeff()) != 0)
                    && (formattedString.length() > 0) ? " + " : "")
            .append((Math.abs(qe.getBCoeff()) != 1)  && (Math.abs(qe.getBCoeff()) != 0) ? b : "")
            .append(Math.abs(qe.getBCoeff()) != 0 ? "x" : "")
            .append(qe.getCCoeff() < 0 ? " - " : (Math.abs(qe.getCCoeff()) != 0)
                    && (formattedString.length() > 0) ? " + " : "")
            .append(Math.abs(qe.getCCoeff()) != 0 ? c : "");
        return formattedString.toString();
    }

}
