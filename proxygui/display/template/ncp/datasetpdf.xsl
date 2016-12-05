<!--?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	version="1.0">

<xsl:template name="datasetpdf"-->

  <!-- secondo me non ci va e nemmeno lo stylesheet perché se sono incluse nel report giá ce
    l'hanno -->
  <!--fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"-->

     <!--xsl:variable name="countryname" select="../xml/datasets/country/@name" />
     <xsl:variable name="datasetstyle" select="./@style" />
     <xsl:variable name="datasetcomment" select="./comment" />
     <xsl:variable name="datasetnote" select="./note" /-->


<!-- da verificare se se la la parte before,after,body va solo nel reportpdf
     da verificare fo:flow prima della table
     colspan e style(li ho sostituiti con xsl:use-attribute-sets) come si inseriscono col fop
     verificare i fo:block quando visualizzeró il pdf...se ne ho messi troppi o pochi...in base
     a come deve essere paragrafato il testo
     -->

	<!--fo:table table-layout="fixed" xsl:use-attribute-sets='{$datasetstyle}'-->
          <!-- manca il width??? delle colonne é obbligatorio.... -->
               <!--fo:table-column />
               <fo:table-column />
               <fo:table-column />

          <fo:table-body>
		<fo:table-row >
			<fo:table-cell xsl:use-attribute-sets='{$datasetstyle}commentheader'>
                             <fo:block>
				Comments
                             </fo:block>
			</fo:table-cell>
			<fo:table-cell colspan="2" xsl:use-attribute-sets='{$datasetstyle}comment'>
                             <fo:block>
				<xsl:value-of select="$datasetcomment"/>
                             </fo:block>
			</fo:table-cell>
		</fo:table-row>
		<fo:table-row >
			<fo:table-cell colspan="3" xsl:use-attribute-sets='{$datasetstyle}datatable'>
                              <fo:block>
				<xsl:call-template name="xml_datasets">
				<xsl:with-param name="style" select="$datasetstyle" >
                                </xsl:with-param>
				</xsl:call-template>
                              </fo:block>

			</fo:table-cell>
		</fo:table-row>
		<fo:table-row >
			<fo:table-cell xsl:use-attribute-sets='{$datasetstyle}noteheader'>
                          <fo:block>
				Note
                          </fo:block>
			</fo:table-cell>
			<fo:table-cell colspan="2" xsl:use-attribute-sets='{$datasetstyle}noter'>
                          <fo:block>
				<xsl:value-of select="$datasetnote"/>
                          </fo:block>
			</fo:table-cell>
		</fo:table-row>

	 </fo:table-body>

	</fo:table-->
        <!--/fo:root-->
  <!--/xsl:template>


  <xsl:template match="xml_datasets" >

  <fo:block>
  <xsl:param name="style" />
  </fo:block>
         <fo:block>
	  <xsl:for-each select="./xml"  >
               <fo:block>
                <xsl:variable name="countryname" select="current()/datasets/country/@name" />
               </fo:block>
                    <fo:table xsl:use-attribute-sets="{$style}"-->

                         <!-- manca il width??? delle colonne é obbligatorio.... -->
                       <!--fo:table-column />

                       <fo:table-body>
                       <fo:table-row >
                          <fo:table-cell xsl:use-attribute-sets="maincountryname">
                            <fo:block>
                            <xsl:value-of select="$countryname" disable-output-escaping="yes"/>
                            </fo:block>
                          </fo:table-cell>
                       </fo:table-row>
                       <fo:table-row >
                          <fo:table-cell xsl:use-attribute-sets="maintabletitle">
                            <fo:block>
                            <xsl:value-of select="current()/datasets/layer/dataset/@name" disable-output-escaping="yes"/>
                            </fo:block>
                          </fo:table-cell>
                       </fo:table-row>
                       <fo:table-row >
                          <fo:table-cell>
                            <fo:block>
                             <xsl:for-each select="current()/datasets">
                                <xsl:apply-templates select="./layer">
                                    <xsl:with-param name="style" select="$style" >
                                    </xsl:with-param>
                                </xsl:apply-templates>
                             </xsl:for-each>
                            </fo:block>
                          </fo:table-cell>
                       </fo:table-row>
                       </fo:table-body>
                  </fo:table>
	  </xsl:for-each>
          </fo:block>
  </xsl:template-->

	<!-- Template for Country -->
  <!--xsl:template match="country">
  </xsl:template-->


