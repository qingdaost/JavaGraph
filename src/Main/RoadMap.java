package Main;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoadMap {
		public class Segment{ // (edge)
			int segID;
			Road road;
			Node startNode;
			Node endNode;
		}
	
		public class Node{
			int nodeID;
			List<Segment> outSegs = new ArrayList<Segment>();
			List<Segment> inSegs = new ArrayList<Segment>();
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
		
		public void ReadFile (){
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
			List<Road> roadList = new ArrayList<Road>();
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
					Road r = new Road();
					String[] values = line.split("\t");
					
					r.roadid = Integer.parseInt(values[0]);
					r.type  = Integer.parseInt(values[1]);
					roadList.add(r);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		
		}
		

}
