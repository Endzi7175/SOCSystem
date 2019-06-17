import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Log} from '../search-table/search-table.component'
@Injectable({
  providedIn: 'root'
})
export class LogService {
  logUlr = "http://localhost:8090/api/logs"
  constructor(
    private http: HttpClient
  ) { }
  searchDBLog(logs){
    return this.http.post<Log[]>(this.logUlr + "/searchDB", logs)
  }
  searchSessionMemory(logs){
    return this.http.post<Log[]>(this.logUlr + "/searchMemory", logs)
  }

  //   .subscribe(
  //     addedPricelist =>{
  //       console.log(addedPricelist);
  //       this.pricelists.push(addedPricelist);
  //       this.pricelistSource.next(this.pricelists);
  //       this.notificationService.success("Uspešno ste dodali cenovnik!");
  //     },
  //     error =>
  //     {
  //       switch(error.status){
  //           case 406:{
  //             this.notificationService.warn("Neuspešno dodavanje! Početni datum mora biti manji od krajnjeg datuma.");
  //           }
  //           case 409 :{
  //             this.notificationService.warn("Neuspešno dodavanje! Datum koji ste uneli se poklapa sa datumima postojeceg cenovnika.")
  //           }
  //       }

  //     }
  //   )
  // }
  
}
