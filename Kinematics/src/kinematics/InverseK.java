package kinematics;

import utils.Numerics;

import static java.lang.Math.*;
public class InverseK {

    private double L[];


    /**
     * Constructor de cinematica directa
     * Recibe las dimensiones del brazo
     * @param long_arm
     */
    public InverseK(double[] long_arm) {
        this.L = long_arm;
    }

    /**
     * Recibe la coordenada XYZ
     * Encuentra los angulos para cada una de las articulaciones
     * @param coord
     * @return
     */
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

        return Q;
    }
}