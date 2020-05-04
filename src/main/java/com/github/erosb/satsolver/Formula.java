package com.github.erosb.satsolver;

import java.util.Map;
import java.util.Objects;

import static java.lang.String.format;

interface Formula {

    static Formula atomic(String varName) {
        return new AtomicFormula(varName);
    }

    static Formula not(Formula negated) {
        return new UnaryFormula(negated);
    }

    static Formula binary(Formula left, String operator, Formula right) {
        return new BinaryFormula(left, operator, right);
    }

    boolean evaluate(Map<String, Boolean> symbolTable);

}


class AtomicFormula implements Formula {

    private final String varName;

    public AtomicFormula(String varName) {
        this.varName = varName;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> symbolTable) {
        Boolean value = symbolTable.get(varName);
        if (value == null) {
            throw new IllegalStateException(format("no value is assigned to variable [%s]", varName));
        }
        return value;
    }

    @Override
    public String toString() {
        return varName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtomicFormula)) {
            return false;
        }
        AtomicFormula that = (AtomicFormula) o;
        return varName.equals(that.varName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(varName);
    }
}


class UnaryFormula implements Formula {

    private final Formula negated;

    public UnaryFormula(Formula negated) {
        this.negated = negated;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> symbolTable) {
        return !negated.evaluate(symbolTable);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnaryFormula)) {
            return false;
        }
        UnaryFormula that = (UnaryFormula) o;
        return negated.equals(that.negated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(negated);
    }

    @Override
    public String toString() {
        return "not (" + negated.toString() + ")";
    }
}

class BinaryFormula implements Formula {

    private final Formula left;

    private final String operator;

    private final Formula right;

    public BinaryFormula(Formula left, String operator, Formula right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public boolean evaluate(Map<String, Boolean> symbolTable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BinaryFormula)) {
            return false;
        }
        BinaryFormula that = (BinaryFormula) o;
        return left.equals(that.left) &&
                operator.equals(that.operator) &&
                right.equals(that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, operator, right);
    }

    @Override
    public String toString() {
        return "(" + left + ") " + operator + " (" + right + ")";
    }
}
