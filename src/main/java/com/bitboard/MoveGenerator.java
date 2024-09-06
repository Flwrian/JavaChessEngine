package com.bitboard;

import java.util.ArrayList;

public class MoveGenerator {
    private BitBoard board;

    public MoveGenerator(BitBoard board) {
        this.board = board;
    }

    public long generateMask(boolean whiteToMove) {

        if (whiteToMove) {
            return generateWhiteMask();
        } else {
            return generateBlackMask();
        }

    }

    public void printMask(boolean whiteToMove) {
        long mask = generateMask(whiteToMove);
        board.printBitBoard(mask);
    }

    public int countBits(long mask) {
        int count = 0;
        while (mask != 0) {
            mask &= mask - 1;
            count++;
        }
        return count;
    }

    public MoveList generateMoves(boolean whiteToMove) {
        // maximum number of moves is 218
        MoveList moves = new MoveList(218);

        // We will iterate through the board and generate the moves for each piece
        
        // Pawns
        long pawns = whiteToMove ? board.getWhitePawns() : board.getBlackPawns();

        // We iterate through the pawns by getting the least significant bit and then removing it from the bitboard
        while (pawns != 0L) {
            long pawn = BitBoard.getLSB(pawns);
            pawns &= pawns - 1;

            // We get the starting square of the pawn
            int from = BitBoard.getSquare(pawn);

            // We generate the moves for the pawn
            long pawnMoves = generatePawnMoves(pawn, whiteToMove);

            // We iterate through the moves and add them to the list
            while (pawnMoves != 0L) {
                long move = BitBoard.getLSB(pawnMoves);
                pawnMoves &= pawnMoves - 1;

                int to = BitBoard.getSquare(move);

                // If the move is a promotion, we generate all the possible promotions
                if (to >= 56 || to <= 7) {
                    Move promotionQueen = new Move(from, to, board.getPiece(from), board.getPiece(to));
                    promotionQueen.setType(Move.PROMOTION);

                    Move promotionRook = new Move(from, to, board.getPiece(from), board.getPiece(to));
                    promotionRook.setType(Move.PROMOTION);

                    Move promotionBishop = new Move(from, to, board.getPiece(from), board.getPiece(to));
                    promotionBishop.setType(Move.PROMOTION);

                    Move promotionKnight = new Move(from, to, board.getPiece(from), board.getPiece(to));
                    promotionKnight.setType(Move.PROMOTION);

                    moves.add(promotionQueen);
                    moves.add(promotionRook);
                    moves.add(promotionBishop);
                    moves.add(promotionKnight);
                }

                if (to == board.enPassantSquare) {
                    Move enPassent = new Move(from, to, board.getPiece(from), board.getPiece(to));
                    enPassent.setType(Move.EN_PASSENT);
                    moves.add(enPassent);
                }

                Move normalMove = new Move(from, to, board.getPiece(from), board.getPiece(to));
                moves.add(normalMove);
            }

        }

        // Knights
        long knights = whiteToMove ? board.getWhiteKnights() : board.getBlackKnights();

        while (knights != 0L) {
            long knight = BitBoard.getLSB(knights);
            knights &= knights - 1;

            int from = BitBoard.getSquare(knight);

            long knightMoves = whiteToMove ? generateWhiteKnightMoves(knight) : generateBlackKnightMoves(knight);

            while (knightMoves != 0L) {
                long move = BitBoard.getLSB(knightMoves);
                knightMoves &= knightMoves - 1;

                int to = BitBoard.getSquare(move);

                Move normalMove = new Move(from, to, board.getPiece(from), board.getPiece(to));
                moves.add(normalMove);
            }
        }

        // Bishops
        long bishops = whiteToMove ? board.getWhiteBishops() : board.getBlackBishops();

        while (bishops != 0L) {
            long bishop = BitBoard.getLSB(bishops);
            bishops &= bishops - 1;

            int from = BitBoard.getSquare(bishop);

            long bishopMoves = whiteToMove ? generateWhiteBishopMoves(bishop) : generateBlackBishopMoves(bishop);

            while (bishopMoves != 0L) {
                long move = BitBoard.getLSB(bishopMoves);
                bishopMoves &= bishopMoves - 1;

                int to = BitBoard.getSquare(move);

                Move normalMove = new Move(from, to, board.getPiece(from), board.getPiece(to));
                moves.add(normalMove);
            }
        }

        // Rooks
        long rooks = whiteToMove ? board.getWhiteRooks() : board.getBlackRooks();

        while (rooks != 0L) {
            long rook = BitBoard.getLSB(rooks);
            rooks &= rooks - 1;

            int from = BitBoard.getSquare(rook);

            long rookMoves = whiteToMove ? generateWhiteRookMoves(rook) : generateBlackRookMoves(rook);

            while (rookMoves != 0L) {
                long move = BitBoard.getLSB(rookMoves);
                rookMoves &= rookMoves - 1;

                int to = BitBoard.getSquare(move);

                Move normalMove = new Move(from, to, board.getPiece(from), board.getPiece(to));
                moves.add(normalMove);
            }
        }

        // Queens
        long queens = whiteToMove ? board.getWhiteQueens() : board.getBlackQueens();

        while (queens != 0L) {
            long queen = BitBoard.getLSB(queens);
            queens &= queens - 1;

            int from = BitBoard.getSquare(queen);

            long queenMoves = whiteToMove ? generateWhiteQueenMoves(queen) : generateBlackQueenMoves(queen);

            while (queenMoves != 0L) {
                long move = BitBoard.getLSB(queenMoves);
                queenMoves &= queenMoves - 1;

                int to = BitBoard.getSquare(move);

                Move normalMove = new Move(from, to, board.getPiece(from), board.getPiece(to));
                moves.add(normalMove);
            }
        }

        // Kings
        long king = whiteToMove ? board.getWhiteKing() : board.getBlackKing();

        int from = BitBoard.getSquare(king);

        long kingMoves = generateKingMoves(king, whiteToMove);

        while (kingMoves != 0L) {
            long move = BitBoard.getLSB(kingMoves);
            kingMoves &= kingMoves - 1;

            int to = BitBoard.getSquare(move);

            // Castling
            if (Math.abs(from - to) == 2) {
                Move castling = new Move(from, to, board.getPiece(from), board.getPiece(to));
                castling.setType(Move.CASTLING);
                moves.add(castling);
            }

            Move normalMove = new Move(from, to, board.getPiece(from), board.getPiece(to));
            moves.add(normalMove);
        }

        return moves;

    }

