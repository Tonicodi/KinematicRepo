package utils;

public class Numerics {


    public static double[] decimals(double inputs[],int limit){
        double result[] = new double[]{0,0,0};
        for (int i=0;i<inputs.length;i++)
            result[i] = decimals(inputs[i],limit);

        return result;
    }

    public static double decimals(double input,int limit){
        return (double)Math.round(input * Math.pow(10,limit)) / Math.pow(10,limit);
    }
}
