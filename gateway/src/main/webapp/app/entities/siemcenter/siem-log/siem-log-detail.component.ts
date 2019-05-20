import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISiemLog } from 'app/shared/model/siemcenter/siem-log.model';

@Component({
    selector: 'jhi-siem-log-detail',
    templateUrl: './siem-log-detail.component.html'
})
export class SiemLogDetailComponent implements OnInit {
    siemLog: ISiemLog;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ siemLog }) => {
            this.siemLog = siemLog;
        });
    }

    previousState() {
        window.history.back();
    }
}
