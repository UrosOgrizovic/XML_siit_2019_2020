import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewSciencePapersComponent } from './review-science-papers.component';

describe('ReviewSciencePapersComponent', () => {
  let component: ReviewSciencePapersComponent;
  let fixture: ComponentFixture<ReviewSciencePapersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewSciencePapersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewSciencePapersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
