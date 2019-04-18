import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    SiemAgentComponent,
    SiemAgentDetailComponent,
    SiemAgentUpdateComponent,
    SiemAgentDeletePopupComponent,
    SiemAgentDeleteDialogComponent,
    siemAgentRoute,
    siemAgentPopupRoute
} from './';

const ENTITY_STATES = [...siemAgentRoute, ...siemAgentPopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SiemAgentComponent,
        SiemAgentDetailComponent,
        SiemAgentUpdateComponent,
        SiemAgentDeleteDialogComponent,
        SiemAgentDeletePopupComponent
    ],
    entryComponents: [SiemAgentComponent, SiemAgentUpdateComponent, SiemAgentDeleteDialogComponent, SiemAgentDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiemcenterSiemAgentModule {}
