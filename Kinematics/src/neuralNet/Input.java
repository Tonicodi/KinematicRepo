package neuralNet;

import utils.Numerics;
import utils.PRECISION;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

    public ArrayList<double[][]> getInputs(){
        double Xmin=1000,Xmax=-1000,Ymin=1000,Ymax=-1000;
        double GlobalInputMin,GlobalInputMax,GlobalOutputMin,GlobalOutputMax;
        ArrayList<double[][]> inputList = new ArrayList<>();
        double x=0,y=0;
        for(double i=Q1start;i<=Q1end;i+= Precision.getValue()){
            for(double j=Q2start;j<=Q2end;j+= Precision.getValue()){

                x =  L[2] * cos(i + j) + L[1] * cos(i) ;
                y =  L[2] * sin(i + j) + L[1] * sin(i)  + L[0];

                Xmin = (x<Xmin)?x:Xmin;
                Xmax = (x>Xmax)?x:Xmax;
                Ymin = (y<Ymin)?y:Ymin;
                Ymax = (y>Ymax)?y:Ymax;

                inputList.add(new double[][]{ {x,y},{i,j} });
                System.out.println("Q1 "+Math.toDegrees(i)+" Q2 "+Math.toDegrees(j)+" X "+x+" Y "+y);
            }
            System.out.println();
        }
        //guardar el minimo y maximo global en la primera posicion del arreglo de entradas
        GlobalInputMin = (Ymin<Xmin)?Ymin:Xmin;
        GlobalInputMax = (Ymax>Xmax)?Ymax:Xmax;

        GlobalOutputMin = Collections.min(Arrays.asList(new Double[]{Q1start, Q1end, Q2start, Q2end}));
        GlobalOutputMax = Collections.max(Arrays.asList(new Double[]{Q1start, Q1end, Q2start, Q2end}));

        inputList.add(0,new double[][]{ {GlobalInputMax,GlobalInputMin} , {GlobalOutputMax,GlobalOutputMin} });

        System.out.println("Xmax "+ Xmax+" Xmin "+Xmin);
        System.out.println("Ymax "+ Ymax+" Ymin "+Ymin);
        System.out.println("Global input Max "+GlobalInputMax+" Global input Min "+GlobalInputMin);
        System.out.println("Global output Max "+GlobalOutputMax+" Global output Min "+GlobalOutputMin);

        return inputList;
    }

    public ArrayList<double[][]> normalizeInputs(ArrayList<double[][]> inputs){

        ArrayList<double[][]> normalizedInputs = new ArrayList<>();

        //obtener los minimos y maximos
        double GlobalInputMin,GlobalInputMax,GlobalOutputMin,GlobalOutputMax;
        GlobalInputMax  = inputs.get(0)[0][0];
        GlobalInputMin  = inputs.get(0)[0][1];

        GlobalOutputMax = inputs.get(0)[1][0];
        GlobalOutputMin = inputs.get(0)[1][1];
        //limpiar de las entradas
        inputs.remove(0);

        double Xnormalized,Ynormalized,Q1Normalized,Q2Normalized;

        for(int i=0;i<inputs.size();i++){
            Xnormalized  = Numerics.decimals( Normalize( inputs.get(i)[0][0] , GlobalInputMin,GlobalInputMax ) , 6);
            Ynormalized  = Numerics.decimals( Normalize( inputs.get(i)[0][1] , GlobalInputMin,GlobalInputMax ) , 6);

            Q1Normalized = Numerics.decimals( Normalize( inputs.get(i)[1][1] , GlobalOutputMin,GlobalOutputMax ), 6);
            Q2Normalized = Numerics.decimals( Normalize( inputs.get(i)[1][1] , GlobalOutputMin,GlobalOutputMax ), 6);

            normalizedInputs.add( new double[][]{ {Xnormalized,Ynormalized} , {Q1Normalized,Q2Normalized} });

            System.out.println("X normalized  "+Xnormalized+" Y normalized "+Ynormalized);
            System.out.println("Q1 normalized "+Q1Normalized+" Q2 "+Q2Normalized);
            System.out.println();
        }
        return normalizedInputs;
    }

    public boolean saveInputs(ArrayList<double[][]> inputs,String path){
        //escribir en el archivo .dat ubicado en / del proyecto
        try {
            PrintWriter fout = new PrintWriter(new FileWriter(path));

            for(int i = 0; i < inputs.size(); ++i) {

                fout.println( inputs.get(i)[0][0] + ";" + inputs.get(i)[0][1] + ";" +
                        inputs.get(i)[1][0]+ ";" +
                        inputs.get(i)[1][1]);
            }
            fout.close();
            return true;
        } catch (IOException var16) {
            var16.printStackTrace();
            return false;
        }
    }

    static double Normalize(double value, double min, double max)
    {
        return (value - min) / (max - min);
    }

    static double Inverse(double value, double min, double max)
    {
        return value * (max - min) + min;
    }



}
