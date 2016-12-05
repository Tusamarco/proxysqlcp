<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	version="1.0">


  <xsl:template name="textpdf" >


	    <xsl:variable name="textstyle" select="./@style" />
            <xsl:variable name="textname" select="./@name" />
            <xsl:value-of select="." disable-output-escaping="yes"/>


  </xsl:template>

</xsl:stylesheet>
