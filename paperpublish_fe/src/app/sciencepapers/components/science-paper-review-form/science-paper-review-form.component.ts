import { Component, OnInit } from '@angular/core';
import { SciencePapersService } from '../../services/sciencepapers.service';
import { ActivatedRoute } from '@angular/router';
import { MatDialog } from '@angular/material';
import { CommentDialogComponent } from 'src/app/shared/components/comment-dialog/comment-dialog.component';
import { AuthService } from 'src/app/home/services/auth.service';
import { ReviewsService } from '../../services/reviews.service';

@Component({
  selector: 'app-science-paper-review-form',
  templateUrl: './science-paper-review-form.component.html',
  styleUrls: ['./science-paper-review-form.component.css']
})
export class SciencePaperReviewFormComponent implements OnInit {
  id: number;
  commentXML = ""

  constructor(private sciencePapersService: SciencePapersService, private authService: AuthService, public dialog: MatDialog,
    private route: ActivatedRoute,
    private reviewsService: ReviewsService) { }

  ngOnInit() {
    this.id = +this.route.snapshot.paramMap.get('id');
    this.sciencePapersService.getHTML(this.id, 'text').subscribe(result => {
      document.getElementById('sciencePaperHtml').innerHTML = result;
      this.attachEvents();
    });
  }

  attachEvents() {
    document.querySelectorAll('[paragrafid]').forEach((el) => {
      el.addEventListener('click', (e) => {
        const dialogRef = this.dialog.open(CommentDialogComponent, {
          width: '250px',
          data: ''
        });
        dialogRef.afterClosed().subscribe(result => {
          this.commentXML += `<rev:Comment paragrafId='${el.getAttribute('paragrafid')}'>${result}</rev:Comment>`
        });
      });
    });
  }

  generateReviewXML() {
    return `<rev:Review xmlns:rev='http://localhost:8080/Reviews'><rev:Authors><rev:authorUserName>${this.authService.activeUser.username}</rev:authorUserName></rev:Authors><rev:Paper paperId='${this.id}'></rev:Paper><rev:Comments>${this.commentXML}</rev:Comments></rev:Review>`
  }

  onSubmit() {
    this.reviewsService.create({"xml": this.generateReviewXML()}).subscribe((res: any) => {

    });
  }

}
