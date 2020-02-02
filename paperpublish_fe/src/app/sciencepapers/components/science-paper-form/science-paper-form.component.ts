import { Component, OnInit } from '@angular/core';
import { SciencePapersService } from '../../services/sciencepapers.service';
import { CoverLettersService } from '../../services/coverletters.service';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

declare global {
  interface Window { Xonomy: any; }
}
let Xonomy = window.Xonomy || {};

function addNewParagraph(htmlID, param) {
  let xmlString = Xonomy.harvest();
  let maxParagrafId = -1
  let re = /\paragrafId=(\"|\')[0-9]+(\"|\')/g;
  let listOfMatches = xmlString.match(re) || [];
  listOfMatches.forEach((labelAndValue) => {
    let id = parseInt(labelAndValue.match(new RegExp("[0-9]+"))[0]);
    if (maxParagrafId < id) {
      maxParagrafId = id;
    }
  });

  Xonomy.newElementChild(htmlID, `<Papers:Paragraf paragrafId='${maxParagrafId + 1}' xmlns:Papers='http://localhost:8080/SciencePapers'><Papers:content>Placeholder text</Papers:content></Papers:Paragraf>`)
}

const CREATE_SCIENCE_PAPER_SPECIFICATION = {
  elements: {
    "Papers:SciencePaper": {
      menu: [
        {
          caption: "Add paper data",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:PaperData xmlns:Papers='http://localhost:8080/SciencePapers'></Papers:PaperData>",
          hideIf: function(jsElement) {
            return jsElement.hasChildElement("Papers:PaperData");
          }
        },
        {
          caption: "Add a paragraph",
          action: addNewParagraph,
          actionParameter: "<Papers:Paragraf xmlns:Papers='http://localhost:8080/SciencePapers'><Papers:content>Placeholder text</Papers:content></Papers:Paragraf>"
        },
        {
          caption: "Add a citation",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:Citations xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:Citations>"
        },
        {
          caption: "Add cited by segment",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:CitedBy xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:CitedBy>"
        }
      ]
    },
    "Papers:PaperData": {
      menu:[
        {
          caption: "Add title",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:Title xmlns:Papers='http://localhost:8080/SciencePapers' property='pred:title'>Placeholder text</Papers:Title>",
          hideIf: function(jsElement) {
            return jsElement.hasChildElement("Papers:Title");
          }          
        },
        {
          caption: "Add @numberOfReferences='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "numberOfReferences", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("numberOfReferences");
          }
        },
        {
          caption: "Add @contact='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "contact", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("contact");
          }
        },
        {
          caption: "Add @numberOfDownloads='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "numberOfDownloads", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("numberOfDownloads");
          }
        },
        {
          caption: "Add short title",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:ShortTitle xmlns:Papers='http://localhost:8080/SciencePapers' property='pred:shortTitle'>Placeholder text</Papers:ShortTitle>",
          hideIf: function(jsElement) {
            return jsElement.hasChildElement("Papers:ShortTitle");
          }
        },
        {
          caption: "Add a publication date",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:PublicationDate xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:PublicationDate>",
        },
        {
          caption: "Add a citation",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:Citation xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:Citation>",
        },
        {
          caption: "Add a document link",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:DocumentLink xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:DocumentLink>"
        },
        {
          caption: "Add author",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:Author xmlns:Papers='http://localhost:8080/SciencePapers'><Papers:authorUserName property='pred:authorUserName'>Placeholder text</Papers:authorUserName></Papers:Author>"
        },
        {
          caption: "Add download info",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:DownloadInformation xmlns:Papers='http://localhost:8080/SciencePapers'></Papers:DownloadInformation>"
        }
      ],
      attributes: {
        "numberOfReferences": {
          asker: Xonomy.askString,
          menu: [
            {
              caption: "Delete this attribute",
              action: Xonomy.deleteAttribute
            }
          ]
        },
        "contact": {
          asker: Xonomy.askString,
          menu: [
            {
              caption: "Delete this attribute",
              action: Xonomy.deleteAttribute
            }
          ]
        },
        "numberOfDownloads": {
          asker: Xonomy.askString,
          menu: [
            {
              caption: "Delete this attribute",
              action: Xonomy.deleteAttribute
            }
          ]
        }
      }
    },
    "Papers:Title": {
      menu:[
        {
          caption: "Add @documentTitle='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "documentTitle", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("documentTitle");
          }
        },
        {
          caption: "Delete this element",
          action: Xonomy.deleteElement
        }
      ],
      attributes: {
        "documentTitle": {
          asker: Xonomy.askString,
          menu: [{
            caption: "Delete this attribute",
            action: Xonomy.deleteAttribute
          }]
        }
      }
    },
    "Papers:DownloadInformation": {
      menu: [
        {
          caption: "Add a recommended paper",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:RecommendedPaper xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:RecommendedPaper>"
        }
      ]
    },
    "Papers:content": {
      menu: [
        {
          caption: "Add quote",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:quote xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:quote>"
        }
      ]
    },
    "Papers:quote": {
      menu: [
        {
          caption: "Add @citation='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "citation", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("citation");
          }
        },
        {
          caption: "Delete this element",
          action: Xonomy.deleteElement
        }
      ],
      attributes: {
        "citation": {
          asker: Xonomy.askString,
          menu: [{
            caption: "Delete this attribute",
            action: Xonomy.deleteAttribute
          }]
        }
      },
      "Papers:Citations": {
        menu: [
          {
            caption: "Add a citation",
            action: Xonomy.newElementChild,
            actionParameter: "<Papers:citation xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:citation>"
          }
        ]
      },
      "Papers:citation": {
        menu: [
          {
            caption: "Add @cID='something'",
            action: Xonomy.newAttribute,
            actionParameter: {name: "cID", value: "something"},
            hideIf: function(jsElement){
              return jsElement.hasAttribute("cID");
            }
          },
          {
            caption: "Delete this element",
            action: Xonomy.deleteElement
          }
        ],
        attributes: {
          "cID": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      }
    }
  }
}

