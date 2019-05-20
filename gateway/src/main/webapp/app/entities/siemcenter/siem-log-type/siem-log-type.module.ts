import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    SiemLogTypeComponent,
    SiemLogTypeDetailComponent,
    SiemLogTypeUpdateComponent,
    SiemLogTypeDeletePopupComponent,
    SiemLogTypeDeleteDialogComponent,
    siemLogTypeRoute,
    siemLogTypePopupRoute
} from './';

const ENTITY_STATES = [...siemLogTypeRoute, ...siemLogTypePopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SiemLogTypeComponent,
        SiemLogTypeDetailComponent,
        SiemLogTypeUpdateComponent,
        SiemLogTypeDeleteDialogComponent,
        SiemLogTypeDeletePopupComponent
    ],
    entryComponents: [SiemLogTypeComponent, SiemLogTypeUpdateComponent, SiemLogTypeDeleteDialogComponent, SiemLogTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiemcenterSiemLogTypeModule {}
