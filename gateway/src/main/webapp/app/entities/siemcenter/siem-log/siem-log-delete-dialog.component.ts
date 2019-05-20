import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISiemLog } from 'app/shared/model/siemcenter/siem-log.model';
import { SiemLogService } from './siem-log.service';

@Component({
    selector: 'jhi-siem-log-delete-dialog',
    templateUrl: './siem-log-delete-dialog.component.html'
})
export class SiemLogDeleteDialogComponent {
    siemLog: ISiemLog;

    constructor(protected siemLogService: SiemLogService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.siemLogService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'siemLogListModification',
                content: 'Deleted an siemLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-siem-log-delete-popup',
    template: ''
})
export class SiemLogDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ siemLog }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SiemLogDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.siemLog = siemLog;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/siem-log', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/siem-log', { outlets: { popup: null } }]);
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
