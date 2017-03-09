/**
 * Created by Administrator on 2017/2/6.
 */
public class WeightedQuickUnion {
    private int[] intfield;
    private int[] size;

    public WeightedQuickUnion(int N){
        intfield = new int[N];
        size = new int[N];
        for (int i = 0;i<N;i++){
            intfield[i] = i;
            size[i] = 1;
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
