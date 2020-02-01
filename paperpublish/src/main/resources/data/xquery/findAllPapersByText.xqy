xquery version "3.0";
declare namespace Papers = "http://localhost:8080/SciencePapers";
declare variable $textToMatch as xs:string external;
declare variable $authorUserName as xs:string external;
let $nl := "&#10;"

 
    for $doc in fn:collection("/db/apps/paper-publish")
    let $titles := $doc/Papers:SciencePapers/Papers:SciencePaper/Papers:PaperData/Papers:Title/text()
    let $statuses := data($doc/Papers:SciencePapers/Papers:SciencePaper/@status)
    let $documentIDs := data($doc/Papers:SciencePapers/Papers:SciencePaper/@documentId)
    for $title in $titles
    let $status := $statuses[index-of($titles, $title)] 
    let $papers := $doc/Papers:SciencePapers/Papers:SciencePaper
    let $paper := $papers[index-of($titles, $title)]
    let $authorUN := $paper/Papers:PaperData/Papers:Author/Papers:authorUserName/text()
    let $documentID := $documentIDs[index-of($titles, $title)]
    
    where ($status = "accepted" or ($status = "in_procedure" and $authorUN = $authorUserName)) and $paper//*[fn:contains(text(), $textToMatch)] 
     
    return ($documentID, $nl)
    
