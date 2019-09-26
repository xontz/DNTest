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

	//disconnect users based on task conclusion
	private void disconnectUsers() {
		ArrayList<User> newUserList = new ArrayList<User>();
		this.userList.forEach(user -> {
			if (!user.isTaskCompleted()) {
				newUserList.add(user);
			}
		});
		this.userList = newUserList;
	}
	
	// update remaning time of user's task
	private void updateUserTask() {
		this.maxRemainingEffort = -1;
		this.userList.forEach(user -> {
			user.updateUserTask();
			if (user.getTaskRemainTicks() > this.maxRemainingEffort) {
				this.maxRemainingEffort = user.getTaskRemainTicks();
			}
		});

	}

	//total of simultaneously connected users 
	public int getConnectedUsers() {
		return this.userList.size();
	}

	
	public void updateServerActivities() {
		updateUserTask();
		disconnectUsers();
	}
	
	//check if server can accept more connections
	public boolean hasCapacity() {
		return this.umax > this.getConnectedUsers();
	}

}
