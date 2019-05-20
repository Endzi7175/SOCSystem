import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISiemLog } from 'app/shared/model/siemcenter/siem-log.model';
import { SiemLogService } from './siem-log.service';
import { ISiemLogType } from 'app/shared/model/siemcenter/siem-log-type.model';
import { SiemLogTypeService } from 'app/entities/siemcenter/siem-log-type';
import { ISiemAgent } from 'app/shared/model/siemcenter/siem-agent.model';
import { SiemAgentService } from 'app/entities/siemcenter/siem-agent';

@Component({
    selector: 'jhi-siem-log-update',
    templateUrl: './siem-log-update.component.html'
})
export class SiemLogUpdateComponent implements OnInit {
    siemLog: ISiemLog;
    isSaving: boolean;

    siemlogtypes: ISiemLogType[];

    siemagents: ISiemAgent[];
    timestamp: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected siemLogService: SiemLogService,
        protected siemLogTypeService: SiemLogTypeService,
        protected siemAgentService: SiemAgentService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ siemLog }) => {
            this.siemLog = siemLog;
            this.timestamp = this.siemLog.timestamp != null ? this.siemLog.timestamp.format(DATE_TIME_FORMAT) : null;
        });
        this.siemLogTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISiemLogType[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISiemLogType[]>) => response.body)
            )
            .subscribe((res: ISiemLogType[]) => (this.siemlogtypes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.siemAgentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISiemAgent[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISiemAgent[]>) => response.body)
            )
            .subscribe((res: ISiemAgent[]) => (this.siemagents = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.siemLog.timestamp = this.timestamp != null ? moment(this.timestamp, DATE_TIME_FORMAT) : null;
        if (this.siemLog.id !== undefined) {
            this.subscribeToSaveResponse(this.siemLogService.update(this.siemLog));
        } else {
            this.subscribeToSaveResponse(this.siemLogService.create(this.siemLog));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISiemLog>>) {
        result.subscribe((res: HttpResponse<ISiemLog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSiemLogTypeById(index: number, item: ISiemLogType) {
        return item.id;
    }

    trackSiemAgentById(index: number, item: ISiemAgent) {
        return item.id;
    }
}
