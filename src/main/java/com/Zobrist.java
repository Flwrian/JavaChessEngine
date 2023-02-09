package com;
import java.util.Random;

public class Zobrist {
    private final int BOARD_SIZE = 64;
    private final int NUM_PIECES = 12;
    private long[][] zobristTable;
    private Random random;

    public Zobrist() {
        random = new Random();
        zobristTable = new long[BOARD_SIZE][NUM_PIECES];

        // Initialise la table de hashage de Zobrist avec des nombres aléatoires
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < NUM_PIECES; j++) {
                zobristTable[i][j] = random.nextLong();
            }
        }
    }

    public long getHash(int[] board) {
        long hash = 0;

        // Pour chaque case de l'échiquier, ajouter la valeur du hash correspondant
        // à la pièce présente sur cette case (s'il y en a une)
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i] != 0) {
                hash ^= zobristTable[i][board[i] - 1];
            }
        }

        return hash;
    }

    public void updateHash(long hash, int position, int value, int newValue) {
        hash ^= zobristTable[position][value - 1];
        hash ^= zobristTable[position][newValue - 1];
    }
}