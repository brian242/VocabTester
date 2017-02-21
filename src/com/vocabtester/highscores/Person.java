package com.vocabtester.highscores;

import java.util.*;

public class Person implements Comparable<Person> {

	String name;
	int score;

	public Person(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public String toString() {
		return name + " : " + score;
	}

	public int compareTo(Person person) {
		return getName().compareTo(person.getName());
	}

	public static class ScoreComparator implements Comparator<Person> {
		public int compare(Person p1, Person p2) {
			int score1 = p1.getScore();
			int score2 = p2.getScore();

			if (score1 == score2)
				return 0;
			else if (score1 > score2)
				return 1;
			else
				return -1;
		}
	}
	
}
