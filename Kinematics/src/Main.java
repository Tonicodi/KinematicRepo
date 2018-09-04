import kinematics.ForwardK;
import kinematics.InverseK;

import java.util.Arrays;

public class Main{


    public static void main(String[] args){

        double angulos[] = new double[3];
        double[] coord_cartesian = new double[3];
        double L[]  = new double[]{28,50,60};
        //double Q[]  = new double[]{0.12,-0.57,1.220};
        double cartesiano[];
        ForwardK fk = new ForwardK(L);
        InverseK ik = new InverseK(L);


        angulos[0]= 0;
        angulos[1]= 0;
        angulos[2]= 0;


        System.out.println("Angulos     or : "+Arrays.toString(angulos));
        coord_cartesian = fk.getCartesian(angulos,false);
        System.out.println("Coordenadas fk : "+Arrays.toString(coord_cartesian));
        angulos = ik.getAngles(coord_cartesian);

        angulos[0] = Math.toDegrees(angulos[0]);
        angulos[1] = Math.toDegrees(angulos[1]);
        angulos[2] = Math.toDegrees(angulos[2]);

        System.out.println("Angulos     ik : "+Arrays.toString(angulos));


        coord_cartesian = fk.getCartesian(angulos,false);
        System.out.println("Coordenadas fk : "+Arrays.toString(coord_cartesian));




    }
}