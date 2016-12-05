package net.tc.isma.data.generic;

import net.tc.isma.data.common.MultiLanguage;
import java.util.Map;

abstract public class dataObject extends MultiLanguage
{
	public dataObject()
	{
	}

	public abstract Map getRetrivableFieldsMap();

}
