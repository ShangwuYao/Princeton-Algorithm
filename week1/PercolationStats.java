import edu.princeton.cs.algs4.*;

/**
 * Created by Administrator on 2017/2/7.
 */
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] stat;
    public PercolationStats(int n, int trials){
        if(n<=0) throw new IllegalArgumentException();
        if(trials<=0) throw new IllegalArgumentException();
        stat = new double[trials];
        for (int i = 0;i<trials;i++){
            stat[i] = Trial(n);
        }
    }
    private double Trial(int n){
        Percolation connectionSolver = new Percolation(n);
        int gridToOpen = StdRandom.uniform(n*n);
        while (!connectionSolver.percolates()) {
            while (connectionSolver.isOpen(gridToOpen / n + 1, gridToOpen % n + 1)) {
                gridToOpen = StdRandom.uniform(n * n);
            }
            connectionSolver.open(gridToOpen / n + 1, gridToOpen % n + 1);
        }
        double res = ((double)connectionSolver.numberOfOpenSites())/n/n;
        return res;
    }
    public double mean(){
        int num = stat.length;
        double sum = 0.0;
        for(int i = 0;i<num;i++){
            sum+=stat[i];
        }
        double mean = sum/num;
        return mean;
    }

    public double stddev(){
        double s2 = 0.0;
        double sum = 0.0;
        int num = stat.length;
        double mean = mean();
        for(int i = 0; i<num;i++){
            sum += Math.pow(stat[i]-mean,2);
        }
        s2 = sum/(num-1);
        return Math.sqrt(s2);
    }

    public double confidenceLo(){
        int num = stat.length;
        double res = mean()-1.96*stddev()/Math.sqrt(num);
        return res;
    }
    public double confidenceHi(){
        int num = stat.length;
        double res = mean()+1.96*stddev()/Math.sqrt(num);
        return res;
    }

    public static void main(String[] args){
        //Stopwatch watch = new Stopwatch();
        PercolationStats stat = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        StdOut.println(stat.mean());
        StdOut.println(stat.stddev());
        StdOut.println(stat.confidenceLo());
        StdOut.println(stat.confidenceHi());
        //StdOut.println("elapsed time: "+watch.elapsedTime());
    }
}
