package net.emotivecloud.commons;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HadoopJobUpdate  implements Serializable { 

	private static final long serialVersionUID = -4225998813644761137L;

	//@XmlElement
	private HashMap<String,HadoopJob> jobs;
	
	private int currentSlots;
	private int requiredSlots;
	
	public HadoopJobUpdate(){
		this.jobs = new HashMap<String,HadoopJob>();
	}
	public HadoopJobUpdate(int currentSlots, int requiredSlots){
		this.jobs = new HashMap<String,HadoopJob>();
		this.currentSlots=currentSlots;
		this.requiredSlots=requiredSlots;
	}
	public HashMap<String,HadoopJob> getJobs() {
		return this.jobs;
	}
	public void setJobs(HashMap<String,HadoopJob> jobs) {
		this.jobs = jobs;
	}
	public void addJob(HadoopJob j){
		this.jobs.put(j.getJobId(), j);
	}
	public int getCurrentSlots() {
		return this.currentSlots;
	}
	public void setCurrentSlots(int currentSlots) {
		this.currentSlots = currentSlots;
	}
	public int getRequiredSlots() {
		return this.requiredSlots;
	}
	public void setRequiredSlots(int requiredSlots) {
		this.requiredSlots = requiredSlots;
	}
}
