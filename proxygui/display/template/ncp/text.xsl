<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output omit-xml-declaration="yes" method="xml" encoding="UTF-8" indent="yes" />


  <xsl:template name="text" >
	  <xsl:variable name="textstyle" select="./@style" />
	  <xsl:variable name="textname" select="./@name" />
          <xsl:value-of select="." disable-output-escaping="yes"/>
  </xsl:template>

</xsl:stylesheet>
