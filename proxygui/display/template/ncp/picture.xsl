<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output omit-xml-declaration="yes" method="xml" encoding="UTF-8" indent="yes" />

 <xsl:template name="picture" >
	  <xsl:variable name="textstyle" select="./@style" />
	  <xsl:variable name="textname" select="./@name" />
	  <xsl:variable name="imgpath"><xsl:value-of select="." disable-output-escaping="yes"/></xsl:variable>
          <center>
          <div class="{$textstyle}">
              <img src= '{$imgpath}'  alt="{$textname}" title="{$textname}"/>
          </div>
          </center>
  </xsl:template>
</xsl:stylesheet>
