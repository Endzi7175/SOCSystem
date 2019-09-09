import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleAdderComponent } from './role-adder.component';

describe('RoleAdderComponent', () => {
  let component: RoleAdderComponent;
  let fixture: ComponentFixture<RoleAdderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoleAdderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleAdderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
