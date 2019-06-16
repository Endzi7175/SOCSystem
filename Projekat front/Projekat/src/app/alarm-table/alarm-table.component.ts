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
  displayedColumns: string[] = ['type','id', 'userId', 'machineId', 'ip', 'message'];
  dataSource = new MatTableDataSource<Alarm>(ELEMENT_DATA);
  stompClient : Stomp;
  private serverUrl = 'http://localhost:8080/socket'

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.stompClient = Stomp.over(new SockJS(this.serverUrl));
    let that = this;
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe("/alarm", (message) => {
        if(message.body) {
          
          console.log(message.body);
        }
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
}

const ELEMENT_DATA: Alarm[] = [
  {id: 1, type: 1, userId: "pera", machineId: 'PC1', ip:"192.168.1.1", message:"Porukaaa" },
  {id: 2, type: 0, userId: "zika", machineId: 'PC12', ip:"192.168.1.1", message:"Porukaaa" },
  {id: 3, type: 3, userId: "mika", machineId: 'PC12', ip:"192.168.1.1", message:"Porukaaa" },
  {id: 4, type: 3, userId: "pera", machineId: 'PC1', ip:"192.168.1.1", message:"Porukaaa" },
  {id: 5, type: 0, userId: "mika", machineId: 'PC3', ip:"192.168.1.1", message:"Porukaaa" },
  {id: 6, type: 1, userId: "mika", machineId: 'PC3', ip:"192.168.1.1", message:"Porukaaa" }
];

