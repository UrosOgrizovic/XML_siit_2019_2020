import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciencePapersListComponent } from './science-papers-list.component';

describe('SciencePapersListComponent', () => {
  let component: SciencePapersListComponent;
  let fixture: ComponentFixture<SciencePapersListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciencePapersListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciencePapersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
