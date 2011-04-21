package net.emotivecloud.commons;

import java.io.Serializable;
import java.util.UUID;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Network  implements Serializable { //extends Domain
	
	private static final long serialVersionUID = -2655260253745183203L;

	private String name = null; //;
	
	private String id = null; //; //uuid
	
	private String uuid = null; //;

	private String address = null; //;

	private String gateway = null; //; //ip
	
	private String netmask = null; //; //ip
	
	private String dev = null; //;
	
	private String mode = null; //;  //route //nat   //null=isolated/privated
	
	private String bridge = null; //; //bridge name

	private String ip_start = null; //;
	
	private String ip_end = null; //;
	
	private String stp = null; //; //unused
	
	private String delay = null; //; //unused	
	
	private String host = "localhost"; //; //unused	

	
	//name uuid bridge address netmask dev mode start end 
	
	public Network() {
		this.setRandomId();
	}

	public Network(String name) {
		this();
		this.name = name;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUuid() {
		return uuid.toString();
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
		
	
	public void setRandomId() {
		this.uuid = UUID.randomUUID().toString();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	
	public String getBridge() {
		return bridge;
	}

	public void setBridge(String bridge) {
		this.bridge = bridge;
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}
	

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getDev() {
		return dev;
	}

	public void setDev(String dev) {
		this.dev = dev;
	}		

	public String getIp_start() {
		return ip_start;
	}

	public void setIp_start(String ip_start) {
		this.ip_start = ip_start;
	}
	public String getIp_end() {
		return ip_end;
	}

	public void setIp_end(String ip_end) {
		this.ip_end = ip_end;
	}

	public String getStp() {
		return stp;
	}

	public void setStp(String stp) {
		this.stp = stp;
	}
	
	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}	

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}		
	

}
