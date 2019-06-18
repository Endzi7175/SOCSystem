import { Component, OnInit, ViewChild, ViewContainerRef, ComponentFactoryResolver, ComponentFactory, ComponentRef } from '@angular/core';
import { ConditionComponent } from '../condition/condition.component';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-rule-adder',
  templateUrl: './rule-adder.component.html',
  styleUrls: ['./rule-adder.component.css']
})
export class RuleAdderComponent implements OnInit {
  @ViewChild('conditionContainer', {read: ViewContainerRef, static : true}) container: ViewContainerRef;
  constructor(private resolver: ComponentFactoryResolver, private http : HttpClient) { }
  conditions : ComponentRef<ConditionComponent>[] = [];
  time : string = "s";
  interval : number = 1;
  count : number = 1;
  message : string = "ZIka";
  private serverUrl = 'http://localhost:8090'
  ngOnInit() {
  }

  addConditionClick(){
    const factory: ComponentFactory<ConditionComponent> = this.resolver.resolveComponentFactory(ConditionComponent);
    let componentRef: ComponentRef<ConditionComponent> = this.container.createComponent(factory);
    this.conditions.push(componentRef);
  }
  submit(){
    let rule ={
      conditions : this.conditions.map((condition)=> condition.instance.getCondition())
    }
    let dto={
      rule : rule,
      interval : this.interval+this.time,
      count : this.count,
      message : this.message
    }

      let that = this;
      this.http.post<any>(this.serverUrl + "/api/rule", dto).subscribe(
        (response)=>{
          alert("Pravilo uspesno dodato");
        }, 
        (error)=>{
          alert("Doslo je do greske");
        }
      );
    
  }

}
