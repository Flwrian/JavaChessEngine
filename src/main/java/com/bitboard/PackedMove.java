package com.bitboard;

public class PackedMove {

    // Bit layout (64 bits):
    // 0–5   : to
    // 6–11  : from
    // 12–15 : promotion piece
    // 16–19 : captured piece
    // 20–23 : moving piece
    // 24–27 : flags (castle, capture, promo...)
    // 28–47 : SEE score or move score
    // 48–63 : optional (can be 0, or store sort priority, phase, history info...)

    public static long encode(
        int from, int to,
        int pieceFrom, int pieceTo,
        int promotion, int flag,
        int seeScore
    ) {
        return ((long) to & 0x3FL)
             | (((long) from & 0x3FL) << 6)
             | (((long) promotion & 0xFL) << 12)
             | (((long) pieceTo & 0xFL) << 16)
             | (((long) pieceFrom & 0xFL) << 20)
             | (((long) flag & 0xFL) << 24)
             | (((long) seeScore & 0xFFFFF) << 28); // 20 bits
    }

    public static int getFrom(long move)        { return (int)((move >> 6) & 0x3F); }
    public static int getTo(long move)          { return (int)(move & 0x3F); }
    public static int getPromotion(long move)   { return (int)((move >> 12) & 0xF); }
    public static int getCaptured(long move)    { return (int)((move >> 16) & 0xF); }
    public static int getPieceFrom(long move)   { return (int)((move >> 20) & 0xF); }
    public static int getFlags(long move)       { return (int)((move >> 24) & 0xF); }
    public static int getScore(long move)       { return (int)((move >> 28) & 0xFFFFF); }

    public static long setScore(long move, int newScore) {
        return (move & ~(0xFFFFFL << 28)) | (((long) newScore & 0xFFFFF) << 28);
    }
}
