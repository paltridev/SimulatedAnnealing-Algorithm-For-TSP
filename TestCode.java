package code;

import java.io.FileNotFoundException;

public class TestCode {

	public static void main(String[] args) throws FileNotFoundException {
		
		simulatedAnnealing n = new simulatedAnnealing();
		
		//change it to the dataset.txt path in your laptop or pc
		n.setFileLocation("C:\\Users\\tridev\\eclipse-workspace\\simulated Annealing\\src\\code\\dataset.txt"); 
		

		n.annealling();
		
		 String path = "";
         for (int i = 0; i < n.getNodesOrder().size()-1; i++)
         {
             path += n.getNodesOrder().get(i) + " -> ";
				
         }
         
         path += n.getNodesOrder().get(n.getNodesOrder().size() - 1);
         
         System.out.println("\n best tour: " + path);
         System.out.println("The distance is: " + n.shortestCost);
         
         
		
	}

}
