import { Component, OnInit, Input, ViewChild, SimpleChange } from '@angular/core';
import { SciencePaper } from 'src/app/models/science-paper.model';
import { MatTableDataSource, MatSort, MatPaginator, MatDialog } from '@angular/material';
import { Router } from "@angular/router";
import { ConfirmationDialogComponent } from 'src/app/shared/components/confirmation-dialog/confirmation-dialog.component';
import { SciencePapersService } from '../../services/sciencepapers.service';
import { MessageService } from 'src/app/shared/services/message.service';

@Component({
  selector: 'app-science-papers-list',
  templateUrl: './science-papers-list.component.html',
  styleUrls: ['./science-papers-list.component.css']
})
export class SciencePapersListComponent implements OnInit {
  @Input() sciencePapers: SciencePaper[];

  displayedColumns: string[] = ['id', 'shortTitle', 'author', 'details', 'update', 'delete'];

  dataSource: MatTableDataSource<SciencePaper>;

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;


  constructor(
    private sciencePapersService: SciencePapersService,
    private router: Router,
    private messageService: MessageService,
    public dialog: MatDialog
  ) { }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChange) {
    console.log('opa')
    this.initializeDataSource();
  }

  initializeDataSource() {
    this.dataSource  = new MatTableDataSource<SciencePaper>();
    this.dataSource.data = this.sciencePapers || [];
    this.dataSource.sort = this.sort
    this.dataSource.paginator = this.paginator;
    console.log(this.dataSource)
  }

  getAuthorNames(sciencePaper) {
    return sciencePaper.paperData.author.map((el) => {
      return el.authorUserName
    }).join(",");
  }


  redirectToUpdate(id: number) {
    this.router.navigate(['/science-papers/edit', id]);
  }

  redirectToDetails(id: number) {
    this.router.navigate(['/science-papers/detail', id]);
  }

  showDeleteModal(id: number) {
    let dialogData = {
      width: '350px',
      data: "Do you confirm the deletion of this data?"
    };
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, dialogData);

    dialogRef.afterClosed().subscribe(result => {
      if(!result) {
        return;
      }
      this.deleteEntry(id);
    });
  }

  deleteEntry(id: number) {
    // TO-DO: Implement
  }

  public doFilter = (value: string) => {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
  }


}
