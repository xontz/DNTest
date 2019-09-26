package testDN;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadBalance {

	private String fileName;
	private int ttasks;
	private int umax;
	private List<Integer> incomingUser;
	private BufferedWriter oWriter;
	private List<Server> serverList;
	private int costPerServer;
	private int totalCost;

	public LoadBalance(String fileName) {
		this.fileName = fileName;
	}

	public void run() throws Exception {
		try {
			InputReader iReader = new InputReader();
			iReader.readFile(fileName);
			this.ttasks = iReader.getTtask();
			this.umax = iReader.getUmax();
			this.incomingUser = iReader.getIncomingUsers();
			this.oWriter = new OutputWriter("outuput.txt").getWriterObj();
			this.serverList = new ArrayList<Server>();
			this.costPerServer = 1;
			startSimulation();

		} catch (Exception e) {
			throw e;
		}

	}

	public void startSimulation() {

		// each interaction here is a tick
		while (!this.incomingUser.isEmpty() || !this.serverList.isEmpty()) {
			updateServerList();
			if (this.incomingUser.size() > 0) {
				int numberOfNewUsers = this.incomingUser.remove(0);
				connectNewUser(numberOfNewUsers);
			}
			calculeCost();
			writeState();

		}
		writeCost();
		try {
			this.oWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void connectNewUser(int numberOfNewUsers) {
		for (int i = 0; i < numberOfNewUsers; i++) {
			User u = new User(this.ttasks);
			loadBalance(u);
		}
	}

	public void loadBalance(User u) {
		// check if there are any servers
		if (this.serverList.size() > 0) {
			ArrayList<Integer> availableServersIndexes = checkAvailability();
			if (availableServersIndexes.size() == 0) {
				Server s = createNewServer();
				s.connectUser(u);
				this.serverList.add(s);
			} else {
				int index = checkFeasibility(availableServersIndexes);
				this.serverList.get(index).connectUser(u);

			}

		} else {
			Server s = createNewServer();
			s.connectUser(u);
			this.serverList.add(s);
		}

	}

	private int checkFeasibility(ArrayList<Integer> availableServersIndexes) {
		int bestServerIndex = -1;
		int maxRemaingEffortsOnServer = -1;

		for (int i = 0; i < availableServersIndexes.size(); i++) {
			int serverIndex = availableServersIndexes.get(i);
			if (this.serverList.get(serverIndex).getMaxRemainingEffort() > maxRemaingEffortsOnServer) {
				maxRemaingEffortsOnServer = this.serverList.get(serverIndex).getMaxRemainingEffort();
				bestServerIndex = serverIndex;
			}
		}
		return bestServerIndex;
	}

	private ArrayList<Integer> checkAvailability() {
		ArrayList<Integer> availableServerIndexes = new ArrayList<Integer>();
		for (int i = 0; i < this.serverList.size(); i++) {
			if (this.serverList.get(i).hasCapacity()) {
				availableServerIndexes.add(i);
			}
		}
		return availableServerIndexes;
	}

	public Server createNewServer() {
		return new Server(this.umax);
	}

	public void updateServerList() {

		ArrayList<Server> newServerList = new ArrayList<Server>();
		this.serverList.forEach(server -> {
			server.updateServerActivities();
			if (server.getConnectedUsers() > 0) {
				newServerList.add(server);
			}
		});
		this.serverList = newServerList;

	}

	public void calculeCost() {
		this.totalCost += (this.costPerServer * this.serverList.size());
	}

	private void writeState() {
		try {

			if (this.serverList.isEmpty()) {
				this.oWriter.write("0");
				this.oWriter.newLine();
			} else {
				if (this.serverList.size() == 1) {
					this.oWriter.write("" + this.serverList.get(0).getConnectedUsers());
				} else {
					for (int i = 0; i < this.serverList.size() - 1; i++) {
						this.oWriter.write("" + this.serverList.get(i).getConnectedUsers() + ",");
					}
					this.oWriter.write("" + this.serverList.get(this.serverList.size() - 1).getConnectedUsers());
				}
				this.oWriter.newLine();
			}

		}

		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private void writeCost() {
		try {
			this.oWriter.write("" + this.totalCost);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
