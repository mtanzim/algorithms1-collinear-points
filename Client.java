/* *****************************************************************************
 *  Name: Client.java
 *  Date: Mar 12, 2019
 *  Description: Client to test
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Client {

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-2000, 32768);
        StdDraw.setYscale(-2000, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();


        FastCollinearPoints collinearFast = new FastCollinearPoints(points);
        StdOut.println("\nUsing fast segments!");
        for (LineSegment segment : collinearFast.segments()) {
            // if (segment != null) {
            // StdOut.println(segment);

            StdOut.println(segment);
            segment.draw();
            // }
        }
        StdOut.println(collinearFast.numberOfSegments());
        StdDraw.show();


        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        // FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println("Using brute segments!");
        for (LineSegment segment : collinear.segments()) {
            // if (segment != null) {
            // StdOut.println(segment);

            StdOut.println(segment);
            // segment.draw();
            // }
        }
        StdOut.println(collinear.numberOfSegments());
    }
}
