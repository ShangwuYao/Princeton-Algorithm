
/**
 * Created by Administrator on 2017/2/10.
 */
import java.util.Collections;
import java.util.*;

public class FastCollinearPoints {
    private List<LineSegment> Listofline = new ArrayList<LineSegment>();
    public FastCollinearPoints(Point[] points){
        // finds all line segments containing 4 or more points
        // Think of p as the origin.
        if (points == null ) throw new NullPointerException();
        int numbers = points.length;
        for (int i = 0; i<numbers-1; i++){
            Point p = points[i];
            if (p == null) throw new NullPointerException();
            List<Point> listofpoint = new ArrayList<Point>();
            List<Double> listofslope = new ArrayList<>();
            //For each other point q, determine the slope it makes with p.
            for (int j = i+1; j<numbers; j++){
                Point q = points[j];
                if (q == null) throw new NullPointerException();
                if (p.equals(q)) throw new IllegalArgumentException();
                double slope = p.slopeTo(q);
                // you need the slope
                // you need the point
                listofslope.add(slope);
                listofpoint.add(q);
            }
            if (listofpoint.size() <=2) continue;
            //Sort the points according to the slopes they makes with p.
            // sort both the listofpoint and listofslope
            //TODO: this way of sorting list of double
            Collections.sort(listofslope);
            listofpoint.sort(p.slopeOrder());
            // Check if any 3 (or more) adjacent points in the sorted order have
            // equal slopes with respect to p. If so, these points, together with p,
            // are collinear.
            int l = 1;
            for (int k = 0;k<listofpoint.size();){
                // if the two slopes are different, then k should become l and l++

                //TODO: A solution to those complex situation: combine these two conditions together
                if ((l-k)<3 && l>=listofpoint.size())break;
                //TODO: Point is a class, should use equal to compare.
                if (l==listofpoint.size() || !listofslope.get(k).equals(listofslope.get(l)) ){
                    if ((l-k)>=3) {
                        //add it to the LineSegment[]
                        //Point p is known, and all other points could be get() from listofpoint
                        //still have to get those at the ends.
                        List<Point> pointstosort = new ArrayList<Point>();

                        // sort the Points and get those on the ends
                        pointstosort.add(p);
                        for (int z =k; z < l;z++){
                            pointstosort.add(listofpoint.get(z));
                        }

                        // sort it with the Point.compareTo()
                        pointcompare pointcom = new pointcompare();
                        pointstosort.sort(pointcom);
                        Point p1 = pointstosort.get(0);
                        Point p2 = pointstosort.get(pointstosort.size()-1);//the one at the last
                        LineSegment line = new LineSegment(p1,p2);
                        Listofline.add(line);
                    }
                    k = l;
                }
                //if the two slopes are the same, then k should stay the same and l++
                l++;
            }
        }
    }
    public int numberOfSegments(){
        // the number of line segments
        return Listofline.size();
    }
    public LineSegment[] segments(){
        // the line segments
        //TODO: it is important to add the new LineSegment[0]
        return (LineSegment[]) Listofline.toArray(new LineSegment[0]);
    }
    private static class pointcompare implements Comparator<Point> {
        public int compare(Point p1, Point p2){
            return p1.compareTo(p2);
        }
    }
}
