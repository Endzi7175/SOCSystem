import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SiemLog } from 'app/shared/model/siemcenter/siem-log.model';
import { SiemLogService } from './siem-log.service';
import { SiemLogComponent } from './siem-log.component';
import { SiemLogDetailComponent } from './siem-log-detail.component';
import { SiemLogUpdateComponent } from './siem-log-update.component';
import { SiemLogDeletePopupComponent } from './siem-log-delete-dialog.component';
import { ISiemLog } from 'app/shared/model/siemcenter/siem-log.model';

@Injectable({ providedIn: 'root' })
export class SiemLogResolve implements Resolve<ISiemLog> {
    constructor(private service: SiemLogService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISiemLog> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SiemLog>) => response.ok),
                map((siemLog: HttpResponse<SiemLog>) => siemLog.body)
            );
        }
        return of(new SiemLog());
    }
}

export const siemLogRoute: Routes = [
    {
        path: '',
        component: SiemLogComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'SiemLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SiemLogDetailComponent,
        resolve: {
            siemLog: SiemLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiemLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SiemLogUpdateComponent,
        resolve: {
            siemLog: SiemLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiemLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SiemLogUpdateComponent,
        resolve: {
            siemLog: SiemLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiemLogs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const siemLogPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SiemLogDeletePopupComponent,
        resolve: {
            siemLog: SiemLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiemLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
