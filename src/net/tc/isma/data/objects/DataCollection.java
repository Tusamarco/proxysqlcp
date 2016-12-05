package net.tc.isma.data.objects;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import java.io.*;
import net.tc.isma.data.common.MultiLanguage;
import java.util.Map;
import net.tc.isma.data.generic.baseObject;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.data.generic.*;
import java.util.Collections;
import java.util.Comparator;
import net.tc.isma.utils.SynchronizedMap;
import net.tc.isma.comparators.CollectionsSortByOrderComparator;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DataCollection extends baseObject
	implements Serializable
{

	private String[] ExcludedProperty = new String[]{""};
	private static Map RetrivableFields = new HashMap();
	private boolean isGroup;
	private collectionBean bean = null;
	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof DataCollection ) )return false;
		DataCollection castOther = ( DataCollection ) other;
		return new EqualsBuilder()
			.append( this, castOther )
			.isEquals();
	}



	public void setIsGroup( boolean isGroup )
	{
		this.isGroup = isGroup;
	}

	public String[] getExcludedProperty()
	{
		return this.ExcludedProperty;
	}

	public List getRetrivableFields()
	{
		if( RetrivableFields == null )
			RetrivableFields = initRetrivableFields();

		return new ArrayList( RetrivableFields.keySet() );
	}

	public Map getRetrivableFieldsMap()
	{
		if( RetrivableFields == null )
			RetrivableFields = initRetrivableFields();

		return RetrivableFields;
	}

	public void init( Map rowValues )
	{

	}

	public Class itemClass()
	{
		return this.getClass();
	}

/**
	 * This method is a temporary solution for the definition of the mapping between
	 * db and properties
	 * it should be moved to xml file
	 */
	private Map initRetrivableFields()
	{
		RetrivableFields = new HashMap();

//		RetrivableFields.put( "ITEM", "id" );
//		RetrivableFields.put( "DISSEMINATE_IND", "disseminateind" );
//		RetrivableFields.put( "ITEM_TYP", "type" );
//		RetrivableFields.put( "GRP_IND", "grpind" );
//		RetrivableFields.put( "NAME_E", "nameEn" );
//		RetrivableFields.put( "NAME_F", "nameFr" );
//		RetrivableFields.put( "NAME_S", "nameEs" );
//		RetrivableFields.put( "NAME_A", "nameAr" );
//		RetrivableFields.put( "NAME_C", "nameZh" );
//		RetrivableFields.put( "LONG_NAME_E", "nameLongEn" );
//		RetrivableFields.put( "LONG_NAME_F", "nameLongFr" );
//		RetrivableFields.put( "LONG_NAME_S", "nameLongEs" );
//		RetrivableFields.put( "LONG_NAME_A", "nameLongAr" );
//		RetrivableFields.put( "LONG_NAME_C", "nameLongZh" );

/*		private Map note;
		private String thirdkey;
		private String axisElement;
		private String zone;
		private int version;
		private String additionalText;
		private java.util.List items;
		private java.util.List elements;
		private java.util.List areas;
	*/


		return RetrivableFields;
	}

/**
	 * Used to return all the values as a bean
	 */
	public Object getAsBean()
	{

		if(bean != null)
			return this.bean;
		try
		{
			bean = (collectionBean) getAsBean( this,(Map)this );
			setBean(bean);
			return bean;
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
			return null;
		}
	}

	public boolean isIsGroup()
	{
		if( super.values() == null )
			return false;

		return isGroup;
	}
	public Object getAsBeanXml()
	{
		if(bean != null)
			return bean;

		try
		{
			bean = new collectionBean();
			String encoding = (String)get("ENCODING");
			if(encoding == null)
				return null;

			bean.setId(get("datacollection.id"));
			bean.setType((String)get("datacollection.type"));
			bean.setGrpind((String)get("datacollection.grpind"));
			bean.setStartYear((String)get("datacollection.startyear"));
			bean.setEndYear((String)get("datacollection.endyear"));
            bean.setInternalname((String) get("internalname"));
			bean.setNameEn((String)get("nameEn"));
			bean.setNameFr((String)get("nameFr"));
			bean.setNameEs((String)get("nameEs"));
			bean.setNameAr((String)get("nameAr"));
			bean.setNameZh((String)get("nameZh"));
			bean.setFirstkey((String)get("first-key"));
			bean.setSecondkeytex((String)get("second-key-text"));
			bean.setThirdkey((String)get("third-key-text"));
			bean.setSelecttext((String)get("select-text"));
			bean.setCopyrightlink((String)get("copyright-link"));
			bean.setCopyrighttext((String)get("copyright-text"));
			bean.setAxisElement((String)get("axis-element"));

			if(get("notes") != null && get("notes") instanceof Map)
				bean.loadNotes((Map)get("notes"));

			bean.loadRedirect((Map)get("redirect"));
			bean.loadItems(get("items"));
			bean.loadElements(get("elements"));
			bean.loadAreas(get("areas"));

			this.bean = bean;
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}

		return bean;
	}
	public Object getId()
	{
		return get("datacollection.id");

	}
  public void setBean(collectionBean bean)
  {
    this.bean = bean;
  }
  public static  Map sort( Map collections )
  {
	  if(collections != null)
	   {
		   List l = new ArrayList(collections.keySet());
		   Collections.sort( l, ( Comparator )new CollectionsSortByOrderComparator() );
           Collections.reverse(l);

		   Map nsubset = new SynchronizedMap();
		   for(int i = 0 ; i < l.size() ; i++)
		   {
			   Object k = l.get(i);
			   nsubset.put(k,collections.get(k));
		   }
		   collections = nsubset;
	   }
	  return collections;
  }

}
