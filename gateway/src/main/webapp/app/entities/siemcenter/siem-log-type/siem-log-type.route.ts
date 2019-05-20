import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SiemLogType } from 'app/shared/model/siemcenter/siem-log-type.model';
import { SiemLogTypeService } from './siem-log-type.service';
import { SiemLogTypeComponent } from './siem-log-type.component';
import { SiemLogTypeDetailComponent } from './siem-log-type-detail.component';
import { SiemLogTypeUpdateComponent } from './siem-log-type-update.component';
import { SiemLogTypeDeletePopupComponent } from './siem-log-type-delete-dialog.component';
import { ISiemLogType } from 'app/shared/model/siemcenter/siem-log-type.model';

@Injectable({ providedIn: 'root' })
export class SiemLogTypeResolve implements Resolve<ISiemLogType> {
    constructor(private service: SiemLogTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISiemLogType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SiemLogType>) => response.ok),
                map((siemLogType: HttpResponse<SiemLogType>) => siemLogType.body)
            );
        }
        return of(new SiemLogType());
    }
}

export const siemLogTypeRoute: Routes = [
    {
        path: '',
        component: SiemLogTypeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'SiemLogTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SiemLogTypeDetailComponent,
        resolve: {
            siemLogType: SiemLogTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiemLogTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SiemLogTypeUpdateComponent,
        resolve: {
            siemLogType: SiemLogTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiemLogTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SiemLogTypeUpdateComponent,
        resolve: {
            siemLogType: SiemLogTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiemLogTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const siemLogTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SiemLogTypeDeletePopupComponent,
        resolve: {
            siemLogType: SiemLogTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiemLogTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
