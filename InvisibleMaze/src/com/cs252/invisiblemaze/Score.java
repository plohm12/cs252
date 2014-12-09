package com.cs252.invisiblemaze;

public class Score {
	private int id;
	private String name;
	private int score;

	public Score(String name, int score) {
		super();
		this.name = name;
		this.score = score;

	}

	public Score() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Name [id=" + id + ", name=" + name + ", score=" + score + "]";

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
