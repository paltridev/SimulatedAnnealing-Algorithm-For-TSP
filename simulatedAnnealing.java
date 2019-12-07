package code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class simulatedAnnealing {
	
	public String FileLocation;
	public List<Integer> currentOrder = new ArrayList<>();
	public List<Integer> nextOrder = new ArrayList<>();
	public double[][] distances;
	public Random random = new Random();
	public double shortestCost = 0;
	String matrixData = "";
	
	
	public double getShortestCost()
    {
      return shortestCost;
    }
    
    public void setShortestDistance(double value) {
    	this.shortestCost = value;
    }
    
    public String getFileLocation()
    {
      return FileLocation;
    }
    
    public void setFileLocation(String filepath) {
    	this.FileLocation = filepath;
    }
    
    public List<Integer> getNodesOrder(){
    	return currentOrder;
    }
    
    public List<Integer> setNodesOrder(List<Integer> value){
    	return currentOrder = value;
    }
    
    //Load Data from the text file representing the adjacency matrix to Distances 2D array
    public void LoadDataFromFile() throws FileNotFoundException, IOException {
    	
    	try {  
			  Scanner sc = new Scanner(new BufferedReader(new FileReader(FileLocation)));
			  
			  int rows = 5;
			  int columns = 5;
		      distances= new double[rows][columns];
		      while(sc.hasNextLine()) {
		         for (int i=0; i<distances.length; i++) {
		            String[] line = sc.nextLine().trim().split("  ");
		            for (int j=0; j<line.length; j++) {
		               distances[i][j] = Double.parseDouble(line[j]);
		            }
		            
		            //Each row represents a node
		            currentOrder.add(i);
		         } 
		      }
		      
		      if (currentOrder.size() < 1) {
	                throw new Exception("No Nodes to order.");
	        }

		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public double GetTotalDistance(List<Integer> order)
    {
        double distance = 0;

        for (int i = 0; i < order.size() - 1; i++)
        {
            distance += distances[order.get(i)][order.get(i + 1)];
        }

        return distance;
    }
    
    public List<Integer> GetNextArrangement(List<Integer> order)
    {
        List<Integer> newOrder = new ArrayList<>();

        for (int i = 0; i < order.size(); i++) {
            newOrder.add(order.get(i));
        }
      // rearrange two cities by random

        int firstRandomCityIndex = random.nextInt(5);
        int secondRandomCityIndex = random.nextInt(5);

        int temp = newOrder.get(firstRandomCityIndex);
        newOrder.set(firstRandomCityIndex,newOrder.get(secondRandomCityIndex));
        newOrder.set(secondRandomCityIndex,temp);
        
        return newOrder;
    
}
    public void annealling()
    {
        double temperature = 10000.0;
        double deltaDistance = 0;
        double coolingRate = 0.9;
        double absoluteTemperature = 0.00001;

        try {
			LoadDataFromFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

        double distance = GetTotalDistance(currentOrder);

        while (temperature > absoluteTemperature)
        {
        	
        	System.out.println("Current tour: "+currentOrder);

            nextOrder = GetNextArrangement(currentOrder);

            deltaDistance = GetTotalDistance(nextOrder) - distance;

      //if the new order has a smaller distance
     //or if the new order has a larger distance but satisfies the equation condition then accept the arrangement
            if ((deltaDistance < 0) || (distance > 0 && Math.exp(-deltaDistance / temperature) > random.nextDouble()))
            {
                for (int i = 0; i < nextOrder.size(); i++) {
                	currentOrder.set(i,nextOrder.get(i));
                }

                distance = deltaDistance + distance;
            }

            //cool down the temperature
            temperature *= coolingRate;
            
            
            System.out.println("next tour: "+nextOrder);
            System.out.println(distance);
            

            }
        shortestCost = distance;
    }
   
}
