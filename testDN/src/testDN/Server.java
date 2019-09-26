package testDN;

import java.util.ArrayList;
import java.util.List;

class Server {

	private int umax; // maximum number of users
	private int maxRemainingEffort; // check User's longest permanence on the server
	private List<User> userList;

	public Server(int umax) {

		this.userList = new ArrayList<User>();
		this.maxRemainingEffort = 0;
		this.umax = umax;
	}

	public void connectUser(User user) {
		this.userList.add(user);
	}

	public int getMaxRemainingEffort() {
		return maxRemainingEffort;
	}

	private void disconnectUsers() {
		ArrayList<User> newUserList = new ArrayList<User>();
		this.userList.forEach(user -> {
			if (!user.isTaskCompleted()) {
				newUserList.add(user);
			}
		});
		this.userList = newUserList;
	}

	private void updateUserTask() {
		this.maxRemainingEffort = -1;
		this.userList.forEach(user -> {
			user.updateUserTask();
			if (user.getTaskRemainTicks() > this.maxRemainingEffort) {
				this.maxRemainingEffort = user.getTaskRemainTicks();
			}
		});

	}

	public int getConnectedUsers() {
		return this.userList.size();
	}

	public void updateServerActivities() {
		updateUserTask();
		disconnectUsers();
	}

	public boolean hasCapacity() {
		return this.umax > this.getConnectedUsers();
	}

}
