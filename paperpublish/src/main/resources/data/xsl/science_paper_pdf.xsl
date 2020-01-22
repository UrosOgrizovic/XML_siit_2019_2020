<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:Papers="http://localhost:8080/SciencePapers" 
    xmlns:fo="http://www.w3.org/1999/XSL/Format" version="2.0">
    
    
    <xsl:template match="Papers:content/Papers:quote">
        <fo:block margin-left="1in" margin-right="1in" margin-top="0.15in" margin-bottom="0.15in" text-align="justify">
            <fo:inline font-size="10pt">
                <xsl:value-of select="."></xsl:value-of>
            </fo:inline>
        </fo:block>
    </xsl:template>
    
    
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="science_paper-page">
                    <fo:region-body margin="0.75in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="science_paper-page">
                <fo:flow flow-name="xsl-region-body" text-align="justify">
                    <xsl:for-each select="Papers:SciencePapers/Papers:SciencePaper">
                        <xsl:sort select="@documentId"/>
                        <fo:block page-break-after="always">
                            <!-- one way of displaying two fo:blocks on one row -->
                            <fo:list-block space-after="1em">
                                <fo:list-item>
                                    <fo:list-item-label>
                                        <fo:block margin-right="10mm" text-align="left">
                                            <xsl:value-of select="@documentId"/>    
                                        </fo:block>
                                    </fo:list-item-label>
                                    <fo:list-item-body>
                                        <fo:block font-weight="bold" text-align="center" font-size="18pt">
                                            <xsl:value-of select="Papers:PaperData/Papers:Title"/>    
                                        </fo:block>    
                                    </fo:list-item-body>
                                </fo:list-item>
                            </fo:list-block>    
                            
                            <fo:block text-align="center" space-after="1em">
                                <xsl:if test="count(Papers:PaperData/Papers:Author) = 1">
                                    <fo:block> <xsl:value-of select="Papers:PaperData/Papers:Author/Papers:authorUserName"/></fo:block>
                                    <fo:block> <xsl:value-of select="Papers:PaperData/Papers:Author/Papers:authorInstitution"/></fo:block>
                                </xsl:if>
                                <xsl:if test="count(Papers:PaperData/Papers:Author) &gt; 1">
                                    <xsl:for-each select="Papers:PaperData/Papers:Author">
                                        <xsl:value-of select="Papers:authorUserName"></xsl:value-of>
                                        <fo:block/>
                                        <fo:block font-style="italic" space-after="0.5em">
                                            <xsl:value-of select="Papers:authorInstitution"></xsl:value-of>    
                                        </fo:block>
                                    </xsl:for-each>
                                </xsl:if>
                            </fo:block>
                            
                            
                            <fo:block line-height="6mm">
                            <xsl:for-each select="Papers:Paragraf">
                                <xsl:apply-templates select="Papers:content"/>
                                <fo:block/> 
                            </xsl:for-each>
                            </fo:block>
                            <fo:block space-before="2em">
                                <fo:block font-weight="bold" font-size="14">
                                    References
                                </fo:block>
                                <xsl:for-each select="Papers:Citations/Papers:citation/Papers:citer">
                                    <fo:block>
                                        <xsl:for-each select="Papers:authorName">
                                            <xsl:value-of select="."></xsl:value-of>,
                                        </xsl:for-each>
                                        (<xsl:value-of select="Papers:year"></xsl:value-of>)
                                        "<xsl:value-of select="Papers:paperTitle"></xsl:value-of>",
                                        <fo:inline font-style="italic">
                                            <xsl:value-of select="Papers:journalData/Papers:journalTitle"></xsl:value-of>
                                        </fo:inline>
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of select="Papers:journalData/Papers:journalInfo"></xsl:value-of>
                                    </fo:block>    
                                </xsl:for-each>
                            </fo:block>
                            <fo:block space-before="2em">
                                <fo:block font-weight="bold" font-size="14">
                                    Cited by
                                </fo:block>
                                <fo:block>
                                    <xsl:for-each select="Papers:CitedBy/Papers:citer">
                                        <fo:block>
                                            <xsl:for-each select="Papers:authorName">
                                                <xsl:value-of select="."></xsl:value-of>,
                                            </xsl:for-each>
                                            (<xsl:value-of select="Papers:year"></xsl:value-of>)
                                            "<xsl:value-of select="Papers:paperTitle"></xsl:value-of>",
                                            <fo:inline font-style="italic">
                                                <xsl:value-of select="Papers:journalData/Papers:journalTitle"></xsl:value-of>
                                            </fo:inline>
                                            <xsl:text> </xsl:text>
                                            <xsl:value-of select="Papers:journalData/Papers:journalInfo"></xsl:value-of>
                                        </fo:block>
                                    </xsl:for-each>
                                </fo:block>
                            </fo:block>
                        </fo:block>
                    </xsl:for-each>
                    
                    
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
