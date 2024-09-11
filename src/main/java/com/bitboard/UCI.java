package com.bitboard;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.bitboard.algorithms.AdvancedChessAlgorithm;
import com.bitboard.algorithms.CustomAlgorithm;


/**
 * This class is the UCI interface for the chess engine.
 * It is responsible for communicating with the GUI.
 */
public class UCI {
    

    private static String ENGINE_NAME = "FlowgineV2";
    private static String AUTHOR = "Flwrian";
    private static String VERSION = "2.0";
    
    private static BitBoard board = new BitBoard();
    // private static Engine engine;
    static Engine engine = new Engine(board);
    

    public static void main(String[] args) {

//         System.out.println("""
//  _______________________________________________________________________________________

//   ███████╗██╗      ██████╗ ██╗    ██╗ ██████╗ ██╗███╗   ██╗███████╗██╗   ██╗██████╗ 
//   ██╔════╝██║     ██╔═══██╗██║    ██║██╔════╝ ██║████╗  ██║██╔════╝██║   ██║╚════██╗
//   █████╗  ██║     ██║   ██║██║ █╗ ██║██║  ███╗██║██╔██╗ ██║█████╗  ██║   ██║ █████╔╝
//   ██╔══╝  ██║     ██║   ██║██║███╗██║██║   ██║██║██║╚██╗██║██╔══╝  ╚██╗ ██╔╝██╔═══╝ 
//   ██║     ███████╗╚██████╔╝╚███╔███╔╝╚██████╔╝██║██║ ╚████║███████╗ ╚████╔╝ ███████╗
//   ╚═╝     ╚══════╝ ╚═════╝  ╚══╝╚══╝  ╚═════╝ ╚═╝╚═╝  ╚═══╝╚══════╝  ╚═══╝  ╚══════╝
// _______________________________________________________________________________________

//         """);

//         // print available commands
//         System.out.println("Available commands:");
//         System.out.println("uci - start the engine");
//         System.out.println("isready - check if the engine is ready");
//         System.out.println("ucinewgame - start a new game");
//         System.out.println("position - set up the position");
//         System.out.println("go - start calculating the best move");
//         System.out.println("quit - stop the engine");
//         System.out.println("stop - stop calculating the best move");
//         System.out.println("option - set engine options");
//         System.out.println();

        CustomAlgorithm advancedChessAlgorithm = new CustomAlgorithm(6);
        engine.setAlgorithm(advancedChessAlgorithm);
        
        Scanner scanner = new Scanner(System.in);
        // Handle the UCI commands
        while(true){
            String input = scanner.nextLine();
            String[] inputArray = input.split(" ");
            String command = inputArray[0];

            // Write the command to file for debugging
            try {
                FileWriter myWriter = new FileWriter("debug.log", true);
                myWriter.write(input);
                myWriter.write("\r\n");
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
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
                case "stop":
                    stop();
                    break;
                case "option":
                    option(inputArray);
                    break;
                case "d":
                    d();
                    break;
                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        }
    }

    private static void option(String[] inputArray) {
        if(inputArray[1].equals("name")){
            if(inputArray[2].equals("Debug Log File")){
                if(inputArray[3].equals("value")){
                    String fileName = inputArray[4];
                    try {
                        FileWriter myWriter = new FileWriter(fileName, true);
                        myWriter.write("Debug Log File: " + fileName);
                        myWriter.write("\r\n");
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void d(){
        board.printChessBoard();
    }

    private static void stop() {
        
    }

    private static void quit() {
        System.exit(0);
    }

    private static void go(String[] inputArray) {

        // perft test
        if(inputArray[1].equals("perft")){
            int depth = Integer.parseInt(inputArray[2]);
            long time = System.currentTimeMillis();
            String perft = Perft.perftDivideString(board, depth);
            time = System.currentTimeMillis() - time;
            System.out.println(perft);
            System.out.println("Time: " + time + "ms");
        }
        if (inputArray[1].equals("ponder")) {
            // infinite search
            engine.setDepth(20);
            engine.getAlgorithm().search(board);
        }
        if (inputArray[1].equals("capture")) {
            MoveList moveList = MoveGenerator.generateCaptureMoves(board);
            System.out.println("Capture moves: " + moveList);
        }
        if (inputArray[1].equals("legal")) {
            MoveList moveList = board.getLegalMoves();
            System.out.println("Legal moves: " + moveList);
        }
        else{
            
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
    
            if(depth != 0){
                engine.setDepth(depth);
                Move bestMove = engine.getAlgorithm().search(board);
                System.out.println("bestmove " + bestMove.toString());
            }
    
            
        }



        
    }

    private static void position(String[] inputArray) {
        // Example: position startpos moves e2e4 e7e5
        // Example2: position fen rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 moves e2e4 e7e5
        // So we need to load the starting position and then make the moves

        if(inputArray[1].equals("startpos")){
            // Load the starting position
            board = new BitBoard();

            // Make the moves
            for(int i = 3; i < inputArray.length; i++){
                board.makeMove(inputArray[i]);
            }

        } else if(inputArray[1].equals("fen")){
            // Load the position from the FEN string
            String fen = "";
            for(int i = 2; i < inputArray.length; i++){
                if(inputArray[i].equals("moves")){
                    
                }
                fen += inputArray[i] + " ";
            }
            board.loadFromFen(fen);

            // Make the moves
            for(int i = 0; i < inputArray.length; i++){
                if(inputArray[i].equals("moves")){
                    for(int j = i + 1; j < inputArray.length; j++){
                        Move move = new Move(inputArray[j]);
                        board.makeMove(move);
                    }
                }
            }
        }

    }

    private static void uciNewGame() {
        board = new BitBoard();
    }

    private static void isReady() {
        System.out.println("readyok");
    }

    private static void uci() {
        System.out.println("id name " + ENGINE_NAME);
        System.out.println("id author " + AUTHOR);
        System.out.println("id version " + VERSION);
        System.out.println("option name Hash type spin default 16 min 1 max 1024");
        System.out.println("option name Threads type spin default 1 min 1 max 1024");
        System.out.println("option name Ponder type check default false");
        System.out.println("option name Debug Log File type string default debug.log");
        System.out.println();
        System.out.println("uciok");
    }

}
