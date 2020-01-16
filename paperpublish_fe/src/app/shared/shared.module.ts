import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmationDialogComponent } from './components/confirmation-dialog/confirmation-dialog.component';
import { SharedMaterialModule } from './shared-material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ToastrModule } from 'ngx-toastr';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [ConfirmationDialogComponent],
  exports: [ConfirmationDialogComponent],
  imports: [
    CommonModule,
    SharedMaterialModule,
    FlexLayoutModule,
    ToastrModule.forRoot({ positionClass: 'inline' }),
    FormsModule
  ],
  entryComponents: [ConfirmationDialogComponent]
})
export class SharedModule { }
