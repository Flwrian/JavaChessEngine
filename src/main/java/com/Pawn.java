package com;

import java.util.Arrays;

public class Pawn extends Piece {

    private int startingPosition;

    public Pawn(int type, int position, Board board) {
        super(type, position, board);
        this.startingPosition = position;
    }

    @Override
    public boolean isValidMove(int position) {

        if (position < 0 || position > 63) {
            return false;
        }
        // Check if the pawn is trying to move to the same position
        if (position == this.getPosition()) {
            return false;
        }

        // Check if the pawn is trying to move to a position with a piece of the same color
        if (this.getBoard().getPiece(position) != null && this.getBoard().getPiece(position).getColor() == this.getColor()) {
            return false;
        }

        // For white pawns
        if (this.getColor()) {

            // Check if the pawn is trying to move en passant
            if (this.getBoard().getPiece(position) == null && position - this.getPosition() == 7 && this.hasMoved == 1) {
                if (this.getBoard().getPiece(position - 8) != null && this.getBoard().getPiece(position - 8).getType() == 1) {
                    Pawn pawn = (Pawn) this.getBoard().getPiece(position - 8);
                    if (pawn.startingPosition == position - 16) {
                        return true;
                    }
                }
            }

            if (position < this.getPosition() || position > this.getPosition() + 16) {
                return false;
            }
            if (this.getBoard().getPiece(position) != null && (position - this.getPosition() == 9 || position - this.getPosition() == 7)) {
                return true;
            }
            if (this.getPosition() < 8 && position > this.getPosition() + 7) {
                return false;
            }
            if (position == this.getPosition() + 8 || position == this.getPosition() + 16) {
                return true;
            }

        // For black pawns
        } else {

            // Check if the pawn is trying to move en passant
            if (this.getBoard().getPiece(position) == null && this.getPosition() - position == 7 && this.hasMoved == 1) {
                if (this.getBoard().getPiece(position + 8) != null && this.getBoard().getPiece(position + 8).getType() == 1) {
                    Pawn pawn = (Pawn) this.getBoard().getPiece(position + 8);
                    if (pawn.startingPosition == position + 16) {
                        return true;
                    }
                }
            }


            if (position > this.getPosition() || position < this.getPosition() - 16) {
                return false;
            }
            if (this.getBoard().getPiece(position) != null && (this.getPosition() - position == 9 || this.getPosition() - position == 7)) {
                return true;
            }
            if (this.getPosition() > 55 && position < this.getPosition() - 7) {
                return false;
            }
            if (position == this.getPosition() - 8 || position == this.getPosition() - 16) {
                return true;
            }
        }
        return false;
    }
}