    public void countMoves(boolean whiteToMove) {
        MoveList moves = generateMoves(whiteToMove);
        System.out.println("Number of moves: " + moves.size());
    }

    private long generateWhiteMask() {
        long whiteAttacks = 0L;

        // Générer les mouvements des pions blancs
        long whitePawnMask = generatePawnMask(board.getWhitePawns(), true);
        whiteAttacks |= whitePawnMask;

        // Générer les mouvements des cavaliers blancs
        long whiteKnightMask = generateKnightMask(board.getWhiteKnights());
        whiteAttacks |= whiteKnightMask;

        // Générer les mouvements des fous blancs
        long whiteBishopMask = generateBishopMask(board.getWhiteBishops());
        whiteAttacks |= whiteBishopMask;

        // Générer les mouvements des tours blanches
        long whiteRookMask = generateRookMask(board.getWhiteRooks());
        whiteAttacks |= whiteRookMask;

        // Générer les mouvements des reines blanches
        long whiteQueenMask = generateQueenMask(board.getWhiteQueens());
        whiteAttacks |= whiteQueenMask;

        // Générer les mouvements des rois blancs
        long whiteKingMask = generateKingMask(board.getWhiteKing(), true);
        whiteAttacks |= whiteKingMask;


        return whiteAttacks;
    }

