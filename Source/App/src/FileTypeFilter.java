import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Filter the type of file wanted to be accepted.
 */
public class FileTypeFilter extends FileFilter {
	private final String extension, description;
	
	public FileTypeFilter(String extension, String description) {
		this.extension = extension;
		this.description = description;
	}

	@Override
	public boolean accept(File file) {
		if(file.isDirectory()) {
			return true;
		}
		return file.getName().endsWith(extension);
	}

	@Override
	public String getDescription() {
		return description + String.format(" (*%s)", extension);
	}
}