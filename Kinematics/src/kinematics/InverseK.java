package kinematics;

import utils.Numerics;

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








        double gamma =  Math.atan2(x,y);


        double h= Math.sqrt(x*x + y*y);

        double c_2 = h*h + Math.pow( (z - L[0]),2);

        double q3 = -1*Math.acos(  (  c_2 - L[1]*L[1] - L[2]*L[2] )/(2*L[1]*L[2])  );

        double q2_gamma = atan2( (z-L[0]),h );
        double q2_alfa  = -1*acos( ( L[1]*L[1] + c_2 - L[2]*L[2] )/( 2*L[1]* Math.sqrt(c_2) ) );


   //     double c2 = (x*x + y*y + z*z  - ( L[0]*L[0] + L[1]*L[1] + L[2]*L[2] ) - 2*L[0]*(z-L[0]))/(2*L[1]*L[2]);
  //      double s2 = ( Math.sqrt(1-c2) );
 //       double beta = atan2(s2,c2);
//        double alpha =  atan2(( (z-L[0])*(cos(gamma) - sin(gamma)) ),(x-y)) - atan2((s2*L[2]),(c2*L[2]+L[1]));

        double Q[] = new double[3];
        Q[0] = gamma;
        Q[1] = q2_gamma - q2_alfa;
        Q[2] = q3;


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