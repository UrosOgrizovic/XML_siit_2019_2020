import { NgModule } from '@angular/core';

import { MatInputModule, MatPaginatorModule, MatProgressSpinnerModule, MatDialogModule,
  MatSortModule, MatTableModule, MatIconModule, MatButtonModule, MatFormFieldModule, 
  MatDatepickerModule, MatNativeDateModule } from '@angular/material';


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
    MatNativeDateModule
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
    MatNativeDateModule
  ]
})
export class SharedMaterialModule { }