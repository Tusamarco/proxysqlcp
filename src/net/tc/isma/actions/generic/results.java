package net.tc.isma.actions.generic;

import java.util.HashMap;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import net.tc.isma.actions.Results;
import net.tc.isma.lang.Language;
import net.tc.isma.utils.SynchronizedMap;

public class results extends SynchronizedMap
	implements Results, Language
{
	public results()
	{
	}

	private String[] languages = null;
	private String currentLang = Language.DEFAULTLANG;
        private boolean processExecuted = true;
        private Throwable th = null;

	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof results ) )return false;
		results castOther = ( results ) other;
		return new EqualsBuilder()
			.append( this.values(), castOther.values() )
			.isEquals();
	}

	public int hashCode()
	{
		return new HashCodeBuilder()
			.append( this.values() )
			.toHashCode();
	}

	public String getLanguage()
	{
		return currentLang;
	}

	public String[] getLanguages()
	{
		return languages;
	}

	public String getCurrentLanguage()
	{
		return getLanguage();
	}

	public void setCurrentLanguage(String lang)
	{
		if(lang != null && !lang.equals(""))
			currentLang = lang;
	}
    public boolean processSuccesflullyExecuted()
    {
        return processExecuted;
    }
    public void processSuccesflullyExecuted(boolean b)
    {
        processExecuted = b;
    }

    public void processSuccesflullyExecuted(boolean b, Throwable thIn)
    {
        processExecuted = b;
        th = thIn;

    }

}
