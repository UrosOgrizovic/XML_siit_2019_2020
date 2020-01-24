import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciencePaperDetailsComponent } from './science-paper-details.component';

describe('SciencePaperDetailsComponent', () => {
  let component: SciencePaperDetailsComponent;
  let fixture: ComponentFixture<SciencePaperDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciencePaperDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciencePaperDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