    private long generateBlackMask() {
        long blackAttacks = 0L;

        // Générer les mouvements des pions noirs
        long blackPawnMask = generatePawnMask(board.getBlackPawns(), false);
        blackAttacks |= blackPawnMask;

        // Générer les mouvements des cavaliers noirs
        long blackKnightMask = generateKnightMask(board.getBlackKnights());
        blackAttacks |= blackKnightMask;

        // Générer les mouvements des fous noirs
        long blackBishopMask = generateBishopMask(board.getBlackBishops());
        blackAttacks |= blackBishopMask;

        // Générer les mouvements des tours noires
        long blackRookMask = generateRookMask(board.getBlackRooks());
        blackAttacks |= blackRookMask;

        // Générer les mouvements des reines noires
        long blackQueenMask = generateQueenMask(board.getBlackQueens());
        blackAttacks |= blackQueenMask;

        // Générer les mouvements des rois noirs
        long blackKingMask = generateKingMask(board.getBlackKing(), false);
        blackAttacks |= blackKingMask;

        return blackAttacks;
    }

    private long generatePawnMask(long pawns, boolean white) {
        long pawnMask = 0L;

        if (white) {
            // Captures diagonales, gauche et droite
            long capturesLeft = (pawns << 9) & ~BitBoard.FILE_H;
            long capturesRight = (pawns << 7) & ~BitBoard.FILE_A;

            pawnMask |= capturesLeft | capturesRight;
        } else {
            // Captures diagonales, gauche et droite
            long capturesLeft = (pawns >> 7) & ~BitBoard.FILE_H;
            long capturesRight = (pawns >> 9) & ~BitBoard.FILE_A;

            pawnMask |= capturesLeft | capturesRight;
        }

        return pawnMask;
    }

    public long generatePawnMoves(long pawns, boolean white) {
        long pawnMoves = 0L;

        if (white) {
            long singlePush = (pawns << 8) & ~board.getBoard();  // Avancer d'une case
            long doublePush = ((pawns & BitBoard.RANK_2) << 16) & ~board.getBoard();  // Avancer de deux cases depuis la rangée initiale
            pawnMoves |= singlePush | doublePush;
            // Captures diagonales, gauche et droite
            long capturesLeft = (pawns << 7) & board.getBlackPieces() & ~BitBoard.FILE_H;
            long capturesRight = (pawns << 9) & board.getBlackPieces() & ~BitBoard.FILE_A;
            pawnMoves |= capturesLeft | capturesRight;
        } else {
            long singlePush = (pawns >> 8) & ~board.getBoard();  // Avancer d'une case
            long doublePush = ((pawns & BitBoard.RANK_7) >> 16) & ~board.getBoard();  // Avancer de deux cases depuis la rangée initiale
            pawnMoves |= singlePush | doublePush;
            // Captures diagonales, gauche et droite
            long capturesLeft = (pawns >> 9) & board.getWhitePieces() & ~BitBoard.FILE_H;
            long capturesRight = (pawns >> 7) & board.getWhitePieces() & ~BitBoard.FILE_A;
            pawnMoves |= capturesLeft | capturesRight;
        }

        return pawnMoves;

    }

    public long generateKnightMask(long knights) {
        // Le cavalier peut se déplacer en L, 2 cases dans une direction et 1 case dans une autre
        long knightMoves = 0L;

        // Déplacements en forme de L
        long upLeft = (knights << 15) & ~BitBoard.FILE_A & ~BitBoard.FILE_B;
        long upRight = (knights << 17) & ~BitBoard.FILE_H & ~BitBoard.FILE_G;
        long leftUp = (knights << 6) & ~BitBoard.FILE_A & ~BitBoard.FILE_B;
        long rightUp = (knights << 10) & ~BitBoard.FILE_H & ~BitBoard.FILE_G;
        long downLeft = (knights >> 17) & ~BitBoard.FILE_A & ~BitBoard.FILE_B;
        long downRight = (knights >> 15) & ~BitBoard.FILE_H & ~BitBoard.FILE_G;
        long leftDown = (knights >> 10) & ~BitBoard.FILE_A & ~BitBoard.FILE_B;
        long rightDown = (knights >> 6) & ~BitBoard.FILE_H & ~BitBoard.FILE_G;

        knightMoves |= upLeft | upRight | leftUp | rightUp | downLeft | downRight | leftDown | rightDown;

        return knightMoves;
    }

