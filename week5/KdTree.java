import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Created by Administrator on 2017/2/15.
 */
public class KdTree {
    private Node root;
    private int size;
    public KdTree(){
        // construct an empty set of points
        size = 0;
        root = null;
    }
    public boolean isEmpty(){
        // is the set empty?
        return (size == 0);
    }
    public int size() {
        // number of points in the set
        return size;
    }
    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) throw new NullPointerException();


        if (root == null) {
            root = new Node(p.x(),p.y(),null);
            size++;
        }
        else if (!contains(p)){// if not contains p
            size++;
            Node pointer = root;
            Node parent = null;
            int left0right1 = -1;
            while (pointer!=null){
                //store the parent of the last pointer
                parent = pointer;

                int cmp = compare(pointer,p);
                if (cmp<0) {
                    pointer = pointer.RightNode;
                    left0right1 = 1;
                }else {
                    pointer = pointer.LeftNode;
                    left0right1 = 0;
                }
            }
            if (left0right1 == 0) {
                parent.insertLeft(p);
            } else {parent.insertRight(p);}
        }
    }
    public boolean contains(Point2D p){
        // does the set contain point p?
        if (p == null) throw new NullPointerException();
        Node pointer = root;
        while (pointer != null){
            //break the loop
            int cmp = compare(pointer,p);
            if (cmp == 0) return true;
            else if (cmp > 0) pointer = pointer.LeftNode;
            else pointer = pointer.RightNode;
        }
        return false;
    }
    private int compare(Node x, Point2D p){
        if (x.level % 2 == 1 ){
            // it would be wrong if two point has the same value
            // if x is the same, compare y.
            //TODO: using compare() will save a lot of trouble
            //TODO: so you don't have to use nesting if-statements
            // compare one after the other will free you from getting into trouble searching
            int cmp =Double.compare(x.LeftVal,p.x());
            if (cmp!=0) return cmp;
            cmp = Double.compare(x.RightVal,p.y());
            return cmp;
        }
        else{
            int cmp =Double.compare(x.RightVal,p.y());
            if (cmp!=0) return cmp;
            cmp = Double.compare(x.LeftVal,p.x());
            return cmp;
        }
    }
    public void draw() {
        // draw all points to standard draw
        // Your draw() method should draw all of the points to standard draw
        // in black and the subdivisions in red (for vertical splits) and blue
        // (for horizontal splits). This method need not be efficient—it is primarily
        // for debugging.
        drawrecursively(root);
    }
    private void drawrecursively(Node pointer){
        //end the loop
        if (pointer == null) return;

        StdDraw.point(pointer.LeftVal, pointer.RightVal);


        if (pointer.level % 2 == 1){
            //red
            StdDraw.setPenColor(Color.red);
            if (pointer.level == 1) {
                StdDraw.line(pointer.LeftVal, 0, pointer.LeftVal, 1);
            }else {

                //the thing that counts is the direction of pointer to its parent
                //is different from one of the ancestor's (same mod 2) direction to its parent.
                //if they are different, then the range should between
                // parent and ancestor's parent
                double boarder1 = findAncestor(pointer);
                double boarder2 = pointer.Parent.RightVal;
                //TODO: math.min saved a lot of work!!!!! no more confusing situation discussions
                StdDraw.line(pointer.LeftVal, Math.min(boarder1, boarder2),
                        pointer.LeftVal, Math.max(boarder1, boarder2));
            }
        }else {
            //blue
            StdDraw.setPenColor(Color.blue);

            double boarder1 = findAncestor(pointer);
            double boarder2 = pointer.Parent.LeftVal;
            StdDraw.line(Math.min(boarder1,boarder2), pointer.RightVal,
                    Math.max(boarder1,boarder2), pointer.RightVal);
        }
        drawrecursively(pointer.LeftNode);
        drawrecursively(pointer.RightNode);
    }

    private static double findAncestor(Node node){
        // loop until find the ancestor's parent with different direction
        Node pointer = node;
        int dir1;//if left 0, if right 1
        int dir2;
        //get the direction 1
        if (pointer.Parent.LeftNode!=null && pointer.Parent.LeftNode.equals(pointer))
            dir1=0;
        else dir1=1;

        dir2 = dir1;
        while (pointer.Parent.Parent != null && pointer.Parent.Parent.Parent != null){
            //TODO: interesting: end the iteration at here

            //iterate
            pointer = pointer.Parent.Parent;
            if (pointer.Parent.LeftNode!=null && pointer.Parent.LeftNode.equals(pointer))
                dir2=0;
            else dir2=1;
            //end the iteration
            if (dir1 != dir2){
                if (pointer.level %2 ==1) return pointer.Parent.RightVal;
                else return pointer.Parent.LeftVal;
            }
        }
            //return boarder
            if (pointer.level %2==1 && dir2 == 0){
                return 0;
            }
            else if (pointer.level %2==1 && dir2 == 1){
                return 1;
            }
            else if (pointer.level %2==0 && dir2 == 0){
                return 0;
            }
            else {
                return 1;
            }
    }

    private Stack<Point2D> stackForRange;

    public Iterable<Point2D> range(RectHV rect){
        // all points that are inside the rectangle
        if (rect == null) throw new NullPointerException();
        stackForRange = new Stack<>();
        searchinterval(root,rect);
        return stackForRange;
    }
    private void searchinterval(Node pointer,RectHV rect){
        // recursively search for all pointers that are in this rect
        if (pointer == null) return;

        if (pointer.level %2==1){
            if (pointer.LeftVal < rect.xmin()){searchinterval(pointer.RightNode,rect);}
            else if (pointer.LeftVal > rect.xmax()){searchinterval(pointer.LeftNode,rect);}
            else {
                // added =
                //TODO: remember to watch out for when == happens
                if (pointer.RightVal<= rect.ymax() && pointer.RightVal >=rect.ymin())
                    stackForRange.add(new Point2D(pointer.LeftVal,pointer.RightVal));
                searchinterval(pointer.LeftNode,rect);
                searchinterval(pointer.RightNode,rect);
            }
        }
        else {
            if (pointer.RightVal < rect.ymin()){searchinterval(pointer.RightNode,rect);}
            else if (pointer.RightVal > rect.ymax()){searchinterval(pointer.LeftNode,rect);}
            else {
                if (pointer.LeftVal >= rect.xmin() && pointer.LeftVal <= rect.xmax())
                    stackForRange.add(new Point2D(pointer.LeftVal,pointer.RightVal));
                searchinterval(pointer.LeftNode,rect);
                searchinterval(pointer.RightNode,rect);
            }
        }
    }
    private double nearestdis;
    private Point2D NearestPoint;
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new NullPointerException();
        nearestdis = Double.MAX_VALUE;
        RectHV rect = new RectHV(0.0,0.0,1.0,1.0);
        findnearest(p,root,rect);
        return NearestPoint;
    }
    private void findnearest(Point2D p,Node pointer, RectHV rect){
        //end the recursion
        if (pointer == null) return;
        Point2D newNearestPoint = new Point2D(pointer.LeftVal,pointer.RightVal);
        //value update
        if (newNearestPoint.distanceTo(p) < nearestdis) {
            NearestPoint = newNearestPoint;
            nearestdis = newNearestPoint.distanceTo(p);
        }


        if (pointer.level %2==1){
            RectHV rectLeft = new RectHV(rect.xmin(),rect.ymin(),pointer.LeftVal,rect.ymax());
            RectHV rectRight = new RectHV(pointer.LeftVal,rect.ymin(),rect.xmax(),rect.ymax());
            if (nearestdis >= rectLeft.distanceTo(p)){
                findnearest(p,pointer.LeftNode,rectLeft);
            }
            if (nearestdis >= rectRight.distanceTo(p)){
                findnearest(p,pointer.RightNode,rectRight);
            }
        }
        else {
            RectHV rectUp = new RectHV(rect.xmin(),pointer.RightVal,rect.xmax(),rect.ymax());
            RectHV rectDown = new RectHV(rect.xmin(),rect.ymin(),rect.xmax(),pointer.RightVal);
            if (nearestdis >= rectUp.distanceTo(p)){
                findnearest(p,pointer.RightNode,rectUp);
            }
            if (nearestdis >= rectDown.distanceTo(p)){
                findnearest(p,pointer.LeftNode,rectDown);
            }
        }
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
        String filename = args[0];
        edu.princeton.cs.algs4.In in = new In(filename);

        // initialize the data structures with N points from standard input
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        System.out.println(kdtree.isEmpty());
        Point2D p = new Point2D(
                0.3,0.2);
        System.out.println(kdtree.nearest(p));
        RectHV rect = new RectHV(0.0,0.0,0.6,0.6);
        System.out.println(kdtree.range(rect));
        System.out.println(kdtree.size());
        System.out.println(kdtree.contains(p));
    }

    private class Node{
        private double LeftVal;
        private double RightVal;
        private Node Parent;
        private Node LeftNode;
        private Node RightNode;
        private int level;
        public Node(double val1,double val2,Node parent){
            LeftVal = val1;
            RightVal = val2;
            Parent = parent;
            if(parent == null) level=1;
            else level = parent.level+1;
        }
        public void insertRight(Point2D p){
            if (this.RightNode != null) throw new IllegalArgumentException();
            Node rightNode = new Node(p.x(),p.y(),this);
            this.RightNode = rightNode;
        }
        public void insertLeft(Point2D p){
            if (this.LeftNode != null) throw new IllegalArgumentException();
            Node leftNode = new Node(p.x(),p.y(),this);
            this.LeftNode = leftNode;
        }
    }
}
