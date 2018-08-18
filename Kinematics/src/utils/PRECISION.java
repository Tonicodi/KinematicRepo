package utils;
     public enum PRECISION{
        LOW       (Math.toRadians(5)),
        MEDIUM    (Math.toRadians(2.5)),
        HIGH      (Math.toRadians(1)),
        VERY_HIGH (Math.toRadians(0.5));

         private final double value;

        PRECISION (double value) {
            this.value = value;
        }

        public double getValue() {
            return this.value;
        }

    }

