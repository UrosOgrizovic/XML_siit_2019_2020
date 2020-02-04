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
}
