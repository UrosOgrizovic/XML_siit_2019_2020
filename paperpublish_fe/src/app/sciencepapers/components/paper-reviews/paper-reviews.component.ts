import { Component, OnInit } from '@angular/core';
import { ReviewsService } from '../../services/reviews.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-paper-reviews',
  templateUrl: './paper-reviews.component.html',
  styleUrls: ['./paper-reviews.component.css']
})
export class PaperReviewsComponent implements OnInit {

  constructor(private reviewsService: ReviewsService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.reviewsService.viewMergedReviews(this.route.snapshot.params.id).subscribe(result => {
      document.getElementById('mergedReviewsHTML').innerHTML = result;
    });
  }

}
