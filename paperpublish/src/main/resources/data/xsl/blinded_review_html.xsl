<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:gesmes="http://www.gesmes.org/xml/2002-08-01"
    xmlns:ecb="http://www.ecb.int/vocabulary/2002-08-01/eurofxref"
    xmlns:rev="http://localhost:8080/Reviews" 
    xmlns:blin="http://localhost:8080/BlindedReviews"
    xmlns:Papers="http://localhost:8080/SciencePapers"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    >
    <xsl:output method="xml" encoding="utf-8" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
    
    <xsl:template match="Papers:content/Papers:quote">
        <div style="text-align: justify; width: 50%;">
            <p style="font-size: 12pt; float: left; width: 50%;">
                <xsl:value-of select="."></xsl:value-of>
            </p>
        </div>
    </xsl:template>
    
    <xsl:template match="Papers:Paragraf" name="paragrafTemplate">
        <xsl:param name="currentPaper"></xsl:param>
        <xsl:for-each select="document('../BlindedReview.xml')/blin:BlindedReviews/blin:BlindedReview">
            <xsl:if test="blin:Paper/@paperId = $document_id">
                <xsl:variable name="currentBlindedReview" select="current()"></xsl:variable>
                <xsl:for-each select="$currentPaper/Papers:Paragraf">
                    <xsl:variable name="currentParagrafId" select="@paragrafId"></xsl:variable>
                    <div>
                        <p style="float: left; width: 50%;">
                            <xsl:attribute name="paragrafId">
                                <xsl:value-of select="@paragrafId"/>
                            </xsl:attribute>
                            <xsl:apply-templates select="Papers:content"/>
                        </p>
                        <xsl:for-each select="$currentBlindedReview/blin:Comments/blin:Comment">
                            <xsl:variable name="currentCommentParagrafId" select="@paragrafId"/>
                            <xsl:variable name="currentComment" select="." />
                            
                            <xsl:if test="$currentCommentParagrafId = $currentParagrafId">
                                <p style="float: right; width: 50%; background-color: yellow;">comment: <xsl:value-of select="$currentComment" /></p>
                            </xsl:if>
                        </xsl:for-each>        
                        
                    </div>
                </xsl:for-each> 
                
                
            </xsl:if>
        </xsl:for-each>
        
    </xsl:template>
    
    <xsl:param name="document_id" />
    
    <xsl:template match="/">
        <xsl:for-each select="document('../science_paper.xml')/Papers:SciencePapers/Papers:SciencePaper">
            <xsl:sort select="@documentId"/>
            <xsl:if test="@documentId = $document_id">
                <xsl:variable name="currentPaper" select="current()"></xsl:variable>
                <html xmlns="http://www.w3.org/1999/xhtml">
                    <head>
                        
                    </head>
                    <body>
                        <div style="font-size: 14pt;">    
                            <div style="display: inline; margin-right: 1em; margin-top: 1em; float: left; width: 50%;">
                                <xsl:value-of select="@documentId"/>
                                <div style="font-weight: bold; font-size: 18pt; text-align: center; float: left; width: 50%;">
                                    <xsl:value-of select="Papers:PaperData/Papers:Title"/>
                                </div>    
                            </div>
                            
                            <div style="text-align: center; float: left; width: 50%; margin-bottom: 1em;">
                                <xsl:if test="count(Papers:PaperData/Papers:Author) = 1">
                                    <div> <xsl:value-of select="Papers:PaperData/Papers:Author/Papers:authorUserName"/></div>
                                    <div> <xsl:value-of select="Papers:PaperData/Papers:Author/Papers:authorInstitution"/></div>
                                </xsl:if>
                                <xsl:if test="count(Papers:PaperData/Papers:Author) &gt; 1">
                                    <xsl:for-each select="Papers:PaperData/Papers:Author">
                                        <xsl:value-of select="Papers:authorUserName"></xsl:value-of>
                                        <br/>
                                        <div style="font-style: italic; margin-bottom: 0.5em;">
                                            <xsl:value-of select="Papers:authorInstitution"></xsl:value-of>    
                                        </div>
                                    </xsl:for-each>
                                </xsl:if>
                            </div>
                            
                           
                            <xsl:call-template name="paragrafTemplate">
                                <xsl:with-param name="currentPaper" select="$currentPaper"></xsl:with-param>
                            </xsl:call-template>
                            <div style="margin-top: 2em; text-align: justify; margin-left: 2em; margin-right: 2em; float: left; width: 50%;">
                                <div style="font-weight:bold; font-size: 14pt;">
                                    References
                                </div>
                                <xsl:for-each select="Papers:Citations/Papers:citation/Papers:citer">
                                    <div>
                                        <xsl:for-each select="Papers:authorName">
                                            <xsl:value-of select="."></xsl:value-of>,
                                        </xsl:for-each>
                                        (<xsl:value-of select="Papers:year"></xsl:value-of>)
                                        "<xsl:value-of select="Papers:paperTitle"></xsl:value-of>",
                                        <i>
                                            <xsl:value-of select="Papers:journalData/Papers:journalTitle"></xsl:value-of>
                                        </i>,
                                        <xsl:text> </xsl:text>
                                        <xsl:value-of select="Papers:journalData/Papers:journalInfo"></xsl:value-of>
                                    </div>    
                                </xsl:for-each>
                            </div>
                            <div style="margin-top: 2em; text-align: justify; margin-left: 2em; margin-right: 2em; float: left; width: 50%;">
                                <div style="font-weight: bold; font-size: 14pt;" >
                                    Cited by
                                </div>
                                <div style="margin-bottom: 1em; float: left; width: 50%;">
                                    <xsl:for-each select="Papers:CitedBy/Papers:citer">
                                        <div>
                                            <xsl:for-each select="Papers:authorName">
                                                <xsl:value-of select="."></xsl:value-of>,
                                            </xsl:for-each>
                                            (<xsl:value-of select="Papers:year"></xsl:value-of>)
                                            "<xsl:value-of select="Papers:paperTitle"></xsl:value-of>",
                                            <i><xsl:value-of select="Papers:journalData/Papers:journalTitle"></xsl:value-of></i>
                                            <xsl:text> </xsl:text>
                                            <xsl:value-of select="Papers:journalData/Papers:journalInfo"></xsl:value-of>
                                        </div>
                                    </xsl:for-each>
                                    
                                </div>
                            </div>
                        </div>
                        
                    </body>
                </html>
            </xsl:if>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>