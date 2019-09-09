import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RoleAdderComponent } from '../role-adder/role-adder.component';

@Component({
  selector: 'app-role-privilage-adder',
  templateUrl: './role-privilage-adder.component.html',
  styleUrls: ['./role-privilage-adder.component.css']
})
export class RolePrivilageAdderComponent implements OnInit {

  constructor(private http : HttpClient) { }
  privilage : string = "";
  roles : string[] = [];
  role : string;
  private serverUrl = 'http://localhost:8090'
  ngOnInit() {
    this.http.get<any>(this.serverUrl + "/api/role").subscribe(
      (response)=>{
        for(let role of response){
           this.roles.push(role);
        }
      }, 
      (error)=>{
        alert("Doslo je do greske");
      }
    );
  }

  submit(){
    let role = {
       name : this.role
    };
    let privilage = {
      name : this.privilage
    };
    let dto={
      role : role,
      privilage : privilage
    };
    this.http.post<any>(this.serverUrl + "/api/addPrivilege", dto).subscribe(
      (response)=>{
        alert("Privilage uspesno dodato");
      }, 
      (error)=>{
        alert("Doslo je do greske");
      }
    );
  }

}
