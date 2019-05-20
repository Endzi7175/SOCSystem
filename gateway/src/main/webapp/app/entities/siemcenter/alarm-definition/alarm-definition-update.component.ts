import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAlarmDefinition } from 'app/shared/model/siemcenter/alarm-definition.model';
import { AlarmDefinitionService } from './alarm-definition.service';
import { ISiemLogType } from 'app/shared/model/siemcenter/siem-log-type.model';
import { SiemLogTypeService } from 'app/entities/siemcenter/siem-log-type';

@Component({
    selector: 'jhi-alarm-definition-update',
    templateUrl: './alarm-definition-update.component.html'
})
export class AlarmDefinitionUpdateComponent implements OnInit {
    alarmDefinition: IAlarmDefinition;
    isSaving: boolean;

    siemlogtypes: ISiemLogType[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected alarmDefinitionService: AlarmDefinitionService,
        protected siemLogTypeService: SiemLogTypeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ alarmDefinition }) => {
            this.alarmDefinition = alarmDefinition;
        });
        this.siemLogTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISiemLogType[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISiemLogType[]>) => response.body)
            )
            .subscribe((res: ISiemLogType[]) => (this.siemlogtypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.alarmDefinition.id !== undefined) {
            this.subscribeToSaveResponse(this.alarmDefinitionService.update(this.alarmDefinition));
        } else {
            this.subscribeToSaveResponse(this.alarmDefinitionService.create(this.alarmDefinition));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAlarmDefinition>>) {
        result.subscribe((res: HttpResponse<IAlarmDefinition>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
