import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaperReviewsComponent } from './paper-reviews.component';

describe('PaperReviewsComponent', () => {
  let component: PaperReviewsComponent;
  let fixture: ComponentFixture<PaperReviewsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaperReviewsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaperReviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
