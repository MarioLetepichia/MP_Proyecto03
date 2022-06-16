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
            temp = dependentTerms[i].multiply(pair.getValue0());
            result = result.add(temp.divide(pair.getValue1()));
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
                result = result.multiply(independentTerms[i].negate());
                denom = denom.multiply(independentTerms[pos].add(independentTerms[i].negate()));
            }
        }
        return new Pair<BigInteger,BigInteger>(result, denom);
    }
}