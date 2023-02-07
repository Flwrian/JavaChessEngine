package com;

import java.util.Arrays;

/**
 * Chess Piece Class
 * Every piece has a color, a type, and a position
 * 
 * the type is an integer that represents the type of piece
 * 1 = white pawn
 * 2 = white knight
 * 3 = white bishop
 * 4 = white rook
 * 5 = white queen
 * 6 = white king
 * 7 = black pawn
 * 8 = black knight
 * 9 = black bishop
 * 10 = black rook
 * 11 = black queen
 * 12 = black king
 * 
 * @author Florian Montourcy
 * @version 1.0
 */
public abstract class Piece {

    protected Board board;
    protected int type;
    protected int position;

    public Piece(int type, int position, Board board) {
        this.type = type;
        this.position = position;
        this.board = board;
    }

    // ? Instead of creating a new instance each time we need to move a piece or whatever else, we could just use this static method that handle everything.
    // * This will make the program faster, but it will make the code harder to read
    public static void move(int position, int newPosition, Board board) {
        int type = board.board[position];

        switch (type) {
            case 1:
                Pawn.move(position, newPosition, board.board, type < 7);
                break;
            case 2:
                Knight.move(position, newPosition, board);
                break;
            case 3:
                Bishop.move(position, newPosition, board);
                break;
            case 4:
                Rook.move(position, newPosition, board);
                break;
            case 5:
                Queen.move(position, newPosition, board);
                break;
            case 6:
                King.move(position, newPosition, board);
                break;
            case 7:
                Pawn.move(position, newPosition, board);
                break;
            case 8:
                Knight.move(position, newPosition, board);
                break;
            case 9:
                Bishop.move(position, newPosition, board);
                break;
            case 10:
                Rook.move(position, newPosition, board);
                break;
            case 11:
                Queen.move(position, newPosition, board);
                break;
            case 12:
                King.move(position, newPosition, board);
                break;
            default:
                break;
        }
    }

    // ! We really need to find a better way to do this
    // ! We need to only create one instance of each piece
    // ! But it can be tricky to do so if we check all the possible moves
    // ! That is the best way to optimize the program
    public static Piece getPiece(int type, int position, Board board) {
        switch (type) {
            case 1:
                return new Pawn(type, position, board);
            case 2:
                return new Knight(type, position, board);
            case 3:
                return new Bishop(type, position, board);
            case 4:
                return new Rook(type, position, board);
            case 5:
                return new Queen(type, position, board);
            case 6:
                return new King(type, position, board);
            case 7:
                return new Pawn(type, position, board);
            case 8:
                return new Knight(type, position, board);
            case 9:
                return new Bishop(type, position, board);
            case 10:
                return new Rook(type, position, board);
            case 11:
                return new Queen(type, position, board);
            case 12:
                return new King(type, position, board);
            default:
                return null;
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean getColor() {
        return type < 7;
    }

    public int getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public abstract void move(int position);

    public boolean isLegalMove(int position) {
        // Check that the move is legal for the piece by checking if the move is valid
        // and if the move puts the king in check
        if (!this.isValidMove(position)) {
            return false;
        }

        // * Double the time of node exploration
        // ? Is it really necessary to check if the move puts the king in check for a chess engine?
        // ? After all, the engine is supposed to be able to find the best move, if the move lose the king it's not the best move so the engine won't choose it.
        // ? Probably not necessary, maybe we will remove this in the future
        board.pushMove(this.position, position);
        boolean inCheck = board.isInCheck(getColor());
        board.popMove();

        return !inCheck;
    }

    // ? Instead of looping through all the possible moves (looping through all the squares on the board), we could loop through all the possible moves of a piece (diag, horiz, vert, etc.)
    public abstract boolean isValidMove(int position);

    public int countValidMoves() {
        // Count the number of valid moves for the piece
        int count = 0;
        for (int i = 0; i < 64; i++) {
            if (this.isValidMove(i)) {
                count++;
            }
        }
        return count;
    }

    public void printValidMoves() {
        for (int i = 0; i < 64; i++) {
            if (this.isValidMove(i)) {
                System.out.println("From " + this.getPosition() + " to " + i);
            }
        }
    }

    public int countLegalMoves() {
        // Count the number of legal moves for the piece
        int count = 0;
        for (int i = 0; i < 64; i++) {
            if (this.isLegalMove(i)) {
                count++;
            }
        }
        return count;
    }

    /**
     * <p>
     * Gets all the legal moves for the piece
     * </p>
     */
    public int[][] getLegalMoves() {

        int[][] legalMoves = new int[0][0];

        for (int i = 0; i < 64; i++) {
            if (this.isLegalMove(i)) {
                legalMoves = Arrays.copyOf(legalMoves, legalMoves.length + 1);
                legalMoves[legalMoves.length - 1] = new int[] { this.getPosition(), i };
            }
        }

        return legalMoves;
    }

    public void printLegalMoves() {
        // Print in a nice format all the legal moves for the piece (from and to)
        int[][] legalMoves = this.getLegalMoves();
        for (int[] move : legalMoves) {
            System.out.println("From " + move[0] + " to " + move[1]);
        }
    }

}