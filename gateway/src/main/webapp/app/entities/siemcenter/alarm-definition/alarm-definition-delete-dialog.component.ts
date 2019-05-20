import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlarmDefinition } from 'app/shared/model/siemcenter/alarm-definition.model';
import { AlarmDefinitionService } from './alarm-definition.service';

@Component({
    selector: 'jhi-alarm-definition-delete-dialog',
    templateUrl: './alarm-definition-delete-dialog.component.html'
})
export class AlarmDefinitionDeleteDialogComponent {
    alarmDefinition: IAlarmDefinition;

    constructor(
        protected alarmDefinitionService: AlarmDefinitionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.alarmDefinitionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'alarmDefinitionListModification',
                content: 'Deleted an alarmDefinition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-alarm-definition-delete-popup',
    template: ''
})
export class AlarmDefinitionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ alarmDefinition }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AlarmDefinitionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.alarmDefinition = alarmDefinition;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/alarm-definition', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/alarm-definition', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
