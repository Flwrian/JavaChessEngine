package com;

public class Game {
    
    public static void main(String[] args) {
        Board board = new Board();
        board.board = new int[64];
        board.board[4] = 6;
        board.board[0] = 4;
        board.board[7] = 4;

        King king = (King) board.getPiece(4);


        for (int i = 0; i < 64; i++) {
            if(king.isValidMove(i)){
                System.out.println("King can move from " + king.getPosition() + " to " + i);
            }
        } 
        System.out.println("King pos at the end : " + king.getPosition());
    }
}
