package com.vocabtester.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vocabtester.bean.Word;
import com.vocabtester.db.DbUtil;

public class WordsDao {

	private Connection connection;

	public WordsDao() {
		connection = DbUtil.connect();
	}

	public List<Word> getAllWords(String nameOfTable) {
		List<Word> words = new ArrayList<Word>();
	
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM " + nameOfTable + ";");
			while (rs.next()) {
				Word word = new Word();
				word.setId(rs.getInt("id"));
				word.setWord(rs.getString("word"));
				word.setDescription(rs.getString("description"));
				words.add(word);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return words;
	}

	public Word getWordById(int id, String nameOfTable) {
		Word word = new Word();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from " + nameOfTable + " where id = ? ;");
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				word.setId(rs.getInt("id"));
				word.setWord(rs.getString("word"));
				word.setDescription(rs.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return word;
	}

}
