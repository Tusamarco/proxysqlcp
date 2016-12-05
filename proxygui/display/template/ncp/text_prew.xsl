<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 <xsl:import href="text.xsl"/>

<xsl:output omit-xml-declaration="yes" method="xml" encoding="UTF-8" indent="yes" />


  <xsl:template match="text_prew" >
  	  <xsl:variable name="textid" select="text/@id" />
	  <xsl:variable name="type" select="text/@type" />
	  <xsl:variable name="textstyle" select="text/@style" />
	  <xsl:variable name="textname" select="text/@name" />
            text  id<br/>
            text type<br/>
            <xsl:value-of select="$textid"/>
            <xsl:value-of select="$type"/>
            <xsl:call-template name="text"></xsl:call-template>

  </xsl:template>

</xsl:stylesheet>
