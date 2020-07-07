package org.dependencies.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dependencies.model.DepAnalyzedText;
import org.dependencies.model.DepAnalysis;
import org.dependencies.model.DepCorpus;
import org.dependencies.model.DepDescription;
import org.dependencies.model.DepEntryCondition;
import org.dependencies.model.DepFeature;
import org.dependencies.model.DepFunction;
import org.dependencies.model.DepLanguage;
import org.dependencies.model.DepMetafunction;
import org.dependencies.model.DepSystem;
import org.dependencies.model.DepText;
import org.dependencies.model.DepWord;
import org.dependencies.model.DepWordFeature;
import org.dependencies.model.DepWording;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * The dependency base.
 * 
 * @author Daniel Couto-Vale
 */
public class MysqlDependencyBase {

	/**
	 * The base URL
	 */
	private static final String url = "jdbc:mysql://localhost:3306/%s?useSSL=false&useUnicode=true&characterEncoding=utf8";

	/**
	 * Gets a property.
	 * 
	 * @param properties   the properties
	 * @param propertyName the property name
	 * @return the property
	 */
	private static final String getProperty(Properties properties, String propertyName) {
		String value = properties.getProperty(propertyName);
		if (value == null) {
			System.err.println("Cannot find property '" + propertyName + "' in database.properties file.");
			System.exit(-1);
		}
		return value;
	}

	/**
	 * Makes a word feature join SQL.
	 * 
	 * @param order the order of the join SQL
	 * @return the SQL
	 */
	private static final String makeWordFeatureJoinSql(Integer order) {
		return String.format(
				"JOIN `word-feature` WF%d ON WF%d.`word-id` = W.`id` \n"
						+ "JOIN `analysis` A%d ON A%d.`id` = WF%d.`analysis-id` AND A%d.`name` = ? \n"
						+ "JOIN `feature` F%d ON F%d.`id` = WF%d.`id` AND F%d.`name` = ? \n"
						+ "JOIN `system` S%d ON S%d.`id` = F%d.`system-id` AND S%d.`name` = ? \n"
						+ "JOIN `description` D%d ON D%d.`id` = S%d.`description-id` AND D%d.`name` = ? \n",
				order, order, order, order, order, order, order, order, order, order, order, order, order, order, order,
				order, order, order);
	}

	/**
	 * The connection
	 */
	private final Connection conn;

