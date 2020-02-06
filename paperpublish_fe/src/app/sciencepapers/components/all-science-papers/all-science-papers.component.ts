import { Component, OnInit } from '@angular/core';
import { SciencePapersService } from '../../services/sciencepapers.service';
import { SciencePaper } from 'src/app/models/science-paper.model';
import { MatTableDataSource, MatSort, MatPaginator, MatDialog } from '@angular/material';
import { User } from 'src/app/models/user.model';
import { Router } from '@angular/router';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ReviewsService } from '../../services/reviews.service';


@Component({
  selector: 'app-all-science-papers',
  templateUrl: './all-science-papers.component.html',
  styleUrls: ['./all-science-papers.component.css']
})
export class AllSciencePapersComponent implements OnInit {
  sciencePapers: SciencePaper[];

  displayedColumns: string[] = ['id', 'shortTitle', 'downloadHTML', 'downloadPDF', 'status', 'author', 'assign', 'accept', 'reject', 'merge'];

  dataSource: MatTableDataSource<SciencePaper>;

  constructor(private reviewsService: ReviewsService, private sciencePapersService: SciencePapersService, private router: Router, public dialog: MatDialog) { }

  ngOnInit() {
    this.sciencePapersService.getAll().subscribe((data: any) => {
      this.sciencePapers = data;
      console.log(data);
      this.initializeDataSource();
    })
  }

  getAuthorNames(sciencePaper) {
    return sciencePaper.paperData.author.map((el) => {
      return el.authorUserName
    }).join(",");
  }

  initializeDataSource() {
    this.dataSource  = new MatTableDataSource<SciencePaper>();
    this.dataSource.data = this.sciencePapers || [];
  }

  redirectToAssign(id: number) {
    this.router.navigate(['/science-papers/assign', id]);
  }

  showAcceptModal(id: number) {
    let dialogData = {
      width: '350px',
      data: "Are you sure you want to accept this document?"
    };
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogData);

    dialogRef.afterClosed().subscribe(result => {
      if(!result) {
        return;
      }
      this.sciencePapersService.changeStatus(id, 'accepted').subscribe((res: any) => {
        this.sciencePapersService.getAll().subscribe((data: any) => {
          this.sciencePapers = data;
          this.initializeDataSource();
        })
      });
    });
  }

  showRejectModal(id: number) {
    let dialogData = {
      width: '350px',
      data: "Are you sure you want to reject this document?"
    };
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogData);

    dialogRef.afterClosed().subscribe(result => {
      if(!result) {
        return;
      }
      this.sciencePapersService.changeStatus(id, 'rejected').subscribe((res: any) => {
        this.sciencePapersService.getAll().subscribe((data: any) => {
          this.sciencePapers = data;
          this.initializeDataSource();
        })    
      });
    });
  }

  showMergeModal(id: number) {
    let dialogData = {
      width: '350px',
      data: "Are you sure you want to merge reviews?"
    };
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogData);

    dialogRef.afterClosed().subscribe(result => {
      if(!result) {
        return;
      }
      this.reviewsService.merge(id).subscribe((res: any) => {});
    });
  }



}
