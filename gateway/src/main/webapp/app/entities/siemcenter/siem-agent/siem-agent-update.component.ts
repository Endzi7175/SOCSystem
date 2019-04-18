import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ISiemAgent } from 'app/shared/model/siemcenter/siem-agent.model';
import { SiemAgentService } from './siem-agent.service';

@Component({
    selector: 'jhi-siem-agent-update',
    templateUrl: './siem-agent-update.component.html'
})
export class SiemAgentUpdateComponent implements OnInit {
    siemAgent: ISiemAgent;
    isSaving: boolean;

    constructor(protected siemAgentService: SiemAgentService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ siemAgent }) => {
            this.siemAgent = siemAgent;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.siemAgent.id !== undefined) {
            this.subscribeToSaveResponse(this.siemAgentService.update(this.siemAgent));
        } else {
            this.subscribeToSaveResponse(this.siemAgentService.create(this.siemAgent));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISiemAgent>>) {
        result.subscribe((res: HttpResponse<ISiemAgent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
