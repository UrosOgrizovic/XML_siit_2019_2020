import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmationDialogComponent } from './components/confirmation-dialog/confirmation-dialog.component';
import { SharedMaterialModule } from './shared-material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ToastrModule } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';
import { CommentDialogComponent } from './components/comment-dialog/comment-dialog.component';

@NgModule({
  declarations: [ConfirmationDialogComponent, CommentDialogComponent],
  exports: [ConfirmationDialogComponent, CommentDialogComponent],
  imports: [
    CommonModule,
    SharedMaterialModule,
    FlexLayoutModule,
    ToastrModule.forRoot({ positionClass: 'inline' }),
    FormsModule
  ],
  entryComponents: [ConfirmationDialogComponent, CommentDialogComponent]
})
export class SharedModule { }