const UPDATE_SCIENCE_PAPER_SPECIFICATION = {
  elements: {
    "SciencePaper": {
      menu: [
        {
          caption: "Add paper data",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:PaperData xmlns:Papers='http://localhost:8080/SciencePapers'></Papers:PaperData>",
          hideIf: function(jsElement) {
            return jsElement.hasChildElement("Papers:PaperData");
          }
        },
        {
          caption: "Add a paragraph",
          action: addNewParagraph,
          actionParameter: "<Papers:Paragraf xmlns:Papers='http://localhost:8080/SciencePapers'><Papers:content>Placeholder text</Papers:content></Papers:Paragraf>"
        },
        {
          caption: "Add a citation",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:Citations xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:Citations>"
        },
        {
          caption: "Add cited by segment",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:CitedBy xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:CitedBy>"
        }
      ]
    },
    "PaperData": {
      menu:[
        {
          caption: "Add title",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:Title xmlns:Papers='http://localhost:8080/SciencePapers' property='pred:title'>Placeholder text</Papers:Title>",
          hideIf: function(jsElement) {
            return jsElement.hasChildElement("Papers:Title");
          }          
        },
        {
          caption: "Add @numberOfReferences='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "numberOfReferences", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("numberOfReferences");
          }
        },
        {
          caption: "Add @contact='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "contact", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("contact");
          }
        },
        {
          caption: "Add @numberOfDownloads='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "numberOfDownloads", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("numberOfDownloads");
          }
        },
        {
          caption: "Add short title",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:ShortTitle xmlns:Papers='http://localhost:8080/SciencePapers' property='pred:shortTitle'>Placeholder text</Papers:ShortTitle>",
          hideIf: function(jsElement) {
            return jsElement.hasChildElement("Papers:ShortTitle");
          }
        },
        {
          caption: "Add a publication date",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:PublicationDate xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:PublicationDate>",
        },
        {
          caption: "Add a citation",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:Citation xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:Citation>",
        },
        {
          caption: "Add a document link",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:DocumentLink xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:DocumentLink>"
        },
        {
          caption: "Add author",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:Author xmlns:Papers='http://localhost:8080/SciencePapers'><Papers:authorUserName property='pred:authorUserName'>Placeholder text</Papers:authorUserName></Papers:Author>"
        },
        {
          caption: "Add download info",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:DownloadInformation xmlns:Papers='http://localhost:8080/SciencePapers'></Papers:DownloadInformation>"
        }
      ],
      attributes: {
        "numberOfReferences": {
          asker: Xonomy.askString,
          menu: [
            {
              caption: "Delete this attribute",
              action: Xonomy.deleteAttribute
            }
          ]
        },
        "contact": {
          asker: Xonomy.askString,
          menu: [
            {
              caption: "Delete this attribute",
              action: Xonomy.deleteAttribute
            }
          ]
        },
        "numberOfDownloads": {
          asker: Xonomy.askString,
          menu: [
            {
              caption: "Delete this attribute",
              action: Xonomy.deleteAttribute
            }
          ]
        }
      }
    },
    "Title": {
      menu:[
        {
          caption: "Add @documentTitle='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "documentTitle", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("documentTitle");
          }
        },
        {
          caption: "Delete this element",
          action: Xonomy.deleteElement
        }
      ],
      attributes: {
        "documentTitle": {
          asker: Xonomy.askString,
          menu: [{
            caption: "Delete this attribute",
            action: Xonomy.deleteAttribute
          }]
        }
      }
    },
    "DownloadInformation": {
      menu: [
        {
          caption: "Add a recommended paper",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:RecommendedPaper xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:RecommendedPaper>"
        }
      ]
    },
    "content": {
      menu: [
        {
          caption: "Add quote",
          action: Xonomy.newElementChild,
          actionParameter: "<Papers:quote xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:quote>"
        }
      ]
    },
    "quote": {
      menu: [
        {
          caption: "Add @citation='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "citation", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("citation");
          }
        },
        {
          caption: "Delete this element",
          action: Xonomy.deleteElement
        }
      ],
      attributes: {
        "citation": {
          asker: Xonomy.askString,
          menu: [{
            caption: "Delete this attribute",
            action: Xonomy.deleteAttribute
          }]
        }
      },
      "Citations": {
        menu: [
          {
            caption: "Add a citation",
            action: Xonomy.newElementChild,
            actionParameter: "<Papers:citation xmlns:Papers='http://localhost:8080/SciencePapers'>Placeholder text</Papers:citation>"
          }
        ]
      },
      "citation": {
        menu: [
          {
            caption: "Add @cID='something'",
            action: Xonomy.newAttribute,
            actionParameter: {name: "cID", value: "something"},
            hideIf: function(jsElement){
              return jsElement.hasAttribute("cID");
            }
          },
          {
            caption: "Delete this element",
            action: Xonomy.deleteElement
          }
        ],
        attributes: {
          "cID": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      }
    }
  }
}

