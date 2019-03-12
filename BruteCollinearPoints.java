/* *****************************************************************************
 *  Name: BruteCollinearPoints
 *  Date: Mar 10, 2019
 *  Description: Brute force method to detect collinear points
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;


public class BruteCollinearPoints {
    // finds all line segments containing 4 points

    // private static final int Nfactorial = 24;
    // N factorial is 24 for N = 4; the max array size needed
    // private LineSegment[] segments = new LineSegment[Nfactorial];

    private int segTracker = 0;
    //  we will not supply any input to BruteCollinearPoints that has 5 or more collinear point
    private int segmentSize = 10;
    private LineSegment[] segmentArr = new LineSegment[segmentSize];


    private void generateCollinear (Point [] points) {

    }


    public BruteCollinearPoints(Point[] points) {
        // check for illegal arguments
        if (points == null) throw new IllegalArgumentException("Null values not alloved!");

        int size = points.length;

        // decide not to optimze this, will run 16x :(

        for (int i = 0; i < size; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Null points are not allowed");
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    if (points[i].compareTo(points[j]) == 0)
                        throw new IllegalArgumentException(
                                "Do not supply same points multiple times!");
                }
            }
        }


        // generate all line segment possibilities!
        // prevent duplicate entries
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j) continue;
                for (int k = 0; k < size; k++) {
                    if (k == j || k == i) continue;
                    for (int l = 0; l < size; l++) {
                        if (l == k || l == j || l == i) continue;
                        // code combinations here!
                        /*StdOut.print("i: " + i); // p
                        StdOut.print(" j: " + j); // q
                        StdOut.print(" k: " + k); // r
                        StdOut.print(" l: " + l); // s
                        StdOut.println(" end");*/

                        // set member i as the base point p;

                        if (points[i].compareTo(points[j]) <= 0) continue;
                        double pq = points[i].slopeTo(points[j]);

                        if (points[i].compareTo(points[k]) <= 0) continue;
                        double pr = points[i].slopeTo(points[k]);
                        if (pq != pr) continue;

                        if (points[i].compareTo(points[l]) <= 0) continue;
                        double ps = points[i].slopeTo(points[l]);
                        if (ps != pr) continue;

                        // otherwise, form a line segment with p->q or i/j
                        // added line segment
                        StdOut.println("Added line segment number " + segTracker);

                        // resize segment tracker

                        if (segTracker == segmentArr.length) {
                            LineSegment[] temp = new LineSegment[segmentArr.length * 2];
                            for (int resizeTracker = 0; resizeTracker < segmentArr.length;
                                 resizeTracker++) {
                                temp[resizeTracker] = segmentArr[resizeTracker];
                            }
                            segmentArr = temp;
                        }
                        segmentArr[segTracker++] = new LineSegment(points[i], points[l]);


                    }
                }
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return segTracker;
    }

    // the line segments
    public LineSegment[] segments() {
        StdOut.println(numberOfSegments());
        StdOut.println(segmentArr);
        return segmentArr;
    }

    public static void main(String[] args) {
        Point A = new Point(0, 0);
        Point B = new Point(2, 5);
        Point C = new Point(4, 5);
        Point D = new Point(100, 100);
        Point E = new Point(101, 400);
        Point[] points = { A, B, C, D, E };
        BruteCollinearPoints esTuBrute = new BruteCollinearPoints(points);


    }
}
