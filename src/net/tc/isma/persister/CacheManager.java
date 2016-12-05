package net.tc.isma.persister;

import java.io.Serializable;
import net.tc.isma.resources.Resource;

public class CacheManager
{
	private static ObjPersistentManager PersistentManager = null;
	private static ObjTransientManager TransientManager = null;
	private static ObjEternalManager EternalManager = null;
	private static boolean inizialized = false;
	CacheManager()
	{
		InizializeCacheLayer();
	}

	ObjPersistentManager getPersistentManager()
	{
		if( PersistentManager == null )
			InizializeCacheLayer();

		return PersistentManager;
	}

	ObjEternalManager getEternalManager()
	{
		if( EternalManager == null )
			InizializeCacheLayer();

		return EternalManager;
	}

	ObjTransientManager getTransientManager()
	{
		if( TransientManager == null )
			InizializeCacheLayer();

		return TransientManager;
	}

	private synchronized void InizializeCacheLayer()
	{
		if(inizialized)
			return;
		try
		{
			TransientManager = ObjTransientManager.getInstance();
			EternalManager = ObjEternalManager.getInstance();
			PersistentManager = ObjPersistentManager.getInstance();
			inizialized = true;
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}

	}

	Object get(Class cls, Serializable id)
	{
		Object obj = null;
		try{
			obj = getEternalManager().getObject( cls, id );

			if( obj == null )
				obj = getPersistentManager().getObject( cls, id );
			if( obj == null )
				obj = getTransientManager().getObject( cls, id );
		}catch(Exception ex)
		{
			IsmaPersister.getLogSystem().error(ex.getMessage(),ex);
		}
		if(obj != null && obj instanceof PersistentObject)
			obj = ((PersistentObject)obj).getResource();

		return (Object)obj;
	}
	void set(Serializable id, PersistentObject obj)
	{
		try
		{
//			Resource res = ( Resource ) obj;
			persistentObjectImpl po = ( persistentObjectImpl ) obj;

			set( po.getResource().getClass(), id, obj, po.objectLifeCycle() );
		}catch(Exception ex)
		{IsmaPersister.getLogSystem().error(ex.getMessage(),ex);}
	}
	void remove(Serializable id, PersistentObject obj)
	{
		try
		{
			Resource res = ( Resource ) obj;
			PersistentObject po = ( PersistentObject ) obj;

			remove( res.getResource().getClass(), id, obj, po.objectLifeCycle() );
		}catch(Exception ex)
		{IsmaPersister.getLogSystem().error(ex.getMessage(),ex);}

	}
	void remove(Class cls, Serializable id, Object obj, int LifeCycle)
	{
		try{
			PersistentObject po = null;
			if( obj instanceof PersistentObject )
			{
				po = ( PersistentObject ) obj;
			}
			else
			{
				po = new persistentObjectImpl( LifeCycle, obj );
				po.setKey( id );
			}

			switch( LifeCycle )
			{
				case Resource.ETERNAL:
					getEternalManager().removeObj( cls, po );
					break;
				case Resource.PERSISTENT:
					getPersistentManager().removeObj( cls, po );
					break;
				case Resource.TRANSIENT:
					getTransientManager().removeObj( cls, po );
					break;
				default:
					break;
			}
			IsmaPersister.getLogSystem().info(" Removing Object " + cls + " id:" + id + " LifeCycle = " + po.objectLifeCycle());

		}catch(Exception ex)
		{IsmaPersister.getLogSystem().error(ex.getMessage(),ex);}

	}
	void set(Class cls, Serializable id, Object obj, int LifeCycle)
	{
		try{
			PersistentObject po = null;
			if( obj instanceof PersistentObject )
			{
				po = ( PersistentObject ) obj;
			}
			else
			{
				po = new persistentObjectImpl( LifeCycle, obj );
				po.setKey( id );
			}

			switch( LifeCycle )
			{
				case Resource.ETERNAL:
					getEternalManager().storeObj( cls, po );
					break;
				case Resource.PERSISTENT:
					getPersistentManager().storeObj( cls, po );
					break;
				case Resource.TRANSIENT:
					getTransientManager().storeObj( cls, po );
					break;
				default:
					break;
			}
			IsmaPersister.getLogSystem().info("Storing Object " + cls + " id:" + id + " LifeCycle = " + po.objectLifeCycle());

		}catch(Exception ex)
		{IsmaPersister.getLogSystem().error(ex.getMessage(),ex);}

	}
}
