package net.tc.isma.resources;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import net.tc.isma.persister.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ConfigParams extends ResourceBaseImp
implements PersistentObject
{
	private long retrieved = 0;

	boolean isInizialized = false;
	Long resourceDate = null;
	private int resType = 0;
	private int lifeCycle = 0;
	private int type = Resource.INI;
	private int physicalType = Resource.INI;
	private File internalPhysicalReference = null;
	private long lastmodify = 0 ;

	public ConfigParams()
	{
	}

	public ConfigParams( File f, int resource, int life, int handleAs) throws IOException
	{
		if(handleAs != 0)
			this.type = handleAs;

		if(f == null || resource == 0 || life == 0)
			throw new IOException();
		internalPhysicalReference = f;
		if(!internalPhysicalReference.exists())
			throw new IOException();

		lastmodify = internalPhysicalReference.lastModified();
		if(internalPhysicalReference.getPath().toLowerCase().indexOf(".xml") > 0)
			physicalType = Resource.XML;
		resType = resource;
		lifeCycle = life;

		if(physicalType == Resource.XML)
			loadXml();
		else
			load();

		isInizialized =true;
	}

	public ConfigParams( File f, int resource, int life ) throws IOException
	{
		if(f == null || resource == 0 || life == 0)
			throw new IOException();
		internalPhysicalReference = f;
		if(!internalPhysicalReference.exists())
			throw new IOException();

		lastmodify = internalPhysicalReference.lastModified();
		resType = resource;
		lifeCycle = life;
		if(internalPhysicalReference.getPath().toLowerCase().indexOf(".xml") > 0)
			physicalType = Resource.XML;

		if(physicalType == Resource.XML)
			loadXml();
		else
			load();
	}

	public ConfigParams( File f ) throws IOException
	{
		internalPhysicalReference = f;
		if(!internalPhysicalReference.exists())
			throw new IOException();

		lastmodify = internalPhysicalReference.lastModified();
		resType = Resource.CONFIGURATION;
		lifeCycle = Resource.ETERNAL;
		if(internalPhysicalReference.getPath().toLowerCase().indexOf(".xml") > 0)
			physicalType = Resource.XML;

		if(physicalType == Resource.XML)
			loadXml();
		else
			load();
	}

	public ConfigParams( String file ) throws IOException
	{
		if(file == null || file.equals(""))
			return;
		File internalPhysicalReference = new File(file);
		if(!internalPhysicalReference.exists())
			throw new IOException();

		resType = Resource.CONFIGURATION;
		lifeCycle = Resource.ETERNAL;
		if(internalPhysicalReference.getPath().toLowerCase().indexOf(".xml") > 0)
			physicalType = Resource.XML;

		if(physicalType == Resource.XML)
			loadXml();
		else
			load();

	}

	public boolean checkLastModify( Object obj )
	{
		return false;
	}

	public boolean isInizialized()
	{
		return isInizialized;
	}

	public void load()
	{
	}
	public void loadXml()
	{
		if( internalPhysicalReference == null )
			return;
		try
		{
			SAXReader reader = new SAXReader();
			Document document = reader.read( internalPhysicalReference );
			Element el = null;
			try{ el = document.getRootElement();}catch(Throwable th){IsmaPersister.getLogSystem().error("", th);}
			treeWalk(el);
			return;

		}
		catch( DocumentException ex )
		{
			ex.printStackTrace();
		}
	}

	public void reFresh()
	{
		isInizialized =false;
		if(physicalType == Resource.XML)
			loadXml();
		else
			load();
		isInizialized =true;

	}

	public int getResourceType()
	{
		return type;
	}

	public int objectLifeCycle()
	{
		return lifeCycle;
	}

	public Object[] getResorceBundleResources()
	{
		if(isInizialized && size() > 0)
		{
			ArrayList arList = null;
			Iterator it = keySet().iterator();
			if(it.hasNext())
				arList = new ArrayList();
			while(it.hasNext())
			{
				String key = (String)it.next();
				if(("_" + key).indexOf("ResourceBundle") > 0 )
				{
					arList.add(get(key));
				}

			}
			return arList.toArray();
		}

		return null;
	}
	public boolean checkLastModify()
	{
		File f = null;
		if( internalPhysicalReference instanceof File )
			f = new File(((File)internalPhysicalReference).getAbsolutePath());
		if( f.lastModified() > lastmodify)
		{
			return true;
		}

		return false;
	}
	public Object getKey()
	{
		/** @todo Add key */
		return null;

	}

	public void setKey(Object keyin)
	{
		/** @todo Add key */
	}

	/* (non-Javadoc)
	 * @see net.tc.ismapersister.PersistentObject#getLastModify()
	 */
	public long getLastModify() {
		// TODO Auto-generated method stub
		return this.lastmodify;
	}

	/* (non-Javadoc)
	 * @see net.tc.ismaresources.Resource#treeWalk(org.dom4j.Element)
	 */
	public void treeWalk(Element element) {
		super.treeWalk(element);

	}
	public void increaseRetrieved()
	{
		retrieved++;
	}

	public long getRetrieved()
	{
		return retrieved;
	}

	public void resetRetrieved()
	{
		retrieved = 0;
	}


}
