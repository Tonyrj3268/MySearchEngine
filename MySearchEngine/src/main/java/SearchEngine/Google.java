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


public class Google {
	private String keyword;
	private String url;
	private String content = null;
	private ArrayList<WebPage> pagelist = new ArrayList<WebPage>();
	private ArrayList<WebPage> childPagelist ;
	private WebTree tree;
	private WebNode child;
	private Keyword keylist = new Keyword();
	Google(){
		
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public void run() {
		constructor();
		try {
			query();
			createWebTree();
		} catch (IOException e) {
			System.out.println("queryµo¥Í¿ù»~");
			e.printStackTrace();
		}
	}
	
	private void constructor() {
		this.url = "http://www.google.com/search?q="+keyword+"&oe=utf8&num=30";
		
		tree = new WebTree(new WebPage(url));
		
		
	}
	
	private String fetchContent() throws IOException{
		String retVal = "";
		URL u = new URL(url);

		URLConnection conn = u.openConnection();

		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");

		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in,"utf-8");

		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line=bufReader.readLine())!=null)
		{
			retVal+=line;

		}
		return retVal;
	}
	
	public void query() throws IOException{
		if(content==null){
			
			content= fetchContent();

		}		
		Document doc = Jsoup.parse(content);
		Elements lis = doc.select("div");
		lis = lis.select(".kCrYT");
		
		for(Element li : lis){
			try {
				String citeUrl = li.select("a").get(0).attr("href");
				citeUrl = citeUrl.substring(7);
				String title = li.select("a").get(0).select(".vvjwJb").text();
				if(title.equals("")) {
					continue;
				}
				WebPage page = new WebPage(citeUrl);
				page.setTitle(title);
				System.out.println(title+"|"+citeUrl);
				pagelist.add(page);

			} catch (IndexOutOfBoundsException e) {

			}

		}

	}
	
	public void createWebTree() throws IOException {
		System.out.println("--------building tree--------");
		for(WebPage page:pagelist) {
			child = new WebNode(page);
			tree.root.addChild(child);
			//childQuery(page.getUrl());
			
			
		}
		tree.setPostOrderScore(keylist.getapplelist());
		tree.eularPrintTree();
	}
	
	private String childFetchContent(String childUrl) throws IOException{
		String retVal = "";
		URL u = new URL(childUrl);

		URLConnection conn = u.openConnection();

		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");

		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in,"utf-8");

		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line=bufReader.readLine())!=null)
		{
			retVal+=line;

		}
		return retVal;
	}
	
	public void childQuery(String childUrl) throws IOException{
		String childContent = null;
		int i=0;
		if(childContent==null){
			
			childContent= childFetchContent(childUrl);

		}		
		Document doc = Jsoup.parse(childContent);
		Elements lis = doc.select("div");
		lis = lis.select(".kCrYT");
		
		for(Element li : lis){
			if(i>=3) {
				break;
			}
			try {
				String citeUrl = li.select("a").get(0).attr("href");
				citeUrl = citeUrl.substring(7);
				String title = li.select("a").get(0).select(".vvjwJb").text();
				if(title.equals("")) {
					continue;
				}
				WebPage page = new WebPage(citeUrl);
				page.setTitle(title);
				child.addChild(new WebNode(page));
				i++;
			} catch (IndexOutOfBoundsException e) {

			}

		}

	}
}
