import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISiemAgent } from 'app/shared/model/siemcenter/siem-agent.model';

@Component({
    selector: 'jhi-siem-agent-detail',
    templateUrl: './siem-agent-detail.component.html'
})
export class SiemAgentDetailComponent implements OnInit {
    siemAgent: ISiemAgent;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ siemAgent }) => {
            this.siemAgent = siemAgent;
        });
    }

    previousState() {
        window.history.back();
    }
}
