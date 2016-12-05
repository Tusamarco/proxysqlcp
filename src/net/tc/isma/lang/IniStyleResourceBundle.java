package net.tc.isma.lang;

import java.io.IOException;
import java.util.*;
import net.tc.isma.utils.IniFile;
import java.util.ResourceBundle;
import java.io.File;
import net.tc.isma.persister.PersistentObject;
import net.tc.isma.utils.Text;
import org.dom4j.Element;

/*
 String[] files = getConfigParameterValues("ResourceBundle.filename");
 File f = new File(fullPath);
 if (f.lastModified() > Long.parseLong(iniRes.getLastModify()))
 {
 for (int x = 0; x < files.length; x++)
 {
 String fullPathx = ISMAMainServlet.ABSOLUTEPATH +"/"+ files[x];
 File fx = new File(fullPathx);
 fx.setLastModified(f.lastModified());
 }
 FaostatPersister.getLogSystem().info(" File changed while the application is running: ReLoading multi-lingual text from " + fullPath);
 return true;
 }

 */
public class IniStyleResourceBundle extends ResourceBundle
	implements PersistentObject
{
	private long retrieved = 0;

	IniFile defs;
	Locale locale = null;
	File f = null;
	long lastModify = 0;
	private String iniPath;
	boolean isInizialized = false;
	Long resourceDate = null;
	private int lifeCycle = 0;
	private Object key = null;

	public IniStyleResourceBundle( IniFile ini, Locale locale )
	{
		this.defs = ini;
		this.locale = locale;
		this.setLastModify( ini.getProperty( "lastModify" ) );
		this.setIniPath( ini.getProperty( "iniFilePath" ) );
	}

	public String getUTFString(String key, String lang)
	{
		String s = null;
		s = Text.getUTFStringFromDb(super.getString(key),lang);
		return s;
	}
	public String getDirectString(String key)
	{
		String s = null;
		s = super.getString(key);
		return s;
	}

	public Locale getLocale()
	{
		return this.locale;
	}

	protected Object handleGetObject( String key )
	{
		String lang = getLocale().getLanguage();
		String txt = null;

		key = key.toLowerCase();
		txt = defs.getProperty( lang + "." + key );

		if( txt == null )
			txt = defs.getProperty( "var." + key );

		if( txt == null )
			txt = "{" + lang + "." + key + "}";

		return txt;
	}

	public Enumeration getKeys()
	{
		Enumeration enuml = defs.keys();
		Vector keys = new Vector();
		String lang = getLocale().getLanguage();

		while( enuml.hasMoreElements() )
		{
			String key = ( String ) enuml.nextElement();
			if( key.startsWith( lang + "." ) )
				keys.add( key.substring( lang.length() + 1 ) );
		}

		return keys.elements();
	}

	public static void main( String[] argv )
	{
		try
		{
			IniFile ini = new IniFile();

			ResourceBundle rsc = new IniStyleResourceBundle( ini, new Locale( "en" ) );

			Enumeration enuml = rsc.getKeys();
			while( enuml.hasMoreElements() )
			{
				String key = ( String ) enuml.nextElement();
				System.out.println( key + "=" + rsc.getString( key ) );
			}

		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}

	public long getLastModify()
	{
		return lastModify;
	}


	public void setLastModify( String lastModify )
	{
		//invalid operation the last modify is assigned automatically
	}

	public String getIniPath()
	{
		return iniPath;
	}

	public void setIniPath( String iniPath )
	{
		//invalid operation the value is assigned automatically
	}

	public boolean checkLastModify()
	{
		File f = null;
		f = new File( this.getIniPath());
		if( f.lastModified() > this.getLastModify() )
		{
			return true;
		}

		return false;

	}

	public boolean isInizialized()
	{
		return isInizialized;
	}

	public int objectLifeCycle()
	{
		return lifeCycle;
	}

	public void reFresh()
	{
	}

	public void setIsInizialized( boolean isInizialized )
	{
		this.isInizialized = isInizialized;
	}

	public void setLifeCycle( int lifeCycle )
	{
		this.lifeCycle = lifeCycle;
	}

	public Object getKey()
	{
		return key;
	}
	public void setKey(Object keyin)
	{
		key = keyin;
	}
	public Object getResource()
	{
		return this.defs;
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
