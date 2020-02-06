import { Component, OnInit, Input, ViewChild, SimpleChange } from '@angular/core';
import { SciencePaper } from 'src/app/models/science-paper.model';
import { MatTableDataSource, MatSort, MatPaginator, MatDialog } from '@angular/material';
import { Router } from "@angular/router";
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SciencePapersService } from '../../services/sciencepapers.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { formatDate } from '@angular/common';
import { User } from 'src/app/models/user.model';
import { ThrowStmt } from '@angular/compiler';

@Component({
  selector: 'app-science-papers-list',
  templateUrl: './science-papers-list.component.html',
  styleUrls: ['./science-papers-list.component.css']
})
export class SciencePapersListComponent implements OnInit {
  @Input() sciencePapers: SciencePaper[];

  displayedColumns: string[] = ['id', 'shortTitle', 'downloadHTML', 'downloadPDF', 'author'];

  dataSource: MatTableDataSource<SciencePaper>;

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;


  keywordsForSearch = '';
  textForSearch = '';
  paperPublishDate: Date = new Date(1);
  searchOnlyMyPapers = false;
  user: User;


  constructor(
    private sciencePapersService: SciencePapersService,
    private router: Router,
    private messageService: MessageService,
    public dialog: MatDialog
  ) { }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  ngOnChanges(changes: SimpleChange) {
    this.initializeDataSource();
  }

  initializeDataSource() {
    this.dataSource  = new MatTableDataSource<SciencePaper>();
    this.dataSource.data = this.sciencePapers || [];
  }

  getAuthorNames(sciencePaper) {
    return sciencePaper.paperData.author.map((el) => {
      return el.authorUserName
    }).join(",");
  }


  redirectToUpdate(id: number) {
    this.router.navigate(['/science-papers/edit', id]);
  }

  redirectToDetails(id: number) {
    this.router.navigate(['/science-papers/detail', id]);
  }

  showDeleteModal(id: number) {
    let dialogData = {
      width: '350px',
      data: "Do you confirm the deletion of this data?"
    };
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogData);

    dialogRef.afterClosed().subscribe(result => {
      if(!result) {
        return;
      }
      this.deleteEntry(id);
    });
  }

  deleteEntry(id: number) {
    this.sciencePapersService.delete(id).subscribe((data: any) => {
      this.router.navigate(['/']);
    });
  }

  public doFilter = (value: string) => {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
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

  performSearch() {
    this.keywordsForSearch = this.keywordsForSearch.trim();
    this.textForSearch = this.textForSearch.trim();
    let username = '';
    if (this.user != null) {
      username = this.user.username;
    }
    if ((document.getElementById('paperPublishDatepicker') as HTMLInputElement).value !== '') {
      this.paperPublishDate = new Date((document.getElementById('paperPublishDatepicker') as HTMLInputElement).value);
    }


    this.sciencePapersService.performSearch(this.keywordsForSearch,
      formatDate(this.paperPublishDate, 'dd-MM-yyyy', 'en-US'), this.textForSearch, username, this.searchOnlyMyPapers)
      .subscribe(res => {
        this.dataSource.data = res;
      });
  }

}
