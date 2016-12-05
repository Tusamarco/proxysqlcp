package net.tc.isma.persister;

import net.tc.isma.resources.Resource;
import org.dom4j.Element;
import java.io.File;
import net.tc.isma.resources.ResourceBaseImp;
import org.dom4j.io.SAXReader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import net.tc.isma.resources.ConfigResource;
import net.tc.isma.IsmaException;

public class persistentObjectImpl extends ResourceBaseImp
	implements PersistentObject
{
	private Object sourceObject = null;
	boolean inizialized = false;
	int objLifeCycle = 0;

	private long retrieved = 0;
	private long lastmodify = 0;
	private Object key = null;
	private Document document = null;
	private int resourceType = 0 ;
	public persistentObjectImpl()
	{
	}

	public persistentObjectImpl( ConfigResource crf ) throws IsmaException
	{
		String path = crf.getStringValue();
		int handleas = 0;
		if( crf.getParameter().containsKey( "tohandleas" ) )
			handleas = Integer.parseInt( ( String ) crf.getParameter().get( "tohandleas" ) );

		if( crf.getParameter().containsKey( "lifecycle" ) )
			objLifeCycle = Integer.parseInt( ( String ) crf.getParameter().get( "lifecycle" ) );


		if(crf.getKey() != null)
			key = crf.getKey();

		if( path != null && !path.equals( "" ) )
		{
                    File f = new File( IsmaPersister.getMAINROOT() + "/" + path );

                    if(f.exists())
                    {
                      path = IsmaPersister.getMAINROOT() + "/" + path;
                    }
                    else
                    {
                        f = new File( path );
                        if(!f.exists())
                        {
                          throw new IsmaException("Error in file assignment file " + path + "do not exist");

                        }

                    }
			Object obj = new File( path );
			super.internalPhysicalReference = (File)obj;
			super.setPhysicalType();
			init(obj, handleas);
		}
	}
	public persistentObjectImpl( int LifeCycle, Object obj)
	{
		if( obj != null )
		{
			objLifeCycle = LifeCycle;
			super.put(obj.getClass().getPackage()+ "." + obj.getClass().getName(),obj);
			sourceObject = obj;
			inizialized = true;
			lastmodify = System.currentTimeMillis();
		}

	}


	public persistentObjectImpl( Object obj, int handleas )
	{
		if( obj != null )
			init(obj, handleas);
	}

	private void init(Object obj, int handleas)
	{
		if( obj != null )
		{
			sourceObject = obj;

			if( sourceObject != null )
				inizialized = true;

			resourceType = handleas;

			if( sourceObject instanceof File )
				lastmodify = ( ( File ) sourceObject ).lastModified();

				if( handleas == Resource.XML && this.getPhysicalType() == Resource.XML )
                    loadXml() ;
		}

	}
	public boolean checkLastModify()
	{
		File f = null;
		if( sourceObject instanceof File )
		{
			f = new File( ( ( File ) sourceObject ).getAbsolutePath() );
			if(f.isFile())
			{
				if( f.lastModified() > lastmodify )
				{
					return true;
				}
			}
			else if(f.isDirectory())
			{
				File[] fl = f.listFiles();
				for(int xf = 0 ; xf < fl.length ; xf++)
				{
					if( fl[xf].lastModified() > lastmodify )
					{
						return true;
					}
				}
				return false;
			}
		}

		return false;
	}

	public boolean isInizialized()
	{
		return inizialized;
	}

	public int objectLifeCycle()
	{
		return objLifeCycle;
	}

	public void reFresh()
	{
		if(sourceObject instanceof File)
		{
			String path = ((File)sourceObject).getAbsolutePath();
			if( path != null && !path.equals( "" ) )
			{
				Object obj = new File( path );
				init( obj, resourceType );
			}
		}

	}

	public int getResourceType()
	{
		return resourceType;
	}

	public void loadXml()
	{
		if( sourceObject instanceof File )
		{
			if( sourceObject == null )
				return;
			try
			{
				SAXReader reader = new SAXReader();
				document = reader.read( ( File ) sourceObject );
				clear();
				treeWalk( document.getRootElement() );
				return;

			}
			catch( DocumentException ex )
			{
				inizialized = false;
				ex.printStackTrace();
			}
		}
	}

	public Object getResource()
	{
		if( inizialized && sourceObject != null )
			return sourceObject;

		return null;
	}
	public String getString(String key)
	{
		if(containsKey(key))
			get(key);

		return null;
	}

	public Object getKey()
	{
		return key;
	}

	public void setKey(Object keyin)
	{
		key = keyin;
	}

	/**
	 * @param sourceObject The sourceObject to set.
	 */
	public void setResource(Object sourceObject) {
		this.sourceObject = sourceObject;
	}
	/**
	 * @return Returns the lastmodify.
	 */
	public long getLastmodify() {
		return lastmodify;
	}
	/**
	 * @param lastmodify The lastmodify to set.
	 */
	public void setLastmodify(long lastmodify) {
		this.lastmodify = lastmodify;
	}

	/* (non-Javadoc)
	 * @see net.tc.ismapersister.PersistentObject#getLastModify()
	 */
	public long getLastModify() {
		// TODO Auto-generated method stub
		return this.lastmodify;
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
