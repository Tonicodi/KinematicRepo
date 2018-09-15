package utils;

import java.io.Serializable;

public enum PRECISION implements Serializable{

    /**
     * Se establece que tan preciso seran los datos de entrenamiento
     */
        LOW       (20), //20*20 para cada articulacion seran 400  datos
        MEDIUM    (30), //30*30 para cada articulacion seran 900  datos
        HIGH      (60), //60*60 para cada articulacion seran 3600 datos
        VERY_HIGH (90); //90*90 para cada articulacion seran 8100 datos

         private final double value;

    /**
     * Constructor de PRECISION, asigna el valor del enum
     * @param value
     */
    PRECISION (double value) {
            this.value = value;
        }

    /**
     * Retorna el valor del enum establecido
     * @return
     */
    public double getValue() {
            return this.value;
        }

    }

