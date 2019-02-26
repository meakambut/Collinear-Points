/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     
    private final int y;  
    // private double EPSILON = 0.000001;   

    public Point(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }

    public void draw() 
    {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) 
    {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) 
    {
        // if (((this.x - that.x < EPSILON) && (that.x - this.x < EPSILON)) 
          // && ((this.y - that.y < EPSILON) && (that.y - this.y < EPSILON))) 
            if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
          // if ((this.y - that.y < EPSILON) && (that.y - this.y < EPSILON)) 
          if (that.y == this.y) return +0.0;
          else //if ((this.x - that.x < EPSILON) && (that.x - this.x < EPSILON)) 
            if (this.x == that.x) return Double.POSITIVE_INFINITY;
               else return (double) (that.y - this.y)/(that.x - this.x);  
    }

    public int compareTo(Point that) 
    {
        if (that == null) throw new java.lang.NullPointerException();
        if (this.y < that.y) return -1;
        else if (this.y > that.y) return 1;
             else if (this.x < that.x) return -1;
                  else if (this.x > that.x) return 1;
                       else return 0;
    }

    public Comparator<Point> slopeOrder()
    {
      return new slopeOrder();
    }
    
    private class slopeOrder implements Comparator<Point>   
    {      
      public int compare(Point q1, Point q2)      
      {         
        Point elem = new Point(x, y);
        if (elem.slopeTo(q1) > elem.slopeTo(q2)) return 1;
        else if (elem.slopeTo(q1) < elem.slopeTo(q2)) return -1;
             else return 0;   
      } 

      

    }

    public String toString() 
    {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {}
}