package com;

public class Queen {
    // public Queen(int type, int position, Board board) {
    //     super(type, position, board);
    // }

    // @Override
    // public boolean isValidMove(int position) {
    //     return new Rook(this.type, this.position, this.board).isValidMove(position)
    //             || new Bishop(this.type, this.position, this.board).isValidMove(position);
    // }

    // @Override
    // public void move(int position) {
    //         board.board[this.position] = 0;
    //         board.board[position] = this.type;
    //         this.position = position;
    // }

    // @Override
    // public String toString() {
    //    // Return the type, position, and color of the piece. ex: "Bishop{type=3, position=0, color=0}"
    //    // color is getColor()
    //      return "Queen{type=" + this.type + ", position=" + this.position + ", color=" + this.getColor() + "}";
    // }

    public static void move(int position, int newPosition, Board board, boolean b) {
        int type = board.board[position];
        board.board[position] = 0;
        board.board[newPosition] = type;
    }

    public static boolean isValidMove(int piecePosition, int position, Board board) {
        return Rook.isValidMove(piecePosition, position, board) || Bishop.isValidMove(piecePosition, position, board);
    }
}