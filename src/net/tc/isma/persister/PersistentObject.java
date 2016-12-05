package net.tc.isma.persister;

public interface PersistentObject
{
	public boolean checkLastModify();
	public boolean isInizialized();
	public int objectLifeCycle();
	public void reFresh();
	public Object getKey();
	public void setKey(Object key);
	public Object getResource();
	public long getLastModify();
	public void increaseRetrieved();
	public long getRetrieved();
	public void resetRetrieved();

}
