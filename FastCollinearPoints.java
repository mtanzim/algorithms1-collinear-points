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

    private void resizeSegmentArr() {
        LineSegment[] temp = new LineSegment[segmentArr.length * 2];
        for (int resizeTracker = 0; resizeTracker < segmentArr.length;
             resizeTracker++) {
            temp[resizeTracker] = segmentArr[resizeTracker];
        }
        segmentArr = temp;
    }

    private void generateCollinearFast(Point[] points, int size) {
        StdOut.println("Fast method!");
        // StdOut.println("Sorted for point: " + i + ": " + points[i].toString());
        for (int i = 0; i < size; i++) {
            // for (int j = 0; j < size; j++) {
            //     if (i == j) continue;
            if (i > 0) Arrays.sort(points, 0, i, points[i].slopeOrder());
            if (i < size - 1) Arrays.sort(points, i + 1, size, points[i].slopeOrder());
            StdOut.println("Sorted for point " + i + ": " + points[i].toString());
            for (Point point : points) {
                StdOut.print(point.toString() + " -> ");
            }
            StdOut.println(points);
            // }
        }

        // return null;
    }


    private void checkErrors(Point[] points, int size) {
        // check for illegal arguments
        if (points == null) throw new IllegalArgumentException("Null values not alloved!");

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
    }


    public FastCollinearPoints(Point[] points) {
        checkErrors(points, points.length);
        // sort the array by slopes

        generateCollinearFast(points, points.length);

    }

    // the number of line segments
    public int numberOfSegments() {
        return segTracker;
    }

    // the line segments
    public LineSegment[] segments() {
        StdOut.println(numberOfSegments());
        StdOut.println(segmentArr);
        return segmentArr.clone();
    }

    public static void main(String[] args) {
        Point A = new Point(0, 0);
        Point B = new Point(1, 1);
        Point C = new Point(1, 2);
        Point D = new Point(1, 3);
        Point E = new Point(1, 4);
        // Point[] points = { A, B, C, D, E };
        Point[] points = { C, A, B, E, D };
        FastCollinearPoints usainBolt = new FastCollinearPoints(points);


    }
}
