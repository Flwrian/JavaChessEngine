package com;

public class Game {
    
    public static void main(String[] args) {
        Board board = new Board();
        board.loadFEN("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8"); 
        board.printBoard();

        Engine engine = new Engine(board);

        for (int i = 0; i < 6; i++) {
            System.out.println(engine.getNbLegalMoves(i));
            // System.out.println(engine.getNbValidMoves(i));
        }

        
    }
}
