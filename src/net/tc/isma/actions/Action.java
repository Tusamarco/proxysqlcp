package net.tc.isma.actions;

import java.util.List;
import net.tc.isma.request.generic.requestImpl;

public interface Action
{
	public Action getBoundedAction();
	public String getClassname();
	public String getClasspkg();
	public int getExecorder();
	public String getName();
	public boolean isBounded();
	public void setBounded( boolean bounded );
	public void setBoundedAction( Action boundedAction );
	public void setClassname( String classname );
	public void setClasspkg( String classpkg );
	public void setExecorder( int execorder );
	public void setName( String name );
	public Results getResult();
	public void setResult(Results results);
	public Results execute();
	public boolean reload();
	public void setReload(boolean reload);
    public void setRequest(requestImpl reqImpl);
    public requestImpl getRequest();

}
