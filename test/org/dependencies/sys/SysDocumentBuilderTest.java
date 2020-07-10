package org.dependencies.sys;
import java.io.File;
import java.io.IOException;

import org.dependencies.model.DepMetafunction;
import org.dependencies.model.DepSystem;
import org.junit.jupiter.api.Test;

public class SysDocumentBuilderTest {

	@Test
	public final void testIp() throws IOException {
		File file = new File("ip.sys");
		SysDocumentBuilder builder = new SysDocumentBuilder();
		SysDocument document = builder.parse(file);
		for (DepSystem system : document.getSystems()) {
			if (system == null) throw new Error();
			System.out.println(system);
		}
		for (DepMetafunction metafunction : document.getMetafunctions()) {
			if (metafunction == null) throw new Error();
			System.out.println(metafunction);
		}
	}

}
