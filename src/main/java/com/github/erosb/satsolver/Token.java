package com.github.erosb.satsolver;

import java.util.Objects;

enum TokenType {
    LPAREN, RPAREN, BINARY_OP, UNARY_OP, VAR_NAME
}

class Token {

    static Token LPAREN = new Token("(", TokenType.LPAREN);

    static Token RPAREN = new Token(")", TokenType.RPAREN);

    static Token IMPL = new Token("impl", TokenType.BINARY_OP);

    static Token AND = new Token("and", TokenType.BINARY_OP);

    static Token OR = new Token("or", TokenType.BINARY_OP);

    static Token NOT = new Token("not", TokenType.UNARY_OP);

    static Token variable(String varName) {
        return new Token(varName, TokenType.VAR_NAME);
    }

    final String literal;

    TokenType type;

    Token(String literal, TokenType type) {
        this.literal = literal;
        this.type = type;
    }

    @Override
    public String toString() {
        return literal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Token)) {
            return false;
        }
        Token token = (Token) o;
        return literal.equals(token.literal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(literal);
    }
}
