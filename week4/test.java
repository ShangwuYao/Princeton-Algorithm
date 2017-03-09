import java.util.*;

/**
 * Created by Administrator on 2017/2/13.
 */
public class test {
    private int num;
    public test(int num){
        this.num = num;
    }
    //@Override
    //public int hashCode(){
    //    return num;
    //}
    //@Override
    //public boolean equals(Object o){
    //    return num == o.hashCode();
    //}
    public void read(){
        int[][] a = new int[2][2];
        System.out.println(a.length);
        double x = -0.0;
        double y = +0.0;
        double z = Double.NaN;
        System.out.println(x == y);
        System.out.println(((Double)x).equals((Double) y));
        System.out.println(z == z);
        System.out.println(((Double)z).equals((Double) z));

        boolean m = true;
        int n = 1231;
        System.out.println(Boolean.hashCode(m) == Integer.hashCode(n));
    }

    public static void main(String[] args){
        test j = new test(123);
        test k = new test(456);
        System.out.println(j.equals(k));
        System.out.println(j.hashCode() == k.hashCode());
        k = new test(123);
        System.out.println(j.equals(k));
        System.out.println(j.hashCode() == k.hashCode());
        int[] b = {1,2};
        System.out.println(Arrays.toString(b));
        String a = "abcd";
        System.out.println(a.charAt(0));

    }
}
