import java.io.IOException;
import java.io.InputStream;

/**
 * Get the data from the configuration files.
 */
public class ReadPropertyFile {
	public void main(String fileName) {
		InputStream input = ReadPropertyFile.class.getClassLoader().getResourceAsStream(fileName);
		try {
			MainFrame.prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}