package com.github.erosb.satsolver;

import org.junit.jupiter.api.Test;

import static com.github.erosb.satsolver.Formula.atomic;
import static com.github.erosb.satsolver.Formula.binary;
import static com.github.erosb.satsolver.Formula.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {

    @Test
    void testAtomic() {
        Formula actual = Parser.parse(Tokenizer.tokenize("A"));
        assertEquals(atomic("A"), actual);
    }

    @Test
    void unary() {
        Formula actual = Parser.parse(Tokenizer.tokenize("not A"));
        assertEquals(not(atomic("A")), actual);
    }

    @Test
    void testBinary() {
        Formula actual = Parser.parse(Tokenizer.tokenize("A or B"));
        assertEquals(binary(atomic("A"), "or", atomic("B")), actual);
    }

    @Test
    void binaryWithAssociativity() {
        Formula actual = Parser.parse(Tokenizer.tokenize("A or not B and C"));
        assertEquals(binary(atomic("A"), "or", not(binary(atomic("B"), "and", atomic("C")))), actual);
    }

    @Test
    void parentheses() {
        Formula actual = Parser.parse(Tokenizer.tokenize("((A) or (not B and ((C))))"));
        assertEquals(binary(atomic("A"), "or", not(binary(atomic("B"), "and", atomic("C")))), actual);
    }
}
