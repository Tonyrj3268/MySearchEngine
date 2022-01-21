package SearchEngine;

import java.io.IOException;
import java.util.ArrayList;



public class WebPage {
	public WordCounter counter;
	private String url;
	private String title;
	private int score;
	WebPage(String url){
		this.url = url;
		this.counter = new WordCounter(url);
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getScore() {
		return score;
	}

	public void setScore(ArrayList<Keyword> keywords) throws IOException {
		score = 0;
		for(Keyword k : keywords){	
			
			score+=counter.countKeyword(k.name)*k.getScore();
		}
	}

	public String getTitle() {
		return title;
	}

	
	
	
}
