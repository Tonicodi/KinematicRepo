package neuralNet;

import utils.Numerics;
import utils.PRECISION;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Input {

    double L[];// longitud del brazo
    PRECISION Precision;
    double Q1start,Q1end,Q2start,Q2end;

    public Input(double[] l,PRECISION precision,boolean isRadian, double q1start, double q1end, double q2start, double q2end) {
        L = l;
        Precision = precision;
        Q1start   = (!isRadian)?q1start*PI/180.0:q1start;
        Q1end     = (!isRadian)?q1end*PI/180.0:q1end;
        Q2start   = (!isRadian)?q2start*PI/180.0:q2start;
        Q2end     = (!isRadian)?q2end*PI/180.0:q2end;
    }

    public ArrayList<double[][]>[] getInputs(){
        //almacenan los minimos y maximos, esto para normalizar
        double Xmin=1000,Xmax=-1000,Ymin=1000,Ymax=-1000,Qmin,Qmax;

        //arreglo de ArraysList
        ArrayList<double[][]> inputList[] = new ArrayList[2];

        inputList[0] = new ArrayList<>();
        inputList[1] = new ArrayList<>();

        //obtener datos para entradas en EQ
        Qmin = Collections.min(Arrays.asList(new Double[]{Q1start, Q1end, Q2start, Q2end}));
        Qmax = Collections.max(Arrays.asList(new Double[]{Q1start, Q1end, Q2start, Q2end}));

        //valores que almacenan las evaluaciones
        double x,y;
        int c=0;

        for(double i=Qmin;i<=Qmax;i+= Precision.getValue()){
            x =  L[2] * cos(i) + L[1] * cos(i) ;
            y =  L[2] * sin(i) + L[1] * sin(i)  + L[0];

            Xmin = (x<Xmin)?x:Xmin;
            Xmax = (x>Xmax)?x:Xmax;
            Ymin = (y<Ymin)?y:Ymin;
            Ymax = (y>Ymax)?y:Ymax;

            inputList[0].add(new double[][]{ {x},{i} });
            inputList[1].add(new double[][]{ {y},{i} });
            System.out.println("value "+c+" X "+x+" Y "+y+" Q degree : "+Math.toDegrees(i)+" Q radian "+i);
            c++;
        }
        //guardar el minimo y maximo global en la primera posicion del arreglo de entradas

        inputList[0].add(0,new double[][]{ {Xmax,Xmin} , {Qmax,Qmin} });
        inputList[1].add(0,new double[][]{ {Ymax,Ymin} , {Qmax,Qmin} });

        System.out.println("Xmax "+ Xmax+" Xmin "+Xmin);
        System.out.println("Ymax "+ Ymax+" Ymin "+Ymin);
        System.out.println("Global Q Max "+Qmax+" Global Q Min "+Qmin);

        return inputList;
    }

    public ArrayList<double[][]> normalizeInputs(ArrayList<double[][]> inputs,double new_min,double new_max){

        ArrayList<double[][]> normalizedInputs = new ArrayList<>();

        //obtener los minimos y maximos
        double Vmin,Vmax,Qmin,Qmax;
        Vmax  = inputs.get(0)[0][0];
        Vmin  = inputs.get(0)[0][1];

        Qmax  = inputs.get(0)[1][0];
        Qmin  = inputs.get(0)[1][1];

        //limpiar de las entradas
        inputs.remove(0);

        double Vnormalized,QNormalized;

        for(int i=0;i<inputs.size();i++){
            Vnormalized  =  Normalize( inputs.get(i)[0][0] , Vmin,Vmax ,new_min,new_max);

            //QNormalized =  Normalize( inputs.get(i)[1][0] , Qmin,Qmax,new_min,new_max);

            QNormalized = inputs.get(i)[1][0];

            normalizedInputs.add( new double[][]{ {Vnormalized} , {QNormalized} });

            System.out.println("Value "+i+" normalized  "+Vnormalized + " Q normalized "+QNormalized);
        }
        return normalizedInputs;
    }

    public static boolean saveInputs(ArrayList<double[][]> inputs,String path){
        //escribir en el archivo .dat ubicado en / del proyecto
        try {
            PrintWriter f_input = new PrintWriter(new FileWriter("input_"+path));
            PrintWriter f_output = new PrintWriter(new FileWriter("output_"+path));

            for(int i = 0; i < inputs.size(); ++i) {

                f_input.println( inputs.get(i)[0][0]);
                f_output.println(inputs.get(i)[1][0]);
            }
            f_input.close();
            f_output.close();
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



    public static double Normalize(double value, double min, double max,double new_min,double new_max)
    {
        return (value - min) / (max - min)*(new_max-new_min)+new_min;
    }

    public static double[] Normalize(double values[],double min,double max,double new_min,double new_max){
        double data[] = new double[values.length];
        for (int i=0;i<values.length;i++)
            data[i] = Normalize(values[i],min,max,new_min,new_max);
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
