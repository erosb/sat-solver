package com.github.erosb.satsolver;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.erosb.satsolver.Token.AND;
import static com.github.erosb.satsolver.Token.IMPL;
import static com.github.erosb.satsolver.Token.LPAREN;
import static com.github.erosb.satsolver.Token.NOT;
import static com.github.erosb.satsolver.Token.RPAREN;
import static com.github.erosb.satsolver.Token.variable;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TokenizerTest {

    @Test
    void test() {
        List<Token> actual = Tokenizer.tokenize("(not A and B) impl (A and B)");
        assertEquals(asList(LPAREN, NOT, variable("A"), AND, variable("B"), RPAREN, IMPL, LPAREN, variable("A"), AND, variable("B"), RPAREN), actual);
    }

    @Test
    void empty() {
        assertEquals(emptyList(), Tokenizer.tokenize(""));
    }

    @Test
    void illegalCharacter() {
        assertThrows(TokenizerException.class,
                () -> Tokenizer.tokenize("(not 12)"));
    }

    @Test
    void unknownOperator() {
        assertThrows(TokenizerException.class,
                () -> Tokenizer.tokenize("(not A nand B) impl (A and B)"));
    }
}
