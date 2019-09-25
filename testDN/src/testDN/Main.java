package testDN;

public class Main {

	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				// expecting to get input file from here
				String fileName = args[0];
				if (fileName.length() <= 0) {
					throw new Exception("You should provide a file name");
				}

				new LoadBalance(fileName).run();

			}
			else {
				throw new Exception("You should provide a file name");
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}
