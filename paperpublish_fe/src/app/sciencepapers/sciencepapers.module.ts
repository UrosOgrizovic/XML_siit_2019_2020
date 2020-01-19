import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SciencePapersListComponent } from './components/science-papers-list/science-papers-list.component';
import { SciencePapersPageComponent } from './components/science-papers-page/science-papers-page.component';
import { SciencePapersRoutingModule } from './sciencepapers-routing.module';
import { SciencePapersMaterialModule } from './sciencepapers-material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { SciencePaperFormComponent } from './components/science-paper-form/science-paper-form.component';

@NgModule({
  declarations: [SciencePapersListComponent, SciencePapersPageComponent, SciencePaperFormComponent],
  imports: [
    FlexLayoutModule,
    SciencePapersMaterialModule,
    CommonModule,
    SciencePapersRoutingModule,
  ]
})
export class SciencepapersModule { }
