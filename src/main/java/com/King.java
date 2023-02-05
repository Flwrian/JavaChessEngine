package com;

/**
 * King
 */
public class King extends Piece {


    public King(int type, int position, Board board) {
        super(type, position, board);
    }

    public boolean isInCheck() {
        for (Piece piece : this.board.getPieces()) {
            if (piece.getColor() != this.getColor() && piece.isValidMove(this.getPosition())) {
                return true;
            }
        }
        return false;
    }

    public boolean isAttackedAt(int position) {
        move(position);
        boolean isThreatened = isInCheck();
        undoMove();
        return isThreatened;
    }

    public boolean isValidShortCastle() {

        // Check if the king has moved
        if(this.hasMoved > 0) return false;

        // Check there is a rook on the right side
        Piece rook = this.getBoard().getPiece(this.getPosition() + 3);
        if(rook == null) return false;

        // Check if the rook has moved
        if(rook.hasMoved > 0) return false;

        // Check if the rook is a rook
        if(!(rook.getType() == 4 || rook.getType() == 10)) return false;

        // Check if the rook is the same color as the king
        if(rook.getColor() != this.getColor()) return false;

        // Check if path is clear
        if (this.getBoard().getPiece(this.getPosition() + 1) != null ||
            this.getBoard().getPiece(this.getPosition() + 2) != null) {
            return false;
        }


        // Check if king is in check or if the king is attacked at any point
        if (this.isInCheck() || this.isAttackedAt(this.getPosition() + 1) ||
            this.isAttackedAt(this.getPosition() + 2)) {
            return false;
        }

        // If all checks pass, return true
        System.out.println("Short castle is valid");
        return true;
    }
    
    public boolean isValidLongCastle() {
        
        // Check if the king has moved
        if(this.hasMoved > 0) return false;

        // Check there is a rook on the right side
        Piece rook = this.getBoard().getPiece(this.getPosition() - 4);
        if(rook == null) return false;

        // Check if the rook has moved
        if(rook.hasMoved > 0) return false;

        // Check if the rook is a rook
        if(!(rook.getType() == 4 || rook.getType() == 10)) return false;

        // Check if the rook is the same color as the king
        if(rook.getColor() != this.getColor()) return false;

        // Check if the rook is the same color as the king
        if(rook.getColor() != this.getColor()) return false;

        // Check if path is clear
        if (this.getBoard().getPiece(this.getPosition() - 1) != null ||
            this.getBoard().getPiece(this.getPosition() - 2) != null ||
            this.getBoard().getPiece(this.getPosition() - 3) != null) {
            return false;
        }

        // Check if king is in check or if the king is attacked at any point
        if (this.isInCheck() || this.isAttackedAt(this.getPosition() - 1) ||
            this.isAttackedAt(this.getPosition() - 2)) {
            return false;
        }

        // If all checks pass, return true
        return true;
    }
    

    public boolean isValidMove(int position) {
        if (position < 0 || position > 63) {
            return false;
        }

        
        if (this.getPosition() == position) {
            return false;
        }

        if (position == this.getPosition() + 2) {
            return this.isValidShortCastle();
        }

        // if (position == this.getPosition() - 2) {
        //     return this.isValidLongCastle();
        // }
        
        int rowDiff = Math.abs(position / 8 - this.position / 8);
        int colDiff = Math.abs(position % 8 - this.position % 8);

        if (rowDiff > 1 || colDiff > 1) {
            // King can only move one square at a time
            return false;
        }
        
        if (this.getBoard().getPiece(position) != null && this.getBoard().getPiece(position).getColor() == this.getColor()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isLegalMove(int position) {
        // A move is legal if it is valid and does not put the king in check
        if (this.isValidMove(position) && !this.isAttackedAt(position)) {
            return true;
        }
        return false;
    }


    
}