<!-- Template for the Dataset -->

  <!--xsl:template match="layer">
  <fo:block>
  <xsl:param name="style" >
  </xsl:param>
  </fo:block>

        <fo:block>
	<xsl:for-each select="current()/dataset">
		<xsl:variable name="current_themeid" select="@themeid" />
		<xsl:variable name="already_printed_themeid" select="(count(preceding::dataset[@themeid=$current_themeid]) &gt; 0)" />

		<xsl:choose>
			<xsl:when test="not($already_printed_themeid)" >

			</xsl:when>
			<xsl:otherwise>
			</xsl:otherwise>
		</xsl:choose>

		<xsl:apply-templates select="region">
			<xsl:with-param name="style" select="$style" ></xsl:with-param>
		</xsl:apply-templates>

	</xsl:for-each>
        </fo:block>
  </xsl:template-->


<!-- Template for the Region -->

  <!--xsl:template match="region">

  <xsl:param name="style" />
  <xsl:variable name="columnscount" select="5" /-->
<!-- loop for each extent containing the source name -->
  <!--xsl:variable name="current-themeid" select="ancestor::Theme/@id" />
        <fo:block>
	<xsl:for-each select="current()/extent[@heading='Source Name']"-->
	<!-- printing new table header when the source changes -->
		<!--xsl:variable name="current_source" select="@name" />
		<xsl:variable name="already_printed_source" select="count(preceding::region[(ancestor::Theme/@id = $current-themeid) and (extent[(@heading='Source Name') and (@name=$current_source)])]) &gt; 0" />
		<xsl:choose>
			<xsl:when test="not($already_printed_source)" >
                        <fo:block-->
				<!-- print the source name -->
				<!--fo:table  xsl:use-attribute-sets="{$style}"-->
                                       <!-- manca il width??? delle colonne é obbligatorio.... -->
                                       <!--fo:table-column />
                                       <fo:table-body>
					  <fo:table-row >
						<fo:table-cell>
							<fo:block>
							<hr noshade="noshade"/>
							</fo:block>
						</fo:table-cell>
                                          </fo:table-row>
					  <fo:table-row xsl:use-attribute-sets="{$style}">
						<fo:table-cell>
                                                        <fo:block>
							Source Name: <xsl:value-of select="@name" />
                                                        </fo:block>
						</fo:table-cell>
					  </fo:table-row>
                                       </fo:table-body>
				</fo:table-->

				<!-- print the table headers -->
                                <!-- qui mancavano righe e celle da verificare come dovrebbe essere visualizzato e poi inserirle-->
				<!--fo:table xsl:use-attribute-sets="{$style}"-->

                                     <!-- manca il width??? delle colonne é obbligatorio.... -->
                                        <!--fo:table-column />
                                        <fo:table-column />
                                        <fo:table-column />

                                        <fo:table-body>
                                        <fo:table-row >
                                          <fo:table-cell>
                                            <fo:block>
                                             <xsl:apply-templates select=".">
						 <xsl:with-param name="print_headers_only">true</xsl:with-param>
                                             </xsl:apply-templates>
                                            </fo:block>
                                          </fo:table-cell-->
                                          <!-- printing the table rows  of the current group of descendant extents (the first one with the current source) -->
                                          <!--fo:table-cell>
                                            <fo:block>
                                              <xsl:apply-templates select=".">
                                                <xsl:with-param name="print_headers_only">false</xsl:with-param>
					      </xsl:apply-templates>
                                            </fo:block>
                                          </fo:table-cell-->
					  <!-- printing the table rows  of the current group of descendant extents (the first one with the current source) -->
					  <!--xsl:for-each select="../following::extent[(ancestor::Theme/@id = $current-themeid) and (@heading='Source Name') and (@name=$current_source)]">
                                            <fo:table-cell>
                                             <fo:block>
						<xsl:apply-templates select=".">
                                                     <xsl:with-param name="print_headers_only">false</xsl:with-param>
						</xsl:apply-templates>
                                             </fo:block>
                                           </fo:table-cell>
					  </xsl:for-each>
                                        </fo:table-row>
                                        </fo:table-body>
				</fo:table>
                          </fo:block>
			</xsl:when>
			<xsl:otherwise>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:for-each>
        </fo:block>

  </xsl:template-->

