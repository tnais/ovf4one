package net.emotivecloud.commons;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HadoopJob implements Serializable {

	private static final long serialVersionUID = -4221411911578770234L;
	private long goal;
	private int slots;
	private long prediction;
	private String jobId;
	
	public HadoopJob (){} ;
	public HadoopJob(String id, int slots, long goal, long prediction) {
		this.goal = goal;
		this.slots = slots;
		this.prediction = prediction;
		this.jobId = id;
	}		
	public long getGoal() {
		return goal;
	}
	public void setGoal(long goal) {
		this.goal = goal;
	}
	public int getSlots() {
		return slots;
	}
	public void setSlots(int slots) {
		this.slots = slots;
	}
	public long getPrediction() {
		return prediction;
	}
	public void setPrediction(long prediction) {
		this.prediction = prediction;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
}
