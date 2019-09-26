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
	
	//read file and handle exception
	public void readFile(String fileName) throws Exception {
		BufferedReader objReader = null;
		try {
			String strCurrentLine;

			objReader = new BufferedReader(new FileReader(fileName));
			strCurrentLine = objReader.readLine();
			if (strCurrentLine != null && strCurrentLine.length() > 0) {
				this.ttask = validateAndConvertInput(strCurrentLine);
			} else {
				throw new Exception("There is a blank line on line 1. Please check and run it again!");
			}
			strCurrentLine = objReader.readLine();
			if (strCurrentLine != null && strCurrentLine.length() > 0) {
				this.umax = validateAndConvertInput(strCurrentLine);
			} else {
				throw new Exception("There is a blank line on line 2. Please check and run it again!");
			}
			this.incomingUsers = new ArrayList<Integer>();
			int line = 2;
			while ((strCurrentLine = objReader.readLine()) != null) {
				line++;
				if (strCurrentLine.length() > 0) {
					this.incomingUsers.add((validateAndConvertInput(strCurrentLine)));
				} else {
					throw new Exception("There is a blank line on line:" + line + ". Please check and run it again!");
				}
			}
			validateFields();
			if (objReader != null)
				objReader.close();

		} catch (Exception e) {

			System.out.println(e.toString());

		}
	}
	
	//check integrity of input data

	public Integer validateAndConvertInput(String input) throws Exception {

		if (!input.matches("-?\\d+(\\.\\d+)?")) {
			throw new Exception("Only numbers are expected in the input file!");
		}

		int convertedInput = Integer.parseInt(input);
		if (convertedInput < 0) {
			throw new Exception("There is a negative number on the file!");
		}
		return convertedInput;
	}
	
	//Check limits for input variables

	public void validateFields() throws Exception {

		if (this.ttask <= 0 || this.ttask > 10) {
			throw new Exception("The value of ttask is out of the accepted range.");
		}
		
		if (this.umax <= 0 || this.umax > 10) {
			throw new Exception("The value of umax is out of the accepted range.");
		}
	}
}
