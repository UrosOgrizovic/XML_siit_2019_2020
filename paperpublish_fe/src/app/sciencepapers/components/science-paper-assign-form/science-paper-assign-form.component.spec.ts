import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciencePaperAssignFormComponent } from './science-paper-assign-form.component';

describe('SciencePaperAssignFormComponent', () => {
  let component: SciencePaperAssignFormComponent;
  let fixture: ComponentFixture<SciencePaperAssignFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciencePaperAssignFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciencePaperAssignFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
