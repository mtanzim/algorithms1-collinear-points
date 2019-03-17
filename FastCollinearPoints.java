/* *****************************************************************************
 *  Name: FastCollinearPoints
 *  Date: Mar 12, 2019
 *  Description: Fast method to detect collinear points
 **************************************************************************** */

import java.util.ArrayList;
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

        for (int i = 0; i < size; i++) {
            // StdOut.println("\ni: " + i + ", " + points[i]);
            // always place current item in the 0th position, and sort
            Point temp = points[i];
            points[i] = points[0];
            points[0] = temp;
            // clone the array
            Point[] sorted = points.clone();
            // sort
            Arrays.sort(sorted, 1, size, temp.slopeOrder());

            // StdOut.println("Checking remaining points: ");
            // for (Point curDebugPoint : sorted) {
            // StdOut.print(curDebugPoint + " -> ");
            // }

            // StdOut.println("\n");
            double prevSlope = temp.slopeTo(sorted[1]);
            // StdOut.println("Current slope: " + prevSlope);
            int segLenTracker = 1;
            ArrayList<Integer> currentSegmentList = new ArrayList<Integer>();
            currentSegmentList.add(i);
            int minSeg = 2;
            // int curStart =  2;
            for (int k = 2; k < size; k++) {
                double newSlope = temp.slopeTo(sorted[k]);
                // StdOut.println("k: " + k + ", " + sorted[k]);
                // StdOut.println("Current slope: " + newSlope);
                // slope doesn't match, or the end of array
                if (prevSlope != newSlope || k == size - 1) {
                    // StdOut.println("Current segment streak: " + segLenTracker);


                    // case when we're at the end of the array
                    int scopedMinSeg = minSeg;
                    Point lastPoint = sorted[k - 1];
                    if (k == size - 1 && prevSlope == newSlope) {
                        lastPoint = sorted[k];
                        scopedMinSeg -= 1;
                        currentSegmentList.add(k);
                    }

                    // ensure we're not double counting by disregaring "negative slopes"
                    // if (segLenTracker > scopedMinSeg && temp.compareTo(lastPoint) <= 0) {
                    if (segLenTracker > scopedMinSeg && temp.compareTo(lastPoint) <= 0) {
                        Point maxPoint = lastPoint;
                        Point minPoint = temp;
                        for (int innerCurSegTracker : currentSegmentList) {
                            if (sorted[innerCurSegTracker].compareTo(maxPoint) > 0)
                                maxPoint = sorted[innerCurSegTracker];

                            if (sorted[innerCurSegTracker].compareTo(minPoint) <= 0)
                                minPoint = sorted[innerCurSegTracker];
                        }

                        if (segTracker == segmentArr.length) {
                            resizeSegmentArr();
                        }
                        segmentArr[segTracker] = new LineSegment(minPoint, maxPoint);
                        segTracker++;
                        // StdOut.println(
                        // "\nCreated line segment with points " + temp.toString() + "->"
                        //         + maxPoint.toString() + " and streak of: "
                        //         + segLenTracker);
                    }
                    segLenTracker = 1;
                    currentSegmentList.clear();
                    currentSegmentList.add(i);

                }
                else {
                    segLenTracker++;
                    currentSegmentList.add(k);
                    // StdOut.println("Current streak: " + segLenTracker);
                }
                prevSlope = newSlope;

            }

        }

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
                // StdOut.println(segment.toString());
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
