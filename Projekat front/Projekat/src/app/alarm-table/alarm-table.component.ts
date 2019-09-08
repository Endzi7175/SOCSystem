import { Component, OnInit, ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-alarm-table',
  templateUrl: './alarm-table.component.html',
  styleUrls: ['./alarm-table.component.css']
})
export class AlarmTableComponent implements OnInit {
  displayedColumns: string[] = ['type','id', 'userId', 'machineId', 'ip', 'message', 'date'];
  dataSource = new MatTableDataSource<Alarm>([]);
  stompClient : Stomp;
  private serverUrl = 'http://localhost:8090'
  constructor(private http : HttpClient){};

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.getHtttp();
    this.stompClient = Stomp.over(new SockJS(this.serverUrl+"/socket"));
    let that = this;
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe("/alarm", (frame) => {
        let alarm : Alarm = JSON.parse(frame.body);
        that.dataSource.data.push(alarm);
        that.dataSource.data = [...that.dataSource.data]
      });
    });
  }
 
  private getHtttp() {
    let that = this;
    this.http.get<any>(this.serverUrl + "/api/alarm").subscribe(
      (response)=>{
        that.dataSource.data = response;
        that.dataSource.data = [...that.dataSource.data]
      }
    );
  }
}
export interface Alarm {
  type: number;
  id: number;
  userId: string;
  machineId: string;
  ip: string;
  message: string;
  dateTriggered: any;
}


