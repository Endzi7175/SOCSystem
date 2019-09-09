import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RolePrivilageAdderComponent } from './role-privilage-adder.component';

describe('RolePrivilageAdderComponent', () => {
  let component: RolePrivilageAdderComponent;
  let fixture: ComponentFixture<RolePrivilageAdderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RolePrivilageAdderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RolePrivilageAdderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
