package railo.loader.osgi.factory;

import java.io.File;
import java.io.IOException;

public class NameAlreadyExistsException extends IOException {

	private String name;
	private File file;
	private long size;

	public NameAlreadyExistsException(String name, File file, long size) { 
		super("a entry with name "+name+" is already assigned to the Zip File");
		this.name=name;
		this.file=file;
		this.size=size;
	}

	public String getName() {
		return name;
	}
	
	public File getFile() {
		return file;
	}
	
	public long getSize() {
		return size;
	}
}