const COVER_LETTER_SPECIFICATION = {
  elements: {
    "let:CoverLetter": {
      menu: [
        {
          caption: "Add content",
          action: Xonomy.newElementChild,
          actionParameter: "<let:Content xmlns:let='http://localhost:8080/Letter'>Placeholder text</let:Content>",
          hideIf: function(jsElement) {
            return jsElement.hasChildElement("let:Content");
          }
        },
        {
          caption: "Add author",
          action: Xonomy.newElementChild,
          actionParameter: "<let:Author xmlns:let='http://localhost:8080/Letter'></let:Author>",
        },
        {
          caption: "Add @journalName='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "journalName", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("journalName");
          }
        },
        {
          caption: "Add @journalAddress='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "journalAddress", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("journalAddress");
          }
        },
        {
          caption: "Add @manuscriptTitle='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "manuscriptTitle", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("manuscriptTitle");
          }
        },
        {
          caption: "Add @articleType='research'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "articleType", value: "research"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("articleType");
          }
        },
        {
          caption: "Add @submissionDate='something'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "submissionDate", value: "something"},
          hideIf: function(jsElement){
            return jsElement.hasAttribute("submissionDate");
          }
        },
      ],
      attributes: {
        "journalName": {
          asker: Xonomy.askString,
          menu: [{
            caption: "Delete this attribute",
            action: Xonomy.deleteAttribute
          }]
        },
        "journalAddress": {
          asker: Xonomy.askString,
          menu: [{
            caption: "Delete this attribute",
            action: Xonomy.deleteAttribute
          }]
        },
        "manuscriptTitle": {
          asker: Xonomy.askString,
          menu: [{
            caption: "Delete this attribute",
            action: Xonomy.deleteAttribute
          }]
        },
        "articleType": {
          asker: Xonomy.askPickList,
          askerParameter: [
            {value: "review", caption: "Review"},
            {value: "research", caption: "Research"},
            {value: "caseStudy", caption: "Case Study"}
          ],
          menu: [{
            caption: "Delete this attribute",
            action: Xonomy.deleteAttribute
          }]
        },
        "submissionName": {
          asker: Xonomy.askString,
          menu: [{
            caption: "Delete this attribute",
            action: Xonomy.deleteAttribute
          }]
        }
      }
    },
    "let:Author": {
      menu: [
        {
          caption: "Add full name",
          action: Xonomy.newElementChild,
          actionParameter: "<let:FullName xmlns:let='http://localhost:8080/Letter'>Placeholder text</let:FullName>",
          hideIf: function(jsElement) {
            return jsElement.hasChildElement("let:FullName");
          }
        },
        {
          caption: "Add institution",
          action: Xonomy.newElementChild,
          actionParameter: "<let:Institution xmlns:let='http://localhost:8080/Letter'>Placeholder text</let:Institution>",
          hideIf: function(jsElement) {
            return jsElement.hasChildElement("let:Institution");
          }
        },
        {
          caption: "Add email",
          action: Xonomy.newElementChild,
          actionParameter: "<let:EMail xmlns:let='http://localhost:8080/Letter'>Placeholder text</let:EMail>",
          hideIf: function(jsElement) {
            return jsElement.hasChildElement("let:EMail");
          }
        }
      ]
    }
  }
}

