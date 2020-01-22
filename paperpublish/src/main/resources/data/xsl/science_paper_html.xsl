<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:gesmes="http://www.gesmes.org/xml/2002-08-01"
    xmlns:ecb="http://www.ecb.int/vocabulary/2002-08-01/eurofxref"
    xmlns:Papers="http://localhost:8080/SciencePapers" 
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    >
    <xsl:output method="xml" encoding="utf-8" doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"/>
    
    <xsl:template match="Papers:content/Papers:quote">
        <div style="margin-left: 1in; margin-right: 1in; margin-top: 0.15in; margin-bottom: 0.15in; text-align: justify;">
            <p style="font-size: 12pt;">
                <xsl:value-of select="."></xsl:value-of>
            </p>
        </div>
    </xsl:template>
    
    
    <xsl:template match="/">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
                <title>Science papers</title>
            </head>
            <body style="font-size: 14pt;">
                <xsl:for-each select="Papers:SciencePapers/Papers:SciencePaper">
                    <xsl:sort select="@documentId"/>
                    <div>    
                        <div style="display: inline; margin-right: 1em; margin-top: 1em;">
                            <xsl:value-of select="@documentId"/>
                            <div style="font-weight: bold; font-size: 18pt; text-align: center;">
                                <xsl:value-of select="Papers:PaperData/Papers:Title"/>
                            </div>    
                        </div>
                    
                        <div style="text-align: center;">
                            <br/>
                            <div style="margin-bottom: 1em;">
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
                        </div>
                        
                        
                        <div style="line-height:6mm; text-align: justify; margin-left: 2em; margin-right: 2em;">
                            <xsl:for-each select="Papers:Paragraf">
                                <xsl:apply-templates select="Papers:content"/>
                                <br/>
                            </xsl:for-each>
                        </div>
                        <div style="margin-top: 2em; text-align: justify; margin-left: 2em; margin-right: 2em;">
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
                        <div style="margin-top: 2em; text-align: justify; margin-left: 2em; margin-right: 2em;">
                            <div style="font-weight: bold; font-size: 14pt;" >
                                Cited by
                            </div>
                            <div style="margin-bottom: 1em">
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
                    <hr/>
                </xsl:for-each>
                
                
                
                
                
                
                
                
                
                <!--                 
                <table>
                    <tr>
                        <th>Currency</th>
                        <th>Rate</th>
                    </tr>
                    <xsl:for-each select="/gesmes:Envelope/ecb:Cube/ecb:Cube/ecb:Cube">
                        <tr>
                            <td>
                                <b><xsl:value-of select="@currency"/></b>
                            </td>
                            <td>
                                <xsl:value-of select="@rate"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
                 -->
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>