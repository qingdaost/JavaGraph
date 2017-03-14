package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
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
import java.util.Random;

import Main.Location;

import Main.GUI.Move;
import ecs100.UI;



public class RoadMap extends GUI { // data
	
		Graph roadMap  = new Graph()  ;
		String dataDirectory = "C:/Users/Adrian/workspace/JavaGraph/data/large/";
		public class Segment{ // (edge)
			int segID;
			Road road;
			Node startNode;
			Node endNode;
			float	length ;   // ID
			List<Location> coords = new ArrayList<Location>();
			Random random = new Random();
			public void draw(Graphics g,double x , double y) {
				g.setColor(new Color(random.nextInt()));
				g.drawOval((int)x, (int)y, 1, 1);
				
			}
			
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
			
		    	
		
		}
		
		public void drawLocations(Graph g, Location origin,double scale) {
			for (Segment s : g.segments){
				
				Point p1 =Location.newFromLatLon(s.startNode.lat, s.startNode.lon).asPoint(origin, scale);
				Point p2 = Location.newFromLatLon(s.endNode.lat, s.endNode.lon).asPoint(origin, scale);
				
				UI.drawLine ( p1.x, p1.y,  p2.x, p2.y);
				// System.out.println(p1.x+","+p1.y+";"); //drawPoint(pt);
				List<Location> segmentPoints = new ArrayList<Location>();
				for ( Location   c  : s.coords)  {
					
					segmentPoints.add(c);
					
				} 
				for (int i=0 ; i <segmentPoints.size()-1;i+=2){
					

					Point c1 =Location.newFromLatLon(segmentPoints.get(i).x , segmentPoints.get(i).y).asPoint(origin, scale);
					Point c2 = Location.newFromLatLon(segmentPoints.get(i+1).x, segmentPoints.get(i+1).y).asPoint(origin, scale);
					
					UI.drawLine ( c1.x, c1.y,  c2.x, c2.y);
				 
				}
		
			}
			
		}
		
		@SuppressWarnings("null")
		public Collection<Segment> ReadSegmentFile (File segementFile, Map<Integer, Road> roads, Map<Integer, Node> nodes){
			
			// Read file line by line
		
			BufferedReader data = null;
			try {
				data = new BufferedReader(new FileReader(segementFile));
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
					for (int i=4 ; i< values.length-1 ; i+=2){
						Location location = new Location(Float.parseFloat(values[i]) , Float.parseFloat(values[i+1]));
						segment.coords.add(location);
					}
					
					segments.add(segment);
				}
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return segments ; 
			
		
		}
		
		public Map<Integer,Node> ReadNodeFile (File nodeFile){
		
			// Read file line by line
			BufferedReader data = null;
			try {
				data = new BufferedReader(new FileReader(nodeFile));
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
		
		public Map<Integer,Road> ReadRoadFile (File roadFile){
	
			// Read file line by line
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
					for (int i=2 ; i <values.length-7 ; i++){
						r.label += values[i]; 
					}
					
					r.city = values[values.length-7];
					r.oneway=Integer.parseInt(values[values.length-6]);	
					r.speed =	Integer.parseInt(values[values.length-5]);
					r.roadclass=Integer.parseInt(values[values.length-4]);
					r.notforcar=Integer.parseInt(values[values.length-3]);
					r.notforpede=Integer.parseInt(values[values.length-2]);	
					r.notforbicy =  Integer.parseInt(values[values.length-1]);
							
					 
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
			
//			for (Segment s : roadMap.segments)
//				for ( Location   c  : s.coords)   //
//						 s.draw(g , c.x, c.y);
			
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
			//getTextOutputArea().setText("example doesn't load any files.");
			Graph g = new Graph () ; 
			g.roads = ReadRoadFile(roads);
			
			g.nodes = ReadNodeFile (nodes);
			g.segments = ReadSegmentFile (segments,g.roads,g.nodes);
			// populate segments inside road and node:
			
			for (Map.Entry<Integer, Road> entry : g.roads.entrySet())
			{
				for (Segment s : g.segments ) {
					if(s.road.roadid == entry.getKey()){
						 entry.getValue().segments.add(s);
					}
				}
				
			    //System.out.println(entry.getKey() + "/" + entry.getValue());
			}
			
			for (Map.Entry<Integer, Node> entry : g.nodes.entrySet())
			{
				for (Segment s : g.segments ) { 
					if(s.startNode.nodeID == entry.getKey()){    // if node B is the startNode for edge 5, then edge 5 is the outgoing segment for node B
						 entry.getValue().outSegs.add(s) ; 
					}else if(s.endNode.nodeID == entry.getKey()){
						entry.getValue().inSegs.add(s) ; 
					}
				}
				
			    //System.out.println(entry.getKey() + "/" + entry.getValue());
			}
			 System.out.println(g.segments);
			
			 
			 Location location = new Location(0 ,0);
			    
				drawLocations(g, location, 800) ;
			 
		}
		
		public static void main(String[] args) {
			new RoadMap();
		}

		

}
