import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { SciencePapersService } from '../../services/sciencepapers.service';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-science-paper-assign-form',
  templateUrl: './science-paper-assign-form.component.html',
  styleUrls: ['./science-paper-assign-form.component.css']
})
export class SciencePaperAssignFormComponent implements OnInit {
  private routeSub: Subscription;

  private proposals: User[];
  private proposal: string;
  private documentId;

  constructor(
    private route: ActivatedRoute,
    private sciencePapersService: SciencePapersService,
    private router: Router
  ) { }


  ngOnInit() {
    this.routeSub = this.route.params.subscribe(params => {
      this.sciencePapersService.getProposals(params['id']).subscribe((data: any) => {
        this.proposals = data;
        this.documentId = params['id']
      })
    });
  }

  addReviewer() {
    if (this.proposal) {
      this.sciencePapersService.addReviewer(this.documentId, this.proposal).subscribe((data: any) => {
        this.router.navigate(['/all-science-papers']);
      })
    }
  }

}
