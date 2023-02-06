package com;

import javax.sound.midi.Soundbank;

public class Game {
    
    public static void main(String[] args) {
        Board board = new Board();
        board.loadFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        
        Engine engine = new Engine(board);
        System.out.println("Number of validMoves: " + engine.getNbValidMoves());
    }
}
