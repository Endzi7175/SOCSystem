import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from 'app/shared';
import {
    AlarmDefinitionComponent,
    AlarmDefinitionDetailComponent,
    AlarmDefinitionUpdateComponent,
    AlarmDefinitionDeletePopupComponent,
    AlarmDefinitionDeleteDialogComponent,
    alarmDefinitionRoute,
    alarmDefinitionPopupRoute
} from './';

const ENTITY_STATES = [...alarmDefinitionRoute, ...alarmDefinitionPopupRoute];

@NgModule({
    imports: [GatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AlarmDefinitionComponent,
        AlarmDefinitionDetailComponent,
        AlarmDefinitionUpdateComponent,
        AlarmDefinitionDeleteDialogComponent,
        AlarmDefinitionDeletePopupComponent
    ],
    entryComponents: [
        AlarmDefinitionComponent,
        AlarmDefinitionUpdateComponent,
        AlarmDefinitionDeleteDialogComponent,
        AlarmDefinitionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiemcenterAlarmDefinitionModule {}
