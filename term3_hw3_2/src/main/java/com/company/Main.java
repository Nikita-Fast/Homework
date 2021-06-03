package com.company;import com.company.algorithms.BigIntAddition;import com.company.algorithms.BigIntAdditionSingleThreaded;import java.util.Arrays;public class Main {    public static void main(String[] args) {        String a ="978637849047655";        String b ="24666478904876";        BigIntAddition bigIntAddition = new BigIntAddition();        bigIntAddition.calculate(4, a, b);        int[] resultSingle = BigIntAdditionSingleThreaded.calculate(a, b);        //System.out.println(Arrays.toString(bigIntAddition.getResult()) + " <-- multiproc");        //System.out.println(Arrays.toString(resultSingle) + " <-- single");        System.out.println(Arrays.equals(bigIntAddition.getResult(), resultSingle));    }}