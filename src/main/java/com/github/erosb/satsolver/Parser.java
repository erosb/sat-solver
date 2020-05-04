package com.github.erosb.satsolver;

import java.util.List;

import static com.github.erosb.satsolver.TokenType.BINARY_OP;
import static java.lang.String.format;

public class Parser {
    public static Formula parse(List<Token> a) {
        return new Parser(a).parse();
    }

    private final List<Token> tokens;

    private int cursor = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Formula parse() {
        if (current() != null) {
            Formula left;
            if (current().type == TokenType.LPAREN) {
                ++cursor;
                left = parse();
                ++cursor;
                if (current() == null || current().type != TokenType.RPAREN) {
                    throw failure("unexpected token at %s");
                }
            } else if (current().type == TokenType.VAR_NAME) {
                left = Formula.atomic(current().literal);
            } else if (current().type == TokenType.UNARY_OP) {
                ++cursor;
                left = Formula.not(parse());
            } else {
                throw failure(format("unexpected token [%s] at position %d", current(), cursor));
            }
            if (lookahead() != null && lookahead().type == BINARY_OP) {
                ++cursor;
                Token operator = current();
                ++cursor;
                Formula right = parse();
                return Formula.binary(left, operator.literal, right);
            }
            return left;
        }
        throw failure("failed to parse input");
    }

    private IllegalArgumentException failure(String format) {
        return new IllegalArgumentException(format);
    }

    private Token lookahead() {
        if (cursor >= tokens.size() - 1) {
            return null;
        }
        return tokens.get(cursor + 1);
    }

    private Token current() {
        if (cursor >= tokens.size()) {
            return null;
        }
        return tokens.get(cursor);
    }

}
