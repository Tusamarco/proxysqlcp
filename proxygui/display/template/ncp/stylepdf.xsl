<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	version="1.0">


        <xsl:attribute-set name="ncpTitle">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">10pt</xsl:attribute>
           <xsl:attribute name="font-weight">bolder</xsl:attribute>
           <xsl:attribute name="text-align">center</xsl:attribute>
           <xsl:attribute name="color">red</xsl:attribute>
        </xsl:attribute-set>

        <xsl:attribute-set name="ncpTitle1">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">12pt</xsl:attribute>
           <xsl:attribute name="font-weight">bolder</xsl:attribute>
           <xsl:attribute name="text-align">center</xsl:attribute>
           <xsl:attribute name="color">black</xsl:attribute>
        </xsl:attribute-set>

        <xsl:attribute-set name="chap">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">10pt</xsl:attribute>
           <xsl:attribute name="font-weight">bolder</xsl:attribute>
           <xsl:attribute name="text-align">center</xsl:attribute>
           <xsl:attribute name="space-before">6pt</xsl:attribute>
           <xsl:attribute name="space-after">6pt</xsl:attribute>
        </xsl:attribute-set>

        <xsl:attribute-set name="footer">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">9pt</xsl:attribute>
           <xsl:attribute name="border-top-style">solid</xsl:attribute>
           <xsl:attribute name="border-width">0.5pt</xsl:attribute>
        </xsl:attribute-set>

        <xsl:attribute-set name="tableTitle">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">9pt</xsl:attribute>
           <xsl:attribute name="font-weight">bolder</xsl:attribute>
           <xsl:attribute name="text-align">center</xsl:attribute>
        </xsl:attribute-set>

        <xsl:attribute-set name="tableSubTitle">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">9pt</xsl:attribute>
           <xsl:attribute name="font-weight">bolder</xsl:attribute>
           <xsl:attribute name="text-align">left</xsl:attribute>
        </xsl:attribute-set>

        <xsl:attribute-set name="tableText">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">9pt</xsl:attribute>
           <xsl:attribute name="text-align">left</xsl:attribute>
        </xsl:attribute-set>

         <xsl:attribute-set name="tableText_1">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">9pt</xsl:attribute>
           <xsl:attribute name="text-align">center</xsl:attribute>
        </xsl:attribute-set>

        <xsl:attribute-set name="tableTextSpecial">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">9pt</xsl:attribute>
           <xsl:attribute name="text-align">center</xsl:attribute>
           <xsl:attribute name="font-style">italic</xsl:attribute>
        </xsl:attribute-set>

        <xsl:attribute-set name="tableTextSpecial_1">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">9pt</xsl:attribute>
           <xsl:attribute name="text-align">left</xsl:attribute>
           <xsl:attribute name="font-style">italic</xsl:attribute>
        </xsl:attribute-set>

        <xsl:attribute-set name="tableTextSpecial_2">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">9pt</xsl:attribute>
           <xsl:attribute name="text-align">right</xsl:attribute>
           <xsl:attribute name="font-style">italic</xsl:attribute>
        </xsl:attribute-set>

        <xsl:attribute-set name="ssChap">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">5pt</xsl:attribute>
           <xsl:attribute name="font-weight">bolder</xsl:attribute>
           <!--xsl:attribute name="space-before">6pt</xsl:attribute-->
        </xsl:attribute-set>

        <xsl:attribute-set name="parag">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">10pt</xsl:attribute>
           <xsl:attribute name="space-before">24pt</xsl:attribute>
           <xsl:attribute name="text-decoration">underline</xsl:attribute>
        </xsl:attribute-set>

        <xsl:attribute-set name="ncpText">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">8pt</xsl:attribute>
           <xsl:attribute name="text-align">justify</xsl:attribute>
        </xsl:attribute-set>

        <xsl:attribute-set name="tableau">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">10pt</xsl:attribute>
           <xsl:attribute name="space-before">6pt</xsl:attribute>
           <xsl:attribute name="text-decoration">underline</xsl:attribute>
        </xsl:attribute-set>

        <xsl:attribute-set name="figure">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">10pt</xsl:attribute>
           <xsl:attribute name="list-style-type">disc</xsl:attribute>
           <xsl:attribute name="text-indent">1.06cm</xsl:attribute>
        </xsl:attribute-set>

         <xsl:attribute-set name="ssParag">
           <xsl:attribute name="font-family">Arial</xsl:attribute>
           <xsl:attribute name="font-size">10pt</xsl:attribute>
           <xsl:attribute name="font-style">italic</xsl:attribute>
           <xsl:attribute name="text-indent">1.06cm</xsl:attribute>
           <xsl:attribute name="space-before">12pt</xsl:attribute>
           <xsl:attribute name="space-after">6pt</xsl:attribute>
        </xsl:attribute-set>

</xsl:stylesheet>
