package net.tc.proxysql;
// Generated 8-Dec-2016 2:36:43 PM by Hibernate Tools 5.2.0.CR1

/**
 * DebugLevels generated by hbm2java
 */
public class DebugLevels implements java.io.Serializable {

	private String module;
	private int verbosity;

	public DebugLevels() {
	}

	public DebugLevels(String module, int verbosity) {
		this.module = module;
		this.verbosity = verbosity;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public int getVerbosity() {
		return this.verbosity;
	}

	public void setVerbosity(int verbosity) {
		this.verbosity = verbosity;
	}

}
