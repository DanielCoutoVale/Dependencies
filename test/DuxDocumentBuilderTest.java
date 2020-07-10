import java.io.File;

import org.dependencies.dux.DuxCommand;
import org.dependencies.dux.DuxDocument;
import org.dependencies.dux.DuxDocumentBuilder;
import org.junit.jupiter.api.Test;

public class DuxDocumentBuilderTest {

	@Test
	public final void testIttbIp() {
		File file = new File("ittb-ip.dux");
		DuxDocumentBuilder builder = new DuxDocumentBuilder();
		DuxDocument document = builder.parse(file);
		for (DuxCommand command : document) {
			if (command == null) throw new Error();
			System.out.println(command);
		}
	}

}
