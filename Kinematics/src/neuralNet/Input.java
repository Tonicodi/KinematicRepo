package neuralNet;

import kinematics.ForwardK;
import utils.Numerics;
import utils.PRECISION;

import java.io.*;
import java.util.*;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Input implements Serializable {

    double L[];// longitud del brazo
    PRECISION Precision;
    double Q1start,Q1end,Q2start,Q2end;

    public Input(double[] l,PRECISION precision, double q1start, double q1end, double q2start, double q2end) {
        L = l;
        Precision = precision;
        Q1start   = q1start;
        Q1end     = q1end;
        Q2start   = q2start;
        Q2end     = q2end;

        util.Numerics.PRECISION = precision.getValue();
    }

    public ArrayList<double[]>[] getInputs(){
        /**almacenan los minimos y maximos, esto para normalizar **/
        double Ymin= 0 ,Ymax= 110,Zmin= -32,Zmax= 138;

        double Q1min= (Q1start<Q1end)?Q1start:Q1end;
        double Q1max= (Q1start>Q1end)?Q1start:Q1end;

        double Q2min= (Q2start<Q2end)?Q2start:Q2end;
        double Q2max= (Q2start>Q2end)?Q2start:Q2end;

        double n = Precision.getValue();
        double step1 = (Q1max-Q1min)/(n-1);
        double step2 = (Q2max-Q2min)/(n-1);

        ForwardK fk = new ForwardK(L);

        ArrayList<double[]>[] IO_data = new ArrayList[2];

        IO_data[0] = new ArrayList<>();
        IO_data[1] = new ArrayList<>();

        int decimals = 4;
        int c=0;
        for(double i=Q1min;i<Q1max;i+=step1){
            for(double j=Q2min;j<Q2max;j+=step2){

                System.out.println("Iteracion "+ (c+1));

                double angles[] = {0, Numerics.justNdecimals( i , decimals),  Numerics.justNdecimals( j ,decimals) };

                double angle_normalized[] = {  Numerics.justNdecimals( Input.Normalize( angles[1] , Q1min,Q1max) , decimals+2)  , Numerics.justNdecimals(  Input.Normalize( angles[2] , Q2min,Q2max) ,decimals+2)   };

                IO_data[1].add( angle_normalized);// agregando la salida (angulos normalizados)

                System.out.println("Angulo         " + Arrays.toString(angles) +"\t Normalizado "+Arrays.toString(angle_normalized) );

                double coord[] = fk.getCartesian(angles,false);

                coord[1] =  Numerics.justNdecimals(coord[1],decimals);
                coord[2] =  Numerics.justNdecimals(coord[2],decimals);

                double coord_normalized[] = {  Numerics.justNdecimals( Input.Normalize( coord[1] , Ymin,Ymax) , decimals+2)  , Numerics.justNdecimals(  Input.Normalize( coord[2] , Zmin,Zmax) ,decimals+2)   };
                IO_data[0].add( coord_normalized); // coordenada Y Z

                System.out.println("Fk Coordenadas "+Arrays.toString(coord)+"\t Normalizado "+ Arrays.toString(coord_normalized));

                System.out.println();
                c++;
            }
        }

        System.out.println("Ymax "+ Ymax+" Ymin "+Ymin);
        System.out.println("Zmax "+ Zmax+" Zmin "+Zmin);

        System.out.println("Q1max "+ Q1max+" Q1min "+Q1min);
        System.out.println("Q2max "+ Q2max+" Q2min "+Q2min);

        return IO_data;
    }

    public static boolean saveFile(ArrayList<double[]> inputs,String path){
        //escribir en el archivo .dat ubicado en / del proyecto
        try {
            PrintWriter fileData = new PrintWriter(new FileWriter(path));
            for(int i = 0; i < inputs.size(); ++i) {
                fileData.println( inputs.get(i)[0]+","+inputs.get(i)[1] );
            }
            fileData.close();
            return true;
        } catch (IOException var16) {
            var16.printStackTrace();
            return false;
        }
    }

    public static ArrayList<double[]> loadFile(String path){
        //escribir en el archivo .dat ubicado en / del proyecto

        ArrayList<double[]> inputs = new ArrayList<>();
        try {
            Scanner s = new Scanner(new File(path));
            while (s.hasNext()){
                double c1 = s.nextDouble();
                double c2 = 0;
                if(s.hasNext()) c2 = s.nextDouble();
                System.out.println("c1 "+c1 +" c2 "+c2 );
                inputs.add(new double[]{ c1,c2});
            }
            s.close();
            return inputs;
        } catch (Exception var16) {
            var16.printStackTrace();
            return inputs;
        }
    }



    public static double Normalize(double value, double min, double max)
    {
        return (value - min) / (max - min);
    }

    public static double[] Normalize(double values[],double min,double max){
        double data[] = new double[values.length];
        for (int i=0;i<values.length;i++)
            data[i] = Normalize(values[i],min,max);
        return data;
    }

    public static double DeNormalize(double value, double min, double max)
    {
        return value * (max - min) + min;
    }

    public static double[] DeNormalize(double values[],double min,double max){
        double data[] = new double[values.length];
        for (int i=0;i<values.length;i++)
            data[i] = DeNormalize(values[i],min,max);
        return data;
    }

}
