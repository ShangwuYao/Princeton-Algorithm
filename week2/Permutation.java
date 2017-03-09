/**
 * Created by Administrator on 2017/2/5.
 */
//this line is important
import edu.princeton.cs.algs4.*;
public class Permutation {
    public static void main(String[] args){
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        // read strings from std input:
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            rq.enqueue(s);
            StdOut.println(s);
        }

        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
