import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SciencePapersPageComponent } from './science-papers-page.component';

describe('SciencePapersPageComponent', () => {
  let component: SciencePapersPageComponent;
  let fixture: ComponentFixture<SciencePapersPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SciencePapersPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SciencePapersPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
