/**
 * Created by Administrator on 2017/2/14.
 */
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Stack;

public class Solver {
    private class SearchNode implements Comparable<SearchNode>{
        //TODO: learn to modify the previous data structure with a new class
        private Board board;
        private int moves;
        private int priority;
        private SearchNode previousNode;

        public SearchNode(SearchNode previousnode, Board thisboard){
            this.previousNode= previousnode;
            if (previousnode != null) moves = previousNode.moves + 1;
            else moves = 1;
            board = thisboard;
            priority = moves + board.hamming();//TODO: hamming
        }
        public int priority(){return priority;}
        //TODO: use compareTo for the priority queue. so it doesn't has to be a comparator.
        public int compareTo(SearchNode that){
            if (this.priority > that.priority) return 1;
            else if (this.priority < that.priority) return -1;
            return 0;
        }
    }
    private boolean isSolvable;
    private Stack<Board> solution;
    public Solver(Board initial){
        // find a solution to the initial board (using the A* algorithm)
        if (initial == null) throw new NullPointerException();
        solution = new Stack<>();


        MinPQ<SearchNode> SNS = new MinPQ<>();
        MinPQ<SearchNode> TwinSNS = new MinPQ<>();
        SearchNode initialSN = new SearchNode(null,initial);
        SearchNode initialTwinSN = new SearchNode(null,initial.twin());
        //solution.add(initial);
        SNS.insert(initialSN);
        TwinSNS.insert(initialTwinSN);
        // how to determine it has stop.
        SearchNode currentNode = SNS.delMin();
        SearchNode currentTwinNode = TwinSNS.delMin();

        int currentmoves = 1;
        while (currentmoves < 100) {
            //TODO : first check if the loop has ended, then update
            if (currentTwinNode.board.isGoal()) {
                isSolvable = false;
                break;
            }
            if (currentNode.board.isGoal()) {
                isSolvable = true;

                //record the solution iteratively
                SearchNode thisnode = currentNode;
                while (thisnode != null) {
                    solution.add(thisnode.board);
                    thisnode = thisnode.previousNode;
                }
                break;
            } else {
                //put the neightbors into the minpq
                //TODO: this is interesting, return a Iterable type, and you can access them
                // iteratively.
                for (Board neighbor : currentNode.board.neighbors()) {
                    SearchNode SN = new SearchNode(currentNode, neighbor);
                    //critical optimization, should not be the same as previous node
                    if (!SN.equals(currentNode.previousNode)) SNS.insert(SN);
                }
                currentNode = SNS.delMin();

                for (Board neighbor : currentTwinNode.board.neighbors()) {
                    SearchNode TwinSN = new SearchNode(currentTwinNode, neighbor);
                    if (!TwinSN.equals(currentNode.previousNode)) TwinSNS.insert(TwinSN);
                }
                currentTwinNode = TwinSNS.delMin();
                currentmoves++;
            }
        }
        // reverse the order of the stack
        Stack<Board> board = new Stack<>();
        board.add(initial);
        int k = solution.size();
        for (int i = 0; i<k;i++ ){
            board.push(solution.pop());
        }
        solution = board;
    }
    public boolean isSolvable(){
        // is the initial board solvable?
        return isSolvable;
    }
    public int moves(){
        // min number of moves to solve initial board; -1 if unsolvable
        if (isSolvable)
            return solution.size()-1;
        else return -1;
    }
    public Iterable<Board> solution(){
        // sequence of boards in a shortest solution; null if unsolvable
        if (isSolvable) return solution;
        else return null;
    }
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
