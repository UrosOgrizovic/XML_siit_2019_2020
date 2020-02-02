import { Component, OnInit } from '@angular/core';
import { SciencePaper } from 'src/app/models/science-paper.model';
import { MatTableDataSource, MatSort, MatPaginator, MatDialog } from '@angular/material';
import { Router } from "@angular/router";
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SciencePapersService } from '../../services/sciencepapers.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/home/services/auth.service';

@Component({
  selector: 'app-review-science-papers',
  templateUrl: './review-science-papers.component.html',
  styleUrls: ['./review-science-papers.component.css']
})
export class ReviewSciencePapersComponent implements OnInit {
  sciencePapers: SciencePaper[];
  private routeSub: Subscription;

  displayedColumns: string[] = ['id', 'shortTitle', 'downloadHTML', 'downloadPDF', 'author', 'review'];

  dataSource: MatTableDataSource<SciencePaper>;

  constructor(private route: ActivatedRoute, private authService: AuthService,
    private sciencePapersService: SciencePapersService, private router: Router, public dialog: MatDialog) { }

  ngOnInit() {
    this.sciencePapersService.getForReview(this.authService.activeUser.username).subscribe((data: any) => {
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

  redirectToReview(id: number) {
    this.router.navigate(['/science-papers/review', id]);
  }

}
