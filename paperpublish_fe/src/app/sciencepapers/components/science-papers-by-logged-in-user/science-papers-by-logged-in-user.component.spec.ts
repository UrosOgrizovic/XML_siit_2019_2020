import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciencePapersByLoggedInUserComponent } from './science-papers-by-logged-in-user.component';

describe('SciencePapersByLoggedInUserComponent', () => {
  let component: SciencePapersByLoggedInUserComponent;
  let fixture: ComponentFixture<SciencePapersByLoggedInUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciencePapersByLoggedInUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciencePapersByLoggedInUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
