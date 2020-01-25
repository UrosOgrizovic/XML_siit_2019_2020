import { Component, OnInit } from '@angular/core';
import { SciencePapersService } from '../../services/sciencepapers.service';
import { SciencePaper } from 'src/app/models/science-paper.model';
import { Router } from "@angular/router";

@Component({
  selector: 'app-science-papers-page',
  templateUrl: './science-papers-page.component.html',
  styleUrls: ['./science-papers-page.component.css']
})
export class SciencePapersPageComponent implements OnInit {
  sciencePapers: SciencePaper[];

  constructor(
    private sciencePapersService: SciencePapersService,
    private router: Router, 
  ) { }

  ngOnInit() {
    this.sciencePapersService.getAll().subscribe((data: any) => {
      this.sciencePapers = data;
    })
  }

  

}
