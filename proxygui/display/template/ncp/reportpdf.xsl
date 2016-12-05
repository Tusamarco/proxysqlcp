<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	version="1.0">

 <xsl:import href="../webroot/display/template/ncp/textpdf.xsl"/>
 <!--xsl:import href="../webroot/display/template/ncp/datasetpdf.xsl"/-->
 <xsl:import href="../webroot/display/template/ncp/picturepdf.xsl"/>
 <xsl:import href="../webroot/display/template/ncp/stylepdf.xsl"/>

 <!--xsl:import href="textpdf.xsl"/-->
 <!--xsl:import href="datasetpdf.xsl"/-->
 <!--xsl:import href="picturepdf.xsl"/>
<xsl:import href="stylepdf.xsl"/-->

  <xsl:template match="/NCPReport" >

    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

         <!-- page  -->
         <fo:layout-master-set>
                <fo:simple-page-master master-name="simple" page-height="29.7cm" page-width="21cm" margin-top="0.5cm" margin-bottom="0cm" margin-left="0.85cm" margin-right="0.85cm">
                    <fo:region-body margin-top="2cm" margin-bottom="2cm"/>
                    <fo:region-before extent="5.5cm"/>
                    <fo:region-after extent="1cm"/>
                    <fo:region-start extent="0cm"/>
                    <fo:region-end extent="0cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

          <!-- index table of contents -->
          <fo:page-sequence master-reference="simple">

             <!-- header -->

              <fo:static-content flow-name="xsl-region-before">
                    <!--fo:block  line-height="14pt" space-after.optimum="10pt" padding-top="3pt"></fo:block-->
  	      </fo:static-content>

           <!--footer -->
                <fo:static-content flow-name="xsl-region-after">
                    <fo:table table-layout="fixed">
                        <fo:table-column column-width="19cm"/>
                        <fo:table-column column-width="0.3cm"/>
                        <fo:table-body>
                            <fo:table-row border-left-color="green" border-left-width="0.5pt" border-left-style="solid">
                                <fo:table-cell>
                                    <fo:block>
                                    <fo:leader leader-pattern="rule" rule-thickness="0.01cm" leader-length="19cm"/>
                                    </fo:block>
                                    <fo:block font-size="6pt" font-family="sans-serif" text-align="left">
                                         <xsl:value-of select="/NCPReport/Country/name"/>Nutrition Profile&#160;-&#160;Food and Nutrition Division,FAO,2005
                                     </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>
                                    <fo:leader leader-pattern="rule" rule-thickness="0.01cm" leader-length="0.3cm"/>
                                    </fo:block>
                                    <fo:block font-size="6pt" font-family="sans-serif" text-align="right">
                                        <fo:page-number/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                </fo:static-content >


            <!-- per il momento la paginazione é fittizia, poi va sistemata -->
            <!-- body index -->

            <fo:flow flow-name="xsl-region-body">
              <fo:table table-layout="fixed">
                <fo:table-column column-width="19cm"/>
                <fo:table-column column-width="0.3cm"/>
                <fo:table-body>
                  <fo:table-row height="0.6cm">
                    <fo:table-cell border-left-style="solid" border-top-style="solid" border-bottom-style="solid" border-width="0.5pt" display-align="center">
                        <fo:block text-align="center" font-size="10pt" font-weight="bold">
                            TABLE OF CONTENTS
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell border-right-style="solid" border-top-style="solid" border-bottom-style="solid" border-width="0.5pt" display-align="center">
                        <fo:block>
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.6cm">
                    <fo:table-cell >
                        <fo:block >
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            Acknowledgments..................................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             2
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            Summary.................................................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             3
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            Summary Table.......................................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             4
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            List of table and figures.........................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             6
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            Acronyms................................................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             7
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            Part I: Overview and basic indicators...................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             8
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            &#160;&#160;&#160;&#160;&#160;I.1 Context..........................................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             8
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            &#160;&#160;&#160;&#160;&#160;I.2 Population.....................................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             8
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Population indicators......................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             8
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Population pyramid for 2001...........................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             9
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            &#160;&#160;&#160;&#160;&#160;I.3 Agriculture....................................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             9
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Land use and irrigation statistics...................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             10
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Main crops, agricultural calendar, seasonal food shortage...........................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             10
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Livestock production and fishery...................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             10
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            &#160;&#160;&#160;&#160;&#160;I.4 Economy......................................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             11
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            &#160;&#160;&#160;&#160;&#160;I.5 Social indicators.........................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             11
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                   <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Health indicators............................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             11
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Water and sanitation......................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             12
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Access to health services..............................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             13
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Education.......................................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             13
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Level of development, poverty.......................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             14
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Other social indicators...................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             14
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            Part II: Food and nutrition situation.....................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             15
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            &#160;&#160;&#160;&#160;&#160;II.1 Qualitative aspects of the diet and food security....................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             15
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Food consumption patterns...........................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             15
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Food security situation...................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             15
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            &#160;&#160;&#160;&#160;&#160;II.2 National food supply data..........................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             16
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Supply of major food groups..........................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             16
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Dietary energy supply, distribution by macronutrient and diversity of the food supply..................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             17
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Vegetable/animal origin of macronutrients....................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             18
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Dietary energy supply by food group.............................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             18
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Food imports and exports expressed as percentage of DES........................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             19
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Food aid.........................................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             20
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            &#160;&#160;&#160;&#160;&#160;II.3 Food consumption.....................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             20
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;National level surveys....................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             20
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            &#160;&#160;&#160;&#160;&#160;II.4 Infant and young child feeding practices.................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             21
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            &#160;&#160;&#160;&#160;&#160;II.5 Nutritional anthropometry.........................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             23
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Low birth weight............................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             23
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Anthropometry of preschool children............................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             23
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Anthropometry of school-age children and adolescents...............................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             27
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Anthropometry of adult women.....................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             27
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Anthropometry of adult men..........................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             30
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                   <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            &#160;&#160;&#160;&#160;&#160;II.6 Micronutrient deficiencies.........................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             30
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Iodine deficiency disorders(IDD)...................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             30
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Prevalence of goitre and urinary iodine level...........................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             30
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Iodization of salt at household level.........................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             30
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Vitamin A deficiency(VAD)............................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             31
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Prevalence of sub-clinical and clinical vitamin A deficiency.....................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             32
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Vitamin A supplementation.......................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             32
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Iron deficiency anemia(IDA)..........................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             33
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Prevalence of IDA....................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             33
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Interventions to combat IDA.....................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             35
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt">
                            &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;Other micronutrient deficiencies....................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             35
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            &#160;&#160;&#160;&#160;&#160;II.7 Policies and programmes aiming to improve nutrition and food security...........................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             35
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell>
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            Reference list.........................................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt" font-weight="bold">
                             37
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                </fo:table-body>
              </fo:table>
            </fo:flow>
          </fo:page-sequence>

          <!-- index List of tables and figures -->

          <fo:page-sequence master-reference="simple">

             <!-- header -->

              <fo:static-content flow-name="xsl-region-before">
                    <!--fo:block  line-height="14pt" space-after.optimum="10pt" padding-top="3pt"></fo:block-->
  	      </fo:static-content>

           <!--footer -->
                <fo:static-content flow-name="xsl-region-after">
                    <fo:table table-layout="fixed">
                        <fo:table-column column-width="19cm"/>
                        <fo:table-column column-width="0.3cm"/>
                        <fo:table-body>
                            <fo:table-row border-left-color="green" border-left-width="0.5pt" border-left-style="solid">
                              <!-- da fare un'altra cella dove ci metto la numerazione -->
                                <fo:table-cell>
                                    <fo:block>
                                    <fo:leader leader-pattern="rule" rule-thickness="0.01cm" leader-length="19cm"/>
                                    </fo:block>
                                    <fo:block font-size="6pt" font-family="sans-serif" text-align="left">
                                         <xsl:value-of select="/NCPReport/Country/name"/>Nutrition Profile&#160;-&#160;Food and Nutrition Division,FAO,2005
                                     </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>
                                    <fo:leader leader-pattern="rule" rule-thickness="0.01cm" leader-length="0.3cm"/>
                                    </fo:block>
                                    <fo:block font-size="6pt" font-family="sans-serif" text-align="right">
                                        <fo:page-number/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                </fo:static-content >



            <!-- body index list of tables and figures -->

            <fo:flow flow-name="xsl-region-body">
              <fo:table table-layout="fixed">
                <fo:table-column column-width="19cm"/>
                <fo:table-column column-width="0.3cm"/>
                <!--fo:table-column column-width="19cm"/-->
                <fo:table-body>
                  <fo:table-row height="0.6cm">
                    <fo:table-cell border-left-style="solid" border-top-style="solid" border-bottom-style="solid" border-width="0.5pt" display-align="center">
                        <fo:block text-align="center" font-size="10pt" font-weight="bold">
                            List of tables and figures
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell border-right-style="solid" border-top-style="solid" border-bottom-style="solid" border-width="0.5pt" display-align="center">
                        <fo:block>
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.6cm">
                    <fo:table-cell >
                        <fo:block >
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            List of tables
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block>
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 1: Population indicators.................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             9
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 2: Land use and irrigation..............................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             10
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 3: Livestock and fishery statistics..................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             11
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 4: Basic economic indicators.........................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             11
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 5: Health indicators.......................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             12
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 6: Access to safe water and sanitation.........................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             13
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 7: Access to Health Services........................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             13
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 8: Education..................................................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             13
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 9: Human development and poverty.............................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             14
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 10: Other social indicators.............................................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             14
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 11: Trends in per capita supply of major food groups(in g/day).....................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             16
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 12: Share of the main food groups in the Dietary Energy Supply(DES), trends............................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             19
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 13: Initiation and duration of breastfeeding...................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             21
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 14: Type of infant and young child feeding...................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             22
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 15: Consumption of complementary foods, and meal frequency by breastfeeding status and age.............................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             22
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 16: Anthropometry of preschool children......................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             24
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 17: Anthropometry of adult women...............................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             28
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 18: Prevalence of goitre and level of urinary iodine in school-age children..................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             30
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 19: Iodization of salt at household level........................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             31
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 20: Prevalence of sub-clinical vitamin A deficiency in children under 6 years..............................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             31
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 21: Prevalence of clinical and sub-clinical vitamin A deficiency in mothers during their last pregnancy and during lactation......................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             32
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 22: Vitamin A supplementation of children and mothers..............................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             33
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 23: Prevalence of anemia in preschool children ..........................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             34
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 24: Prevalence of anemia in women of childbearing age ............................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             34
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 25: Prevalence of anemia in adult men .......................................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             34
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            Table 26: Iron supplementation: Percentage of mothers who took iron tablets/syrups during pregnancy ............................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             35
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.6cm">
                    <fo:table-cell >
                        <fo:block >
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt" font-weight="bold">
                            List of figures
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block>
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                          <!-- sistemare il simbolo dovrebbe essere un quadrato capire come farlo apparire-->
                           &#x2022;&#160;&#160;&#160;&#160;&#160;Figure 1: Dietary energy supply(DES),trends and distribution by macronutrient ............................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             17
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                   <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            &#x2022;&#160;&#160;&#160;&#160;&#160;Figure 2: Vegetable/animal origin of energy, protein and lipid supplies ..........................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             18
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            &#x2022;&#160;&#160;&#160;&#160;&#160;Figure 3: Dietary energy supply by food group ...............................................................................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             18
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            &#x2022;&#160;&#160;&#160;&#160;&#160;Figure 4: Major food exports as percentage of Dietary Energy Supply(DES),trends ......................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             19
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>
                  <fo:table-row height="0.4cm">
                    <fo:table-cell >
                        <fo:block text-align="left" font-size="8pt">
                            &#x2022;&#160;&#160;&#160;&#160;&#160;Figure 5: Major food imports as percentage of Dietary Energy Supply(DES),trends ......................................................................................................
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell >
                        <fo:block text-align="right" font-size="8pt">
                             20
                        </fo:block>
                    </fo:table-cell>
                  </fo:table-row>

                </fo:table-body>
              </fo:table>
            </fo:flow>
          </fo:page-sequence>





          <!-- pages -->
          <fo:page-sequence master-reference="simple">

           <!-- header -->

              <fo:static-content flow-name="xsl-region-before">
                    <!--fo:block  line-height="14pt" space-after.optimum="10pt" padding-top="3pt"></fo:block-->
  	      </fo:static-content>

           <!--footer -->
                <fo:static-content flow-name="xsl-region-after">
                    <fo:table table-layout="fixed">
                        <fo:table-column column-width="19cm"/>
                        <fo:table-column column-width="0.3cm"/>
                        <fo:table-body>
                            <fo:table-row border-left-color="green" border-left-width="0.5pt" border-left-style="solid">
                                <fo:table-cell>
                                    <fo:block>
                                    <fo:leader leader-pattern="rule" rule-thickness="0.01cm" leader-length="19cm"/>
                                    </fo:block>
                                    <fo:block font-size="6pt" font-family="sans-serif" text-align="left">
                                         <xsl:value-of select="/NCPReport/Country/name"/>Nutrition Profile&#160;-&#160;Food and Nutrition Division,FAO,2005
                                     </fo:block>
                                </fo:table-cell>
                                <fo:table-cell>
                                    <fo:block>
                                    <fo:leader leader-pattern="rule" rule-thickness="0.01cm" leader-length="0.3cm"/>
                                    </fo:block>
                                    <fo:block font-size="6pt" font-family="sans-serif" text-align="right">
                                        <fo:page-number/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-body>
                    </fo:table>
                </fo:static-content >

            <!-- BODY -->

          <fo:flow flow-name="xsl-region-body">


  	  <xsl:variable name="reportid" select="/NCPReport/@id" />
	  <xsl:variable name="languageprod" select="/NCPReport/@languageofproduction" />
	  <xsl:variable name="yearprod" select="/NCPReport/@yearofproduction" />
          <xsl:variable name="countryiso3" select="/NCPReport/Country/@iso3" />
  	  <xsl:variable name="faostatcode" select="/NCPReport/Country/@faostatcode" />
 	  <xsl:variable name="countryname">
            <xsl:value-of select="/NCPReport/Country/name" disable-output-escaping="no"/></xsl:variable>
          <xsl:variable name="countrylongname">
            <xsl:value-of select="/NCPReport/Country/longname" disable-output-escaping="no"/></xsl:variable>


	<xsl:comment >reportid =<xsl:value-of select="$reportid"/></xsl:comment>
	<xsl:comment >language prod = <xsl:value-of select="$languageprod"/></xsl:comment>
	<xsl:comment >yearprod = <xsl:value-of select="$yearprod"/></xsl:comment>
	<xsl:comment >countryiso3= <xsl:value-of select="$countryiso3"/></xsl:comment>
	<xsl:comment >countryname= <xsl:value-of select="$countryname"/></xsl:comment>
	<xsl:comment >countrylongname= <xsl:value-of select="$countrylongname"/></xsl:comment>
	<xsl:comment >faostatcode= <xsl:value-of select="$faostatcode"/></xsl:comment>



        <xsl:value-of select="$countrylongname"/>
	<xsl:value-of select="$yearprod"/>
        <xsl:call-template name="Theme"/>

        </fo:flow>

        </fo:page-sequence>
      </fo:root>
  </xsl:template>


        <xsl:template name="Theme" >
          <xsl:for-each select="Theme" >
                <fo:block xsl:use-attribute-sets="chap">
		<xsl:call-template name="readtheme"/>
                </fo:block>
          </xsl:for-each>
        </xsl:template>


        <xsl:template name="readtheme" >

             <xsl:value-of select="./@name" disable-output-escaping="no"/>
             <xsl:for-each select="./Textual" >
               <!--fo:block xsl:use-attribute-sets="ncpTitle"-->
                <!--fo:block text-align="center" font-family="sans-serif" font-size="12pt" font-weight="bold" color="red"-->
		<xsl:call-template name="readtextual"/>
                <!--/fo:block-->
             </xsl:for-each>
        </xsl:template>

        <xsl:template name="readtextual">

        <fo:block>
	<xsl:value-of select="./@name" disable-output-escaping="no"/>
        </fo:block>
              <xsl:for-each select="(*)">
                    <xsl:comment>
                      <xsl:value-of select="name(.)" />
                    </xsl:comment>

                    <xsl:if test="name(.) = 'picture'" >
                            <fo:block>
                            <xsl:call-template name="picturepdf"/>
                            </fo:block>
                    </xsl:if>
                    <!--xsl:if test="name(.) = 'dataset'" >
                            <fo:block>
                            <xsl:call-template name="dataset"/>
                            </fo:block>
                    </xsl:if-->
                    <xsl:if test="name(.) = 'text'" >
                            <fo:block xsl:use-attribute-sets="ncpText">
                            <xsl:call-template name="textpdf"/>
                            </fo:block>
                    </xsl:if>
                    <xsl:if test="name(.) = 'unbound'" >
                            <fo:block xsl:use-attribute-sets="ncpText">
                            <xsl:call-template name="textpdf"/>
                            </fo:block>
                    </xsl:if>
             </xsl:for-each>
        </xsl:template>



</xsl:stylesheet>
