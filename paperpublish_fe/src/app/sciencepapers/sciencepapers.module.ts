import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SciencePapersListComponent } from './components/science-papers-list/science-papers-list.component';
import { SciencePapersPageComponent } from './components/science-papers-page/science-papers-page.component';
import { SciencePapersRoutingModule } from './sciencepapers-routing.module';
import { SciencePapersMaterialModule } from './sciencepapers-material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { SciencePaperFormComponent } from './components/science-paper-form/science-paper-form.component';
import { SciencePaperDetailsComponent } from './components/science-paper-details/science-paper-details.component';
import { SciencePapersByLoggedInUserComponent } from './components/science-papers-by-logged-in-user/science-papers-by-logged-in-user.component';
import { AllSciencePapersComponent } from './components/all-science-papers/all-science-papers.component';
import { SciencePaperAssignFormComponent } from './components/science-paper-assign-form/science-paper-assign-form.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [SciencePapersListComponent, SciencePapersPageComponent, SciencePaperFormComponent, SciencePaperDetailsComponent, SciencePapersByLoggedInUserComponent, AllSciencePapersComponent, SciencePaperAssignFormComponent],
  imports: [
    FormsModule,
    FlexLayoutModule,
    SciencePapersMaterialModule,
    CommonModule,
    SciencePapersRoutingModule,
  ]
})
export class SciencepapersModule { }
