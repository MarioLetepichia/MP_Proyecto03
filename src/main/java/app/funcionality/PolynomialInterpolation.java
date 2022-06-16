package app.funcionality;

import org.javatuples.Pair;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Clase encargada de generar el polinomio de interpolacion y recuperar el termino independiente del 
 * supuesto polinomio desconocido.
 * @author MarioLetepichia
 * @version 1.0.2
 * @since semestre 2022-2
 */
public class PolynomialInterpolation{
    //cada punto [x, f(x)=y]
    private BigInteger[] independentTerms;
    private BigInteger[] dependentTerms;
    private BigInteger prime = new BigInteger("70686495658400933414639106111057084786528548103624568191646660087445779373471");

    /**
     * Constructor 01 - Recibe una lista de puntos e inicializa la clase
     * @param list Lista de tuplas [x,P(x)]
     */
    public PolynomialInterpolation(ArrayList<Pair<BigInteger, BigInteger>> list) {
        independentTerms = new BigInteger[list.size()];
        dependentTerms = new BigInteger[list.size()];
        for(int i = 0; i < list.size(); i++){
            independentTerms[i] = list.get(i).getValue0();
            dependentTerms[i] = list.get(i).getValue1();
        }
    }

    /**
     * Metodo principal que calcula el polinomio de interpolacion en el valor 0 para 
     * recuperar unicamente el termino independiente
     * @return  Termino independiente
     */
    public BigInteger calculateIndependent(){
        BigInteger result = BigInteger.ZERO;
        BigInteger temp;
        Pair<BigInteger, BigInteger> pair;
        for(int i = 0; i < independentTerms.length; i++){
            pair = polynomialBaseAtZero(independentTerms[i], i);
            System.out.println(pair.getValue0());
            System.out.println(pair.getValue1());
            temp = (dependentTerms[i].multiply(pair.getValue0())).multiply(pair.getValue1().modInverse(prime));
            result = (prime.add(result).add(temp)).mod(prime);
        }
        return result;
    }

    /**
     * Metedo auxiliar de 'calculateIndependent' para calcular el polinomio base de cada punto
     * @param x Punto del cual se calculara su polinomio base
     * @param pos Posicion en el array de dicho punto
     * @return El polinomio base en forma de tupla [numerador, denominador]
     */
    private Pair<BigInteger, BigInteger> polynomialBaseAtZero(BigInteger x, Integer pos){
        BigInteger result = BigInteger.ONE;
        BigInteger denom = BigInteger.ONE;
        for(int i = 0; i < independentTerms.length; i++){
            if(i != pos){
                result = (result.multiply(independentTerms[i].negate())).mod(prime);
                denom = (denom.multiply(independentTerms[pos].subtract(independentTerms[i]))).mod(prime);
            }
        }
        return new Pair<BigInteger,BigInteger>(result, denom);
    }

    /**
     * public static void main(String[] args){
        BigInteger[] arr = {new BigInteger("6"), new BigInteger("8")};
        RandomPolynomial polinomio1 = new RandomPolynomial(arr, new BigInteger("19"));
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
     */
}