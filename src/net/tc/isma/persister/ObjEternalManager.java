package net.tc.isma.persister;

import org.apache.jcs.JCS;
import org.apache.jcs.engine.behavior.IElementAttributes;
import net.tc.isma.IsmaException;
import org.apache.jcs.access.exception.*;
import java.io.Serializable;


public class ObjEternalManager extends ObjBaseManager
{
	private static ObjEternalManager instance;
	private static int checkedOut = 0;
	private static JCS CacheManager;

	private ObjEternalManager() throws IsmaException
	{
		try
		{
			CacheManager = super.getObjManagerInstance( "eternal" );
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
	public static ObjEternalManager getInstance() throws IsmaException
	{
		if( instance == null )
		{
			synchronized( ObjEternalManager.class )
			{
				if( instance == null )
				{
					instance = new ObjEternalManager();
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
