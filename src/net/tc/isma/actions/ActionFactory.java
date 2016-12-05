package net.tc.isma.actions;

import java.util.Map;
import net.tc.isma.actions.Results;

public interface ActionFactory
{
	public Map getActionChains(String[] chainsNames);
}
