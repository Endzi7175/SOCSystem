import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Alarm } from 'app/shared/model/siemcenter/alarm.model';
import { AlarmService } from './alarm.service';
import { AlarmComponent } from './alarm.component';
import { AlarmDetailComponent } from './alarm-detail.component';
import { AlarmUpdateComponent } from './alarm-update.component';
import { AlarmDeletePopupComponent } from './alarm-delete-dialog.component';
import { IAlarm } from 'app/shared/model/siemcenter/alarm.model';

@Injectable({ providedIn: 'root' })
export class AlarmResolve implements Resolve<IAlarm> {
    constructor(private service: AlarmService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAlarm> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Alarm>) => response.ok),
                map((alarm: HttpResponse<Alarm>) => alarm.body)
            );
        }
        return of(new Alarm());
    }
}

export const alarmRoute: Routes = [
    {
        path: '',
        component: AlarmComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Alarms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AlarmDetailComponent,
        resolve: {
            alarm: AlarmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Alarms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AlarmUpdateComponent,
        resolve: {
            alarm: AlarmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Alarms'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AlarmUpdateComponent,
        resolve: {
            alarm: AlarmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Alarms'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const alarmPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AlarmDeletePopupComponent,
        resolve: {
            alarm: AlarmResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Alarms'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
