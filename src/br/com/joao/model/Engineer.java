package br.com.joao.model;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 09 Dec.
 */
public class Engineer {

    private final int id;

    private final String name;

    /**
     * Default construct.
     *
     * @param id Engineer identifier.
     * @param name Engineer name.
     */
    public Engineer(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return Engineer name.
     */
    public String getName() {
        return name;
    }

}
