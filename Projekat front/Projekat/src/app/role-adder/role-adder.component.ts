import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-role-adder',
  templateUrl: './role-adder.component.html',
  styleUrls: ['./role-adder.component.css']
})
export class RoleAdderComponent implements OnInit {

  constructor(private http : HttpClient) { }
  roleName : string = "";
  private serverUrl = 'http://localhost:8090'
  ngOnInit() {
  }

  submit(){
      let that = this;
      this.http.post<any>(this.serverUrl + "/api/role", this.roleName).subscribe(
        (response)=>{
          alert("Rola uspesno dodato");
        }, 
        (error)=>{
          alert("Doslo je do greske");
        }
      );
    
  }

}


