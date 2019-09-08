import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-condition',
  templateUrl: './condition.component.html',
  styleUrls: ['./condition.component.css']
})
export class ConditionComponent implements OnInit {

  constructor() { }

  value : string="";
  comapreOperator : string="EQUAL_TO";
  field : string="message";
  trailingOperator : string="AND";

  ngOnInit() {
  }

  public getCondition(){
    return {
      value : this.value,
      comapreOperator : this.comapreOperator,
      field : this.field,
      trailingOperator : this.trailingOperator
    }
  }

}
