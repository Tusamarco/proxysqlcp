package net.tc.isma.actions;

import java.util.List;
import net.tc.isma.views.View;
import java.util.Map;
import java.util.TreeSet;


public interface ActionChain
{
	public int getExecOrder();
	public String getName();
	public View getView();
	public boolean isActive();
	public void setActive( boolean active );
	public void setExecOrder( int execOrder );
	public void setName( String name );
	public void setView( View view );
        public void setSecurity( String restricted );
        public String getSecurity(  );
	public void putAction(Object key, Action action);
	public Action getAction(Object key);
	public Object[][] getActions();
	public void setActions(Object[][] Actions);
}
