package net.tc.proxysql;
// Generated 8-Dec-2016 2:36:43 PM by Hibernate Tools 5.2.0.CR1

/**
 * GlobalVariables generated by hbm2java
 */
public class GlobalVariables implements java.io.Serializable {

	private String variableName;
	private String variableValue;

	public GlobalVariables() {
	}

	public GlobalVariables(String variableName, String variableValue) {
		this.variableName = variableName;
		this.variableValue = variableValue;
	}

	public String getVariableName() {
		return this.variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public String getVariableValue() {
		return this.variableValue;
	}

	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}

}