public class Main{


    public static void main(String[] args){



        double angulos[];
        float[] coord_cartesian = new float[3];
        double L[] = new double[]{50,70,75};
        double Q[] = new double[]{0,0,0};
        double cartesiano[];
        ForwardK fk = new ForwardK(L);
        InverseK ik = new InverseK(L);

        coord_cartesian[0] = 0;
        coord_cartesian[1] = 50;
        coord_cartesian[2] = 0;
        angulos = ik.getAngles(coord_cartesian);

        angulos[0] = angulos[0] * 180/Math.PI;
        angulos[1] = angulos[1] * 180/Math.PI;
        angulos[2] = angulos[2] * 180/Math.PI;

        cartesiano   = fk.getCartesian(new double[]{angulos[0],angulos[1],angulos[2]});

        System.out.println("fk  Px  "+cartesiano[1] + " Py " +cartesiano[2] + " Pz "+cartesiano[0] );


        System.out.println("ik  Q[0]  "+angulos[0] + " Q[1] " +angulos[1] + " Q[2] "+angulos[2] );

    }
}