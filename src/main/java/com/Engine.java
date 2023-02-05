package com;

/*
 * This file is used to create a Chess Engine that will be used to play chess.
 * It will have a depth, and will use the minimax algorithm to determine the best
 * move.
 */
public class Engine {

    private Board board;

    public Engine(Board board) {
        this.board = board;
    }

    public int getNumMoves(int depth) {
        int count = 0;
        
        for (int i = 0; i < 64; i++) {
            if (board.getPiece(i) != null && board.getPiece(i).getColor() == board.whiteTurn) {
                Piece piece = board.getPiece(i);
                System.out.println(piece + " nb of legal moves: " + piece.getLegalMoves().length);
                count += piece.getLegalMoves().length;
            }
        }
        return count;
    }
}
