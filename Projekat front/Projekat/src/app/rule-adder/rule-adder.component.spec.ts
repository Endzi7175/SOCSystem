import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RuleAdderComponent } from './rule-adder.component';

describe('RuleAdderComponent', () => {
  let component: RuleAdderComponent;
  let fixture: ComponentFixture<RuleAdderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RuleAdderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RuleAdderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
