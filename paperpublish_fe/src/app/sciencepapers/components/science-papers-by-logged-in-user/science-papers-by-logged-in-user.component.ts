import { Component, OnInit, Input, ViewChild, OnChanges } from '@angular/core';
import { SciencePapersService } from '../../services/sciencepapers.service';
import { SciencePaper } from 'src/app/models/science-paper.model';
import { MatTableDataSource, MatSort, MatPaginator, MatDialog } from '@angular/material';
import { User } from 'src/app/models/user.model';
import { Router } from '@angular/router';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-science-papers-by-logged-in-user',
  templateUrl: './science-papers-by-logged-in-user.component.html',
  styleUrls: ['./science-papers-by-logged-in-user.component.css']
})
export class SciencePapersByLoggedInUserComponent implements OnInit, OnChanges {
  sciencePapers: SciencePaper[];

  displayedColumns: string[] = ['id', 'shortTitle', 'downloadHTML', 'downloadPDF', 'author', 'status', 'reviews', 'details', 'update', 'delete'];

  dataSource: MatTableDataSource<SciencePaper>;


  constructor(private sciencePapersService: SciencePapersService, private router: Router, public dialog: MatDialog) { }

  ngOnInit() {
    const user: User = JSON.parse(localStorage.getItem('user'));
    this.sciencePapersService.getByAuthorUsername(user.username).subscribe((data: any) => {
      this.sciencePapers = data;
      this.initializeDataSource();
    })
  }

  ngOnChanges() {
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

  redirectToReviews(id: number) {
    this.router.navigate(['/paper-reviews', id])
  }

  redirectToDetails(id: number) {
    this.router.navigate(['/science-papers/detail', id]);
  }

  redirectToAddNewPage() {
    this.router.navigate(['/science-papers/add-new'])
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
      const user: User = JSON.parse(localStorage.getItem('user'));
      this.sciencePapersService.getByAuthorUsername(user.username).subscribe((data: any) => {
        this.sciencePapers = data;
        this.initializeDataSource();
      });
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

      let link = document.createElement('a');
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
