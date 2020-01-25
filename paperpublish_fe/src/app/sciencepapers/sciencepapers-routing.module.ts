import { NgModule }             from '@angular/core';
import { SciencePapersPageComponent } from './components/science-papers-page/science-papers-page.component';
import { Routes, RouterModule } from '@angular/router';
import { SciencePaperFormComponent } from './components/science-paper-form/science-paper-form.component';
import { SciencePaperDetailsComponent } from './components/science-paper-details/science-paper-details.component';
import { SciencePapersByLoggedInUserComponent } from './components/science-papers-by-logged-in-user/science-papers-by-logged-in-user.component';
import { AllSciencePapersComponent } from './components/all-science-papers/all-science-papers.component';
import { SciencePaperAssignFormComponent } from './components/science-paper-assign-form/science-paper-assign-form.component';

const routes: Routes = [
  {
    path: 'science-papers',
    component: SciencePapersPageComponent
  },
  {
    path: 'science-papers/add-new',
    component: SciencePaperFormComponent
  },
  {
    path: 'science-papers/edit/:id',
    component: SciencePaperFormComponent
  },
  {
    path: 'science-papers/detail/:id',
    component: SciencePaperDetailsComponent
  },
  {
    path: 'my-science-papers',
    component: SciencePapersByLoggedInUserComponent
  },
  {
    path: 'all-science-papers',
    component: AllSciencePapersComponent
  },
  {
    path: 'science-papers/assign/:id',
    component: SciencePaperAssignFormComponent
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