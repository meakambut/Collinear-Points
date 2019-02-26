/******************************************************************************
 *  Name:    Olga Soloveva
 *  NetID:   olgrit
 *  Precept: P03/1
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Brute Collinear Point
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints
{
   private LineSegment[] segmentsArray = new LineSegment[0];
   private int numOfSegments = 0;

   public BruteCollinearPoints(Point[] points)
   {
     if (points == null) throw new java.lang.IllegalArgumentException();
     int n = points.length;

     for (int i = 0; i < n; i++)
       if (points[i] == null) throw new java.lang.IllegalArgumentException();
     
     sort(points); 
     for (int i = 0; i < n - 1; i++)
       if (points[i].compareTo(points[i+1]) == 0)
         throw new java.lang.IllegalArgumentException();
    
     for (int i = 0; i < n - 3; i++)
       for (int j = i + 1; j < n - 2; j++)
         for (int k = j + 1; k < n - 1; k++)
           for (int l = k + 1; l < n; l++)
           {
             Point p = points[i];
             Point q = points[j];
             Point r = points[k];
             Point s = points[l];
             if (p.slopeTo(q) == p.slopeTo(r))
               if (p.slopeTo(q) == p.slopeTo(s))
               {
                 numOfSegments++;
                 Point[] a = new Point[4];
                 a[0] = p; a[1] = q; a[2] = r; a[3] = s;  
                 sort(a);
                 segmentsArray = add(segmentsArray, a[0], a[3]);
               }
           }
   }

   private void sort(Point[] array)
   {
     for (int i = 0; i < array.length; i++)      
       for (int j = i; j > 0 && (array[j].compareTo(array[j-1]) > 0); j--)  
         {        
           Point temp = array[j-1];
           array[j-1] = array[j];
           array[j] = temp; 
         }
   } 

   private LineSegment[] add(LineSegment[] segmentsArray, Point p, Point q)
   {
     if (segmentsArray == null)
     {
      segmentsArray = new LineSegment[1];
      segmentsArray[0] = new LineSegment(p, q);
      return segmentsArray;
     }
     int n = segmentsArray.length;
     LineSegment[] copy = new LineSegment[n+1];    
     for (int i = 0; i < n; i++)       
       copy[i] = segmentsArray[i];    
     copy[n] = new LineSegment(p, q); 
     segmentsArray = copy;
     return segmentsArray;
   }

   public int numberOfSegments()  
   {
     return numOfSegments;
   }

   public LineSegment[] segments()    
   {
     LineSegment[] copy = segmentsArray;
     return copy; 
   }
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
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768); 
    for (Point p : points) {
        System.out.println(p.toString());
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    System.out.println(collinear.numberOfSegments());
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}