import { Component, OnInit, ViewChild } from '@angular/core';
import { SciencePapersService } from '../../services/sciencepapers.service';
import { ActivatedRoute } from '@angular/router';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-science-paper-details',
  templateUrl: './science-paper-details.component.html',
  styleUrls: ['./science-paper-details.component.css']
})
export class SciencePaperDetailsComponent implements OnInit {
  
  id: number;
  metadata: MatTableDataSource<any>;
  tempMetadata: any;

  displayedColumns: string[] = ['metaName', 'metaContent'];

  constructor(private sciencePapersService: SciencePapersService, private route: ActivatedRoute) { }


  ngOnInit() {
    this.tempMetadata = [];

    this.id = +this.route.snapshot.paramMap.get('id');
    this.sciencePapersService.getHTML(this.id, 'text').subscribe(result => {
      document.getElementById('sciencePaperHtml').innerHTML = result;
      const allTempMetadata = Array.from(document.getElementById('sciencePaperHtml').getElementsByTagName('meta'));
      for (let i = 0; i < allTempMetadata.length; i++) {
        if (allTempMetadata[i].name !== '') {
          this.tempMetadata.push(allTempMetadata[i]);
        }
      }
      this.initializeDataSource();
    });
  }

  initializeDataSource() {
    this.metadata  = new MatTableDataSource<any>();
    this.metadata.data = this.tempMetadata;
  }

  toggleTableVisibility() {
    if (document.getElementById('metadataTable').style.visibility === 'hidden') {
      document.getElementById('metadataTable').style.visibility = 'visible';
    } else {
      document.getElementById('metadataTable').style.visibility = 'hidden';
    }
  }

  
  downloadPDF(id: number) {
    this.sciencePapersService.getPDF(id).subscribe(result => {
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(result);
        return;
      }
      // For other browsers:
      // Create a link pointing to the ObjectURL containing the blob.
      const data = window.URL.createObjectURL(result);

      var link = document.createElement('a');
      link.href = data;
      this.sciencePapersService.getPaperTitleById(id).subscribe(title => { 
        link.download = title + '.pdf';
        // this is necessary as link.click() does not work on the latest firefox
        link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

        setTimeout(function () {
            // For Firefox it is necessary to delay revoking the ObjectURL
            window.URL.revokeObjectURL(data);
        }, 100);
      });
    });
  }

  downloadHTML(id: number) {
    this.sciencePapersService.getHTML(id, 'blob').subscribe(result => {
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(result);
        return;
      } 
      // For other browsers: 
      // Create a link pointing to the ObjectURL containing the blob.
      const data = window.URL.createObjectURL(result);

      var link = document.createElement('a');
      link.href = data;
      this.sciencePapersService.getPaperTitleById(id).subscribe(title => {
        link.download = title + '.html';
        // this is necessary as link.click() does not work on the latest firefox
        link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

        setTimeout(function () {
          // For Firefox it is necessary to delay revoking the ObjectURL
          window.URL.revokeObjectURL(data);
        }, 100);
      });
    })
  }
}
