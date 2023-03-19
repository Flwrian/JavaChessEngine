package com;
import java.util.*;

/**
 * This class is the UCI interface for the chess engine.
 * It is responsible for communicating with the GUI.
 */
public class UCI {
    

    private static String ENGINE_NAME = "FlowBot";
    private static Board board;
    

    public static void main(String[] args) {
        
        // Handle the UCI commands
        while(true){
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            String[] inputArray = input.split(" ");
            String command = inputArray[0];
            switch(command){
                case "uci":
                    uci();
                    break;
                case "isready":
                    isReady();
                    break;
                case "ucinewgame":
                    uciNewGame();
                    break;
                case "position":
                    position(inputArray);
                    break;
                case "go":
                    go(inputArray);
                    break;
                case "quit":
                    quit();
                    break;
                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        }
    }

    private static void quit() {
        System.exit(0);
    }

    private static void go(String[] inputArray) {
        int depth = 0;
        int time = 0;
        for(int i = 1; i < inputArray.length; i++){
            if(inputArray[i].equals("depth")){
                depth = Integer.parseInt(inputArray[i + 1]);
            }
            else if(inputArray[i].equals("wtime")){
                time = Integer.parseInt(inputArray[i + 1]);
            }
            else if(inputArray[i].equals("btime")){
                time = Integer.parseInt(inputArray[i + 1]);
            }
        }
        if(depth == 0){
            depth = 1;
        }
        if(time == 0){
            time = 10000;
        }
        // Move bestMove = board.getBestMove(depth, time);
        // System.out.println("bestmove " + bestMove);
    }

    private static void position(String[] inputArray) {
        if(inputArray[1].equals("startpos")){
            board.loadFEN(Board.STARTING_FEN);
        }
        else if(inputArray[1].equals("fen")){
            String fen = "";
            for(int i = 2; i < inputArray.length; i++){
                if(inputArray[i].equals("moves")){
                    break;
                }
                fen += inputArray[i] + " ";
            }
            board.loadFEN(fen);
        }
        if(inputArray.length > 2){
            for(int i = 2; i < inputArray.length; i++){
                if(inputArray[i].equals("moves")){
                    for(int j = i + 1; j < inputArray.length; j++){
                        board.makeMove(inputArray[j]);
                    }
                    break;
                }
            }
        }
    }

    private static void uciNewGame() {
        board = new Board();
    }

    private static void isReady() {
        System.out.println("readyok");
    }

    private static void uci() {
        System.out.println("id name " + ENGINE_NAME);
        System.out.println("id author FlowBot");
        System.out.println("uciok");
    }

}