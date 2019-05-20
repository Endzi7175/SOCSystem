import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    SiemLogComponent,
    SiemLogDetailComponent,
    SiemLogUpdateComponent,
    SiemLogDeletePopupComponent,
    SiemLogDeleteDialogComponent,
    siemLogRoute,
    siemLogPopupRoute
} from './';

const ENTITY_STATES = [...siemLogRoute, ...siemLogPopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SiemLogComponent,
        SiemLogDetailComponent,
        SiemLogUpdateComponent,
        SiemLogDeleteDialogComponent,
        SiemLogDeletePopupComponent
    ],
    entryComponents: [SiemLogComponent, SiemLogUpdateComponent, SiemLogDeleteDialogComponent, SiemLogDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiemcenterSiemLogModule {}
