import app.funcionality.PolynomialInterpolation;
import app.funcionality.RandomPolynomial;

import java.math.BigInteger;
import java.util.ArrayList;

import org.javatuples.Pair;

/**
 * Clase principal del programa
 */

public class Main{
    public static void main(String[] args) {
        RandomPolynomial polinomio1 = new RandomPolynomial(2, "115");
        //int[] arr = {9, -2};
        //RandomPolynomial polinomio1 = new RandomPolynomial(arr, new BigInteger("115"));
        System.out.println(polinomio1.toString());
        System.out.println(String.format("Grado del polinomio: %d", polinomio1.getGrade()));
        System.out.println("Evaluamos 4 puntos distintos...");
        ArrayList<Pair<BigInteger, BigInteger>> evaluaciones = polinomio1.getNPoints(4);
        for (Pair<BigInteger,BigInteger> pair : evaluaciones) {
            System.out.println(String.format("( %s , %s )", 
                pair.getValue0().toString(), 
                pair.getValue1().toString()));
        }

        //Ahora utilizamos 4 de estos puntos para aplicar polinomio de interpolacion
        evaluaciones.remove(0);
        PolynomialInterpolation polinomioBase = new PolynomialInterpolation(evaluaciones);
        System.out.println(String.format("\n\n\n\nUsando Polinomio de redireccionamiento sabemos que el termino independiente del polinomio es: %s",
            polinomioBase.calculateIndependent().toString()));
    
        }


    /**
     * DUDAS
     * Porque no compila cuando lo corro desde terminal
     * El metodo de horner contempla negativos?
     */
}