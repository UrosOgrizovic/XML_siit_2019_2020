import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciencePaperReviewFormComponent } from './science-paper-review-form.component';

describe('SciencePaperReviewFormComponent', () => {
  let component: SciencePaperReviewFormComponent;
  let fixture: ComponentFixture<SciencePaperReviewFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciencePaperReviewFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciencePaperReviewFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
