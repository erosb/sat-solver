package com.github.erosb.satsolver;

import static java.util.Objects.requireNonNull;

public class SatSolver {

    private String formula;

    public SatSolver(String formula) {
        this.formula = requireNonNull(formula);
    }

    public boolean isSatisfiable() {
        return true;
    }

}
