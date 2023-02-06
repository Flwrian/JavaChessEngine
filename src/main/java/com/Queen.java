package com;

public class Queen extends Piece {
    public Queen(int type, int position, Board board) {
        super(type, position, board);
    }

    @Override
    public boolean isValidMove(int position) {
        return new Rook(this.type, this.position, this.board).isValidMove(position)
                || new Bishop(this.type, this.position, this.board).isValidMove(position);
    }

    @Override
    public void move(int position) {
        if (this.isLegalMove(position)) {
            board.board[this.position] = 0;
            board.board[position] = this.type;
            this.position = position;
        }
    }
}