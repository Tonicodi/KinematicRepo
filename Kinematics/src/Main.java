import java.util.Arrays;

public class Main{


    public static void main(String[] args){



        double angulos[];
        double[] coord_cartesian = new double[3];
        double L[] = new double[]{10,5,5};
        double Q[] = new double[]{0,0,0};
        double cartesiano[];
        ForwardK fk = new ForwardK(L);
        InverseK ik = new InverseK(L);

        coord_cartesian[0] = 0;
        coord_cartesian[1] = 10;
        coord_cartesian[2] = 10;
        angulos = ik.getAngles(coord_cartesian);

        cartesiano   = fk.getCartesian(new double[]{0,0,0});

        System.out.println("fk  Px  "+ Arrays.toString(cartesiano));

        System.out.println("ik  Q[0]  "+angulos[0] + " Q[1] " +angulos[1] + " Q[2] "+angulos[2] );

    }
}