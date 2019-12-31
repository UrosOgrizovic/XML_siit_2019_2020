package rs.ac.uns.ftn.examples.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

	/**
	 * Convenience method for reading file contents into a string.
	 */
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static void writeFile(String path, String content) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(path);
		out.println(content);
		out.close();
	}
	
}
