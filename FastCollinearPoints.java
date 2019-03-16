/* *****************************************************************************
 *  Name: FastCollinearPoints
 *  Date: Mar 12, 2019
 *  Description: Fast method to detect collinear points
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


public class FastCollinearPoints {
    // finds all line segments containing 4 points

    // private static final int Nfactorial = 24;
    // N factorial is 24 for N = 4; the max array size needed
    // private LineSegment[] segments = new LineSegment[Nfactorial];

    private int segTracker = 0;
    private int segmentSize = 10;
    private LineSegment[] segmentArr = new LineSegment[segmentSize];

    public FastCollinearPoints(Point[] points) {

        // check for illegal arguments
        if (points == null) throw new IllegalArgumentException("Null values not alloved!");
        int size = points.length;
        for (int i = 0; i < size; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Null points are not allowed");
            for (int j = 0; j < size; j++) {
                if (points[j] == null)
                    throw new IllegalArgumentException("Null points are not allowed");
                if (i != j) {
                    if (points[i].compareTo(points[j]) == 0)
                        throw new IllegalArgumentException(
                                "Do not supply same points multiple times!");
                }
            }
        }
        // sort the array by slopes
        generateCollinearFast(points, points.length);

    }

    private void resizeSegmentArr() {
        LineSegment[] temp = new LineSegment[segmentArr.length * 2];
        for (int resizeTracker = 0; resizeTracker < segmentArr.length;
             resizeTracker++) {
            temp[resizeTracker] = segmentArr[resizeTracker];
        }
        segmentArr = temp;
    }

    private void generateCollinearFast(Point[] points, int size) {


        // StdOut.println("Sorted for point: " + i + ": " + points[i].toString());
        // Points[] orig = points.clone();
        for (int i = 0; i < size; i++) {
            // StdOut.println("i: " + i + ", " + points[i]);
            // always place current item in the 0th position, and sort
            Point temp = points[i];
            points[i] = points[0];
            points[0] = temp;

            // clone the array
            Point[] sorted = points.clone();
            // sort
            Arrays.sort(sorted, 1, size, temp.slopeOrder());

            // for debug only
            /*for (int j = 1; j < size; j++) {
                StdOut.print(sorted[j].toString() + " -> ");
            }
            StdOut.println("");*/

            double prevSlope = temp.slopeTo(sorted[1]);
            // int prevK = 0;
            int segLenTracker = 0;
            // StdOut.println("first slope: " + prevSlope);

            for (int k = 2; k < size; k++) {
                double newSlope = temp.slopeTo(sorted[k]);
                // StdOut.println("Current slope: " + newSlope);
                if (prevSlope == newSlope) {
                    segLenTracker++;
                    // continue;
                    // StdOut.println("Current segment streak: " + segLenTracker);
                }
                // also check if array is finished to add unfinished line segments
                if (prevSlope != newSlope || k == size - 1) {
                    if (segLenTracker > 1) {
                        if (segTracker == segmentArr.length) {
                            resizeSegmentArr();
                        }
                        //add previous point as the last point to be colliear
                        // StdOut.println("Adding segment of length: " + segLenTracker);
                        Point lastPoint;
                        if (k == size - 1 && prevSlope == newSlope) {
                            lastPoint = sorted[k];
                        }
                        else {
                            lastPoint = sorted[k - 1];
                        }

                        segmentArr[segTracker++] = new LineSegment(temp, lastPoint);
                        StdOut.println("Created line segment with points " + temp.toString() + "->"
                                               + lastPoint.toString());

                        // segmentSize++;
                    }
                    // reset to zero
                    segLenTracker = 0;
                }

                prevSlope = newSlope;


            }


            // StdOut.println("");
        }

        // return null;
    }


    // the number of line segments
    public int numberOfSegments() {
        return segTracker;
    }

    // the line segments
    public LineSegment[] segments() {
        // StdOut.println(numberOfSegments());
        // StdOut.println(segmentArr);
        LineSegment[] segmentCloned = new LineSegment[segTracker];
        int cloneTracker = 0;
        for (LineSegment segment : segmentArr) {
            if (segment != null) {
                segmentCloned[cloneTracker] = segment;
                cloneTracker++;
            }
        }
        // ensure all values were copied over
        assert (cloneTracker == segTracker);

        return segmentCloned;
    }

    public static void main(String[] args) {
        Point A = new Point(0, 0);
        Point B = new Point(1, 1);
        Point C = new Point(1, 2);
        Point D = new Point(1, 3);
        Point E = new Point(1, 4);
        Point NULLIDO = null;
        // Point[] points = { A, B, C, D, E };
        // Point[] points = { C, B, E, A, D };
        Point[] points = { C, A, E, B, D };
        // Point[] pointsWithNullido = { C, A, NULLIDO, B, D };
        // FastCollinearPoints usainBoltAndNullido = new FastCollinearPoints(pointsWithNullido);
        FastCollinearPoints usainBolt = new FastCollinearPoints(points);
        // FastCollinearPoints usainBoltNull = new FastCollinearPoints(null);


    }
}
