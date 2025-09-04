package br.com.sudoku.util;

public final class BoardTemplate {

    private BoardTemplate(){}

    public final static String BOARD_TEMPLATE =
            """
            +-------+-------+-------+
            | %s %s %s | %s %s %s | %s %s %s |
            | %s %s %s | %s %s %s | %s %s %s |
            | %s %s %s | %s %s %s | %s %s %s |
            +-------+-------+-------+
            | %s %s %s | %s %s %s | %s %s %s |
            | %s %s %s | %s %s %s | %s %s %s |
            | %s %s %s | %s %s %s | %s %s %s |
            +-------+-------+-------+
            | %s %s %s | %s %s %s | %s %s %s |
            | %s %s %s | %s %s %s | %s %s %s |
            | %s %s %s | %s %s %s | %s %s %s |
            +-------+-------+-------+
            """;

}
