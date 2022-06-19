import java.util.ArrayList;
import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;


public class PointSET {
	private TreeSet<Point2D> vals;
	
   public  PointSET()     
   {
	   vals = new TreeSet<Point2D>();
	   // construct an empty set of points 
   }
   public           boolean isEmpty()     
   {
	   return vals.isEmpty();
   }
   public               int size()     
   {// number of points in the set 
	   return vals.size();
   }
   public              void insert(Point2D p) 
   
   {
	   if(p==null)
	   {
		   throw new IllegalArgumentException();
	   }
	   vals.add(p);
   }
   public   boolean contains(Point2D p) 
   {
	   if(p==null)
	   {
		   throw new IllegalArgumentException();
	   }
	   return vals.contains(p);
   }
   public   void draw()  
   {
	   // draw all points to standard draw 
   }
   public Iterable<Point2D> range(RectHV rect) 
   {
	   if(rect==null)
	   {
		   throw new IllegalArgumentException();
	   }
	   ArrayList<Point2D> arr = new ArrayList<Point2D>();
	   // all points that are inside the rectangle (or on the boundary) 
	   for(Point2D p : vals)
	   {
		   if(p.x() <= rect.xmax() && p.x() >= rect.xmin())
		   {
			   if(p.y() <= rect.ymax() && p.y() >= rect.ymin())
			   {
				   arr.add(p);
			   }
		   }
	   }
	   
	   return arr;
   }
   public           Point2D nearest(Point2D p)  
   {// a nearest neighbor in the set to point p; null if the set is empty 
	   if(p==null)
	   {
		   throw new IllegalArgumentException();
	   }
	   Point2D champion = null;
	   for(Point2D point : vals)
	   {
		   if(champion == null || p.distanceTo(point) < p.distanceTo(champion))
		   {
			   champion = point;
		   }
	   }
	   return champion;
   }

   public static void main(String[] args)  
   {// unit testing of the methods (optional) 
   }
   
}