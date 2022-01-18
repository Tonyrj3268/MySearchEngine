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
		
		setPostOrderScore(root, keywords);
		
	}
	
	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException{
		for(WebNode child : startNode.getChildren()){
			
			child.setNodeScore(keywords);
			console.add(child);
		}
		startNode.setNodeScore(keywords);
		}
	
	public void eularPrintTree(){
		eularPrintTree(root);
	}
	
	private void eularPrintTree(WebNode startNode){
		quickSort(0, console.size()-1);
		
		for(WebNode node:console) {
			System.out.println(node.webPage.getTitle()+"|"+node.webPage.getScore());
		}
		
		
	}
	
	private void quickSort(int leftbound, int rightbound){
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
	
	private void swap(int aIndex, int bIndex){
		WebNode temp = console.get(aIndex);
		console.set(aIndex, console.get(bIndex));
		console.set(bIndex, temp);
	}
	
	
}
