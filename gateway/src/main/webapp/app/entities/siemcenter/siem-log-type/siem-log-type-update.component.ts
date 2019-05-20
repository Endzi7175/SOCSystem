import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISiemLogType } from 'app/shared/model/siemcenter/siem-log-type.model';
import { SiemLogTypeService } from './siem-log-type.service';

@Component({
    selector: 'jhi-siem-log-type-update',
    templateUrl: './siem-log-type-update.component.html'
})
export class SiemLogTypeUpdateComponent implements OnInit {
    siemLogType: ISiemLogType;
    isSaving: boolean;

    constructor(protected siemLogTypeService: SiemLogTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ siemLogType }) => {
            this.siemLogType = siemLogType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.siemLogType.id !== undefined) {
            this.subscribeToSaveResponse(this.siemLogTypeService.update(this.siemLogType));
        } else {
            this.subscribeToSaveResponse(this.siemLogTypeService.create(this.siemLogType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISiemLogType>>) {
        result.subscribe((res: HttpResponse<ISiemLogType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
