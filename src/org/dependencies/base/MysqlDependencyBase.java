package org.dependencies.base;

import static java.lang.String.format;

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
import org.dependencies.dux.DuxFactory;
import org.dependencies.dux.DuxFunction;
import org.dependencies.dux.DuxMatch;
import org.dependencies.dux.DuxWord;
import org.dependencies.model.DepAnalysis;
import org.dependencies.model.DepCorpus;
import org.dependencies.model.DepDependency;
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
import org.dependencies.model.DepWordFunction;
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
	 * @param wOrder the word order of the join SQL
	 * @param fOrder the feature order of the join SQL
	 * @return the SQL
	 */
	private static final String makeWordFeatureJoinSql(Integer wOrder, Integer fOrder) {
		return format(
				"JOIN `word-feature` W%dWF%d ON W%dWF%d.`word-id` = W%d.`id` \n"
						+ "JOIN `analysis` W%dA%d ON W%dA%d.`id` = W%dWF%d.`analysis-id` AND W%dA%d.`name` = ? \n"
						+ "JOIN `feature` W%dF%d ON W%dF%d.`id` = W%dWF%d.`id` AND W%dF%d.`name` = ? \n"
						+ "JOIN `system` W%dS%d ON W%dS%d.`id` = W%dF%d.`system-id` AND W%dS%d.`name` = ? \n"
						+ "JOIN `description` W%dD%d ON W%dD%d.`id` = W%dS%d.`description-id` AND W%dD%d.`name` = ? \n",
				wOrder, fOrder, wOrder, fOrder, wOrder,
				wOrder, fOrder, wOrder, fOrder, wOrder, fOrder, wOrder, fOrder,
				wOrder, fOrder, wOrder, fOrder, wOrder, fOrder, wOrder, fOrder,
				wOrder, fOrder, wOrder, fOrder, wOrder, fOrder, wOrder, fOrder,
				wOrder, fOrder, wOrder, fOrder, wOrder, fOrder, wOrder, fOrder);
	}

	/**
	 * Makes a word function join SQL.
	 * 
	 * @param order the order of the join SQL
	 * @param wordIndex the word index of the join SQL
	 * @param headIndex the head index of the join SQL
	 * @return the SQL
	 */
	private static String makeWordFunctionJoinSql(Integer order, Integer wordIndex, Integer headIndex) {
		return format("JOIN `word-function` WF%d ON \n"
				+ "\t WF%d.`word-id` = W%d.`id` \n"
				+ "\t AND WF%d.`head-id` = W%d.`id` \n"
				+ "JOIN `function` WF%dF ON WF%dF.`id` = WF%d.`id` AND WF%dF.`name` LIKE ? \n"
				+ "JOIN `feature` WF%dFA ON WF%dFA.`id` = WF%d.`word-rank-id` AND WF%dFA.`name` = ? \n"
				+ "JOIN `feature` WF%dFB ON WF%dFB.`id` = WF%d.`head-rank-id` AND WF%dFB.`name` = ? \n"
				+ "JOIN `system` WF%dSA ON WF%dSA.`id` = WF%dFA.`system-id` AND (WF%dSA.`name` = 'RANK' OR WF%dSA.`name` LIKE '%s-COMPLEXITY') \n"
				+ "JOIN `system` WF%dSB ON WF%dSB.`id` = WF%dFB.`system-id` AND (WF%dSB.`name` = 'RANK' OR WF%dSB.`name` LIKE '%s-COMPLEXITY') \n"
				+ "JOIN `metafunction` WF%dM ON WF%dM.`id` = WF%dF.`metafunction-id` AND WF%dM.`name` = ? \n"
				+ "JOIN `analysis` WF%dA ON WF%dA.`id` = WF%d.`analysis-id` AND WF%dA.`name` = ? \n"
				+ "JOIN `description` WF%dD ON \n"
				+ "\t WF%dD.`name` = ? \n"
				+ "\t AND WF%dD.`id` = WF%dM.`description-id` \n"
				+ "\t AND WF%dD.`id` = WF%dA.`description-id` \n"
				+ "\t AND WF%dD.`id` = WF%dSA.`description-id` \n"
				+ "\t AND WF%dD.`id` = WF%dSB.`description-id` \n",
				order,
				order, wordIndex,
				order, headIndex,
				order, order, order, order,
				order, order, order, order,
				order, order, order, order,
				order, order, order, order, order, "%",
				order, order, order, order, order, "%",
				order, order, order, order,
				order, order, order, order,
				order, 
				order, 
				order, order, 
				order, order, 
				order, order, 
				order, order);
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
			this.conn = DriverManager.getConnection(format(url, name), user, password);
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
		sql = "SELECT F.*, WF.`word-id`, H.`order` AS `head-order` FROM `function` F " 
				+ "JOIN `word-function` WF ON WF.`id` = F.`id` "
				+ "JOIN `word` W ON W.`id` = WF.`word-id` " 
				+ "JOIN `word` H ON H.`id` = WF.`head-id` " 
				+ "JOIN `wording` Ws ON Ws.`id` = W.`wording-id` "
				+ "WHERE Ws.`text-id` = ? AND WF.`analysis-id` = ?";
		stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, text.getId());
		stmt.setInt(2, analysisId);
		rs = stmt.executeQuery();
		while (rs.next()) {
			DepFunction function = new DepFunction();
			function.setId(rs.getInt("id"));
			function.setName(rs.getString("name"));
			DepDependency dependency = new DepDependency();
			dependency.setHeadOrder(rs.getInt("head-order"));
			dependency.setFunction(function);
			DepWord word = wordMap.get(rs.getInt("word-id"));
			word.addDependency(dependency);
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
			throw new SQLException();
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
		sql = "SELECT MF.* FROM `metafunction` MF "
				+ "WHERE MF.`description-id` = ?";
		stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, description.getId());
		rs = stmt.executeQuery();
		Map<Integer, DepMetafunction> metafunctionMap = new HashMap<>();
		while (rs.next()) {
			DepMetafunction metafunction = new DepMetafunction();
			metafunction.setId(rs.getInt("id"));
			metafunction.setName(rs.getString("name"));
			description.addMetafunction(metafunction);
			metafunctionMap.put(metafunction.getId(), metafunction);
		}
		stmt.close();
		sql = "SELECT F.* FROM `function` F " + "JOIN `metafunction` MF ON MF.`id` = F.`metafunction-id` "
				+ "WHERE MF.`description-id` = ?";
		stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, description.getId());
		rs = stmt.executeQuery();
		while (rs.next()) {
			DepFunction function = new DepFunction();
			function.setId(rs.getInt("id"));
			function.setName(rs.getString("name"));
			DepMetafunction metafunction = metafunctionMap.get(rs.getInt("metafunction-id"));
			metafunction.addFunction(function);
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
	 * Removes a function as a property of the specified word at the specified rank
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
	public final void removeWordFunction(Integer analysisId, Integer id, Integer wordId, Integer wordRankId,
			Integer headId, Integer headRankId) throws SQLException {
		String sql = "DELETE FROM `word-function` \n"
				+ "\t WHERE `analysis-id` = ? \n"
				+ "\t AND `id` = ? \n"
				+ "\t AND `word-id` = ? \n"
				+ "\t AND `word-rank-id` = ? \n"
				+ "\t AND `head-id` = ? \n"
				+ "\t AND `head-rank-id` = ? \n";
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		stmt.setInt(1, analysisId);
		stmt.setInt(2, id);
		stmt.setInt(3, wordId);
		stmt.setInt(4, wordRankId);
		stmt.setInt(5, headId);
		stmt.setInt(6, headRankId);
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
	public final List<List<DepWord>> searchForWords(DuxFactory factory, List<DuxMatch> matches) throws SQLException {
		StringBuffer buffer1 = new StringBuffer();
		for (int i = 0; i < matches.size(); i++) {
			DuxMatch match = matches.get(i);
			if (match instanceof DuxWord) {
				if (buffer1.length() > 0) {
					buffer1.append(", ");
				}
				String sql = "W%d.`id` AS `id%d`, "
						+ "W%d.`form` AS `form%d`, "
						+ "W%d.`backspaced` AS `backspaced%d`, "
						+ "W%d.`lemma` AS `lemma%d`";
				Integer index = i + 1;
				buffer1.append(format(sql,
						index, index,
						index, index,
						index, index, 
						index, index));
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT " + buffer1 + " \n");
		List<DepWordFeature> lemmata = new LinkedList<>();
		List<String> wordIndices = new LinkedList<>();
		for (int i = 0; i < matches.size(); i++) {
			DuxMatch match = matches.get(i);
			DepWordFeature lemma = null;
			if (match instanceof DuxWord) {
				wordIndices.add(format("W%s", i + 1));
				if (i == 0) {
					buffer.append("FROM `word` W1 \n");
				} else {
					buffer.append(format("JOIN `word` W%d \n", i + 1));
				}
				DuxWord word = (DuxWord) match;
				List<DepWordFeature> wordFeatures = factory.makeWordFeatures(word);
				for (int j = 0; j < wordFeatures.size(); j++) {
					if (wordFeatures.get(j).getSystemName().equals("LEMMA")) {
						lemma = wordFeatures.get(j);
						continue;
					}
					buffer.append(makeWordFeatureJoinSql(i + 1, j + 1));
				}
			} else {
				DuxFunction function = (DuxFunction) match;
				buffer.append(makeWordFunctionJoinSql(i + 1, function.getWordIndex(), function.getHeadIndex()));
			}
			lemmata.add(lemma);
		}
		StringBuffer buffer2 = new StringBuffer();
		for (int i = 0; i < lemmata.size(); i++) {
			DepWordFeature lemma = lemmata.get(i);
			if (lemma == null) continue;
			if (buffer2.length() > 0) {
				buffer2.append(" AND ");
			}
			buffer2.append(format("W%s.`lemma` = ?", i + 1));
		}
		for (int i = 0; i < wordIndices.size(); i++) {
			for (int j = i + 1; j < wordIndices.size(); j++) {
				if (buffer2.length() > 0) {
					buffer2.append(" AND ");
				}
				buffer2.append(format("%s.`id` <> %s.`id`", wordIndices.get(i), wordIndices.get(j)));
			}
		}
		if (buffer2.length() > 0) {
			buffer.append("WHERE " + buffer2.toString() + " \n");
		}
		String sql = buffer.toString();
		PreparedStatement stmt = (PreparedStatement) this.conn.prepareStatement(sql);
		int index = 1;
		for (DuxMatch match : matches) {
			if (match instanceof DuxWord) {
				DuxWord word = (DuxWord) match;
				List<DepWordFeature> wordFeatures = factory.makeWordFeatures(word);
				for (DepWordFeature wordFeature : wordFeatures) {
					if (wordFeature.getSystemName().equals("LEMMA")) {
						continue;
					}
					stmt.setString(index++, wordFeature.getAnalysisName());
					stmt.setString(index++, wordFeature.getName());
					stmt.setString(index++, wordFeature.getSystemName());
					stmt.setString(index++, wordFeature.getDescriptionName());
				}
			} else {
				DuxFunction function = (DuxFunction) match;
				DepWordFunction wordFunction = factory.makeWordFunction(function);
				stmt.setString(index++, wordFunction.getName());
				stmt.setString(index++, wordFunction.getWordRankName());
				stmt.setString(index++, wordFunction.getHeadRankName());
				stmt.setString(index++, wordFunction.getMetafunctionName());
				stmt.setString(index++, wordFunction.getAnalysisName());
				stmt.setString(index++, wordFunction.getDescriptionName());
			}
		}
		for (DepWordFeature lemma : lemmata) {
			if (lemma != null) {
				stmt.setString(index++, lemma.getName());
			}
		}
		System.out.println(stmt);
		ResultSet rs = stmt.executeQuery();
		List<List<DepWord>> wordMatrix = new LinkedList<>();
		while (rs.next()) {
			List<DepWord> words = new LinkedList<>();
			for (int i = 0; i < matches.size(); i++) {
				DuxMatch match = matches.get(i);
				if (match instanceof DuxFunction) {
					words.add(null);
					continue;
				} else {
					index = i + 1;
					DepWord word = new DepWord();
					word.setId(rs.getInt("id" + index));
					word.setForm(rs.getString("form" + index));
					word.setBackspaced(rs.getBoolean("backspaced" + index));
					word.setLemma(rs.getString("lemma" + index));
					words.add(word);
				}
			}
			wordMatrix.add(words);
		}
		return wordMatrix;
	}

}
