package kinematics;

import static java.lang.Math.*;
public class InverseK {

    private double L[];


    public InverseK(double[] long_arm) {
        this.L = long_arm;
    }


    public double[] getAngles(double coord[]) {
        double x= coord[0];
        double y= coord[1];
        double z= coord[2];


        System.out.println("\nX " +x+ " Y "+ y + " Z "+ z);


        double gamma =  atan2(y,x);
        double c3 = (x*x + y*y + z*z  - ( L[0]*L[0] + L[1]*L[1] + L[2]*L[2] ) - 2*L[0]*(z-L[0]))/(2*L[1]*L[2]);
        double s3 = Math.sqrt(1-c3);
        double beta = atan2(s3,c3);
        double alpha =  atan2(( (z-L[0])*(cos(gamma) - sin(gamma)) ),(x-y)) - atan2((s3*L[2]),(c3*L[2]+L[1]));

        double Q[] = new double[3];
        Q[0] = gamma;
        Q[1] = alpha;
        Q[2] = beta;


        /**
         * Q[0] = atan2(coord.getX(),coord.getY());
         * Q[2] = -PI +  acos(( pow(d,2) - pow(L[2],2) - pow(L[1],2) )/(2*L[1]*L[2]));
         * Q[1] =  atan2(r,coord.getZ()-L[0]) - atan2(L[1] + L[2]*cos(Q[2]),L[2]*sin(Q[2]));
         */


        /** Salidas en de angulos en radianes **/
        //System.out.println("Q[0]  "+Q[0] + " Q[1] " +Q[1] + " Q[2] "+Q[2] );

        return Q;
    }
}