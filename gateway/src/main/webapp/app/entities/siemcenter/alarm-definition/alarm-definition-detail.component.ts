import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAlarmDefinition } from 'app/shared/model/siemcenter/alarm-definition.model';

@Component({
    selector: 'jhi-alarm-definition-detail',
    templateUrl: './alarm-definition-detail.component.html'
})
export class AlarmDefinitionDetailComponent implements OnInit {
    alarmDefinition: IAlarmDefinition;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ alarmDefinition }) => {
            this.alarmDefinition = alarmDefinition;
        });
    }

    previousState() {
        window.history.back();
    }
}
