<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

 <xsl:import href="text.xsl"/>
 <xsl:import href="dataset.xsl"/>
 <xsl:import href="picture.xsl"/>

<xsl:output omit-xml-declaration="yes" method="xml" encoding="UTF-8" indent="yes" />

  <xsl:template match="/NCPReport" >
  	  <xsl:variable name="mainpath" select="/NCPReport/@mainpath" />
  	  <xsl:variable name="reportid" select="/NCPReport/@id" />
	  <xsl:variable name="languageprod" select="/NCPReport/@languageofproduction" />
	  <xsl:variable name="yearprod" select="/NCPReport/@yearofproduction" />
	  <xsl:variable name="countryiso3" select="/NCPReport/Country/@iso3" />
  	  <xsl:variable name="faostatcode" select="/NCPReport/Country/@faostatcode" />
 	  <xsl:variable name="countryname"><xsl:value-of select="/NCPReport/Country/name" disable-output-escaping="no"/></xsl:variable>
   	  <xsl:variable name="countrylongname"><xsl:value-of select="/NCPReport/Country/longname" disable-output-escaping="no"/></xsl:variable>

<head>
  <link rel="stylesheet" href="/ncp/display/css/ncp.css" type="text/css"/>
  <title><!--xsl:value-of select="$countryname"/> - <xsl:value-of select="$yearprod"/--></title>
  <meta name="mainpath" content="{$mainpath}" />


</head>


	<!--xsl:comment >reportid =<xsl:value-of select="$reportid"/></xsl:comment>
	<xsl:comment >language prod = <xsl:value-of select="$languageprod"/></xsl:comment>
	<xsl:comment >yearprod = <xsl:value-of select="$yearprod"/></xsl:comment>
	<xsl:comment >countryiso3= <xsl:value-of select="$countryiso3"/></xsl:comment>
	<xsl:comment >countryname= <xsl:value-of select="$countryname"/></xsl:comment>
	<xsl:comment >countrylongname= <xsl:value-of select="$countrylongname"/></xsl:comment>
	<xsl:comment >faostatcode= <xsl:value-of select="$faostatcode"/></xsl:comment-->


        <!--xsl:value-of select="$countrylongname"/><br/>
	<xsl:value-of select="$yearprod"/><br/-->

        <xsl:call-template name="Theme"/><br/>


  </xsl:template>


<xsl:template name="Theme" >
	<xsl:for-each select="Theme" >
		<xsl:call-template name="readtheme"/>
	</xsl:for-each>
</xsl:template>


<xsl:template name="readtheme" >
                        <xsl:if test="position() = 1" >
                        <!--xsl:value-of select="position()"/-->
                          <div class="NCP_title1"><xsl:value-of select="./@name" disable-output-escaping="no"/></div><br/>
                         </xsl:if>
                         <xsl:if test="not(position() = 1)" >
                         <div class="chap"> <xsl:value-of select="./@name" disable-output-escaping="no"/></div><br/>
                         </xsl:if>
	<xsl:for-each select="./Textual" >
		<xsl:call-template name="readtextual"/><br/>
	</xsl:for-each>
</xsl:template>

<xsl:template name="readtextual">

	<xsl:value-of select="./@name" disable-output-escaping="no"/><br/>

              <xsl:for-each select="(*)">
                    <xsl:comment> <xsl:value-of select="name(.)" /></xsl:comment><br/>
                    <xsl:if test="name(.) = 'picture'" >
                            <xsl:call-template name="picture"/>
                    </xsl:if>
                    <xsl:if test="name(.) = 'dataset'" >
                            <xsl:call-template name="dataset"/>
                    </xsl:if>
                    <xsl:if test="name(.) = 'text'" >
                            <xsl:call-template name="text"/><br/>
                    </xsl:if>
                    <xsl:if test="name(.) = 'unbound'" >
                            <xsl:call-template name="text"/>
                    </xsl:if>
             </xsl:for-each>
</xsl:template>

</xsl:stylesheet>
