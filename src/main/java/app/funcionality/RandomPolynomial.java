package app.funcionality;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import org.javatuples.Pair;

/**
 * Clase generadora de polinomios psudoaleatorios
 * @author MarioLetepichia
 * @version 1.0.2
 * @since semestre 2022-2
 */
public class RandomPolynomial {
    //Contiene el escalar de la n-esima potencia de x (x^n)
    BigInteger[] exponents;
    BigInteger independent;
    BigInteger prime = new BigInteger("70686495658400933414639106111057084786528548103624568191646660087445779373471");

    /**
     * Constructor 01 - Genera un polinomio aleatorio sin termino independiente
     * @param p Grado del polinomio
     */
    public RandomPolynomial(int p){
        Random rn = new Random();
        exponents = new BigInteger[p];
        independent = BigInteger.ZERO;
        for(int i = 0; i < exponents.length; i++)
            exponents[i] = getRandomCoefficient(rn);
    }

    /**
     * Constructor 02 - Genera un polinomio aleatorio
     * @param p Grado del polinomio
     * @param ind  Valor que tendra el termino independiente
     */
    public RandomPolynomial(int p, String ind){
        Random rn = new Random();
        exponents = new BigInteger[p];
        independent = (new BigInteger(ind)).mod(prime);
        for(int i = 0; i < exponents.length; i++)
            exponents[i] = getRandomCoefficient(rn);
    }

    /**
     * Constructor 03 - Crea un polinomio especifico
     * @param p Arreglo de enteros que contiene las potencias del polinomio
     * @param ind Valor que tendra el termino independiente
     */
    public RandomPolynomial(BigInteger[] p, BigInteger ind){
        exponents = p;
        independent = ind;
    }

    /**
     * @return Arreglo que contiene los exponentes del polinomio
     */
    public BigInteger[] getExponents(){
        return exponents;
    }

    /**
     * @return Grado del polinomio
     */
    public int getGrade(){
        return exponents.length;
    }

    /**
     * Procesa el polinomio para mostrar su representacion escrita
     */
    @Override
    public String toString(){
        String result = "";
        int grade = exponents.length;
        for(int i = 0; i < exponents.length; i++){
            BigInteger scalar = exponents[i];
            if(scalar != BigInteger.ZERO){
                //El signo se pone antes de la expresion

                //Si result esta vacia y el numero es positivo - Se omite el signo
                //Si result esta vacio, pero es negativo  - se pone menos
                //Si es positivo - pone mas
                //Si es negativo - pone menos
                if(result == "" && scalar.compareTo(BigInteger.ZERO) > 0)
                    result += " ";
                else if(result == "" && scalar.compareTo(BigInteger.ZERO) < 0)
                    result += "-";
                else if(scalar.compareTo(BigInteger.ZERO) > 0)
                    result += " + ";
                else
                    result += " - ";
                result += String.format("%sx^%d", scalar.toString(), grade--);
            }
        }
        int comparison = independent.compareTo(BigInteger.ZERO);
        if(comparison != 0){
            //Si independent es zero
            if(comparison < 0)
                result += " - ";
            else 
                result += " + ";
            result += independent.abs().toString();
        }
        return result;
    }

    /**
     * Evalua el polinomio en un punto especifico
     * @param x Punto a evaluar
     * @return Evaluacion del polinomio en x 
     */
    public BigInteger evaluate(BigInteger x){
        //Utilizaremos el m'etodo de horner para evaluar los polinomios
        BigInteger result = BigInteger.ZERO;
        for(int i = 0; i < exponents.length; i++){
            result = result.multiply(x);
            result = result.add(exponents[i]);
        }
        //Hacemos una 'ultima mult' ya que el termino independiente no se incluye en la iteracion
        result = result.multiply(x);
        result = result.add(independent);
        return result.mod(prime);
    }

    /**
     * Genera una lista de tuplas, cada tupla contiene en [0] su valor x y en [1] su
     * evaluacion del polinomio. Cada entrada es generada aleatoriamente.
     * @param n Numero de tuplas a generar
     * @return  Lista de tuplas generadas
     */
    public ArrayList<Pair<BigInteger, BigInteger>> getNPoints(Integer n){
        ArrayList<Pair<BigInteger, BigInteger>> evaluations = new ArrayList<>();
        Random rn = new Random();
        BigInteger x;
        BigInteger y;
        for(int i = 0; i < n; i++){
            x = getRandomCoefficient(rn);
            y = evaluate(x);
            evaluations.add(new Pair<BigInteger,BigInteger>(x, y));
        }
        return evaluations;
    }

    /**
     * Metodo auxiliar para generar un coeficiente aleatorio; el rango va de -1495628564 a 1495628564.
     * @param rn Objeto para generar los randoms
     * @return Coeficiente aleatorio dentro del rango especificado
     */
    private BigInteger getRandomCoefficient(Random rn){
        BigInteger r;
            while(true){
                r = new BigInteger(prime.bitLength(), rn);
                if(r.compareTo(BigInteger.ZERO) > 0 && r.compareTo(prime) < 0)
                    break;
            }
        return r;
    }
}