@Component({
  selector: 'app-science-paper-form',
  templateUrl: './science-paper-form.component.html',
  styleUrls: ['./science-paper-form.component.css']
})
export class SciencePaperFormComponent implements OnInit {

  xmlSciencePaperContent: string = "<Papers:SciencePaper status='in_procedure' versionId='1' documentId='' xmlns:Papers='http://localhost:8080/SciencePapers'></Papers:SciencePaper>";
  xmlCoverLetterContent = "<let:CoverLetter xmlns:let='http://localhost:8080/Letter'></let:CoverLetter>";
  // xmlSciencePaperContent = "<Papers:SciencePaper versionId='1' documentId='' xmlns:Papers='http://localhost:8080/SciencePapers'><Papers:PaperData numberOfReferences='3' contact='sava@gmail.com' numberOfDownloads='3'><Papers:ShortTitle property='pred:shortTitle' xml:space='preserve'>NEKI TAJTL dobar</Papers:ShortTitle><Papers:Author><Papers:authorUserName property='pred:authorUserName' xml:space='preserve'>user</Papers:authorUserName></Papers:Author><Papers:DocumentLink xml:space='preserve'>neki link</Papers:DocumentLink><Papers:Title property='pred:title' documentTitle='something' xml:space='preserve'>neki tajtl bas dobar</Papers:Title></Papers:PaperData><Papers:Paragraf><Papers:content xml:space='preserve'>PARAGRAAAAAAAF</Papers:content></Papers:Paragraf></Papers:SciencePaper>";
  // xmlCoverLetterContent = "<let:CoverLetter journalName='something' journalAddress='something' manuscriptTitle='something' articleType='research' xmlns:let='http://localhost:8080/Letter'><let:Author><let:FullName xml:space='preserve'>Placeholder text</let:FullName><let:Institution xml:space='preserve'>Placeholder text</let:Institution><let:EMail xml:space='preserve'>Placeholder text</let:EMail></let:Author><let:Content xml:space='preserve'>Placeholder text</let:Content></let:CoverLetter>";
  isSciencePaperMade = false;
  submitButtonLabel = "Next";
  isEditMode = false;

  private routeSub: Subscription;


  constructor(
    private route: ActivatedRoute,
    private sciencePapersService: SciencePapersService,
    private coverLettersService: CoverLettersService
  ) { }

  ngOnInit() {
    this.routeSub = this.route.params.subscribe(params => {
      if (params['id']) {
        this.sciencePapersService.getOne(params['id']).subscribe((data: any) => {
          this.xmlSciencePaperContent = data;
          Xonomy.render(this.xmlSciencePaperContent, document.getElementById("editor"), UPDATE_SCIENCE_PAPER_SPECIFICATION)
          this.isEditMode = true;
        })
      } else {
        Xonomy.render(this.xmlSciencePaperContent, document.getElementById("editor"), CREATE_SCIENCE_PAPER_SPECIFICATION)
      }
    });
  }

  switchMode() {
    Xonomy.mode === "nerd" ? Xonomy.setMode("laic") : Xonomy.setMode("nerd");
  }

  onSubmit() {
    if (!this.isSciencePaperMade) {
      this.isSciencePaperMade = !this.isSciencePaperMade;
      this.xmlSciencePaperContent = Xonomy.harvest();
      return Xonomy.render(this.xmlCoverLetterContent, document.getElementById("editor"), COVER_LETTER_SPECIFICATION);
    }

    this.xmlCoverLetterContent = Xonomy.harvest();
    if (!this.isEditMode) {
      this.sciencePapersService.create({
        "xml": this.xmlSciencePaperContent
      }).subscribe((data: any) => {
        this.coverLettersService.create({
          "xml": this.xmlCoverLetterContent
        }).subscribe((data: any) => {
          console.log(data);
        })
      })
    }
    else {
      let documentId = this.xmlSciencePaperContent.match(new RegExp("documentId=(\"|\')[0-9]+(\"|\')"))[0].match(new RegExp("[0-9]+"));
      this.xmlSciencePaperContent = this.xmlSciencePaperContent.replace(new RegExp("versionId=(\"|\')[0-9]+(\"|\')"), (labelAndValue) => {
        return labelAndValue.replace(new RegExp("[0-9]+"), (value) => {
          let newValue = parseInt(value);
          newValue += 1;
          return newValue.toString();
        })
      });
      this.sciencePapersService.update(documentId, {
        "xml": this.xmlSciencePaperContent
      }).subscribe((data: any) => {
        this.coverLettersService.create({
          "xml": this.xmlCoverLetterContent
        }).subscribe((data: any) => {
          console.log(data);
        })
      })
    }
    
  }
}
