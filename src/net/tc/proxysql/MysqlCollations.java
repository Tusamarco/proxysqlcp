package net.tc.proxysql;
// Generated 8-Dec-2016 2:36:43 PM by Hibernate Tools 5.2.0.CR1

/**
 * MysqlCollations generated by hbm2java
 */
public class MysqlCollations implements java.io.Serializable {

	private int id;
	private String collation;
	private String charset;
	private String default_;

	public MysqlCollations() {
	}

	public MysqlCollations(int id, String collation, String charset, String default_) {
		this.id = id;
		this.collation = collation;
		this.charset = charset;
		this.default_ = default_;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCollation() {
		return this.collation;
	}

	public void setCollation(String collation) {
		this.collation = collation;
	}

	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getDefault_() {
		return this.default_;
	}

	public void setDefault_(String default_) {
		this.default_ = default_;
	}

}
