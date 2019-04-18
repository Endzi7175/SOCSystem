import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISiemAgent } from 'app/shared/model/siemcenter/siem-agent.model';
import { SiemAgentService } from './siem-agent.service';

@Component({
    selector: 'jhi-siem-agent-delete-dialog',
    templateUrl: './siem-agent-delete-dialog.component.html'
})
export class SiemAgentDeleteDialogComponent {
    siemAgent: ISiemAgent;

    constructor(
        protected siemAgentService: SiemAgentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.siemAgentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'siemAgentListModification',
                content: 'Deleted an siemAgent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-siem-agent-delete-popup',
    template: ''
})
export class SiemAgentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ siemAgent }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SiemAgentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.siemAgent = siemAgent;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/siem-agent', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/siem-agent', { outlets: { popup: null } }]);
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
