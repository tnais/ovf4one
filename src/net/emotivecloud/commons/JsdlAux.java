package net.emotivecloud.commons;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsdlAux {
	private String xml;
	private String command;
	

	
	public JsdlAux() {
		this.xml = null;
		this.command = "/bin/slep 120";
	}

	public JsdlAux(String command) {
		this();
		this.command = command;
	}	
	
	public JsdlAux(String command, String xml) {
		this.xml = xml;
		this.command = command;
	}	
	
	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getXml() {
		return xml;
	}	
	
	public void setCommand(String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}
}
