import java.io.IOException;
import java.io.InputStream;

/**
 * Get the data from the config.properties file.
 */
public class ReadPropertyFile {
	public void main() {
		InputStream input = ReadPropertyFile.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			MainFrame.prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}