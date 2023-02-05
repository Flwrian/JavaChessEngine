package com;


public class Knight extends Piece {
    public Knight(int type, int position, Board board) {
        super(type, position, board);
    }

    @Override
    public boolean isValidMove(int position) {
        // if (board.whiteTurn != this.getColor()) {
        //     return false;
        // }

        if (position < 0 || position > 63) {
            return false;
        }
        // Check if the knight is trying to move to the same position
        if (position == this.getPosition()) {
            return false;
        }
        // Check if the knight is trying to move to a position with a piece of the same
        // color
        if (this.getBoard().getPiece(position) != null
                && this.getBoard().getPiece(position).getColor() == this.getColor()) {
            return false;
        }

        // The knight move in an L shape.

        // The offsets for the 8 different moves

        int[] moveOffsets = { -17, -15, -10, -6, 6, 10, 15, 17 };
        for (int move : moveOffsets) {
            int newPos = this.getPosition() + move;
            if (newPos == position) {
                if ((this.getPosition() % 8 == 0 && (move == -17 || move == -10 || move == 6 || move == 15)) ||
                        (this.getPosition() % 8 == 7 && (move == -15 || move == -6 || move == 10 || move == 17))) {
                    return false;
                }
                return true;
            }
        }

        return false;
    }
}