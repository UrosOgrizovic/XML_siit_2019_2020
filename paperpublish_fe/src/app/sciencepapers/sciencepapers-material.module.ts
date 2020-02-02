import { NgModule } from '@angular/core';

import { MatInputModule, MatPaginatorModule, MatProgressSpinnerModule, MatDialogModule,
  MatSortModule, MatTableModule, MatIconModule, MatButtonModule, MatFormFieldModule, MatDatepickerModule, MatCheckboxModule } from "@angular/material";


@NgModule({
  imports: [
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatInputModule,
    MatSortModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatDialogModule,
    MatDatepickerModule,
    MatCheckboxModule
  ],
  exports: [
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatInputModule,
    MatSortModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatDialogModule,
    MatDatepickerModule,
    MatCheckboxModule
  ]
})
export class SciencePapersMaterialModule { }