    // Generate knight legal moves
    public long generateWhiteKnightMoves(long knights) {
        // Le cavalier peut se déplacer en L, 2 cases dans une direction et 1 case dans une autre et ne peut pas aller sur une case occupée par une pièce de la même couleur
        long knightMoves = 0L;

        // Déplacements en forme de L
        long knightMask = generateKnightMask(knights);

        // On ne peut pas aller sur une case occupée par une pièce de la même couleur
        knightMoves = knightMask & ~board.getWhitePieces();

        return knightMoves;
    }



    private long generateBishopMask(long bishops) {
        // Le fou peut se déplacer dans toutes les diagonales mais le mask s'arrête lorsqu'il rencontre une pièce (inclusif)

        long bishopMask = 0L;

        while (bishops != 0L) {
            long bishop = BitBoard.getLSB(bishops);
            bishops &= bishops - 1;

            long upLeft = bishop;
            long upRight = bishop;
            long downLeft = bishop;
            long downRight = bishop;

            // Déplacements en diagonale
            while (upLeft != 0L) {
                upLeft = (upLeft << 7) & ~BitBoard.FILE_A;
                bishopMask |= upLeft;
                if ((upLeft & board.getBoard()) != 0L) {
                    break;
                }
            }

            while (upRight != 0L) {
                upRight = (upRight << 9) & ~BitBoard.FILE_H;
                bishopMask |= upRight;
                if ((upRight & board.getBoard()) != 0L) {
                    break;
                }
            }

            while (downLeft != 0L) {
                downLeft = (downLeft >> 9) & ~BitBoard.FILE_A;
                bishopMask |= downLeft;
                if ((downLeft & board.getBoard()) != 0L) {
                    break;
                }
            }

            while (downRight != 0L) {
                downRight = (downRight >> 7) & ~BitBoard.FILE_H;
                bishopMask |= downRight;
                if ((downRight & board.getBoard()) != 0L) {
                    break;
                }
            }
        }

        return bishopMask;
        
    }

    private long generateWhiteBishopMoves(long bishops) {
        // La seule différence entre les mouvements des fous et les masques des fous est que selon la couleur, les fous ne peuvent pas capturer une pièce de la même couleur

        long bishopMoves = 0L;

        // Déplacements en diagonale
        long bishopMask = generateBishopMask(bishops);

        // On ne peut pas capturer une pièce de la même couleur
        bishopMoves = bishopMask & ~board.getWhitePieces();

        return bishopMoves;
    }
    

    private long generateRookMask(long rooks) {
        // La tour peut se déplacer en ligne droite et le mask s'arrête lorsqu'il rencontre une pièce (inclusif)
        long rookMoves = 0L;

        while (rooks != 0L) {
            long rook = BitBoard.getLSB(rooks);
            rooks &= rooks - 1;

            long up = rook;
            long down = rook;
            long left = rook;
            long right = rook;

            // Déplacements en ligne droite
            while (up != 0L) {
                up = (up << 8) & ~BitBoard.RANK_1;
                rookMoves |= up;
                if ((up & board.getBoard()) != 0L) {
                    break;
                }
            }

            while (down != 0L) {
                down = (down >> 8) & ~BitBoard.RANK_8;
                rookMoves |= down;
                if ((down & board.getBoard()) != 0L) {
                    break;
                }
            }

            while (left != 0L) {
                left = (left << 1) & ~BitBoard.FILE_H;
                rookMoves |= left;
                if ((left & board.getBoard()) != 0L) {
                    break;
                }
            }

            while (right != 0L) {
                right = (right >> 1) & ~BitBoard.FILE_A;
                rookMoves |= right;
                if ((right & board.getBoard()) != 0L) {
                    break;
                }
            }
        }

        return rookMoves;
        
    }

