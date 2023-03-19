package com;

public class Game {

    static boolean isEnded = false;
    
    public static void main(String[] args) {
        Board board = new Board();
        board.loadFEN(Board.STARTING_FEN);
        board.printBoard();

        Engine engine = new Engine(board);
        
        engine.setDepth(4);

        try{
            while(!isEnded){

                if(board.is3FoldRepetition()){
                    System.out.println("3-fold repetition");
                    isEnded = true;
                    break;
                }

                engine.play();
                board.printBoard();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(Board.gamePGN);
    }
}
