package com;

public class Pawn extends Piece {

    // TODO: add promotion
    public static void move(int position, int destination, Board board, boolean color) {
        // If on the last row, promote to queen
        if (color && destination > 55) {
            board.board[position] = 0;
            board.board[destination] = 5;
            return;
        } else if (!color && destination < 8) {
            board.board[position] = 0;
            board.board[destination] = 11;
            return;
        }
        int type = board.board[position];
        board.board[position] = 0;
        board.board[destination] = type;
    }

    public static boolean isValidMove(int piecePosition, int position, Board board) {
        boolean color = board.board[piecePosition] < 7;

        // Check that the move is inside the board
        if (position < 0 || position > 63) {
            return false;
        }

        // Check that the move is not on the same square
        if (piecePosition == position) {
            return false;
        }

        // Check that the move is not on a square occupied by a piece of the same color
        if (board.board[position] != 0 && board.board[position] < 7 == board.board[piecePosition] < 7) {
            return false;
        }

        // For white pawns
        if (color) {

            // Check if the pawn is trying to move en passant
            // if (board.getPiece(position) == null && position - piecePosition == 7 &&
            // board.getPiece(piecePosition).hasMoved == 1) {
            // if (board.getPiece(position - 8) != null && board.getPiece(position -
            // 8).getType() == 1) {
            // Pawn pawn = (Pawn) board.getPiece(position - 8);
            // if (pawn.startingPosition == position - 16) {
            // return true;
            // }
            // }
            // }

            // Check if the pawn is trying to move to a position that is not in front of it
            if (position < piecePosition || position > piecePosition + 16) {
                return false;
            }

            // Check if the pawn is trying to move to a position that is diagonal to it
            if (board.board[position] != 0 && (position - piecePosition == 9 || position - piecePosition == 7)) {
                return true;
            }

            if(board.board[position] != 0) {
                return false;
            }

            // Check if the pawn is trying to move to a position that is more than 2 squares
            // in front of it
            if (piecePosition < 8 && position > piecePosition + 7) {
                return false;
            }

            int row = piecePosition / 8;

            if (position == piecePosition + 8 || (position == piecePosition + 16 && row == 1)) {
                return true;
            }

            // For black pawns
        } else {

            // Check if the pawn is trying to move en passant
            // if (board.getPiece(position) == null && piecePosition - position == 7 &&
            // board.getPiece(piecePosition).hasMoved == 1) {
            // if (board.getPiece(position + 8) != null && board.getPiece(position +
            // 8).getType() == 1) {
            // Pawn pawn = (Pawn) board.getPiece(position + 8);
            // if (pawn.startingPosition == position + 16) {
            // return true;
            // }
            // }
            // }

            if (position > piecePosition || position < piecePosition - 16) {
                return false;
            }

            if (board.board[position] != 0 && (piecePosition - position == 9 || piecePosition - position == 7)) {
                return true;
            }
            
            if(board.board[position] != 0) {
                return false;
            }

            if (piecePosition > 55 && position < piecePosition - 7) {
                return false;
            }
            int row = piecePosition / 8;

            if (position == piecePosition - 8 || (position == piecePosition - 16 && row == 6)) {
                return true;
            }

        }

        return false;

    }
}
