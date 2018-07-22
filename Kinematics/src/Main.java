import java.util.Arrays;

public class Main{


    public static void main(String[] args){

        double angulos[];
        double[] coord_cartesian = new double[3];
        double L[] = new double[]{10,5,5};
        double Q[] = new double[]{0.12,-0.57,1.220};
        double cartesiano[];
        ForwardK fk = new ForwardK(L);
        InverseK ik = new InverseK(L);

        coord_cartesian[0] = 8;
        coord_cartesian[1] = 1;
        coord_cartesian[2] = 10;
        angulos = ik.getAngles(coord_cartesian);

        cartesiano   = fk.getCartesian(angulos);

        System.out.println("fk  Px  "+ Arrays.toString(cartesiano));

        System.out.println("angulos  Q[0]  "+angulos[0] + " Q[1] " +angulos[1] + " Q[2] "+angulos[2] );
        cartesiano = fk.getCartesian(Q);

        System.out.println("test  Px  "+ Arrays.toString(cartesiano));


    }
}