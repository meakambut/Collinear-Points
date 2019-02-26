import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
public class FastCollinearPoints 
{
  private int numOfSegments = 0;
  private Point[] pointsInSegments = new Point[0];;

  public FastCollinearPoints(Point[] points)    
  {
    if (points == null) throw new java.lang.IllegalArgumentException();
    int n = points.length;
    Point[] copyArray = new Point[n];
    for (int i = 0; i < n; i++)
    {
      if (points[i] == null) throw new java.lang.IllegalArgumentException();
      copyArray[i] = points[i];
    }
    sort(points);
    for (int i = 0; i < n - 1; i++)
      if (points[i].compareTo(points[i+1]) == 0)
         throw new java.lang.IllegalArgumentException();

    if (points[n-1] == null) throw new java.lang.IllegalArgumentException();
    copyArray[n-1] = points[n-1];

    for (int ind = 0; ind < points.length; ind++)
    { 
      Point q = copyArray[ind];

      mergeSort(points, q);
      double[] slopes = new double[points.length];

      for (int i = 0; i < points.length; i++)
      {
        slopes[i] = q.slopeTo(points[i]);
        if (i > 0 && slopes[i] == Double.NEGATIVE_INFINITY) throw new java.lang.IllegalArgumentException();
      }

      int[] equal = threeEqualElemInArray(slopes);

      for (int i = 0; i < equal.length; i++)
      {
        
        int ind1 = equal[i], ind2 = equal[++i];
        int count = ind2 - (ind1 - 1) + 1;

        if (ind1 != ind2)
        {
          Point[] a = new Point[count];
          a[0] = q; 
          for (int j = 1; j < count; j++)
            a[j] = points[ind1 + j - 1];

          sort(a);
          if (!isPointInSegments(q, pointsInSegments, slopes[ind1]))
            {
              numOfSegments++;
              pointsInSegments = add(pointsInSegments, a[0], a[count - 1]);
            }       
         }
       }
    }
  }  
  
  private static void mergeSort(Point[] points, Point q)
  {
    Comparator<Point> comp = q.slopeOrder();
    Point[] aux = new Point[points.length];
    mergeSort(points, aux, 0, points.length-1, comp);
  }
  
  private static void mergeSort(Point[] points, Point[] aux, int lo, int hi, Comparator<Point> comp)
  {
    if (lo >= hi) return;
    int mid = lo + (hi - lo)/2;
    mergeSort(points, aux, lo, mid, comp);
    mergeSort(points, aux, mid + 1, hi, comp);
    merge(points, aux, lo, mid, hi, comp);
  }

  private static void merge(Point[] points, Point[] aux, int lo, int mid, int hi, Comparator<Point> comp)
  {       
    assert isSorted(points, lo, mid, comp);
    assert isSorted(points, mid + 1, hi, comp);

    int i = lo, j = mid + 1;
    for (int k = lo; k <= hi; k++)
      aux[k] = points[k];
    for (int k = lo; k <= hi; k++)
    {
      if (i > mid) points[k] = aux[j++];
      else if (j > hi) points[k] =  aux[i++];
           else if (comp.compare(aux[j], aux[i]) < 0) points[k] = aux[j++]; 
                 else points[k] = aux[i++];
    } 

    assert isSorted(points, lo, hi, comp);
  }

  private static boolean isSorted(Point[] points, int lo, int hi, Comparator<Point> comp)
    {
      for (int i = lo; i < hi - 1; i++)
        if (comp.compare(points[i], points[i+1]) > 1)
          return false;
      return true;
    }
 
  private Point[] add(Point[] array, Point p, Point q)
  {
    if (array == null)
    {
      Point[] copy = new Point[2];
      copy[0] = p;
      copy[1] = q;
      return copy; 
    }
    int n = array.length;
    Point[] copy = new Point[n+2];    
    for (int i = 0; i < n; i++)       
      copy[i] = array[i];    
    array = copy; 
    array[n] = p;
    array[++n] = q; 
    return array; 
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

   private int[] threeEqualElemInArray(double[] array)
   {
     int[] res = new int[2];
     res[0] = 0; res[1] = 0;
     for (int i = 0; i < array.length; i++)
     {
       double tmp = array[i];
       int j = i;
       int count = 1;
       while (array[j] == tmp && j < array.length - 1)
       {	
         count++;
         j++;
       }
       if (array[j] == tmp)
       {
         count++;
         j++;
       }
       if (count > 3)
       {
         int n = res.length;
         int[] copy = new int[n + 2];
         for (int k = 0; k < n; k++)
           copy[k] = res[k]; 
         copy[n - 2] = i;
         copy[n - 1] = j - 1;
         res = copy;
       }
     }
     return res;
   }

   private boolean isPointInSegments(Point point, Point[] array, double slope)
   {
     if (array == null)
       return false;
     for (int i = 0; i < array.length; i+=2)
     {
       double tmp = array[i].slopeTo(array[i+1]);
       if ((point.slopeTo(array[i]) == tmp || point.slopeTo(array[i+1]) == tmp) && (tmp == slope))
         return true;
     }
     return false;
   }

   public int numberOfSegments()        
   { 
     return numOfSegments;
   }

   public LineSegment[] segments()              
   {
     LineSegment[] segmentsArray = new LineSegment[numOfSegments];
     for (int i = 0, j = 0; i < pointsInSegments.length; j++, i++)
       segmentsArray[j] = new LineSegment(pointsInSegments[i], pointsInSegments[++i]);
     return segmentsArray;
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    System.out.println(collinear.numberOfSegments());
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show(); }
}