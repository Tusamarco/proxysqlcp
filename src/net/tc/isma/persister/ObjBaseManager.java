package net.tc.isma.persister;

import org.apache.jcs.JCS;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

abstract public class ObjBaseManager
{

	private List names = new ArrayList();
	public ObjBaseManager()
	{

	}

	public JCS getObjManagerInstance( String region )
	{
		if( region != null && !region.equals( "" ) )
		{
			try
			{
				//JCS.setConfigFilename(FaostatPersister.getMAINROOT() + "/WEB-INF/cache.ccf");
				return JCS.getInstance( region );
			}
			catch( Throwable ex )
			{
				ex.printStackTrace();
//				throw new IsmaException( " Invalid Chache region " );
			}

		}
		return null;
//		throw new IsmaException( " Invalid Chache region " );
	}

	public String getObjectName(Class clss, Serializable id )
	{
		String idObj = null;
		if(clss != null && id != null)
		{
			idObj = clss.getName().trim() + "|" + id.toString().trim();
		}
		return idObj.trim();
	}



	/**
		 * Retrieves a Object.  Default to look in the cache.
		 */
		public Object getObject( Class clss, Serializable id )
		{
			Serializable objId = getObjectName( clss, id );
			return getObject( clss, objId, true );
		}

	/**
		 * Retrieves a Object. Second argument decides whether to look
		 * in the cache. Returns a new value object if one can't be
		 * loaded from the database. Database cache synchronization is+
		 * handled by removing cache elements upon modification.
		 */
		public Object getObject( Class clss, Serializable id, boolean fromCache )
		{
			IsmaPersister.getLogSystem().info("Retrieving " + id);
			Object Obj = null;

			// First, if requested, attempt to load from cache

			if( fromCache )
			{
				Obj = ( Object ) CacheManagerInstance().get( id );
			}

			// Either fromCache was false or the object was not found, so
			// call loadObject to create it

			if( Obj == null )
			{
				/** @todo IMPLEMENTS AS SOOSN AS POSSIBLE A GENERIC LOAD METHOD
				 * currently the cration and loading of the object is demanded to the application
				 * this as to be considered as a temporary solution.*/
//				Obj = loadObj( clss, id );
			}
			if(Obj instanceof PersistentObject)
			{
				Object ObjIn = ( (PersistentObject ) Obj ).getResource();

				if(ObjIn instanceof PersistentObject)
				{
					if(( ( PersistentObject ) ObjIn ).checkLastModify())
					{
						( ( PersistentObject ) ObjIn ).reFresh();
						( (persistentObjectImpl ) Obj ).setResource(ObjIn);
						( (persistentObjectImpl ) Obj ).setLastmodify(((PersistentObject)ObjIn).getLastModify());
						storeObj((( PersistentObject ) Obj ).getResource().getClass(),(PersistentObject)Obj);
					}
				}
				( ( PersistentObject ) Obj ).increaseRetrieved();
			}

			return Obj;
		}

		/** @todo IMPLEMENTS AS SOOSN AS POSSIBLE A GENERIC LOAD METHOD
		 * currently the cration and loading of the object is demanded to the application
		 * this as to be considered as a temporary solution.*/
		Object loadObj( Class clss, Serializable id )
		{
			Object Obj = null;
			try
			{
				Obj = clss.newInstance();
			}
			catch( Exception ex )
			{
				ex.printStackTrace();
			}

			/** @todo Add an Identifier for the object */
			if(Obj instanceof PersistentObject)
			{
				( ( PersistentObject ) Obj ).setKey( id );

				try
				{
					boolean found = false;

					// load the data and set the rest of the fields
					// set found to true if it was found

					found = true;

					// cache the value object if found

					if( found )
					{
						// could use the defaults like this
						// bookCache.put( "BookVObj" + id, vObj );
						// or specify special characteristics

					// put to cache

						CacheManagerInstance().put( id, Obj );
					}

				}
				catch( Exception e )
				{
					// Handle failure putting object to cache
				}
			}
			else
			{
				return null;
			}

			names.add(id);
			return Obj;
		}

	/**
		 * Stores BookVObj's in database.  Clears old items and caches
		 * new.
		 */
		public void storeObj( Class clss, PersistentObject pObj )
		{
			if( pObj.getKey() != null)
			{
				Serializable id = this.getObjectName( clss, (Serializable)pObj.getKey() );
				synchronized( id )
				{
					synchronized( id )
					{
						try
						{
							// since any cached data is no longer valid, we should
							// remove the item from the cache if it an update.

							if( pObj.getKey() != null )
							{
								CacheManagerInstance().remove( id );
								names.remove(id);
							}

							// put the new object in the cache
							IsmaPersister.getLogSystem().info(" Storing " + id);
							CacheManagerInstance().put( id, pObj );
							names.add(id);
						}
						catch( Exception e )
						{
							e.printStackTrace();
							// Handle failure removing object or putting object to cache.
						}
					}
				}

			}
		}
		public void removeObj( Class clss, PersistentObject pObj )
		{
			if( pObj.getKey() != null)
			{
				Serializable id = this.getObjectName( clss, (Serializable)pObj.getKey() );
				try
				{
					// since any cached data is no longer valid, we should
					// remove the item from the cache if it an update.

					if( pObj.getKey() != null )
					{
						CacheManagerInstance().remove( id );
						names.remove(id);
					}
				}
				catch( Exception e )
				{
					e.printStackTrace();
					// Handle failure removing object or putting object to cache.
				}
			}
		}

	public List getNames()
	{
		return names;
	}
	public abstract JCS CacheManagerInstance();

}
