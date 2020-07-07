package org.dependencies.sys;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.dependencies.base.MysqlDependencyBase;
import org.dependencies.model.DepDescription;
import org.dependencies.model.DepFeature;
import org.dependencies.model.DepSystem;

/**
 * A SYS document importer.
 *  
 * @author Daniel Couto-Vale
 */
public class SysFileImporter {

	public final void importFile(String descriptionName, String fileName) throws IOException, SQLException {
		File file = new File(fileName);
		SysDocumentBuilder builder = new SysDocumentBuilder();
		SysDocument document = builder.parse(file);
		MysqlDependencyBase base = new MysqlDependencyBase();
		DepDescription description = base.addDescription(descriptionName);
		for (DepSystem docSystem : document.getSystems()) {
			DepSystem system = base.addSystem(description.getId(), docSystem.getName(), docSystem.getEntryConditionForm());
			description.addSystem(system);
			for (DepFeature docFeature : docSystem.getFeatures()) {
				DepFeature feature = base.addFeature(system.getId(), docFeature.getName());
				system.addFeature(feature);
			}
		}
	}

}
