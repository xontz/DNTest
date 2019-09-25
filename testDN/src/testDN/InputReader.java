package testDN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputReader {

	private int ttask;
	private int umax;
	private List<Integer> incomingUsers;

	public int getTtask() {
		return ttask;
	}

	public int getUmax() {
		return umax;
	}

	public List<Integer> getIncomingUsers() {
		return incomingUsers;
	}

	public void readFile(String fileName) {
		BufferedReader objReader = null;
		try {
			String strCurrentLine;

			objReader = new BufferedReader(new FileReader(fileName));
			strCurrentLine = objReader.readLine();
			if (strCurrentLine != null && strCurrentLine.length() > 0) {
				this.ttask = Integer.parseInt(strCurrentLine);
			} else {
				throw new Exception("There is a blank line on line 1. Please check and run it again!");
			}
			strCurrentLine = objReader.readLine();
			if (strCurrentLine != null && strCurrentLine.length() > 0) {
				this.umax = Integer.parseInt(strCurrentLine);
			} else {
				throw new Exception("There is a blank line on line 2. Please check and run it again!");
			}
			this.incomingUsers = new ArrayList<Integer>();
			int line = 2;
			while ((strCurrentLine = objReader.readLine()) != null) {
				line++;
				if (strCurrentLine.length() > 0) {
					this.incomingUsers.add((Integer.parseInt(strCurrentLine)));
				} else {
					throw new Exception("There is a blank line on line:" + line + ". Please check and run it again!");
				}
			}
			validateFields();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			try {
				if (objReader != null)
					objReader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void validateFields() throws Exception {

		if (this.ttask <= 0 && this.ttask > 10) {
			throw new Exception("The value of ttask is out of the accepted range.");
		}
	}
}
