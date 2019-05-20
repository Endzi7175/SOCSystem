import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISiemLogType } from 'app/shared/model/siemcenter/siem-log-type.model';

@Component({
    selector: 'jhi-siem-log-type-detail',
    templateUrl: './siem-log-type-detail.component.html'
})
export class SiemLogTypeDetailComponent implements OnInit {
    siemLogType: ISiemLogType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ siemLogType }) => {
            this.siemLogType = siemLogType;
        });
    }

    previousState() {
        window.history.back();
    }
}