<!-- Template for the Extent with heading = Source -->


  <!--xsl:template match="extent[@heading='Source Name']">
	<xsl:param name="print_headers_only" />
	<xsl:choose>
		<xsl:when test="$print_headers_only='true'" >
                <fo:block>
                        <fo:table-->
                        <!-- manca il width??? delle colonne é obbligatorio.... -->
                        <!--fo:table-column />
                        <fo:table-column />
                        <fo:table-column />
                        <fo:table-column />

                        <fo:table-body>
			<fo:table-row  xsl:use-attribute-sets="trdatasetheaders">
				<fo:table-cell xsl:use-attribute-sets="tddatasetheader">
                                  <fo:block>
                                      Region
                                  </fo:block>
                                </fo:table-cell>
				<fo:table-cell xsl:use-attribute-sets="tddatasetheader">
                                  <fo:block>
                                      Layer
                                  </fo:block>
                                </fo:table-cell>
				<xsl:for-each select="descendant::extent" >
					<xsl:choose>
						<xsl:when test="@heading = 'Indicator'" >
							<fo:table-cell xsl:use-attribute-sets="tddatasetheader">
                                                            <fo:block>
								<xsl:value-of disable-output-escaping="no" select="@name" />
                                                            </fo:block>
							</fo:table-cell>
						</xsl:when>
						<xsl:otherwise>
							<fo:table-cell xsl:use-attribute-sets="tddatasetheader">
                                                            <fo:block>
								<xsl:value-of disable-output-escaping="no" select="@heading" />
                                                            </fo:block>
							</fo:table-cell>
							<xsl:if test="extent/year/@sample_size">
								<fo:table-cell xsl:use-attribute-sets="tddatasetheader">
                                                                  <fo:block>
                                                                    Sample Size
                                                                  </fo:block>
                                                                </fo:table-cell>
							</xsl:if>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>
			</fo:table-row>
                        </fo:table-body>
                        </fo:table>
                      </fo:block>
		</xsl:when>
		<xsl:otherwise-->

<!-- print a new table row for each extent having the "indicator"  extents as children (usually axis=6) -->
                <!--fo:block>
                        <fo:table-->
                         <!-- manca il width??? delle colonne é obbligatorio.... -->
                         <!--fo:table-column />
                         <fo:table-column />
                         <fo:table-column />
                         <fo:table-column />
                         <fo:table-column />
                         <fo:table-column />

                         <fo:table-body>
			 <xsl:for-each select="descendant::extent[extent[@heading = 'Indicator']]" >
				<fo:table-row xsl:use-attribute-sets="trdatasetdata">
					<fo:table-cell xsl:use-attribute-sets="tddatasetdata">
                                               <fo:block>
						  <xsl:value-of disable-output-escaping="yes" select="ancestor::region/@name" />
                                               </fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tddatasetdata">
                                               <fo:block>
						<xsl:value-of disable-output-escaping="yes" select="ancestor::region/@layername" />
                                               </fo:block>
					</fo:table-cell-->
                                        <!-- print the axis columns before the indicators values -->
					<!--xsl:variable name='current_axis' select="number(@axis)" />
					<xsl:for-each select="ancestor::extent[(@heading != 'Source Name') and (number(@axis) &lt; $current_axis)]" >
						<fo:table-cell xsl:use-attribute-sets="tddatasetdata">
                                                   <fo:block>
							<xsl:value-of disable-output-escaping="yes" select="@name" />
                                                   </fo:block>
						</fo:table-cell>
					</xsl:for-each>
					<fo:table-cell xsl:use-attribute-sets="tddatasetdata">
                                                <fo:block>
						    <xsl:value-of disable-output-escaping="yes" select="@name" />
                                                </fo:block>
					</fo:table-cell>
					<fo:table-cell xsl:use-attribute-sets="tddatasetdata">
                                               <fo:block>
						    <xsl:value-of disable-output-escaping="yes" select="extent/year/@sample_size" />
                                               </fo:block>
					</fo:table-cell-->
                                        <!-- print the axis columns of the indicators values -->
					<!--xsl:for-each select="extent[@heading = 'Indicator']" >
						<fo:table-cell xsl:use-attribute-sets="tddatasetdata">
                                                      <fo:block>
							  <xsl:value-of disable-output-escaping="yes" select="year/@value" />
                                                      </fo:block>
						</fo:table-cell>
					</xsl:for-each>
				</fo:table-row>
			 </xsl:for-each>
                        </fo:table-body>
                      </fo:table>
                  </fo:block>
		 </xsl:otherwise>
	</xsl:choose>


  </xsl:template>

</xsl:stylesheet-->
