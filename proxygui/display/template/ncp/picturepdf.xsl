<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	version="1.0">

 <xsl:template name="picturepdf" >

              <xsl:variable name="imgroot">C:/projects/ncp/webroot/</xsl:variable>
	      <xsl:variable name="textstyle" select="./@style" />
	      <xsl:variable name="textname" select="./@name" />
        <fo:block >
	      <xsl:variable name="imgpath">
                <xsl:value-of select="$imgroot"/><xsl:value-of select="." disable-output-escaping="yes"/>
              </xsl:variable>

          <!--fo:block xsl:use-attribute-sets="{$textstyle}"-->
          <!-- ho tolto alt="{$textname}" title="{$textname}" non sapevo come definirli...
              con gli attributi corrispondenti
              alt non credo che serva-->
              <xsl:value-of select="$imgpath"/>

              <!--fo:external-graphic  src="url('{$imgpath}')"/-->

              <!--fo:external-graphic  src="file:{$imgpath}"/-->

           <!--xsl:element name="fo:external-graphic">
             <xsl:attribute name="src">url('<xsl:value-of select="$imgpath"/>')
             </xsl:attribute>
          </xsl:element-->

           <fo:external-graphic>
                <xsl:attribute name="src">url('<xsl:value-of select="$imgpath"/>')
                </xsl:attribute>
           </fo:external-graphic>
        </fo:block>

  </xsl:template>
</xsl:stylesheet>
