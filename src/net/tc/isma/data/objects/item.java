package net.tc.isma.data.objects;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import java.io.*;
import net.tc.isma.data.generic.baseObject;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.utils.*;
import net.tc.isma.data.generic.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */


/**
 * Object class that implements MAP for retreving and storing informations
* it could be used/populated by source coming from a db or any other data retrival
* <p>
* For EACH object of this time there MUST be a corrispondent BEAN that has as property all the information
* stored in the values Map
 */
public class item extends baseObject
	implements Serializable
{

	private String[] ExcludedProperty = new String[]{""};
	private static Map RetrivableFields = new HashMap();
	private boolean isGroup;
	private itemBean bean = null;


	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof item ) )return false;
		item castOther = ( item ) other;
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
		if( RetrivableFields == null  || RetrivableFields.size() <= 0)
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

		RetrivableFields.put( "ITEM", "item.id" );
		RetrivableFields.put( "DISSEMINATE_IND", "item.disseminateind" );
		RetrivableFields.put( "ITEM_TYP", "item.type" );
		RetrivableFields.put( "GRP_IND", "item.grpind" );
		RetrivableFields.put( "NAME_E", "nameEn" );
		RetrivableFields.put( "NAME_F", "nameFr" );
		RetrivableFields.put( "NAME_S", "nameEs" );
		RetrivableFields.put( "NAME_A", "nameAr" );
		RetrivableFields.put( "NAME_C", "nameZh" );
		RetrivableFields.put( "LONG_NAME_E", "namelongEn" );
		RetrivableFields.put( "LONG_NAME_F", "namelongFr" );
		RetrivableFields.put( "LONG_NAME_S", "namelongEs" );
		RetrivableFields.put( "LONG_NAME_A", "namelongAr" );
		RetrivableFields.put( "LONG_NAME_C", "namelongZh" );

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
			IsmaPersister.getLogDataAccess().error("",ex);
			return null;
		}
	}

	public Object getAsBeanXml( )
	{
		if(bean != null)
			return bean;

		try
		{
			bean = new itemBean();
			String encoding = (String)get("ENCODING");
			if(encoding == null)
				return null;


			bean.setId(get("item.id"));
			bean.setGrpind((String)get("item.grpind"));
			bean.setDisseminateind((String)get("item.disseminateind"));
			bean.setEndYear((String)get("item.endyear"));
			bean.setStartYear((String)get("item.startyear"));
			bean.setType((String)get("item.type"));

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

/*			bean.setNameEn(Text.getEncodedString(Text.getEncodedString((String)get("nameEn"),"ISO-8859-1"),"UTF-8"));
			bean.setNameFr(Text.getEncodedString(Text.getEncodedString((String)get("nameFr"),"ISO-8859-1"),"UTF-8"));
			bean.setNameEs(Text.getEncodedString(Text.getEncodedString((String)get("nameEs"),"ISO-8859-1"),"UTF-8"));
			bean.setNameAr(Text.getEncodedString(Text.getEncodedString((String)get("nameAr"),"windows-1256"),"UTF-8"));
			bean.setNameZh(Text.getEncodedString(Text.getEncodedString((String)get("nameZh"),"EUC-CN"),"UTF-8"));

			bean.setNameLongEn(Text.getEncodedString(Text.getEncodedString((String)get("namelongEn"),"ISO-8859-1"),"UTF-8"));
			bean.setNameLongFr(Text.getEncodedString(Text.getEncodedString((String)get("namelongFr"),"ISO-8859-1"),"UTF-8"));
			bean.setNameLongEs(Text.getEncodedString(Text.getEncodedString((String)get("namelongEs"),"windows-1256"),"UTF-8"));
			bean.setNameLongAr(Text.getEncodedString(Text.getEncodedString((String)get("namelongAr"),"windows-1256"),"UTF-8"));
			bean.setNameLongZh(Text.getEncodedString(Text.getEncodedString((String)get("namelongZh"),"EUC-CN"),"UTF-8"));
*/
            if(get("groups") != null && !get("groups").equals(""))
			bean.loadGroups((Object)get("groups"));


			this.bean = bean;
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}

		return bean;
	}

	public boolean isIsGroup()
	{
		if( super.values() == null )
			return false;

		return isGroup;
	}

	public Object getId()
	{
		return get("item.id");

	}
}