	/**
	 * Constructor
	 */
	public MysqlDependencyBase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Cannot find mysql driver.");
			System.exit(-1);
		}
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("database.properties"));
		} catch (IOException e) {
			System.err.println("Cannot find database.properties file.");
			System.exit(-1);
		}
		String name = getProperty(properties, "DependencyBase.name");
		String user = getProperty(properties, "DependencyBase.user");
		String password = getProperty(properties, "DependencyBase.password");
		try {
			this.conn = DriverManager.getConnection(String.format(url, name), user, password);
		} catch (SQLException e) {
			System.err.println("Cannot open connection with database '" + name + "'.");
			System.exit(-1);
			throw new Error();
		}
	}

	/**
	 * Adds an analysis to the specified description in this dependency base.
	 * 
	 * @param descriptionId the description id
	 * @param name          the analysis name
	 * @return the analysis
	 * @throws SQLException if the query fails
	 */
	public final DepAnalysis addAnalysis(Integer descriptionId, String name) throws SQLException {
		String sql = "INSERT INTO `analysis` (`description-id`, `name`) VALUES (?, ?)";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, descriptionId);
		stmt.setString(2, name);
		int ar = stmt.executeUpdate();
		if (ar != 1)
			throw new SQLException();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		Integer id = rs.getInt(1);
		stmt.close();
		DepAnalysis analysis = new DepAnalysis();
		analysis.setId(id);
		analysis.setName(name);
		return analysis;
	}

	/**
	 * Adds a corpus to this dependency base.
	 * 
	 * @param name the corpus name
	 * @return the corpus
	 * @throws SQLException if the query fails
	 */
	public final DepCorpus addCorpus(String name) throws SQLException {
		String sql = "INSERT INTO `corpus` (`name`) VALUES (?)";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, name);
		int ar = stmt.executeUpdate();
		if (ar != 1)
			throw new SQLException();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		Integer id = rs.getInt(1);
		stmt.close();
		DepCorpus corpus = new DepCorpus();
		corpus.setId(id);
		corpus.setName(name);
		return corpus;
	}

	/**
	 * Adds a description to this dependency base.
	 * 
	 * @param name the description name
	 * @return the description
	 * @throws SQLException if the query fails
	 */
	public final DepDescription addDescription(String name) throws SQLException {
		String sql = "INSERT INTO `description` (`name`) VALUES (?)";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, name);
		int ar = stmt.executeUpdate();
		if (ar != 1)
			throw new SQLException();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		Integer id = rs.getInt(1);
		stmt.close();
		DepDescription description = new DepDescription();
		description.setId(id);
		description.setName(name);
		return description;
	}

	/**
	 * Adds a feature to the specified system in this dependency base.
	 * 
	 * @param systemId the system id
	 * @param name     the feature name
	 * @return the feature
	 * @throws SQLException if the query fails
	 */
	public final DepFeature addFeature(Integer systemId, String name) throws SQLException {
		String sql = "INSERT INTO `feature` (`system-id`, `name`) VALUES (?, ?)";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, systemId);
		stmt.setString(2, name);
		int ar = stmt.executeUpdate();
		if (ar != 1)
			throw new SQLException();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		Integer id = rs.getInt(1);
		stmt.close();
		DepFeature feature = new DepFeature();
		feature.setId(id);
		feature.setName(name);
		return feature;
	}

	/**
	 * Adds a function to the specified metafunction in this dependency base.
	 * 
	 * @param metafunctionId the metafunction id
	 * @param name           the function name
	 * @return the function
	 * @throws SQLException if the query fails
	 */
	public final DepFunction addFunction(Integer metafunctionId, String name) throws SQLException {
		String sql = "INSERT INTO `function` (`metafunction-id`, `name`) VALUES (?, ?)";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, metafunctionId);
		stmt.setString(2, name);
		int ar = stmt.executeUpdate();
		if (ar != 1)
			throw new SQLException();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		Integer id = rs.getInt(1);
		stmt.close();
		DepFunction function = new DepFunction();
		function.setId(id);
		function.setName(name);
		return function;
	}

	/**
	 * Adds a language to this dependency base.
	 * 
	 * @param name the language name
	 * @return the language
	 * @throws SQLException if the query fails
	 */
	public final DepLanguage addLanguage(String name) throws SQLException {
		String sql = "INSERT INTO `language` (`name`) VALUES (?)";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, name);
		int ar = stmt.executeUpdate();
		if (ar != 1)
			throw new SQLException();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		Integer id = rs.getInt(1);
		stmt.close();
		DepLanguage language = new DepLanguage();
		language.setId(id);
		language.setName(name);
		return language;
	}

	/**
	 * Adds a metafunction to the specified description in this dependency base.
	 * 
	 * @param descriptionId the description id
	 * @param name          the metafunction name
	 * @return the metafunction
	 * @throws SQLException if the query fails
	 */
	public final DepMetafunction addMetafunction(Integer descriptionId, String name) throws SQLException {
		String sql = "INSERT INTO `metafunction` (`description-id`, `name`) VALUES (?, ?)";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, descriptionId);
		stmt.setString(2, name);
		int ar = stmt.executeUpdate();
		if (ar != 1)
			throw new SQLException();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		Integer id = rs.getInt(1);
		stmt.close();
		DepMetafunction metafunction = new DepMetafunction();
		metafunction.setId(id);
		metafunction.setName(name);
		return metafunction;
	}

	/**
	 * Adds a system to the specified description in this dependency base.
	 * 
	 * @param descriptionId      the description id
	 * @param name               the system name
	 * @param entryConditionForm the entry condition form
	 * @return the system
	 * @throws SQLException if the query fails
	 */
	public final DepSystem addSystem(Integer descriptionId, String name, String entryConditionForm)
			throws SQLException {
		String sql = "INSERT INTO `system` (`description-id`, `name`, `entry-condition`) VALUES (?, ?, ?)";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, descriptionId);
		stmt.setString(2, name);
		stmt.setString(3, entryConditionForm);
		int ar = stmt.executeUpdate();
		if (ar != 1)
			throw new SQLException();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		Integer id = rs.getInt(1);
		stmt.close();
		DepSystem system = new DepSystem();
		system.setId(id);
		system.setName(name);
		system.setEntryCondition(new DepEntryCondition(entryConditionForm));
		return system;
	}

	/**
	 * Adds a text to the specified corpus in the specified language in this
	 * dependency base.
	 * 
	 * @param corpusId   the corpus id
	 * @param languageId the language id
	 * @param title      the text title
	 * @return the text
	 * @throws SQLException if the query fails
	 */
	public final DepText addText(Integer corpusId, Integer languageId, String title) throws SQLException {
		String sql = "INSERT INTO `text` (`corpus-id`, `language-id`, `title`) VALUES (?, ?, ?)";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, corpusId);
		stmt.setInt(2, languageId);
		stmt.setString(3, title);
		int ar = stmt.executeUpdate();
		if (ar != 1)
			throw new SQLException();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		Integer id = rs.getInt(1);
		stmt.close();
		DepText text = new DepText();
		text.setId(id);
		text.setTitle(title);
		return text;
	}

	/**
	 * Adds a word to the specified wording at the specified order in this
	 * dependency base.
	 * 
	 * @param wordingId  the wording id
	 * @param order      the word order in the wording
	 * @param form       the word form
	 * @param backspaced the word backspace
	 * @param lemma      the word lemma
	 * @return the word
	 * @throws SQLException if the query fails
	 */
	public final DepWord addWord(Integer wordingId, Integer order, String form, boolean backspaced, String lemma)
			throws SQLException {
		String sql = "INSERT INTO `word` (`wording-id`, `order`, `form`, `backspaced`, `lemma`) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, wordingId);
		stmt.setInt(2, order);
		stmt.setString(3, form);
		stmt.setBoolean(4, backspaced);
		stmt.setString(5, lemma);
		int ar = stmt.executeUpdate();
		if (ar != 1)
			throw new SQLException();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		Integer id = rs.getInt(1);
		stmt.close();
		DepWord word = new DepWord();
		word.setId(id);
		word.setForm(form);
		word.setBackspaced(backspaced);
		word.setLemma(lemma);
		return word;
	}

	/**
	 * Adds a feature as a property of the specified word in the specified analysis
	 * in this dependency base.
	 * 
	 * @param analysisId the analysis id
	 * @param id         the word id
	 * @param wordId     the word id
	 * @throws SQLException if the query fails
	 */
	public final void addWordFeature(Integer analysisId, Integer id, Integer wordId) throws SQLException {
		String sql = "INSERT INTO `word-feature` (`analysis-id`, `id`, `word-id`) VALUES (?, ?, ?)";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, analysisId);
		stmt.setInt(2, id);
		stmt.setInt(3, wordId);
		int ar = stmt.executeUpdate();
		if (ar != 1)
			throw new SQLException();
		stmt.close();
	}

	/**
	 * Adds a function as a property of the specified word at the specified rank
	 * relative to the specified head word at the specified rank in the specified
	 * analysis in this dependency base.
	 * 
	 * @param analysisId the analysis id
	 * @param id         the function id
	 * @param wordId     the word id
	 * @param wordRankId the word rank id
	 * @param headId     the head word id
	 * @param headRankId the head word rank id
	 * @throws SQLException if the query fails
	 */
	public final void addWordFunction(Integer analysisId, Integer id, Integer wordId, Integer wordRankId,
			Integer headId, Integer headRankId) throws SQLException {
		String sql = "INSERT INTO `word-function` (`analysis-id`, `id`, `word-id`, `word-rank-id`, `head-id`, `head-rank-id`) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, analysisId);
		stmt.setInt(2, id);
		stmt.setInt(3, wordId);
		stmt.setInt(4, wordRankId);
		stmt.setInt(5, headId);
		stmt.setInt(6, headRankId);
		int ar = stmt.executeUpdate();
		if (ar != 1)
			throw new SQLException();
		stmt.close();
	}

	/**
	 * Adds a wording to the specified text at the specified order in this
	 * dependency base.
	 * 
	 * @param textId the text id
	 * @param order  the wording order in the text
	 * @param form   the wording form
	 * @return the wording
	 * @throws SQLException if the query fails
	 */
	public final DepWording addWording(Integer textId, Integer order, String form) throws SQLException {
		String sql = "INSERT INTO `wording` (`text-id`, `order`, `form`) VALUES (?, ?, ?)";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, textId);
		stmt.setInt(2, order);
		stmt.setString(3, form);
		int ar = stmt.executeUpdate();
		if (ar != 1)
			throw new SQLException();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		Integer id = rs.getInt(1);
		stmt.close();
		DepWording wording = new DepWording();
		wording.setId(id);
		wording.setForm(form);
		return wording;
	}

	/**
	 * Closes the connection.
	 * 
	 * @throws SQLException if the connection cannot be closed.
	 */
	public final void close() throws SQLException {
		this.conn.close();
	}

	/**
	 * Gets the specified analyzed text of the specified corpus in the specified
	 * language in this dependency base.
	 * 
	 * @param corpusId   the corpus id
	 * @param languageId the language id
	 * @param title      the text title
	 * @param analysisId the analysis id
	 * @return the analyzed text
	 * @throws SQLException if any of the queries fail
	 */
	public final DepAnalyzedText getAnalyzedText(Integer corpusId, Integer languageId, String title, Integer analysisId)
			throws SQLException {
		DepAnalyzedText text = new DepAnalyzedText(this.getText(corpusId, languageId, title));
		String sql = "SELECT Ws.* FROM `wording` Ws " + "WHERE Ws.`text-id` = ?";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, text.getId());
		ResultSet rs = stmt.executeQuery();
		Map<Integer, DepWording> wordingMap = new HashMap<>();
		while (rs.next()) {
			DepWording wording = new DepWording();
			wording.setId(rs.getInt("id"));
			wording.setForm(rs.getString("form"));
			wordingMap.put(wording.getId(), wording);
			text.addWording(wording);
		}
		stmt.close();
		sql = "SELECT W.* FROM `word` W " + "JOIN `wording` Ws ON Ws.`id` = W.`wording-id` " + "WHERE Ws.`text-id` = ?";
		stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, text.getId());
		rs = stmt.executeQuery();
		Map<Integer, DepWord> wordMap = new HashMap<>();
		while (rs.next()) {
			DepWord word = new DepWord();
			word.setId(rs.getInt("id"));
			word.setForm(rs.getString("form"));
			word.setLemma(rs.getString("lemma"));
			word.setBackspaced(rs.getBoolean("backspaced"));
			wordMap.put(word.getId(), word);
			DepWording wording = wordingMap.get(rs.getInt("wording-id"));
			wording.addWord(word);
		}
		stmt.close();
		sql = "SELECT F.*, WF.`word-id` FROM `feature` F " + "JOIN `word-feature` WF ON WF.`id` = F.`id` "
				+ "JOIN `word` W ON W.`id` = WF.`word-id` " + "JOIN `wording` Ws ON Ws.`id` = W.`wording-id` "
				+ "WHERE Ws.`text-id` = ? AND WF.`analysis-id` = ?";
		stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, text.getId());
		stmt.setInt(2, analysisId);
		rs = stmt.executeQuery();
		while (rs.next()) {
			DepFeature feature = new DepFeature();
			feature.setId(rs.getInt("id"));
			feature.setName(rs.getString("name"));
			DepWord word = wordMap.get(rs.getInt("word-id"));
			word.addFeature(feature);
		}
		stmt.close();
		return text;
	}

	/**
	 * Get the specified analysis in the specified description in this dependency
	 * base.
	 * 
	 * @param descriptionId the description id
	 * @param name          the analysis name
	 * @return the analysis
	 * @throws SQLException if the query fails
	 */
	public final DepAnalysis getAnalysis(Integer descriptionId, String name) throws SQLException {
		String sql = "SELECT * FROM `analysis` WHERE `description-id` = ? AND `name` = ?";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, descriptionId);
		stmt.setString(2, name);
		ResultSet rs = stmt.executeQuery();
		if (!rs.next())
			return null;
		DepAnalysis analysis = new DepAnalysis();
		analysis.setId(rs.getInt("id"));
		analysis.setName(rs.getString("name"));
		stmt.close();
		return analysis;
	}

	/**
	 * Gets the specified corpus in this dependency base.
	 * 
	 * @param name the corpus name
	 * @return the corpus
	 * @throws SQLException if the query fails
	 */
	public final DepCorpus getCorpus(String name) throws SQLException {
		String sql = "SELECT * FROM `corpus` WHERE `name` = ?";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		if (!rs.next())
			return null;
		DepCorpus corpus = new DepCorpus();
		corpus.setId(rs.getInt("id"));
		corpus.setName(rs.getString("name"));
		stmt.close();
		return corpus;
	}

	/**
	 * Gets the specified description in this dependency base.
	 * 
	 * @param name the description name
	 * @return the description
	 * @throws SQLException if the query fails
	 */
	public final DepDescription getDescription(String name) throws SQLException {
		String sql = "SELECT * FROM `description` WHERE `name` = ?";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		if (!rs.next())
			return null;
		DepDescription description = new DepDescription();
		description.setId(rs.getInt("id"));
		description.setName(rs.getString("name"));
		stmt.close();
		sql = "SELECT S.* FROM `system` S " + "WHERE S.`description-id` = ?";
		stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, description.getId());
		rs = stmt.executeQuery();
		Map<Integer, DepSystem> systemMap = new HashMap<>();
		while (rs.next()) {
			DepSystem system = new DepSystem();
			system.setId(rs.getInt("id"));
			system.setName(rs.getString("name"));
			system.setEntryCondition(new DepEntryCondition(rs.getString("entry-condition")));
			systemMap.put(system.getId(), system);
			description.addSystem(system);
		}
		stmt.close();
		sql = "SELECT F.* FROM `feature` F " + "JOIN `system` S ON S.`id` = F.`system-id` "
				+ "WHERE S.`description-id` = ?";
		stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, description.getId());
		rs = stmt.executeQuery();
		while (rs.next()) {
			DepFeature feature = new DepFeature();
			feature.setId(rs.getInt("id"));
			feature.setName(rs.getString("name"));
			DepSystem system = systemMap.get(rs.getInt("system-id"));
			system.addFeature(feature);
		}
		stmt.close();
		sql = "SELECT S.* FROM `function` S " + "JOIN `metafunction` MF ON MF.`id` = S.`metafunction-id` "
				+ "WHERE MF.`description-id` = ?";
		stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, description.getId());
		rs = stmt.executeQuery();
		while (rs.next()) {
			DepFunction function = new DepFunction();
			function.setId(rs.getInt("id"));
			function.setName(rs.getString("name"));
			description.addFunction(function);
		}
		stmt.close();
		return description;
	}

	/**
	 * Get the specified language in this dependency base.
	 * 
	 * @param name the language name
	 * @return the language
	 * @throws SQLException if the query fails
	 */
	public final DepLanguage getLanguage(String name) throws SQLException {
		String sql = "SELECT * FROM `language` WHERE `name` = ?";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setString(1, name);
		ResultSet rs = stmt.executeQuery();
		if (!rs.next())
			return null;
		DepLanguage language = new DepLanguage();
		language.setId(rs.getInt("id"));
		language.setName(rs.getString("name"));
		stmt.close();
		return language;
	}

	/**
	 * Gets the specified text of the specified corpus in the specified language in
	 * this dependency base.
	 * 
	 * @param corpusId   the corpus id
	 * @param languageId the language id
	 * @param title      the text title
	 * @return the text
	 * @throws SQLException if the query fails
	 */
	public final DepText getText(Integer corpusId, Integer languageId, String title) throws SQLException {
		String sql = "SELECT * FROM `text` WHERE `corpus-id` = ? AND `language-id` = ? AND `title` = ?";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, corpusId);
		stmt.setInt(2, languageId);
		stmt.setString(3, title);
		ResultSet rs = stmt.executeQuery();
		if (!rs.next())
			throw new SQLException();
		DepText text = new DepText();
		text.setId(rs.getInt("id"));
		text.setTitle(rs.getString("title"));
		stmt.close();
		return text;
	}

	/**
	 * Remove the specified feature as a property of the specified word in the
	 * specified analysis in this dependency base.
	 * 
	 * @param analysisId the analysis id
	 * @param id         the feature id
	 * @param wordId     the word id
	 * @throws SQLException if the query fails
	 */
	public final void removeWordFeature(Integer analysisId, Integer id, Integer wordId) throws SQLException {
		String sql = "DELETE FROM `word-feature` WHERE `analysis-id` = ? AND `id` = ? AND `word-id` = ?";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, analysisId);
		stmt.setInt(2, id);
		stmt.setInt(3, wordId);
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Search for words that have the specified features in this dependency base.
	 * 
	 * @param wordFeatures the word features
	 * @return the words
	 * @throws SQLException if the query fails
	 */
	public final List<DepWord> searchForWords(DepWordFeature... wordFeatures) throws SQLException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT W.* \n");
		buffer.append("FROM `word` W \n");
		for (int i = 1; i <= wordFeatures.length; i++) {
			buffer.append(makeWordFeatureJoinSql(i));
		}
		String sql = buffer.toString();
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		int index = 1;
		for (DepWordFeature wordFeature : wordFeatures) {
			stmt.setString(index++, wordFeature.getAnalysisName());
			stmt.setString(index++, wordFeature.getFeatureName());
			stmt.setString(index++, wordFeature.getSystemName());
			stmt.setString(index++, wordFeature.getDescriptionName());
		}
		ResultSet rs = stmt.executeQuery();
		List<DepWord> words = new LinkedList<>();
		while (rs.next()) {
			DepWord word = new DepWord();
			word.setId(rs.getInt("id"));
			word.setForm(rs.getString("form"));
			word.setBackspaced(rs.getBoolean("backspaced"));
			word.setLemma(rs.getString("lemma"));
			words.add(word);
		}
		return words;
	}

}
