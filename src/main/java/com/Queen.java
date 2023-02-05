package com;

import java.util.Arrays;

public class Queen extends Piece {
    public Queen(int type, int position, Board board) {
        super(type, position, board);
    }

    @Override
    public boolean isValidMove(int position) {
        return new Rook(this.type, this.position, this.board).isValidMove(position)
                || new Bishop(this.type, this.position, this.board).isValidMove(position);
    }
}