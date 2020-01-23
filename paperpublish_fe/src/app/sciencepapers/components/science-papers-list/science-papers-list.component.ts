import { Component, OnInit, Input, ViewChild, SimpleChange } from '@angular/core';
import { SciencePaper } from 'src/app/models/science-paper.model';
import { MatTableDataSource, MatSort, MatPaginator, MatDialog } from '@angular/material';
import { Router } from "@angular/router";
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SciencePapersService } from '../../services/sciencepapers.service';
import { MessageService } from 'src/app/shared/services/message.service';

@Component({
  selector: 'app-science-papers-list',
  templateUrl: './science-papers-list.component.html',
  styleUrls: ['./science-papers-list.component.css']
})
export class SciencePapersListComponent implements OnInit {
  @Input() sciencePapers: SciencePaper[];

  displayedColumns: string[] = ['id', 'shortTitle', 'downloadHTML', 'downloadPDF', 'author', 'details', 'update', 'delete'];

  dataSource: MatTableDataSource<SciencePaper>;

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;


  constructor(
    private sciencePapersService: SciencePapersService,
    private router: Router,
    private messageService: MessageService,
    public dialog: MatDialog
  ) { }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChange) {
    this.initializeDataSource();
  }

  initializeDataSource() {
    this.dataSource  = new MatTableDataSource<SciencePaper>();
    this.dataSource.data = this.sciencePapers || [];
    this.dataSource.sort = this.sort
    this.dataSource.paginator = this.paginator;
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
    // TO-DO: Implement
  }

  public doFilter = (value: string) => {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
  }

  downloadPDF(id: number) {
    this.sciencePapersService.downloadPDF(id).subscribe(result => {
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(result);
        return;
      } 
      // For other browsers: 
      // Create a link pointing to the ObjectURL containing the blob.
      const data = window.URL.createObjectURL(result);

      var link = document.createElement('a');
      link.href = data;
      link.download = "science_paper.pdf";
      // this is necessary as link.click() does not work on the latest firefox
      link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

      setTimeout(function () {
          // For Firefox it is necessary to delay revoking the ObjectURL
          window.URL.revokeObjectURL(data);
      }, 100);
    });
  }

  downloadHTML(id: number) {
    this.sciencePapersService.downloadHTML(id).subscribe(result => {
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(result);
        return;
      } 
      // For other browsers: 
      // Create a link pointing to the ObjectURL containing the blob.
      const data = window.URL.createObjectURL(result);

      var link = document.createElement('a');
      link.href = data;
      link.download = "science_paper.html";
      // this is necessary as link.click() does not work on the latest firefox
      link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

      setTimeout(function () {
          // For Firefox it is necessary to delay revoking the ObjectURL
          window.URL.revokeObjectURL(data);
      }, 100);
    })
  }

}
