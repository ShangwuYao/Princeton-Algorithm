/**
 * Created by Administrator on 2017/2/15.
 */
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PointSET {
    private List<Point2D> listOfPoints;
    public PointSET(){
        // construct an empty set of points
        listOfPoints = new ArrayList<>();
    }
    public boolean isEmpty(){
        // is the set empty?
        return listOfPoints.isEmpty();
    }
    public int size() {
        // number of points in the set
        return listOfPoints.size();
    }
    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) throw new NullPointerException();
        if (!listOfPoints.contains(p)) listOfPoints.add(p);
    }
    public boolean contains(Point2D p){
        // does the set contain point p?
        if (p == null) throw new NullPointerException();
        if (listOfPoints.contains(p)) return true;
        else return false;
    }
    public void draw() {
        // draw all points to standard draw
        //TODO: Your draw() method should draw all of the points to standard draw
        // in black and the subdivisions in red (for vertical splits) and blue
        // (for horizontal splits). This method need not be efficient—it is primarily
        // for debugging.
        for (Point2D p : listOfPoints){
            StdDraw.point(p.x(),p.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect){
        // all points that are inside the rectangle
        if (rect == null) throw new NullPointerException();
        Stack<Point2D> pstack = new Stack<>();
        for (Point2D p : listOfPoints){
            if (isInRect(p,rect)) pstack.push(p);
        }
        return pstack;
    }
    private static boolean isInRect(Point2D point, RectHV rect){
        if ((point.x() <= rect.xmax()) && (point.x() >= rect.xmin())
                && (point.y() <= rect.ymax()) && (point.y() >= rect.ymin()))
            return true;
        else return false;
    }
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new NullPointerException();
        double mindistance = Double.MAX_VALUE;
        Point2D minpoint = p;
        for (Point2D otherp : listOfPoints){
            //if (p.equals(otherp)) { continue;}
            if (p.distanceTo(otherp) < mindistance) {
                mindistance = p.distanceTo(otherp);
                minpoint = otherp;
            }
        }
        return minpoint;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
        String filename = args[0];
        edu.princeton.cs.algs4.In in = new In(filename);

        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
        System.out.println(brute.isEmpty());
        Point2D p = new Point2D(0,0.51);
        System.out.println(brute.nearest(p));
        RectHV rect = new RectHV(0.0,0.0,0.6,0.6);
        System.out.println(brute.range(rect));
        System.out.println(brute.size());
        System.out.println(brute.contains(p));
    }
}
