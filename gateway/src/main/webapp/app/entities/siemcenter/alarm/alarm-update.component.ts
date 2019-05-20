import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAlarm } from 'app/shared/model/siemcenter/alarm.model';
import { AlarmService } from './alarm.service';
import { ISiemAgent } from 'app/shared/model/siemcenter/siem-agent.model';
import { SiemAgentService } from 'app/entities/siemcenter/siem-agent';
import { IAlarmDefinition } from 'app/shared/model/siemcenter/alarm-definition.model';
import { AlarmDefinitionService } from 'app/entities/siemcenter/alarm-definition';

@Component({
    selector: 'jhi-alarm-update',
    templateUrl: './alarm-update.component.html'
})
export class AlarmUpdateComponent implements OnInit {
    alarm: IAlarm;
    isSaving: boolean;

    siemagents: ISiemAgent[];

    alarmdefinitions: IAlarmDefinition[];
    timestamp: string;
    firstTimestamp: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected alarmService: AlarmService,
        protected siemAgentService: SiemAgentService,
        protected alarmDefinitionService: AlarmDefinitionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ alarm }) => {
            this.alarm = alarm;
            this.timestamp = this.alarm.timestamp != null ? this.alarm.timestamp.format(DATE_TIME_FORMAT) : null;
            this.firstTimestamp = this.alarm.firstTimestamp != null ? this.alarm.firstTimestamp.format(DATE_TIME_FORMAT) : null;
        });
        this.siemAgentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISiemAgent[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISiemAgent[]>) => response.body)
            )
            .subscribe((res: ISiemAgent[]) => (this.siemagents = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.alarmDefinitionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAlarmDefinition[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAlarmDefinition[]>) => response.body)
            )
            .subscribe((res: IAlarmDefinition[]) => (this.alarmdefinitions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.alarm.timestamp = this.timestamp != null ? moment(this.timestamp, DATE_TIME_FORMAT) : null;
        this.alarm.firstTimestamp = this.firstTimestamp != null ? moment(this.firstTimestamp, DATE_TIME_FORMAT) : null;
        if (this.alarm.id !== undefined) {
            this.subscribeToSaveResponse(this.alarmService.update(this.alarm));
        } else {
            this.subscribeToSaveResponse(this.alarmService.create(this.alarm));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlarm>>) {
        result.subscribe((res: HttpResponse<IAlarm>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSiemAgentById(index: number, item: ISiemAgent) {
        return item.id;
    }

    trackAlarmDefinitionById(index: number, item: IAlarmDefinition) {
        return item.id;
    }
}
