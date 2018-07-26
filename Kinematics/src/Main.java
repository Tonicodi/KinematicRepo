import kinematics.ForwardK;
import kinematics.InverseK;

import java.util.Arrays;

public class Main{


    public static void main(String[] args){

        double angulos[];
        double[] coord_cartesian = new double[3];
        double L[]  = new double[]{28,50,60};
        //double Q[]  = new double[]{0.12,-0.57,1.220};
        double cartesiano[];
        ForwardK fk = new ForwardK(L);
        InverseK ik = new InverseK(L);

        coord_cartesian[0] = 50;
        coord_cartesian[1] = 40;
        coord_cartesian[2] = 40;
        angulos = ik.getAngles(coord_cartesian);



        System.out.println("angulos   degrees Q[0]  "+Math.toDegrees(angulos[0]) + " Q[1] " + Math.toDegrees(angulos[1]) + " Q[2] "+Math.toDegrees(angulos[2]));

        cartesiano   = fk.getCartesian(angulos,true);

        System.out.println("fk  Px  "+ Arrays.toString(cartesiano));

        System.out.println("angulos  Q[0]  "+angulos[0] + " Q[1] " +angulos[1] + " Q[2] "+angulos[2]);
        //cartesiano = fk.getCartesian(Q);

        //System.out.println("test  Px  "+ Arrays.toString(cartesiano));


    }
}