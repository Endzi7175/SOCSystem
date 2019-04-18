import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SiemAgent } from 'app/shared/model/siemcenter/siem-agent.model';
import { SiemAgentService } from './siem-agent.service';
import { SiemAgentComponent } from './siem-agent.component';
import { SiemAgentDetailComponent } from './siem-agent-detail.component';
import { SiemAgentUpdateComponent } from './siem-agent-update.component';
import { SiemAgentDeletePopupComponent } from './siem-agent-delete-dialog.component';
import { ISiemAgent } from 'app/shared/model/siemcenter/siem-agent.model';

@Injectable({ providedIn: 'root' })
export class SiemAgentResolve implements Resolve<ISiemAgent> {
    constructor(private service: SiemAgentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISiemAgent> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<SiemAgent>) => response.ok),
                map((siemAgent: HttpResponse<SiemAgent>) => siemAgent.body)
            );
        }
        return of(new SiemAgent());
    }
}

export const siemAgentRoute: Routes = [
    {
        path: '',
        component: SiemAgentComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'SiemAgents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SiemAgentDetailComponent,
        resolve: {
            siemAgent: SiemAgentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiemAgents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SiemAgentUpdateComponent,
        resolve: {
            siemAgent: SiemAgentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiemAgents'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SiemAgentUpdateComponent,
        resolve: {
            siemAgent: SiemAgentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiemAgents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const siemAgentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SiemAgentDeletePopupComponent,
        resolve: {
            siemAgent: SiemAgentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiemAgents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
