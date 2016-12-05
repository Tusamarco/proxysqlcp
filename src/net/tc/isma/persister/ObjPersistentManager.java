package net.tc.isma.persister;

import org.apache.jcs.JCS;
import org.apache.jcs.engine.behavior.IElementAttributes;
import net.tc.isma.IsmaException;
import org.apache.jcs.access.exception.*;
import java.io.Serializable;

public class ObjPersistentManager extends ObjBaseManager
{
	private static ObjPersistentManager instance;
	private static int checkedOut = 0;
	private static JCS CacheManager;

	private ObjPersistentManager() throws IsmaException
	{
		try
		{
			CacheManager = super.getObjManagerInstance( "persistent" );
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
	public static ObjPersistentManager getInstance() throws IsmaException
	{
		if( instance == null )
		{
			synchronized( ObjPersistentManager.class )
			{
				if( instance == null )
				{
					instance = new ObjPersistentManager();
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
