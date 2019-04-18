import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISiemAgent } from 'app/shared/model/siemcenter/siem-agent.model';

type EntityResponseType = HttpResponse<ISiemAgent>;
type EntityArrayResponseType = HttpResponse<ISiemAgent[]>;

@Injectable({ providedIn: 'root' })
export class SiemAgentService {
    public resourceUrl = SERVER_API_URL + 'siemcenter/api/siem-agents';
    public resourceSearchUrl = SERVER_API_URL + 'siemcenter/api/_search/siem-agents';

    constructor(protected http: HttpClient) {}

    create(siemAgent: ISiemAgent): Observable<EntityResponseType> {
        return this.http.post<ISiemAgent>(this.resourceUrl, siemAgent, { observe: 'response' });
    }

    update(siemAgent: ISiemAgent): Observable<EntityResponseType> {
        return this.http.put<ISiemAgent>(this.resourceUrl, siemAgent, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISiemAgent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISiemAgent[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISiemAgent[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
