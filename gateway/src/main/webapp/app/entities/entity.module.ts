import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'siem-agent',
                loadChildren: './siemcenter/siem-agent/siem-agent.module#SiemcenterSiemAgentModule'
            },
            {
                path: 'alarm',
                loadChildren: './siemcenter/alarm/alarm.module#SiemcenterAlarmModule'
            },
            {
                path: 'alarm-definition',
                loadChildren: './siemcenter/alarm-definition/alarm-definition.module#SiemcenterAlarmDefinitionModule'
            },
            {
                path: 'siem-log-type',
                loadChildren: './siemcenter/siem-log-type/siem-log-type.module#SiemcenterSiemLogTypeModule'
            },
            {
                path: 'siem-log',
                loadChildren: './siemcenter/siem-log/siem-log.module#SiemcenterSiemLogModule'
            },
            {
                path: 'alarm-definition',
                loadChildren: './siemcenter/alarm-definition/alarm-definition.module#SiemcenterAlarmDefinitionModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayEntityModule {}
