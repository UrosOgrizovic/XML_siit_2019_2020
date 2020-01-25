import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AllSciencePapersComponent } from './all-science-papers.component';

describe('AllSciencePapersComponent', () => {
  let component: AllSciencePapersComponent;
  let fixture: ComponentFixture<AllSciencePapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllSciencePapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllSciencePapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
