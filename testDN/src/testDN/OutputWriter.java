package testDN;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputWriter {

	private BufferedWriter writerObj;

	public BufferedWriter getWriterObj() {
		return writerObj;
	}

	public OutputWriter(String fileName) {
		try {
			this.writerObj = new BufferedWriter(new FileWriter(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
