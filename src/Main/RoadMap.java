package Main;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Main.GUI.Move;


public class RoadMap extends GUI {
	
	
		public class Segment{ // (edge)
			int segID;
			Road road;
			Node startNode;
			Node endNode;
			float	length ;   // ID
			//coords
		}
	
		public class Node{  //intersections
			int nodeID;
			List<Segment> outSegs = new ArrayList<Segment>();
			List<Segment> inSegs = new ArrayList<Segment>();
			float lat ;
			float lon ; 
		}
		public class Graph{
			Map<Integer,Node> nodes = new HashMap<Integer,Node>();
			Collection<Segment> segments;
			Map<Integer,Road> roads = new HashMap<Integer,Road>();
		} 
		public class Road{  
			List<Segment> segments = new ArrayList<Segment>();  
			int roadid;	
			int type	;
			String label  ;  	
			String city ;   	
			int oneway;	
			int speed	;
			int roadclass;	
			int notforcar;	
			int notforpede	;
			int notforbicy;
		}
		
		
		public RoadMap (){
			Graph g = new Graph () ; 
		
			g.roads = ReadRoadFile();
			g.nodes = ReadNodeFile ();
			g.segments = ReadSegmentFile (g.roads,g.nodes);
			 System.out.println(g.segments);
			//g.
			//node.outSegs =
					//node.inSegs=
		
		}
		@SuppressWarnings("null")
		public Collection<Segment> ReadSegmentFile (Map<Integer, Road> roads, Map<Integer, Node> nodes){
			String dataDirectory = "C:/Users/Adrian/workspace/JavaGraph/data/small/";
			// Read file line by line
			File roadFile = new File(dataDirectory +"roadSeg-roadID-length-nodeID-nodeID-coords.tab");
			BufferedReader data = null;
			try {
				data = new BufferedReader(new FileReader(roadFile));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String line = null ;String headers = null;
			Collection<Segment> segments =  new ArrayList<Segment>() ;
			
			try {
				 headers = data.readLine();
				 System.out.println(headers);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				while ((line = data.readLine()) != null) {
					// Process each line using split method
					Segment segment = new Segment();
					String[] values = line.split("\t");
					
					//roadID	length	nodeID1	nodeID2	coords
					
					segment.road=roads.get(Integer.parseInt(values[0]));
					segment.length = Float.parseFloat(values[1]);
					segment.startNode =  nodes.get(Integer.parseInt(values[2]));
					segment.endNode = nodes.get(Integer.parseInt(values[3]));
					segment.segID=  segment.startNode.nodeID + segment.endNode.nodeID ;
					
					segments.add(segment);
				}
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return segments ; 
			
		
		}
		
		public Map<Integer,Node> ReadNodeFile (){
			String dataDirectory = "C:/Users/Adrian/workspace/JavaGraph/data/small/";
			// Read file line by line
			File roadFile = new File(dataDirectory +"nodeID-lat-lon.tab");
			BufferedReader data = null;
			try {
				data = new BufferedReader(new FileReader(roadFile));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String line = null ;
			Map<Integer,Node> nodes = new HashMap<Integer,Node>();
			try {
				while ((line = data.readLine()) != null) {
					// Process each line using split method
					Node node = new Node ();
					String[] values = line.split("\t");
					node.nodeID= Integer.parseInt(values[0]);
					
					node.lat =Float.parseFloat(values[1]);
					node.lon =Float.parseFloat(values[2]); 
					nodes.put(node.nodeID, node);
				}
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return nodes ; 
			
		
		}
		
		public Map<Integer,Road> ReadRoadFile (){
			String dataDirectory = "C:/Users/Adrian/workspace/JavaGraph/data/small/";
			// Read file line by line
			File roadFile = new File(dataDirectory +"roadID-roadInfo.tab");
			BufferedReader data = null;
			try {
				data = new BufferedReader(new FileReader(roadFile));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String line = null ;String headers = null;
			Map<Integer,Road> roads = new HashMap<Integer,Road>();
			
			try {
				 headers = data.readLine();
				 System.out.println(headers);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				while ((line = data.readLine()) != null) {
					// Process each line using split method
					
					String[] values = line.split("\t");
					Road r = new Road();		
					r.roadid = Integer.parseInt(values[0]);
					r.type  = Integer.parseInt(values[1]);
//					  Collection<Segment>  segments = ReadSegmentFile();
//					  for (Segment s : segments) {
//					      if( s.road.roadid ==r.roadid ){
//					    	  r.segments.add(s) ;
//					      }
//					  }
					 
					roads.put(r.roadid,r);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return roads ; 
			
		
		}
		
		@Override
		protected void redraw(Graphics g) {
	
		}

		@Override
		protected void onClick(MouseEvent e) {
			/*
			 * we search from the back to the front of the list (while drawing
			 * happens front-to-back) so that we always remove the top square if
			 * there are any overlapping. this is why we use a list and not a set to
			 * store the squares in the first place.
			 */
			

			//getTextOutputArea().append("\nsquares remaining: " + squares.size());
		}

		@Override
		protected void onSearch() {
			getTextOutputArea().setText(getSearchBox().getText());
		}

		@Override
		protected void onMove(Move m) {
			//makeSquares();
		}

		@Override
		protected void onLoad(File nodes, File roads, File segments, File polygons) {
			getTextOutputArea().setText("example doesn't load any files.");
		}
		
		public static void main(String[] args) {
			new RoadMap();
		}

		

}
