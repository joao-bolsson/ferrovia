package br.com.joao.model;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 09 Dec.
 */
public class Train {

    private int id;

    private Engineer engineer;
    
    private Type type;

    public class Type {

        private final int id;
        private final String name;

        /**
         * Train type.
         *
         * @param id Type id.
         * @param name Type name.
         */
        public Type(final int id, final String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

}
