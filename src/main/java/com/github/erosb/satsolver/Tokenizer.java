package com.github.erosb.satsolver;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Tokenizer {

    public static List<Token> tokenize(String formulaString) {
        return new Tokenizer(formulaString).tokenize();
    }

    private final String formulaString;

    private int cursor = 0;

    private final List<Token> tokens = new ArrayList<>();

    public Tokenizer(String formulaString) {
        this.formulaString = requireNonNull(formulaString);
    }

    private List<Token> tokenize() {
        while (cursor < formulaString.length()) {
            char ch = currentChar();
            if (ch == '(') {
                tokens.add(Token.LPAREN);
                ++cursor;
            } else if (ch == ')') {
                tokens.add(Token.RPAREN);
                ++cursor;
            } else if (isUppercaseLetter(ch)) {
                tokens.add(consumeVariable());
            } else if (isLowercaseLetter(ch)) {
                tokens.add(consumeOperator());
            } else if (ch == ' ' || ch == '\n' || ch == '\t' || ch == 11) {
                ++cursor;
            } else {
                throw new TokenizerException("unknown character: " + ch);
            }
        }
        return tokens;
    }

    private Token consumeOperator() {
        StringBuilder sb = new StringBuilder();
        for (char ch = currentChar(); isLowercaseLetter(ch); ++cursor, ch = currentChar()) {
            sb.append(ch);
        }
        String rawOperator = sb.toString();
        if (rawOperator.equals("impl")) {
            return Token.IMPL;
        } else if (rawOperator.equals("or")) {
            return Token.OR;
        } else if (rawOperator.equals("and")) {
            return Token.AND;
        } else if (rawOperator.equals("not")) {
            return Token.NOT;
        }
        throw new TokenizerException("unknown operator " + rawOperator);
    }

    private boolean isLowercaseLetter(char ch) {
        return 'a' <= ch && ch <= 'z';
    }

    private boolean isUppercaseLetter(char ch) {
        return 'A' <= ch && ch <= 'Z';
    }

    private Token consumeVariable() {
        StringBuilder sb = new StringBuilder();
        for (char ch = currentChar(); cursor < formulaString.length() && isUppercaseLetter(ch = currentChar()); ++cursor) {
            sb.append(ch);
        }
        return Token.variable(sb.toString());
    }

    private char currentChar() {
        return formulaString.charAt(cursor);
    }
}
