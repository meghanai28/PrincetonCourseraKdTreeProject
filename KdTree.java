import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	
	private class Node 
	{
		public boolean vertical;
		public Point2D value;
		public Node right;
		public Node left;
		public RectHV rect;
		
		public Node()
		{
			vertical = true;
			value = null;
			right = null;
			left = null;
			rect = null;

		}
		
		public Node(boolean ver, Point2D val, RectHV rec)
		{
			vertical = ver;
			value = val;
			rect = rec;

		}
		
		
	}
	
	private Node root;
	private int size;
	private Node champion;
   
	public         KdTree()
   {
	   root = null;
	   size = 0;
   }
	
   public  boolean isEmpty()    
   {
	  if(root == null)
	  {
		  return true;
	  }
	  else
	  {
		  return false;
	  }
   }
   
   public  int size()     
   {
	   return size;
   }
   
   public void insert(Point2D p)  
   {
	   if(p==null)
	   {
		   throw new IllegalArgumentException();
	   }
	   root = insert(root,p,true,1,0,1,0);
	  
   }
   
   private int compare(Point2D one,Point2D two, boolean vertical) {
		if(vertical == true)
		{
			if(one.x()>two.x())
			{
				return -1;
			}
			else if(one.x()<two.x())
			{
				return 1;
			}
			else
			{
				if(one.y()>two.y())
				{
					return -1;
				}
				else if(one.y()<two.y())
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
		}
		else
		{
			if(one.y()>two.y())
			{
				return -1;
			}
			else if(one.y()<two.y())
			{
				return 1;
			}
			else
			{
				if(one.x()>two.x())
				{
					return -1;
				}
				else if(one.x()<two.x())
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
		}
   }
   
   private Node insert(Node x, Point2D p, Boolean ver, double xmax, double xmin, double ymax, double ymin)
   {
	   if(x==null)
	   {
		   size++;
		   RectHV rectangle = new RectHV(xmin,ymin,xmax,ymax);
		   return new Node(ver,p,rectangle);
	   }
	   int comp = compare(x.value, p, ver);
	   
	   if(comp<0)
	   {
		   if(!x.vertical != true)
		   {
			   x.left = insert(x.left,p, !ver, x.value.x(),xmin,ymax,ymin );
		   }
		   else
		   {
			   x.left = insert(x.left,p, !ver,xmax,xmin,x.value.y(),ymin);
		   }
	   }
	   else if(comp>0)
	   {
		   if(!x.vertical != true)
		   {
			   x.right = insert(x.right,p, !ver, xmax ,x.value.x(), ymax ,ymin );
		   }
		   else
		   {
			   x.right = insert(x.right,p, !ver, xmax, xmin, ymax, x.value.y() );
		   }
	   }
	   else
	   {
		   x.value = p;
	   }
	   return x;
   }
   
   public boolean contains(Point2D p)  
   {// does the set contain point p? 
	   if(p==null)
	   {
		   throw new IllegalArgumentException();
	   }
	   if(isEmpty())
	   {
		   return false;
	   }
	   Node x = root;
	   Boolean ver = !root.vertical;
	   while(x!=null)
	   {
		   ver = !ver;
		   int comp = compare(x.value,p,ver);
		  
		   if(comp<0) 
		   {
			   x= x.left;
		   }
		   else if(comp>0)
		   {
			   x=x.right;
		   }
		   else
		   {
			   return true;
		   }
		   
	   }
	   return false;  
   }
  
   
   public void draw() { 
   
   }
   
  
   public Iterable<Point2D> range(RectHV rect)    
   {
	   if(rect==null)
	   {
		   throw new IllegalArgumentException();
	   }
	    
	   ArrayList<Point2D> arr = new ArrayList<Point2D>();
	    
	   range(root,rect,arr);
	   
	   return arr;
   }
   
   private void range(Node node, RectHV rectangle, ArrayList<Point2D> arr)
   {
	   if(node!= null && node.rect.intersects(rectangle))
	   {
		   if(rectangle.contains(node.value))
		   {
			   arr.add(node.value);
		   }
		   range(node.left,rectangle,arr);
		   range(node.right,rectangle,arr);
	   }
   }
   
   
   public Point2D nearest(Point2D p)  
   {// a nearest neighbor in the set to point p; null if the set is empty 
	   if(p==null)
	   {
		   throw new IllegalArgumentException();
	   }
	   if(isEmpty())
	   {
		   return null;
	   }
	   champion = root;
	   nearest(root,p);
	   return champion.value;
   }
   
   private void nearest(Node node, Point2D p)
   {
	   if(node!= null)
	   {
		   
		   if(p.distanceTo(node.value) < p.distanceTo(champion.value))
		   {
			   champion = node;
		   }
	   
		   if(node.vertical == true)
		   {
			   if(p.x()>node.value.x())
			   {
				   nearest(node.right,p);
				   RectHV rect1 = new RectHV(node.rect.xmin(),node.rect.ymin(),node.value.x(),node.rect.ymax());
			
				   if(Math.sqrt(rect1.distanceSquaredTo(p)) < p.distanceTo(champion.value))
				   {
					   nearest(node.left,p);
				   }
				   
			   }
			   else
			   {
				   nearest(node.left,p);
				   RectHV rect1 = new RectHV(node.value.x(),node.rect.ymin(),node.rect.xmax(),node.rect.ymax());
				   if(Math.sqrt(rect1.distanceSquaredTo(p)) < p.distanceTo(champion.value))
				   {
					   nearest(node.right,p);
				   }
			   }
		   }
		   else
		   {
			   if(p.y()>node.value.y())
			   {
				   nearest(node.right,p);
				   RectHV rect1 = new RectHV(node.rect.xmin(),node.rect.ymin(),node.rect.xmax(),node.value.y());
				   if(Math.sqrt(rect1.distanceSquaredTo(p)) < p.distanceTo(champion.value))
				   {
					   nearest(node.left,p);
				   }
			   }
			   else
			   {
				   nearest(node.left,p);
				   RectHV rect1 = new RectHV(node.rect.xmin(),node.value.y(),node.rect.xmax(),node.rect.ymax());
				   if(Math.sqrt(rect1.distanceSquaredTo(p)) < p.distanceTo(champion.value))
				   {
					   nearest(node.right,p);
				   }
			   }
		   }
	   } 
		
   }

   public static void main(String[] args) 
   {// unit testing of the methods (optional) 
	  /* KdTree st = new KdTree();
	   
	   st.insert(new Point2D(0.372, 0.497));
       st.insert(new Point2D(0.564, 0.413));
       st.insert(new Point2D(0.226, 0.577));
       st.insert(new Point2D(0.144, 0.179));
       st.insert(new Point2D(0.083, 0.51));
       st.insert(new Point2D(0.32, 0.708));
       st.insert(new Point2D(0.417, 0.362));
       st.insert(new Point2D(0.862, 0.825));
       st.insert(new Point2D(0.785, 0.725));
       st.insert(new Point2D(0.499, 0.208));
       System.out.println(st.nearest(new Point2D(0.41, 0.67)));*/

	   
	  // System.out.println(test.contains(new Point2D(0.4, 0.7)));
	   
   }
}