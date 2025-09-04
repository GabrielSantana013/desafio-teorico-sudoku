package br.com.sudoku;

import br.com.sudoku.model.Board;
import br.com.sudoku.model.Space;
import br.com.sudoku.util.BoardTemplate;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;


public class Main {

    private final static Scanner sc = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {
        final var positions = Stream.of(args).collect(toMap(
                k-> k.split(";")[0],
                v-> v.split(";")[1]
        ));

        var option = 1;
        while(true){

            System.out.println("Selecione uma das opções");
            System.out.println("1- Iniciar um novo jogo");
            System.out.println("2- Colocar um novo número");
            System.out.println("3- Remover um número");
            System.out.println("4- Visualizar jogo atual");
            System.out.println("5- Verificar status do jogo");
            System.out.println("6- limpar jogo");
            System.out.println("7- Finalizar jogo");
            System.out.println("8- Sair");

            option = sc.nextInt();

            switch(option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida!");

            }
        }
    }

    private static void finishGame() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        if(board.gameIsFinished()){
            System.out.println("Parabéns, vc concluiu o jogo");
            showCurrentGame();
            board = null;
        }else if(board.hasErrors()){
            System.out.println("Seu jogo contém erros, verifique novamente!");
        }else{
            System.out.println("Jogo incompleto!");
        }
    }

    private static void clearGame() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Deseja realmente limpar o jogo?");
        var confirm = sc.next();
        while(!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")){
            System.out.println("Informe sim ou nao");
            confirm = sc.next();
        }
        if(confirm.equalsIgnoreCase("sim")) {
            board.reset();
        }
    }

    private static void showGameStatus() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.printf("O jogo atualmente se encontra no status %s\n", board.getStatus().getLabel());
        if(board.hasErrors()){
            System.out.println("O jogo contém erros");
        }else{
            System.out.println("O jogo não contém erros");
        }
    }

    private static void showCurrentGame() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col: board.getSpaces()){
                args[argPos++] = " " + (isNull(col.get(i).getActual()) ? " " : col.get(i).getActual());

            }
            System.out.println("Seu jogo se encontra da seguinte forma:");
            System.out.println(BoardTemplate.BOARD_TEMPLATE.formatted(args));
        }
    }

    private static void removeNumber() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o numero será inserido: ");
        var col = runUntilGetValidNumber(0,8);
        System.out.println("Informe a linha em que o numero será inserido: ");
        var row = runUntilGetValidNumber(0,8);
        System.out.printf("Informe o numero que vai entrar na posição [%s %s]\n", col, row);
        var value = runUntilGetValidNumber(1,9);
        if(!board.clearValue(col, row)){
            System.out.printf("A posição [%s %s] tem um valor fixo!\n", col,row);
        }


    }

    private static void inputNumber() {
        if(isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        System.out.println("Informe a coluna em que o numero será inserido: ");
        var col = runUntilGetValidNumber(0,8);
        System.out.println("Informe a linha em que o numero será inserido: ");
        var row = runUntilGetValidNumber(0,8);
        System.out.printf("Informe o numero que vai entrar na posição [%s %s]\n", col, row);
        var value = runUntilGetValidNumber(1,9);
        if(!board.changeValue(col, row, value)){
            System.out.printf("A posição [%s %s] tem um valor fixo!\n", col,row);
        }
        
    }

    private static void startGame(final Map<String, String> positions) {
        if(nonNull(board)){
            System.out.println("O jogo já foi iniciado");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();
        for(int i = 0; i < BOARD_LIMIT; i++){
            spaces.add(new ArrayList<>());
            for(int j = 0; j < BOARD_LIMIT; j++){
                var positionConfig = positions.get("%s,%s".formatted(i,j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected,fixed);
                spaces.get(i).add(currentSpace);

            }
        }

        board = new Board(spaces);
        System.out.println("O jogo está pronto para começar!");
    }

    private static int runUntilGetValidNumber(final int min, final int max){
        var current = sc.nextInt();
        while(current < min || current > max){
            System.out.printf("Informe um numero valido entre %s e %s\n", min, max);
            current = sc.nextInt();
        }
        return current;
    }


}