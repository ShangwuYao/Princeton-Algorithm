/**
 * Created by Administrator on 2017/2/6.
 */
import edu.princeton.cs.algs4.*;
public class Percolation {
    private boolean[][] field;
    private int size;
    private int numberofopensites;
    private WeightedQuickUnion connectionSolver;

    public Percolation(int n){
        if(n<=0) throw new IllegalArgumentException();
        field = new boolean[n][n];
        for (int i = 0;i<n;i++){
            for (int j = 0;j<n;j++){
                field[i][j] = false;
            }
        }

        connectionSolver = new WeightedQuickUnion(n*n);
        numberofopensites = 0;
        size = n;

    }
    public void open(int row,int col){
        if (row <= 0 || col <= 0) throw new IndexOutOfBoundsException();
        if (!isOpen(row,col)) {
            field[row-1][col-1] = true;
            numberofopensites++;
            if(row > 1 && isOpen(row-1,col))
                connectionSolver.Union(Covert(row-1,col),Covert(row,col));

            if(row < size && isOpen(row+1,col))
                connectionSolver.Union(Covert(row+1,col),Covert(row,col));

            if(col > 1 && isOpen(row,col-1))
                connectionSolver.Union(Covert(row,col-1),Covert(row,col));

            if(col < size && isOpen(row,col+1))
                connectionSolver.Union(Covert(row,col+1),Covert(row,col));
        }
    }
    private int Covert(int row,int col){
        return (row-1)*size+col-1;
    }
    public boolean isOpen(int row,int col){
        if ((row <= 0)||(row > size)||(col<=0)||(col>size)){throw new IndexOutOfBoundsException();}
        if (field[row-1][col-1] == true) return true;
        else return false;
    }

    public boolean isFull(int row, int col){
        //isFull check if it is connected to a open grid in the top row.
        if ((row <= 0)||(row > size)||(col<=0)||(col>size)) throw new IndexOutOfBoundsException();
        //DONE: THIS should take only constant time
        if (isOpen(row,col) && connectionSolver.connected(Covert(row,col),0))return true;
        return false;
    }

    public int numberOfOpenSites(){
        return numberofopensites;
    }

    public boolean percolates(){
        //Done: This should take constant runtime.
        //TODO: however, it introduced a bug into the program: since connected method
        //only check if they have the same root value, and don't care if they are open.
        //I believe to fix it. the field (if open 1, else 0) should be put into WeightedQuickUnion
        if (connectionSolver.connected(0,size*size-1)) return true;
        return false;
    }


    private class WeightedQuickUnion {
        private int[] intfield;
        private int[] size;

        public WeightedQuickUnion(int N){
            intfield = new int[N];
            size = new int[N];
            for (int i = 0;i<N;i++){
                intfield[i] = i;
                size[i] = 1;
            }

            //TODO: this is a new amendment. to make the percolation done in constant time
            int num = (int) Math.round(Math.sqrt(N));
            for (int i = 0;i<num;i++){
                //create a top for all on the first row
                intfield[i] = 0;
                //create a bottom for all on the bottom row
                intfield[N-1-i] = N-1;
            }
        }
        public int root(int n){
            while(intfield[n]!=n){
                intfield[n] = intfield[intfield[n]];//path compression
                n = intfield[n];
            }
            return n;
        }

        public void Union(int N1,int N2){
            if (root(N1)!=root(N2)){
                if (size[root(N1)]>size[root(N2)]){
                    intfield[root(N2)] = intfield[root(N1)];
                    size[root(N1)]+=size[root(N2)];
                }
                else {
                    intfield[root(N1)] = intfield[root(N2)];
                    size[root(N2)]+=size[root(N1)];
                }
            }
        }
        public boolean connected(int N1, int N2){
            if (root(N1) == root(N2))return true;
            else return false;
        }
    }
}
