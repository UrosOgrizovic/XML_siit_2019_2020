import { NgModule }             from '@angular/core';
import { SciencePapersPageComponent } from './components/science-papers-page/science-papers-page.component';
import { Routes, RouterModule } from '@angular/router';
import { SciencePaperFormComponent } from './components/science-paper-form/science-paper-form.component';

const routes: Routes = [
  {
    path: 'science-papers',
    component: SciencePapersPageComponent
  },
  {
    path: 'science-papers/add-new',
    component: SciencePaperFormComponent
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class SciencePapersRoutingModule { }