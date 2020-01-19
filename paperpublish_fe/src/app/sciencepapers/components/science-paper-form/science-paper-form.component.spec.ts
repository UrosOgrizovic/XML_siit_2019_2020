import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciencePaperFormComponent } from './science-paper-form.component';

describe('SciencePaperFormComponent', () => {
  let component: SciencePaperFormComponent;
  let fixture: ComponentFixture<SciencePaperFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciencePaperFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciencePaperFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
