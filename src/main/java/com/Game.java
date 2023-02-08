package com;


public class Game {
    
    public static void main(String[] args) {
        Board board = new Board();
        board.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        
        Engine engine = new Engine(board);
        // engine.showKnps();
        System.out.println(engine.getNbLegalMoves(6));
    }
}
