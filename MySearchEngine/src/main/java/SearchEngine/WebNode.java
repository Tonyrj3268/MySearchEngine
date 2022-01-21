package SearchEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebNode implements Runnable{
	private WebNode parent;
	private ArrayList<WebNode> children;
	private ArrayList<WebNode> pages;
	private WebNode leftChildNode;
	private WebNode rightChildNode;
	private String URL;
	private String title;
	private double score;
	public WebPage webPage;
    private ArrayList<Keyword> key;
	

	WebNode(WebPage webPage){
		this.webPage = webPage;
		this.children = new ArrayList<WebNode>();
	}
	WebNode(ArrayList<WebNode> pages,ArrayList<Keyword> key){
		this.pages = pages;
		this.key = key;
		this.children = new ArrayList<WebNode>();
	}
	public void addChild(WebNode child){
		//add the WebNode to its children list
		this.children.add(child);
		child.parent = this;
	}
	public  void setNodeScore(ArrayList<Keyword> keywords) throws IOException{
				
		webPage.setScore(keywords);
		//**set webPage score to nodeScore
		score = webPage.getScore();			
		for(WebNode child : children){
			child.setNodeScore(keywords);
			score += child.getScore();
		}
		
				
			
	}
	
	public boolean isTheLastChild(){
		if(this.parent == null) return true;
		ArrayList<WebNode> siblings = this.parent.children;	
		return this.equals(siblings.get(siblings.size() - 1));
	}
	
	public int getDepth(){
		int retVal = 1; 
		WebNode currNode = this;
		while(currNode.parent!=null){
			retVal ++;
			currNode = currNode.parent;
		}
		return retVal;
	}
	
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public WebNode getParent() {
		return parent;
	}
	public void setParent(WebNode parent) {
		this.parent = parent;
	}
	public ArrayList<WebNode> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<WebNode> children) {
		this.children = children;
	}
	public WebNode getLeftChildNode() {
		return leftChildNode;
	}
	public void setLeftChildNode(WebNode leftChildNode) {
		this.leftChildNode = leftChildNode;
	}
	public WebNode getRightChildNode() {
		return rightChildNode;
	}
	public void setRightChildNode(WebNode rightChildNode) {
		this.rightChildNode = rightChildNode;
	}
	@Override
	public void run() {
		try {
			for(WebNode node:pages) {
				
				node.childQuery(node.webPage.getUrl());
				node.setNodeScore(key);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private String childFetchContent(String childUrl) {
		String retVal = "";
		//childUrl = new String(childUrl.getBytes("UTF-8"),"UTF-8");
		URL u;
		try {
			u = new URL(childUrl);
			URLConnection conn = u.openConnection();

			conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);			
			InputStream in = conn.getInputStream();
			
			InputStreamReader inReader = new InputStreamReader(in,"utf-8");
			
			BufferedReader bufReader = new BufferedReader(inReader);
			String line = null;

			while((line=bufReader.readLine())!=null)
			{
				retVal+=line;

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		
		return retVal;
	}
	
	public void childQuery(String childUrl) {
		String childContent = null;
		int i=0;
		if(childContent==null){
			
			childContent= childFetchContent(childUrl);

		}		
		Document doc = Jsoup.parse(childContent,childUrl);
		
		Elements lis = doc.select("div");
		//lis = lis.select(".kCrYT");
		String beforeUrl="";
		for(Element li : lis){
			
			try {
				
				String citeUrl = li.select("a").get(0).attr("abs:href");
				if(beforeUrl.equals(citeUrl)) {
					continue;
				}
				beforeUrl = citeUrl;
				WebPage page = new WebPage(citeUrl);				
				addChild(new WebNode(page));
				i++;
			} catch (IndexOutOfBoundsException e) {

			}
			if(i>=3) {
				break;
			}
		}

	}
}
