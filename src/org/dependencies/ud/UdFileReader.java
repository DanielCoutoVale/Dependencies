package org.dependencies.ud;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.dependencies.base.MysqlDependencyBase;
import org.dependencies.model.DepDescription;
import org.dependencies.model.DepFeature;
import org.dependencies.model.DepFunction;
import org.dependencies.model.DepMetafunction;
import org.dependencies.model.DepSystem;
import org.xml.sax.SAXException;

/**
 * A UD file importer.
 * 
 * @author Daniel Couto-Vale
 */
public class UdFileReader {

	public final void importFile(String descriptionName, String fileName) throws ParserConfigurationException, SAXException, IOException, SQLException {
		File file = new File(fileName);
		UdDocumentBuilder builder = new UdDocumentBuilder();
		UdDocument document = builder.parse(file);
		MysqlDependencyBase base = new MysqlDependencyBase();
		DepDescription description = base.addDescription(descriptionName);
		DepMetafunction metafunction = base.addMetafunction(description.getId(), "MIXED");
		description.addMetafunction(metafunction);
		DepSystem rankSystem = base.addSystem(description.getId(), "RANK", "wording");
		DepFeature rankFeature = base.addFeature(rankSystem.getId(), "word");
		rankSystem.addFeature(rankFeature);
		DepSystem tagSystem = base.addSystem(description.getId(), "WORD-CLASS", "word");
		description.addSystem(tagSystem);
		for (UdTag tag : document.getTags()) {
			DepFeature feature = base.addFeature(tagSystem.getId(), tag.getName());
			tagSystem.addFeature(feature);
		}
		for (UdFeature feat : document.getFeats()) {
			DepSystem system = description.getSystem(feat.getName());
			if (system == null) {
				String entryCondition = String.join(" ∨ ", feat.getTagNames());
				system = base.addSystem(description.getId(), feat.getName(), entryCondition);
				description.addSystem(system);
			}
			DepFeature feature = base.addFeature(system.getId(), feat.getValue());
			tagSystem.addFeature(feature);
		}
		for (UdDependency dep : document.getDeps()) {
			DepFunction function = base.addFunction(metafunction.getId(), dep.getName());
			metafunction.addFunction(function);
		}
		base.close();
	}
	
}
