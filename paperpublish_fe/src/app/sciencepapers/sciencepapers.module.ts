import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SciencePapersListComponent } from './components/science-papers-list/science-papers-list.component';
import { SciencePapersPageComponent } from './components/science-papers-page/science-papers-page.component';
import { SciencePapersRoutingModule } from './sciencepapers-routing.module';
import { SciencePapersMaterialModule } from './sciencepapers-material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { SciencePaperFormComponent } from './components/science-paper-form/science-paper-form.component';
import { SciencePaperDetailsComponent } from './components/science-paper-details/science-paper-details.component';

@NgModule({
  declarations: [SciencePapersListComponent, SciencePapersPageComponent, SciencePaperFormComponent, SciencePaperDetailsComponent],
  imports: [
    FlexLayoutModule,
    SciencePapersMaterialModule,
    CommonModule,
    SciencePapersRoutingModule,
  ]
})
export class SciencepapersModule { }
