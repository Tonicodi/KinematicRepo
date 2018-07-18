
import static java.lang.Math.*;
public class InverseK {

    private double Long_arm[];


    public InverseK(double[] long_arm) {
        Long_arm = long_arm;
    }


    public double[] getAngles(double coord[]) {

        double Q[] = new double[3];


        System.out.println("\nX " + coord[0]+ " Y "+ coord[1] + " Z "+ coord[2]);

        double r = Math.sqrt(Math.pow(coord[0],2)+Math.pow(coord[1],2));



        double gamma =  atan2(coord[1], coord[0]);
        double beta =  acos((Math.pow(coord[2]-Long_arm[0],2) + Math.pow(coord[0],2) +  Math.pow(coord[1],2) - Math.pow(Long_arm[2],2) - Math.pow(Long_arm[1],2) )
                / 2*Long_arm[1]*Long_arm[2] );

        double alpha =  (atan2(r,coord[2]-Long_arm[0]) - atan2(Long_arm[1] + Long_arm[2]* cos(beta),Long_arm[2]*sin(beta)));


        Q[0] = gamma;
        Q[1] = alpha;
        Q[2] = beta;

        /** Salidas en de angulos en radianes **/
        System.out.println("Q[0]  "+Q[0] + " Q[1] " +Q[1] + " Q[2] "+Q[2] );

        return Q;
    }
}