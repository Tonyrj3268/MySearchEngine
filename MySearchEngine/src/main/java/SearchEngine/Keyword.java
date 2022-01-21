package SearchEngine;

import java.util.ArrayList;

public class Keyword {
	public String name;
	 private int score;
	 private ArrayList<Keyword> applelist = new ArrayList<Keyword>();

	 public Keyword(String name,int score){
	  this.name = name;
	  this.score = score;
	 }
	 public Keyword() {
		 setapplelist();
	 }
	 
	 public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public ArrayList<Keyword> getapplelist() {
		return applelist;
	}
	public void setapplelist() {
		applelist.add(new Keyword("MAC", 1));
		applelist.add(new Keyword("APPLE", 5));
		applelist.add(new Keyword("MacBook", 5));
		applelist.add(new Keyword("iMac", 5));
		applelist.add(new Keyword("Ä«ªG", 5));
		applelist.add(new Keyword("Pro", 2));
		applelist.add(new Keyword("iphone", 2));
		applelist.add(new Keyword("ipad", 2));
		applelist.add(new Keyword("ipod", 2));
		applelist.add(new Keyword("·s»D", -100));
	}
	
	public void setName(String name) {
	  this.name = name;
	 }
	 public String getName() {
	  return name;
	 }
	 @Override
	 public String toString(){
	  return "["+name+","+"]";
	 }
	 public static void main(String[] args) {
		 System.out.println("QQ");
	 }
}
