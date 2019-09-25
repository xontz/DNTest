package testDN;

class User {

	private int taskSize;
	private int taskRemainTicks; // # of cycles to complete task and remove user

	public int getTaskRemainTicks() {
		return taskRemainTicks;
	}

	public User(int tticks) {
		this.taskSize = tticks;
		this.taskRemainTicks = this.taskSize;
	}

	public void updateUserTask() {
		this.taskRemainTicks -= 1;
	}

	public boolean isTaskCompleted() {
		return this.taskRemainTicks <= 0;

	}
}
