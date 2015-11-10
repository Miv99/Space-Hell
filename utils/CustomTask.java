package utils;

import com.badlogic.gdx.utils.Timer.Task;

public class CustomTask {
	public Task task;
	public float delay, interval;
	public int repeatCount = -1;
	
	public CustomTask(Task task, float delay, float interval, int repeatCount) {
		this.task = task;
		this.delay = delay;
		this.interval = interval;
		this.repeatCount = repeatCount;
	}
	public CustomTask(Task task, float delay, float interval) {
		this.task = task;
		this.delay = delay;
		this.interval = interval;
	}
}