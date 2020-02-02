import { Component, OnInit, ViewChild } from '@angular/core';
import { AuthService } from 'src/app/home/services/auth.service';
import { ReviewAssignment } from 'src/app/models/review-assignment.model';
import { MatTableDataSource, MatSort, MatPaginator, MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ReviewsService } from '../../services/reviews.service';

@Component({
  selector: 'app-assignments',
  templateUrl: './assignments.component.html',
  styleUrls: ['./assignments.component.css']
})
export class AssignmentsComponent implements OnInit {

  assignments: ReviewAssignment[];

  displayedColumns: string[] = ['id', 'details', 'accept', 'decline'];

  dataSource: MatTableDataSource<ReviewAssignment>;

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;


  constructor(
    private reviewsService: ReviewsService,
    private authService: AuthService,
    private router: Router,
    public dialog: MatDialog
  ) { }

  ngOnInit() {
    this.reviewsService.getAllAssignments(this.authService.activeUser.username).subscribe((res: ReviewAssignment[]) => {
      this.assignments = res;
      this.initializeDataSource();
    })
  }



  initializeDataSource() {
    this.dataSource  = new MatTableDataSource<ReviewAssignment>();
    this.dataSource.data = this.assignments || [];
  }

  redirectToDetails(id: number) {
    this.router.navigate(['/science-papers/detail', id]);
  }

  showAcceptModal(id: number) {
    let dialogData = {
      width: '350px',
      data: "Are you sure you want to accept this assignment?"
    };
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogData);

    dialogRef.afterClosed().subscribe(result => {
      if(!result) {
        return;
      }
      this.reviewsService.acceptAssignment(id, this.authService.activeUser.username).subscribe((res: any) => {
        this.router.navigateByUrl('/')
      });
    });
  }

  showDeclineModal(id: number) {
    let dialogData = {
      width: '350px',
      data: "Are you sure you want to reject this assignment?"
    };
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogData);

    dialogRef.afterClosed().subscribe(result => {
      if(!result) {
        return;
      }
      this.reviewsService.declineAssignment(id, this.authService.activeUser.username).subscribe((res: any) => {
        this.router.navigateByUrl('/')
      });
    });
  }

}
