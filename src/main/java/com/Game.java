package com;

public class Game {
    
    public static void main(String[] args) {
        Board board = new Board();
        board.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"); 
        board.printBoard();

        Engine engine = new Engine(board);
        // for (int i = 0; i < 6; i++) {
        //     System.out.println(engine.getNbLegalMoves(i));
        //     // System.out.println(engine.getNbValidMoves(i));
        // }

        Zobrist table = new Zobrist();
        System.out.println(table.getHash(board.board));
        
    }
}