    public long generateWhiteRookMoves(long rooks) {
        // La seule différence entre les mouvements des tours et les masques des tours est que selon la couleur, les tours ne peuvent pas capturer une pièce de la même couleur

        long rookMoves = 0L;

        // Déplacements en ligne droite
        long rookMask = generateRookMask(rooks);

        // On ne peut pas capturer une pièce de la même couleur
        rookMoves = rookMask & ~board.getWhitePieces();

        return rookMoves;
    }

    private long generateQueenMask(long queens) {
        // La reine peut se déplacer en diagonale et en ligne droite soit les mouvements du fou et de la tour
        long queenMoves = 0L;

        // Déplacements en diagonale
        long straightMoves = generateRookMask(queens);
        long diagonalMoves = generateBishopMask(queens);

        queenMoves |= straightMoves | diagonalMoves;

        return queenMoves;
    }

    private long generateWhiteQueenMoves(long queens) {
        // La seule différence entre les mouvements des reines et les masques des reines est que selon la couleur, les reines ne peuvent pas capturer une pièce de la même couleur

        long queenMoves = 0L;

        // Déplacements en diagonale et en ligne droite
        long queenMask = generateQueenMask(queens);

        // On ne peut pas capturer une pièce de la même couleur
        queenMoves = queenMask & ~board.getWhitePieces();

        return queenMoves;
    }

    private long generateKingMask(long king, boolean white){
        // Le roi peut se déplacer d'une case dans toutes les directions et peut roquer
        long kingMoves = 0L;

        // Déplacements d'une case
        long up = (king << 8);
        long down = (king >> 8);
        long left = (king << 1) & ~BitBoard.FILE_H;
        long right = (king >> 1) & ~BitBoard.FILE_A;
        long upLeft = (king << 9) & ~BitBoard.FILE_H;
        long upRight = (king << 7) & ~BitBoard.FILE_A;
        long downLeft = (king >> 7) & ~BitBoard.FILE_H;
        long downRight = (king >> 9) & ~BitBoard.FILE_A;

        kingMoves |= up | down | left | right | upLeft | upRight | downLeft | downRight;

        return kingMoves;
    }

