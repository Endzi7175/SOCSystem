import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISiemLogType } from 'app/shared/model/siemcenter/siem-log-type.model';
import { SiemLogTypeService } from './siem-log-type.service';

@Component({
    selector: 'jhi-siem-log-type-delete-dialog',
    templateUrl: './siem-log-type-delete-dialog.component.html'
})
export class SiemLogTypeDeleteDialogComponent {
    siemLogType: ISiemLogType;

    constructor(
        protected siemLogTypeService: SiemLogTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.siemLogTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'siemLogTypeListModification',
                content: 'Deleted an siemLogType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-siem-log-type-delete-popup',
    template: ''
})
export class SiemLogTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ siemLogType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SiemLogTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.siemLogType = siemLogType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/siem-log-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/siem-log-type', { outlets: { popup: null } }]);
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
