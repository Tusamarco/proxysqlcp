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

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class area extends baseObject
	implements Serializable
{

	private String[] ExcludedProperty = new String[]{""};
	private static Map RetrivableFields = new HashMap();
	private boolean isGroup;
	private areaBean bean = null;

	public area()
	{

	}
	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof area ) )return false;
		area castOther = ( area ) other;
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
		if( RetrivableFields == null  || RetrivableFields.size() <= 0)
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

		RetrivableFields.put( "AREA", "area.id" );
		RetrivableFields.put( "ISO_3_CODE", "area.iso3" );
		RetrivableFields.put( "GRP_IND", "area.grpind" );
		RetrivableFields.put( "START_YR", "area.startyear" );
		RetrivableFields.put( "END_YR", "area.endyear" );
		RetrivableFields.put( "NAME_E", "nameEn" );
		RetrivableFields.put( "NAME_F", "nameFr" );
		RetrivableFields.put( "NAME_S", "nameEs" );
		RetrivableFields.put( "NAME_C", "nameZh" );
		RetrivableFields.put( "NAME_A", "nameAr" );
		RetrivableFields.put( "LONG_NAME_E", "namelongEn" );
		RetrivableFields.put( "LONG_NAME_F", "namelongFr" );
		RetrivableFields.put( "LONG_NAME_S", "namelongEs" );
		RetrivableFields.put( "LONG_NAME_C", "namelongZh" );
		RetrivableFields.put( "LONG_NAME_A", "namelongAr" );



//		RetrivableFields.put( "SUBAREAS", "subAreas" );

		return RetrivableFields;
	}

/**
	 * Used to return all the values as a bean
	 */
	public Object getAsBean()
	{

		try
		{
			return this.getAsBean( this, (Map)this );
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
	   if(bean != null && bean.getId() !=  null)
		   return bean;

	   try
	   {
		   bean = new areaBean();
		   String encoding = (String)get("ENCODING");
		   if(encoding == null)
			   return null;

		   bean.setId(get("area.id"));
		   bean.setGrpInd((String)get("area.grpind"));
		   bean.setISO3((String)get("area.iso3"));
		   bean.setEndYear((String)get("area.endyear"));
		   bean.setStartYear((String)get("area.startyear"));
		   bean.setNameEn((String)get("nameEn"));
		   bean.setNameFr((String)get("nameFr"));
		   bean.setNameEs((String)get("nameEs"));
		   bean.setNameAr((String)get("nameAr"));
		   bean.setNameZh((String)get("nameZh"));

		   bean.setNameLongEn((String)get("namelongEn"));
		   bean.setNameLongFr((String)get("namelongFr"));
		   bean.setNameLongEs((String)get("namelongEs"));
		   bean.setNameLongAr((String)get("namelongAr"));
		   bean.setNameLongZh((String)get("namelongZh"));

		   if(get("subareas") != null && get("subareas") instanceof Map)
			   bean.loadSubAreas((Map)get("subareas"));
			else
				bean.setSubAreas(null);


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
	   return get("area.id");

   }

}