    public long generateKingMoves(long king, boolean white) {
        // Le roi peut se déplacer d'une case dans toutes les directions et peut roquer
        long kingMoves = generateKingMask(king, white);

        // pas la meme couleur
        if (white) {
            kingMoves &= ~board.getWhitePieces();
        } else {
            kingMoves &= ~board.getBlackPieces();
        }

        // La logique du roque est comme suit:
        // Si on possède le droit de roquer, que la tour est à sa place et que les cases entre le roi et la tour sont vides
        // alors on peut roquer
        
        if (white) {
            // Si on a les droits de roquer du côté de du roi
            if(board.whiteCastleKingSide == 1L) {
                // Si la tour est à sa place
                if((board.whiteRooks & BitBoard.BLACK_KING_SIDE_ROOK_SQUARE) != 0L) {
                    // Si il n'y a pas de pièces entre le roi et la tour
                    if((BitBoard.WHITE_KING_SIDE_CASTLE_EMPTY_SQUARES_MASK & board.getBoard()) == 0L) {
                        // Si les cases ne sont pas attaquées
                        if((BitBoard.WHITE_KING_SIDE_CASTLE_NEED_TO_NOT_BE_ATTACKED_MASK & generateMask(false)) == 0L) {
                            // On peut roquer
                            kingMoves |= BitBoard.WHITE_KING_SIDE_CASTLE_KING_SQUARE;
                        }
                    }
                }
            }

            // Si on a les droits de roquer du côté de la dame
            if(board.whiteCastleQueenSide == 1L) {
                // Si la tour est à sa place
                if((board.whiteRooks & BitBoard.BLACK_QUEEN_SIDE_ROOK_SQUARE) != 0L) {
                    // Si il n'y a pas de pièces entre le roi et la tour
                    if((BitBoard.WHITE_QUEEN_SIDE_CASTLE_EMPTY_SQUARES_MASK & board.getBoard()) == 0L) {
                        // Si les cases ne sont pas attaquées
                        if((BitBoard.WHITE_QUEEN_SIDE_CASTLE_NEED_TO_NOT_BE_ATTACKED_MASK & generateMask(false)) == 0L) {
                            // On peut roquer
                            kingMoves |= BitBoard.WHITE_QUEEN_SIDE_CASTLE_KING_SQUARE;
                        }
                    }
                }
            }
        }

        else {
            // Si on a les droits de roquer du côté de du roi
            if(board.blackCastleKingSide == 1L) {
                // Si la tour est à sa place
                if((board.blackRooks & BitBoard.BLACK_KING_SIDE_ROOK_SQUARE) != 0L) {
                    // Si il n'y a pas de pièces entre le roi et la tour
                    if((BitBoard.BLACK_KING_SIDE_CASTLE_EMPTY_SQUARES_MASK & board.getBoard()) == 0L) {
                        // Si les cases ne sont pas attaquées
                        if((BitBoard.BLACK_KING_SIDE_CASTLE_NEED_TO_NOT_BE_ATTACKED_MASK & generateMask(true)) == 0L) {
                            // On peut roquer
                            kingMoves |= BitBoard.BLACK_KING_SIDE_CASTLE_KING_SQUARE;
                        }
                    }
                }
            }

            // Si on a les droits de roquer du côté de la dame
            if(board.blackCastleQueenSide == 1L) {
                // Si la tour est à sa place
                if((board.blackRooks & BitBoard.BLACK_QUEEN_SIDE_ROOK_SQUARE) != 0L) {
                    // Si il n'y a pas de pièces entre le roi et la tour
                    if((BitBoard.BLACK_QUEEN_SIDE_CASTLE_EMPTY_SQUARES_MASK & board.getBoard()) == 0L) {
                        // Si les cases ne sont pas attaquées
                        if((BitBoard.BLACK_QUEEN_SIDE_CASTLE_NEED_TO_NOT_BE_ATTACKED_MASK & generateMask(true)) == 0L) {
                            // On peut roquer
                            kingMoves |= BitBoard.BLACK_QUEEN_SIDE_CASTLE_KING_SQUARE;
                        }
                    }
                }
            }
        }

        return kingMoves;
    }

    public long generateBlackKnightMoves(long knights) {
        // Le cavalier peut se déplacer en L, 2 cases dans une direction et 1 case dans une autre et ne peut pas aller sur une case occupée par une pièce de la même couleur
        long knightMoves = 0L;

        // Déplacements en forme de L
        long knightMask = generateKnightMask(knights);

        // On ne peut pas aller sur une case occupée par une pièce de la même couleur
        knightMoves = knightMask & ~board.getBlackPieces();

        return knightMoves;
    }

    public long generateBlackBishopMoves(long bishops) {
        // La seule différence entre les mouvements des fous et les masques des fous est que selon la couleur, les fous ne peuvent pas capturer une pièce de la même couleur

        long bishopMoves = 0L;

        // Déplacements en diagonale
        long bishopMask = generateBishopMask(bishops);

        // On ne peut pas capturer une pièce de la même couleur
        bishopMoves = bishopMask & ~board.getBlackPieces();

        return bishopMoves;
    }

    public long generateBlackRookMoves(long rooks) {
        // La seule différence entre les mouvements des tours et les masques des tours est que selon la couleur, les tours ne peuvent pas capturer une pièce de la même couleur

        long rookMoves = 0L;

        // Déplacements en ligne droite
        long rookMask = generateRookMask(rooks);

        // On ne peut pas capturer une pièce de la même couleur
        rookMoves = rookMask & ~board.getBlackPieces();

        return rookMoves;
    }

    public long generateBlackQueenMoves(long queens) {
        // La seule différence entre les mouvements des reines et les masques des reines est que selon la couleur, les reines ne peuvent pas capturer une pièce de la même couleur

        long queenMoves = 0L;

        // Déplacements en diagonale et en ligne droite
        long queenMask = generateQueenMask(queens);

        // On ne peut pas capturer une pièce de la même couleur
        queenMoves = queenMask & ~board.getBlackPieces();

        return queenMoves;
    }
}
