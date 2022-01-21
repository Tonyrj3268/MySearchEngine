package SearchEngine;

import java.io.IOException;
import java.util.ArrayList;

public class WebTree {

	public WebNode root;
	private ArrayList<WebNode> console = new ArrayList<WebNode>();
	public WebTree(WebPage rootPage){
		this.root = new WebNode(rootPage);
	}
	
	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException{
		
		try {
			setPostOrderScore(root, keywords);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException, InterruptedException{
		ArrayList<WebNode> children1 = new ArrayList<WebNode>(startNode.getChildren().subList(0,startNode.getChildren().size()/2));
		ArrayList<WebNode> children2 = new ArrayList<WebNode>(startNode.getChildren().subList(startNode.getChildren().size()/2,startNode.getChildren().size()));
	

		Thread thread1 = new Thread(new WebNode(children1,keywords));
		Thread thread2 = new Thread(new WebNode(children2,keywords));
		thread1.start();	
		thread2.start();
		thread1.join();
		thread2.join();
		console.addAll(children1);
		console.addAll(children2);
		/*for(WebNode child : startNode.getChildren()){
			
			child.setNodeScore(keywords);
			console.add(child);
		}
		startNode.setNodeScore(keywords);*/
		}
	
	public void eularPrintTree(){
		eularPrintTree(root);
	}
	
	private void eularPrintTree(WebNode startNode){
		quickSort(0, console.size()-1);
		
		for(WebNode node:console) {
			System.out.println(node.webPage.getTitle()+"|"+node.getScore());
		}
		
		
	}
	
	private void quickSort1(int leftbound, int rightbound){
		//1. implement quickSort algorithm
		if(leftbound>=rightbound) return;
		int l = leftbound;
		int r = rightbound-1;
		WebNode pivot = console.get(rightbound);
		
			while(l<=r) {
				while(pivot.webPage.getScore()<=console.get(l).webPage.getScore()&&l<r) l++;
				while(pivot.webPage.getScore()>console.get(r).webPage.getScore()&&l<r) r--;
				if(l<r) {
					swap(l,r);
				}
			}
			swap(l,rightbound);
			quickSort(leftbound,l-1);
			quickSort(l+1,rightbound);
			
		
	}
	
	private void swap1(int aIndex, int bIndex){
		WebNode temp = console.get(aIndex);
		console.set(aIndex, console.get(bIndex));
		console.set(bIndex, temp);
	}
	
	public void swap(int aIndex, int bIndex){
		//double temp = console.get(aIndex).getScore();
		WebNode a = console.get(aIndex);
		console.set(aIndex, console.get(bIndex));
		console.set(bIndex, a);
		
	}
	  private void quickSort(int left, int right) {
	        if(left < right) { 
	            int q = partition(left, right); 
	            quickSort( left, q-1); 
	            quickSort( q+1, right); 
	        } 

	    }

	    private int partition( int left, int right) {  
	        int i = left - 1; 
	        for(int j = left; j < right; j++) { 
	            if(console.get(j).getScore() >= console.get(right).getScore()) { 
	                i++; 
	                swap(i, j); 
	            } 
	        } 
	        swap(i+1, right); 
	        return i+1; 
	    } 
}
