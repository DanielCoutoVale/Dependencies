package org.dependencies.dux;
import java.io.File;

import org.dependencies.dux.DuxDocument;
import org.dependencies.dux.DuxDocumentBuilder;
import org.junit.jupiter.api.Test;

public class DuxDocumentBuilderTest {
	
	@Test
	public final void testSample() {
		File home = new File(".");
		DuxDocumentBuilder builder = new DuxDocumentBuilder();
		DuxDocument document = builder.parse(home, "sample");
		for (DuxCommand command : document) {
			if (command == null) throw new Error();
			System.out.println(command);
		}
	}

	@Test
	public final void testIttbIp() {
		File home = new File("ittb-ip");
		DuxDocumentBuilder builder = new DuxDocumentBuilder();
		DuxDocument document = builder.parse(home, "main");
		for (DuxCommand command : document) {
			if (command == null) throw new Error();
			System.out.println(command);
		}
	}

}
