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
// TPoint.java
/*
 Cung cap toa do x, y cua tung hinh
 */
public class TPoint {

    public int x;
    public int y;

    public TPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public TPoint(TPoint point) {
        this.x = point.x;
        this.y = point.y;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TPoint)) {
            return false;
        }

        TPoint pt = (TPoint) other;
        return (x == pt.x && y == pt.y);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
