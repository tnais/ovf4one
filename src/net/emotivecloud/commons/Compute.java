package net.emotivecloud.commons;

import java.io.Serializable;
import java.util.UUID;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Compute  implements Serializable { //extends Domain
	
	private static final long serialVersionUID = -2655260253745183203L;

	private String id;

	private int vmId;

	private String name;

	private String sourceHost;
	
	private int priority;

	private String ip;

	private String gateway;

	// Requirements
	private int homeSize;

	private int swapSize;

	private int diskSize;
	
	private int memory;

	private int cpu;

	private String extension;
	
	private String architecture;
	
	private String mirror;
	
	private String kernelVersion;
	
	private String kernelConstraint;
	
	private String[] packages;
	
	private String release;
	
	private String packageFormat;
	
	private String diskPath;
	
	private String kernelPath;
	
	private String initrdPath;
	
	private String machineHome;
	
	private boolean checkpointable;
	
	private String privateKey;		//Becarefull! do not show to the user
	
	private String publicKey;
	
	public Compute() {
		this.setRandomId();
		this.checkpointable = false;
	}

	public Compute(String name) {
		this();
		this.name = name;
	}

	public int getCPU() {
		return cpu;
	}

	public void setCPU(int cpu) {
		this.cpu = cpu;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public int getHomeSize() {
		return homeSize;
	}

	public void setHomeSize(int homeSize) {
		this.homeSize = homeSize;
	}

	public String getId() {
		return id.toString();
	}

	public void setRandomId() {
		this.id = UUID.randomUUID().toString();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceHost() {
		return this.sourceHost;
	}

	public void setSourceHost(String sourceHost) {
		this.sourceHost = sourceHost;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getSwapSize() {
		return swapSize;
	}

	public void setSwapSize(int swapSize) {
		this.swapSize = swapSize;
	}

	public int getVMId() {
		return vmId;
	}

	public void setVMId(int vmId) { 
		this.vmId = vmId;
	}

	public String getKernelVersion() {
		return kernelVersion;
	}
	
	public void setKernelVersion(String kernelVersion) {
		this.kernelVersion = kernelVersion;
	}

	public String getKernelConstraint() {
		return kernelConstraint;
	}

	public void setKernelConstraint(String kernelConstraint) {
		this.kernelConstraint = kernelConstraint;
	}

		
	public int getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(int diskSize) {
		this.diskSize = diskSize;
	}

	public String getArchitecture() {
		return architecture;
	}

	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}

	public String getMirror() {
		return mirror;
	}

	public void setMirror(String mirror) {
		this.mirror = mirror;
	}

	public String[] getPackages() {
		return packages;
	}

	public void setPackages(String[] packages) {
		this.packages = packages;
	}
	
	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}


	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public String getDiskPath() {
		return diskPath;
	}

	public void setDiskPath(String diskPath) {
		this.diskPath = diskPath;
	}

	public String getKernelPath() {
		return kernelPath;
	}

	public void setKernelPath(String kernelPath) {
		this.kernelPath = kernelPath;
	}

	public String getInitrdPath() {
		return initrdPath;
	}

	public void setInitrdPath(String initrdPath) {
		this.initrdPath = initrdPath;
	}

	public String getMachineHome() {
		return machineHome;
	}

	public void setMachineHome(String machineHome) {
		this.machineHome = machineHome;
	}

	public String getPackageFormat() {
		return packageFormat;
	}

	public void setPackageFormat(String packageFormat) {
		this.packageFormat = packageFormat;
	}
	
	public void setCheckpointable(boolean checkpointable) {
		this.checkpointable = checkpointable;
	}

	public boolean isCheckpointable() {
		return checkpointable;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

}
