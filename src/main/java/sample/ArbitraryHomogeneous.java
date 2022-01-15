package sample;

import src.HomogeneousLinearRecurrenceRelation;
import src.LinearAlgebraSolver;

import java.util.Scanner;

public class ArbitraryHomogeneous {
    private static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        final var order = in.nextInt();
        final var coefficients = nextDoubleArray(order);
        final var initialValues = nextDoubleArray(order);
        final HomogeneousLinearRecurrenceRelation solver = new LinearAlgebraSolver(coefficients, initialValues);
        final var generalTermFormula = solver.getGeneralTermFormula();
        for (int i = 0; i < 10; i++) {
            System.out.println((long)generalTermFormula.value(i));
        }
    }
    private static double[] nextDoubleArray(int length){
        double[] array = new double[length];
        for (int i = 0; i < length; i++) {
            array[i] = in.nextInt();
        }
        return array;
    }
}
