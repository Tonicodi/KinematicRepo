
import static java.lang.Math.*;
public class InverseK {

    private double Long_arm[];


    public InverseK(double[] long_arm) {
        Long_arm = long_arm;
    }


    public double[] getAngles(float coord[]) {

        double Q[] = new double[3];


        System.out.println("\nX " + coord[0]+ " Y "+ coord[1] + " Z "+ coord[2]);

        float r =(float) Math.sqrt(Math.pow(coord[0],2)+Math.pow(coord[1],2));
       // float D = (float) Math.sqrt(Math.pow(coord[3]-Long_arm[0],2) + Math.pow(r,2));



        float gamma = (float) atan2(coord[1], coord[0]);
        float beta = (float) acos((Math.pow(coord[2]-Long_arm[0],2) + Math.pow(coord[0],2) +  Math.pow(coord[1],2) - Math.pow(Long_arm[2],2) - Math.pow(Long_arm[1],2) )
                / 2*Long_arm[1]*Long_arm[2] );

        float alpha = (float) (atan2(r,coord[2]-Long_arm[0]) - atan2(Long_arm[1] + Long_arm[2]* cos(beta),Long_arm[2]*sin(beta)));


        Q[0] = gamma;
        Q[1] = alpha;
        Q[2] = beta;

        /** Salidas en de angulos en radianes **/
        System.out.println("Q[0]  "+Q[0] + " Q[1] " +Q[1] + " Q[2] "+Q[2] );

        return Q;
    }
}