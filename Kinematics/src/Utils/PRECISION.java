package Utils;
     public enum PRECISION{
        LOW       (1),
        MEDIUM    (0.5),
        HIGH      (0.1),
        VERY_HIGH (0.05);

         private final double value;

        PRECISION (double value) {
            this.value = value;
        }

        public double getValue() {
            return this.value;
        }

    }

