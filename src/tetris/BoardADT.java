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
// Board.java
/**
 * Tao ban choi Tetris, cung cap cac kieu hinh. Co chuc nang "undo".
 */
public class BoardADT {

    private int width;
    private int height;
    private boolean[][] grid;
    public static final int PLACE_OK = 0;
    public static final int PLACE_OUT_BOUNDS = 1;
    public static final int PLACE_BAD = 2;

    /**
     * Tao ban choi trong voi su cho truoc do cao va rong cua tung block.
     */
    public BoardADT(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new boolean[width][height];
    }

    /**
     * Vi tri bat dau cua 1 brick
     *
     * @param piece
     * @return
     */
    public TPoint getStartPosition(Piece piece) {
        int x = (width - piece.getWidth()) / 2;
        int y = (height - piece.getHeight()) - 1;
        return (new TPoint(x, y));
    }

    /**
     * Tra ve gia tri max cua do cao cua cot hien tai. Gia tri bang 0 neu rong.
     */
    public int getMaxHeight() {
        int max = 0;
        for (int i = 0; i < this.width; i++) {
            int temp = getColumnHeight(i);
            if (max < temp) {
                max = temp;
            }
        }
        return max;
    }

    /**
     * Cho mot hinh va toa do x, tra ve gia tri toa do y ve cac diem den cua
     * hinh khi cho no roi thang theo truc x.
     */
    public int dropHeight(Piece piece, int x, int y) {
        int[] position = piece.getSkirt();
        int max = 0;
        for (int i = 0; i < piece.getWidth(); i++) {
            int temp = 0;
            for (int j = y; j >= 0; j--) {
                if (grid[x + i][j] == true) {
                    temp = j + 1 - position[i];
                    break;
                }
            }
            if (max < temp) {
                max = temp;
            }
        }
        return max;
    }

    /**
     * Tra ve do cao cua cot - gia tri toa do y cua block cao nhat + 1. Gia tri
     * bang 0 neu cot khong co block nao.
     */
    public int getColumnHeight(int x) {
        for (int i = height - 1; i >= 0; i--) {
            if (grid[x][i] == true) {
                return i + 1;
            }
        }
        return 0;
    }

    /**
     * Tra ve so block trong mot dong cho truoc
     */
    public int getRowWidth(int y) {
        int result = 0;
        for (int i = 0; i < this.width; i++) {
            if (this.grid[i][y] == true) {
                result++;
            }
        }
        return result;
    }

    /**
     * Kiem tra tinh hop le cua Piece
     */
    public int place(Piece piece, int x, int y) {
        if (y < 0 || x < 0 || y >= this.height || x > this.width - piece.getWidth()) {
            return PLACE_OUT_BOUNDS;
        }
        TPoint[] temp = piece.getBody();
        for (int i = 0; i < 4; i++) {
            if (grid[temp[i].x + x][temp[i].y + y] == true) {
                return PLACE_BAD;
            }
        }
        return PLACE_OK;
    }

    /**
     * Cap nhat du lieu cho bang, tra ve true neu co it nhat 1 rowFilled
     *
     * @param piece
     * @param x
     * @param y
     * @return
     */
    public boolean update(Piece piece, int x, int y) {
        TPoint[] temp = piece.getBody();
        for (int i = 0; i < 4; i++) {
            grid[temp[i].x + x][temp[i].y + y] = true;
        }
        for (int i = 0; i < piece.getHeight(); i++) {
            if (getRowWidth(i + y) == width) {
                return true;
            }
        }
        return false;
    }

    /**
     * Kiem tra row y, neu filled thi xoa di va tra ve true
     */
    public boolean clearRow(int y) {
        if (getRowWidth(y) == width) {
            for (int i = y; i < height - 1; i++) {
                for (int j = 0; j < width; j++) {
                    grid[j][i] = grid[j][i + 1];
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Tra ve do rong cua block.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Tra ve do cao cua block.
     */
    public int getHeight() {
        return height;
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public void setGrid(boolean[][] grid) {
        this.grid = grid;
    }

}
