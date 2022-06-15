package app.funcionality;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import org.javatuples.Pair;

public class RandomPolynomial {
    //Contiene el escalar de la n-esima potencia de x (x^n)
    int[] exponents;
    BigInteger independent;

    //Constructores
    //Sin termino independiente
    public RandomPolynomial(int p){
        Random rn = new Random();
        exponents = new int[p];
        independent = BigInteger.ZERO;
        for(int i = 0; i < exponents.length; i++)
            exponents[i] = getRandomCoefficient(rn);
    }

    //Con termino independiente
    public RandomPolynomial(int p, String ind){
        Random rn = new Random();
        exponents = new int[p];
        independent = new BigInteger(ind);
        for(int i = 0; i < exponents.length; i++)
            exponents[i] = getRandomCoefficient(rn);
    }

    //Crear un polinomio especifico
    public RandomPolynomial(int[] p, BigInteger ind){
        exponents = p;
        independent = ind;
    }

    public int[] getExponents(){
        return exponents;
    }

    public int getGrade(){
        return exponents.length;
    }

    @Override
    public String toString(){
        String result = "";
        int grade = exponents.length;
        for(int i = 0; i < exponents.length; i++){
            int scalar = exponents[i];
            if(scalar != 0){
                //El signo se pone antes de la expresion

                //Si result esta vacia y el numero es positivo - Se omite el signo
                //Si result esta vacio, pero es negativo  - se pone menos
                //Si es positivo - pone mas
                //Si es negativo - pone menos
                if(result == "" && scalar > 0)
                    result += " ";
                else if(result == "" && scalar < 0)
                    result += "-";
                else if(scalar > 0)
                    result += " + ";
                else
                    result += " - ";
                result += String.format("%dx^%d", Math.abs(scalar), grade--);
            }
        }
        BigInteger zero = BigInteger.ZERO;
        int comparison = independent.compareTo(zero);
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

    public BigInteger evaluate(int x){
        //Utilizaremos el m'etodo de horner para evaluar los polinomios
        int r = 0;
        for(int i = 0; i < exponents.length; i++){
            r = r*x + exponents[i];
        }
        //Hacemos una 'ultima mult' ya que el termino independiente no se incluye en la iteracion
        r = r*x;
        BigInteger result = new BigInteger(Integer.toString(r));
        result = result.add(independent);
        return result;
    }

    public ArrayList<Pair<BigInteger, BigInteger>> getNPoints(Integer n){
        ArrayList<Pair<BigInteger, BigInteger>> evaluations = new ArrayList<>();
        Random rn = new Random();
        int x;
        BigInteger y;
        for(int i = 0; i < n; i++){
            x = getRandomCoefficient(rn);
            y = evaluate(x);
            evaluations.add(new Pair<BigInteger,BigInteger>(new BigInteger(Integer.toString(x)), y));
        }
        return evaluations;
    }

    /**
     * Metodos por implementar...
     * - Evaluacion en un punto x = i
     */
    //1495628564
    //Metodo auxiliar para generar los escalares aleatorios
    private int getRandomCoefficient(Random rn){
        int number = rn.nextInt(1000);
        if((rn.nextInt(2)) == 1)
            number -= 2*number;
        return number;
    }
}
