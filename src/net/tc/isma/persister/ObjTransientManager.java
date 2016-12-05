package net.tc.isma.persister;

import org.apache.jcs.JCS;
import org.apache.jcs.engine.behavior.IElementAttributes;
import net.tc.isma.IsmaException;
import org.apache.jcs.access.exception.*;
import java.io.Serializable;

public class ObjTransientManager extends ObjBaseManager
{

	private static ObjTransientManager instance;
	private static int checkedOut = 0;
	private static JCS CacheManager;

	private ObjTransientManager() throws IsmaException
	{
		try
		{
			CacheManager = super.getObjManagerInstance( "transient" );
		}
		catch( Exception e )
		{
			e.printStackTrace();
			throw new IsmaException( " Invalid Chache region " );
			// Handle cache region initialization failure
		}

		// Do other initialization that may be necessary, such as getting
		// references to any data access classes we may need to populate
		// value objects later
	}

/**
	 * Singleton access point to the manager.
	 */
	public static ObjTransientManager getInstance() throws IsmaException
	{
		if( instance == null )
		{
			synchronized( ObjTransientManager.class )
			{
				if( instance == null )
				{
					instance = new ObjTransientManager();
				}
			}
		}

		synchronized( instance )
		{
			instance.checkedOut++;
		}

		return instance;
	}
	public JCS CacheManagerInstance()
	{
		return CacheManager;
	}
}
