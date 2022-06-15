package app.funcionality;

import org.javatuples.Pair;


import java.math.BigInteger;
import java.util.ArrayList;

public class PolynomialInterpolation{
    //cada punto [x, f(x)=y]
    private BigInteger[] independentTerms;
    private BigInteger[] dependentTerms;


    public PolynomialInterpolation(ArrayList<Pair<BigInteger, BigInteger>> list) {
        independentTerms = new BigInteger[list.size()];
        dependentTerms = new BigInteger[list.size()];
        for(int i = 0; i < list.size(); i++){
            independentTerms[i] = list.get(i).getValue0();
            dependentTerms[i] = list.get(i).getValue1();
        }
    }

    //Solo es necesario calcularla en el cero
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