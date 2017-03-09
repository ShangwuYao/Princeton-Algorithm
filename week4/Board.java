import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Administrator on 2017/2/14.
 */
public class Board {
    private int dimension;
    private int[][] blocks;
    public Board(int[][] blocks){
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        //TODO: remember this, even though its a 2D array, length still stands for
        // the length of a row
        dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
        for (int i = 0; i< dimension; i++){
            for (int j = 0; j < dimension; j++){
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }
    public int dimension() {
        // board dimension n
        return dimension;
    }
    public int hamming() {
        // number of blocks out of place
        //calculate the value of hamming and manhattan.
        int hamming = 0;
        //TODO: remember 2D should not use only 1 index, it's confusing
        for (int i = 0; i<dimension;i++) {
            for (int j = 0; j<dimension; j++){
                if (blocks[i][j] != i*dimension + j +1 && !isEnd(i,j))
                    hamming++;
            }
        }
        return hamming;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int manhattan = 0;
        for (int i = 0; i<dimension;i++) {
            for (int j = 0;j<dimension;j++) {
                int valueatI = blocks[i][j];
                if (valueatI == 0) continue;
                int x1 = valueatI % dimension - 1;
                if (x1 == -1) x1 = dimension -1;
                int y1 = (valueatI-1) / dimension;
                int dx = Math.abs(j - x1);
                int dy = Math.abs(i - y1);

                manhattan += dx + dy;
            }
        }
        return manhattan;
    }
    public boolean isGoal() {
        // is this board the goal board?
        for (int i = 0; i<dimension; i++){
            for (int j = 0; j<dimension; j++){
                if (isEnd(i,j)) break;
                if(blocks[i][j] != i*dimension + j+1) return false;
            }
        }
        if (blocks[dimension-1][dimension-1] !=0) return false;
        return true;
    }
    private boolean isEnd(int i , int j){
        if (i == dimension -1 && j == dimension - 1) return true;
        return false;
    }
    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        Board board = new Board(blocks);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension - 1; j++) {
                if (blocks[i][j] != 0 && blocks[i][j + 1] != 0) {
                    board.swap(i, j, i, j + 1);
                    return board;
                }
            }
        }
        return board;
    }
    public boolean equals(Object y) {
        // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (!(y.getClass() == this.getClass())) return false;
        /**
         *  notice this, array comparison is different from the comparison of primitive type
         *  and object. it has to compare every item of the array.
         *  so if its a 1D array, you can use Arrays.equals()
         *  if its a 2D array, you should use Arrays.deepEquals() instead.
         */
        if (!Arrays.deepEquals(this.blocks,((Board)y).blocks)) return false;
        return true;
    }
    public Iterable<Board> neighbors() {
        // all neighboring boards
        //TODO: this is new, return something that is Iterable
        int val = -1;
        int i = 0;// i is the index of the 0
        int x = -1;
        int y = -1;
        while (val!=0){
            x = i%dimension;
            y = i/dimension;
            val = blocks[y][x];
            i++;
        }

        Stack<Board> stack = new Stack<>();
        //put all neighbors into this stack
        //TODO: interesting, so this doesn't has to know exactly how many
        Board board1 = new Board(this.blocks);
        //TODO : can't use boolean b1 = this; because this is reference type.
        boolean b1 = board1.swap(y,x,y-1,x);
        if (b1) stack.push(board1);

        Board board2 = new Board(this.blocks);
        boolean b2 = board2.swap(y,x,y+1,x);
        if (b2) stack.push(board2);

        Board board3 = new Board(this.blocks);
        boolean b3 = board3.swap(y,x,y,x-1);
        if (b3) stack.push(board3);

        Board board4 = new Board(this.blocks);
        boolean b4 = board4.swap(y,x,y,x+1);
        if (b4) stack.push(board4);

        return stack;
    }
    private boolean swap(int y0,int x0,int y1, int x1){
        if (y1 < 0 || y1 >= dimension || x1 <0 || x1 >= dimension) return false;

        int temp = this.blocks[y0][x0];
        this.blocks[y0][x0] = this.blocks[y1][x1];
        this.blocks[y1][x1] = temp;
        return true;
    }

    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder sb = new StringBuilder();
        sb.append(dimension);
        sb.append("\n");
        for (int i = 0; i<dimension*dimension; i++) {
            sb.append(" ");
            int x0 =i%dimension;
            int y0 =i/dimension;
            sb.append(blocks[y0][x0]);
            sb.append(" ");
            if (x0==dimension-1){sb.append("\n");}
        }
        String res = sb.toString();
        return res;
    }

    public static void main(String[] args) { // unit tests (not graded)
        int[][] a = new int[][]{{1,2,3},
                                {4,5,6},
                                {8,7,0}};
        Board board = new Board(a);
        Board board2 = new Board(a);
        //System.out.println(board.dimension);
        System.out.println(board.toString());
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.equals(board2));
        //System.out.println(board.twin().toString());
    }
}
