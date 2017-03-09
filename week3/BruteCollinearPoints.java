import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */
public class BruteCollinearPoints {
    private List<LineSegment> listofline;
    public BruteCollinearPoints(Point[] points){
        // finds all line segments containing 4 points
        if (points == null) throw new NullPointerException();
        int numbers = points.length;
        listofline = new ArrayList<>();
        for (int i = 0; i<numbers-3;i++){
            Point p = points[i];
            if (p == null) throw new NullPointerException();

            for (int j = i+1; j<numbers-2;j++){
                Point q = points[j];
                if (p.equals(q)) throw new IllegalArgumentException();
                if (q == null) throw new NullPointerException();
                for (int k = j+1; k<numbers-1;k++){
                    Point r = points[k];
                    if (r == null) throw new NullPointerException();
                    if (r.equals(q) || r.equals(p)) throw new IllegalArgumentException();
                    for (int l = k+1; l<numbers;l++){
                        Point s = points[l];
                        if (s == null) throw new NullPointerException();
                        if (s.equals(q) || s.equals(p) || s.equals(r)) throw new IllegalArgumentException();
                        double slope1 = p.slopeTo(q);
                        double slope2 = p.slopeTo(r);
                        double slope3 = p.slopeTo(s);
                        double error = Double.NEGATIVE_INFINITY;
                        // throw if some points are exactly the same
                        if (slope1 == error ||slope2 == error || slope3 == error )
                            throw new IllegalArgumentException();
                        if (slope1 == slope2 && slope1 == slope3){
                            List<Point> listofpoint = new ArrayList<Point>();
                            listofpoint.add(p);
                            listofpoint.add(q);
                            listofpoint.add(r);
                            listofpoint.add(s);

                            // sort it with the Point.compareTo()
                            pointcompare pointcom = new pointcompare();
                            listofpoint.sort(pointcom);
                            Point p1 = listofpoint.get(0);
                            Point p2 = listofpoint.get(3);
                            LineSegment line = new LineSegment(p1,p2);
                            if (!listofline.contains(line))
                                listofline.add(line);
                        }
                    }
                }
            }
        }

    }
    private static class pointcompare implements Comparator<Point> {
        public int compare(Point p1, Point p2){
            return p1.compareTo(p2);
        }
    }
    public int numberOfSegments(){
        // the number of line segments
        return listofline.size();
    }

    public LineSegment[] segments(){
        // the line segments
        //TODO: it is important to add the new LineSegment[0]
        LineSegment[] lineS = (LineSegment[]) listofline.toArray(new LineSegment[0]);
        return lineS;
    }

}
