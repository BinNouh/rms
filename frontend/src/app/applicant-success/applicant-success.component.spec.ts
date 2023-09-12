import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicantSuccessComponent } from './applicant-success.component';

describe('ApplicantSuccessComponent', () => {
  let component: ApplicantSuccessComponent;
  let fixture: ComponentFixture<ApplicantSuccessComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ApplicantSuccessComponent]
    });
    fixture = TestBed.createComponent(ApplicantSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
