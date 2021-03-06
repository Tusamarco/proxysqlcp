package net.tc.proxysql;
// Generated 8-Dec-2016 2:36:43 PM by Hibernate Tools 5.2.0.CR1

/**
 * RuntimeMysqlServersId generated by hbm2java
 */
public class RuntimeMysqlServersId implements java.io.Serializable {

	private int hostgroupId;
	private String hostname;
	private int port;

	public RuntimeMysqlServersId() {
	}

	public RuntimeMysqlServersId(int hostgroupId, String hostname, int port) {
		this.hostgroupId = hostgroupId;
		this.hostname = hostname;
		this.port = port;
	}

	public int getHostgroupId() {
		return this.hostgroupId;
	}

	public void setHostgroupId(int hostgroupId) {
		this.hostgroupId = hostgroupId;
	}

	public String getHostname() {
		return this.hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RuntimeMysqlServersId))
			return false;
		RuntimeMysqlServersId castOther = (RuntimeMysqlServersId) other;

		return (this.getHostgroupId() == castOther.getHostgroupId())
				&& ((this.getHostname() == castOther.getHostname()) || (this.getHostname() != null
						&& castOther.getHostname() != null && this.getHostname().equals(castOther.getHostname())))
				&& (this.getPort() == castOther.getPort());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getHostgroupId();
		result = 37 * result + (getHostname() == null ? 0 : this.getHostname().hashCode());
		result = 37 * result + this.getPort();
		return result;
	}

}
