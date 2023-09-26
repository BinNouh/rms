import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmptyRedirectComponent } from './empty-redirect.component';

describe('EmptyRedirectComponent', () => {
  let component: EmptyRedirectComponent;
  let fixture: ComponentFixture<EmptyRedirectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmptyRedirectComponent]
    });
    fixture = TestBed.createComponent(EmptyRedirectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
