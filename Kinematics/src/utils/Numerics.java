package utils;

import kinematics.InverseK;

import java.math.BigDecimal;

public class Numerics {
    static double L[] = {28,50,60};

    /**
     * Funcion que reduce un arreglo de entradas a una cantidad de decimales
     * @param inputs
     * @param limit
     * @return
     */
    public static double[] decimals(double inputs[],int limit){
        double result[] = new double[]{0,0,0};
        for (int i=0;i<inputs.length;i++)
            result[i] = decimals(inputs[i],limit);

        return result;
    }

    /**
     * Reduce los decimales de un elemento al valor establecido
     * @param input
     * @param limit
     * @return
     */
    public static double decimals(double input,int limit){
        return (double)Math.round(input * Math.pow(10,limit)) / Math.pow(10,limit);
    }

    /**
     * Funcion que elimina un arreglo de elemento de tipo double a una cantidad de decimales establecida
     * @param x
     * @param numberofDecimals
     * @return
     */
    public static double[] justNdecimals(double x[],int numberofDecimals){
        double decimals[] = new double[x.length];
        for(int i=0;i<decimals.length;i++){
            decimals[i] = justNdecimals( x[i],numberofDecimals );
        }
        return decimals;
    }

    /**
     * Funcion que elimina los decimales de un elemento a una cantidad establecida
     * @param x
     * @param numberofDecimals
     * @return
     */
    public static double justNdecimals(double x, int numberofDecimals)
    {
        if ( x > 0) {
            x = new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR).doubleValue();
        } else {
            x = new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING).doubleValue();
        }
        x = checkRound(x);
        return x;
    }

    /**
     * Si el decimal es X.999999999 se redondea a X+1.0
     * @param x
     * @return
     */
    public static double checkRound(double x){
        int decimal = (int)Math.abs(x);
        double fractional = x - decimal;
        fractional = 1 - fractional;
        if(fractional<=0.001){
            return Math.round(x);
        }else return x;
    }

    /**
     * Evalua si la coordenada es valida
     * Es decir si pertenece a un espacio de trabajo
     * @param c
     * @return
     */
    public static boolean isValidCoord(double[] c){
        InverseK ik = new InverseK(L);
        double Q[] = ik.getAngles(c);
        if(!Double.isNaN(Q[1]) && !Double.isNaN(Q[2])){
            return true;
        }
        return true;
    }

    /**
     * Redondea un arreglo de decimales a una cantidad de decimales de 5
     * @param angles
     * @return
     */
    public static double[] roundDecimals(double[] angles){
        int decimals=5;
        for(int i=0;i<angles.length;i++){
            angles[i] = justNdecimals(angles[i],decimals);
        }
        return angles;
    }

}
