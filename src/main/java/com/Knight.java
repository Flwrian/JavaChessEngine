package com;


public class Knight {
    // public Knight(int type, int position, Board board) {
    //     super(type, position, board);
    // }

    // @Override
    // public boolean isValidMove(int position) {
    //     // if (board.whiteTurn != this.getColor()) {
    //     //     return false;
    //     // }

    //     if (position < 0 || position > 63) {
    //         return false;
    //     }
    //     // Check if the knight is trying to move to the same position
    //     if (position == this.getPosition()) {
    //         return false;
    //     }
    //     // Check if the knight is trying to move to a position with a piece of the same
    //     // color
    //     if (this.getBoard().getPiece(position) != null
    //             && this.getBoard().getPiece(position).getColor() == this.getColor()) {
    //         return false;
    //     }

    //     // The knight move in an L shape.

    //     int newRow = position / 8;
    //     int newCol = position % 8;
    //     int currentRow = this.getPosition() / 8;
    //     int currentCol = this.getPosition() % 8;

    //     if(Math.abs(newRow - currentRow) == 2 && Math.abs(newCol - currentCol) == 1){
	// 		return true;
	// 	}
		
	// 	if(Math.abs(newRow - currentRow) == 1 && Math.abs(newCol - currentCol) == 2){
	// 		return true;
	// 	}
		
	// 	return false;

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
    //    return "Knight{type=" + this.type + ", position=" + this.position + ", color=" + this.getColor() + "}";
    // }

    public static void move(int position, int newPosition, Board board, boolean color) {
        int type = board.board[position];
        board.board[position] = 0;
        board.board[newPosition] = type;
    }

    public static boolean isValidMove(int piecePosition, int position, Board board) {
        boolean color = board.board[piecePosition] < 7;

        int newRow = position / 8;
        int newCol = position % 8;
        int currentRow = piecePosition / 8;
        int currentCol = piecePosition % 8;

        if (position < 0 || position > 63) {
            return false;
        }
        // Check if the knight is trying to move to the same position
        if (position == piecePosition) {
            return false;
        }
        // Check if the knight is trying to move to a position with a piece of the same
        // color
        if (board.board[position] != 0 && color == board.board[position] < 7) {
            return false;
        }

        // The knight move in an L shape.

        if (Math.abs(newRow - currentRow) == 2 && Math.abs(newCol - currentCol) == 1) {
            return true;
        }

        if (Math.abs(newRow - currentRow) == 1 && Math.abs(newCol - currentCol) == 2) {
            return true;
        }

        return false;
    }
}