package utils;

import java.io.Serializable;

public enum PRECISION implements Serializable{

        LOW       (20),
        MEDIUM    (30),
        HIGH      (60),
        VERY_HIGH (90);

         private final double value;

        PRECISION (double value) {
            this.value = value;
        }

        public double getValue() {
            return this.value;
        }

    }

