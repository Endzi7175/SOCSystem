import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AlarmDefinition } from 'app/shared/model/siemcenter/alarm-definition.model';
import { AlarmDefinitionService } from './alarm-definition.service';
import { AlarmDefinitionComponent } from './alarm-definition.component';
import { AlarmDefinitionDetailComponent } from './alarm-definition-detail.component';
import { AlarmDefinitionUpdateComponent } from './alarm-definition-update.component';
import { AlarmDefinitionDeletePopupComponent } from './alarm-definition-delete-dialog.component';
import { IAlarmDefinition } from 'app/shared/model/siemcenter/alarm-definition.model';

@Injectable({ providedIn: 'root' })
export class AlarmDefinitionResolve implements Resolve<IAlarmDefinition> {
    constructor(private service: AlarmDefinitionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAlarmDefinition> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AlarmDefinition>) => response.ok),
                map((alarmDefinition: HttpResponse<AlarmDefinition>) => alarmDefinition.body)
            );
        }
        return of(new AlarmDefinition());
    }
}

export const alarmDefinitionRoute: Routes = [
    {
        path: '',
        component: AlarmDefinitionComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'AlarmDefinitions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AlarmDefinitionDetailComponent,
        resolve: {
            alarmDefinition: AlarmDefinitionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AlarmDefinitions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AlarmDefinitionUpdateComponent,
        resolve: {
            alarmDefinition: AlarmDefinitionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AlarmDefinitions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AlarmDefinitionUpdateComponent,
        resolve: {
            alarmDefinition: AlarmDefinitionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AlarmDefinitions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const alarmDefinitionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AlarmDefinitionDeletePopupComponent,
        resolve: {
            alarmDefinition: AlarmDefinitionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AlarmDefinitions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
