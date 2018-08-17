package NeuralNet;

import Utils.PRECISION;

import java.util.ArrayList;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Input {

    double L[];// longitud del brazo
    PRECISION Precision;
    double Q1start,Q1end,Q2start,Q2end;
    ArrayList<Double[]> inputs;
    public Input(double[] l,PRECISION precision,boolean isRadian, double q1start, double q1end, double q2start, double q2end) {
        L = l;
        Precision = precision;
        Q1start   = (!isRadian)?q1start*PI/180.0:q1start;
        Q1end     = (!isRadian)?q1end*PI/180.0:q1end;
        Q2start   = (!isRadian)?q2start*PI/180.0:q2start;
        Q1end     = (!isRadian)?q2end*PI/180.0:q2end;

    }

    public ArrayList<double[]> getInputs(){

        ArrayList<double[]> inputList = new ArrayList<>();
        double x=0,y=0;
        for(double i=Q1start;i<=Q1end;i+=Precision.getValue()){
            for(double j=Q2start;j<=Q2end;j+=Precision.getValue()){

                x =  L[2] * cos(i + j) + L[1] * cos(i) ;
                y =  L[2] * sin(i + j) + L[1] * sin(i)  + L[0];

                inputList.add(new double[]{x,y});
                System.out.println("Q1 "+Math.toDegrees(i)+" Q2 "+Math.toDegrees(j)+"X "+x+" Y "+y);
            }
            System.out.println();
        }


        return null;

    }


}
