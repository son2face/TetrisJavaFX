/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

/**
 *
 * @author SON
 */
// Piece.java
import java.util.*;

/**
 * Tao hinh
 *
 * Piece pyra = new Piece(PYRAMID_STR); Tao hinh moi ; int width =
 * pyra.getWidth(); Lay do rong ; Piece pyra2 = pyramid.computeNextRotation();
 * Quay; Piece[] pieces = Piece.getPieces(); Chuoi hinh goc ; Piece stick =
 * pieces[STICK]; int width = stick.getWidth(); Tao do rong ; Piece stick2 =
 * stick.fastRotation();
 */
public class Piece {

    private TPoint[] body;
    private int[] skirt;
    private int width;
    private int height;
    private Piece next;

    static private Piece[] pieces;

    /**
     * Xac dinh hinh moi cho boi TPoint[]
     */
    public Piece(TPoint[] points) {
        this.body = points;
        this.width = 0;
        this.height = 0;
        for (int i = 0; i < 4; i++) {
            if (this.width < this.body[i].x) {
                this.width = this.body[i].x;
            }
            if (this.height < this.body[i].y) {
                this.height = this.body[i].y;
            }
        }
        this.height += 1;
        this.width += 1;
    }

    public Piece(String points) {
        this(parsePoints(points));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TPoint[] getBody() {
        return body;
    }

    /**
     * Tra con tro ve quanh cac hinh.
     */
    public int[] getSkirt() {
        skirt = new int[this.width];
        for (int i = 0; i < this.width; i++) {
            skirt[i] = 9999;
        }
        for (int i = 0; i < 4; i++) {
            if (skirt[this.body[i].x] > this.body[i].y) {
                skirt[this.body[i].x] = this.body[i].y;
            }
        }
        return skirt;
    }

    /**
     * Tra ve hinh moi duoc quay 90 do so voi chieu dong ho
     */
    public Piece computeNextRotation() {
        TPoint[] result = new TPoint[4];
        for (int i = 0; i < 4; i++) {
            if (this.body[i].y == height - 1) {
                result[i] = new TPoint(0, this.body[i].x);
            } else if (this.body[i].y == 0) {
                result[i] = new TPoint(height - 1, this.body[i].x);
            } else {
                result[i] = new TPoint(this.body[i].y, this.body[i].x);
            }
        };
        return new Piece(result);
    }

    public Piece fastRotation() {
        return this.next;
    }

    /**
     * Tra ve 2 hinh co cung diem.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Piece)) {
            return false;
        }
        Piece other = (Piece) obj;

        boolean[][] objectA = new boolean[height][width];
        boolean[][] objectB = new boolean[other.getHeight()][other.getWidth()];

        //if the dimensions are unequal, then obviously they are not equal
        if (height != other.getHeight() || width != other.getWidth()) {
            return false;
        }

        //sets as true any point in objectA that is ocupied by a point in body
        for (int k = 0; k < body.length; k++) {
            objectA[(int) body[k].y][(int) body[k].x] = true;
        }

        //same thing for objectB and other.body
        for (int k = 0; k < other.getBody().length; k++) {
            objectB[(int) other.getBody()[k].y][(int) other.getBody()[k].x] = true;
        }

        //this compares the two boolean arrays, they should be the same if the objects are
        //the same
        for (int k = 0; k < objectA.length; k++) {
            for (int d = 0; d < objectA[k].length; d++) {
                if ((objectA[k][d] && !objectB[k][d])
                        || (!objectA[k][d] && objectB[k][d])) {
                    return false;
                }
            }
        }

        return true;
    }

    // Tao 7 hinh trong cho troi
    public static final String STICK_STR = "0 0	0 1	 0 2  0 3";
    public static final String L1_STR = "0 0	0 1	 0 2  1 0";
    public static final String L2_STR = "0 0	1 0 1 1	 1 2";
    public static final String S1_STR = "0 0	1 0	 1 1  2 1";
    public static final String S2_STR = "0 1	1 1	 1 0  2 0";
    public static final String SQUARE_STR = "0 0	0 1	 1 0  1 1";
    public static final String PYRAMID_STR = "0 0  1 0  1 1	2 0";

    public static Piece[] getPieces() {
        // lazy evaluation -- create static array if needed
        if (Piece.pieces == null) {

            Piece.pieces = new Piece[]{
                makeFastRotations(new Piece(STICK_STR)),
                makeFastRotations(new Piece(L1_STR)),
                makeFastRotations(new Piece(L2_STR)),
                makeFastRotations(new Piece(S1_STR)),
                makeFastRotations(new Piece(S2_STR)),
                makeFastRotations(new Piece(SQUARE_STR)),
                makeFastRotations(new Piece(PYRAMID_STR)),};
        }
        return Piece.pieces;
    }

    // Quay hinh pieceRow
    public static Piece makeFastRotations(Piece root) {
        Piece last = root;
        Piece temp = last.computeNextRotation();
        //asigns the next rotation to last, and then sets last to that
        //rotated piece, until the next roation is starter
        while (!root.equals(temp)) {
            last.next = temp;
            last = last.next;
            temp = last.computeNextRotation();
        }

        //this completes the loop by asigning next to starter
        last.next = root;

        return root;
    }

    private static TPoint[] parsePoints(String string) {
        List points = new ArrayList();
        StringTokenizer tok = new StringTokenizer(string);
        try {
            while (tok.hasMoreTokens()) {
                int x = Integer.parseInt(tok.nextToken());
                int y = Integer.parseInt(tok.nextToken());
                points.add(new TPoint(x, y));
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Could not parse x,y string:" + string);
        }

        // Tao mang lua chon
        TPoint[] array = (TPoint[]) points.toArray(new TPoint[0]);
        return array;
    }
}
