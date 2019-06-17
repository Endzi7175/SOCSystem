import { Component, OnInit, ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-alarm-table',
  templateUrl: './alarm-table.component.html',
  styleUrls: ['./alarm-table.component.css']
})
export class AlarmTableComponent implements OnInit {
  displayedColumns: string[] = ['type','id', 'userId', 'machineId', 'ip', 'message', 'date'];
  dataSource = new MatTableDataSource<Alarm>(ELEMENT_DATA);
  stompClient : Stomp;
  private serverUrl = 'http://localhost:8090/socket'

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.stompClient = Stomp.over(new SockJS(this.serverUrl));
    let that = this;
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe("/alarm", (frame) => {
        let alarm : Alarm = JSON.parse(frame.body);
        that.dataSource.data.push(alarm);
        that.dataSource.data = [...that.dataSource.data]
      });
    });
  }
}
export interface Alarm {
  type: number;
  id: number;
  userId: string;
  machineId: string;
  ip: string;
  message: string;
  date: any;
}

const ELEMENT_DATA: Alarm[] = [

];

