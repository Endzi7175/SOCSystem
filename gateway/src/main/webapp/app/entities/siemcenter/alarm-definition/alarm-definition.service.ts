import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAlarmDefinition } from 'app/shared/model/siemcenter/alarm-definition.model';

type EntityResponseType = HttpResponse<IAlarmDefinition>;
type EntityArrayResponseType = HttpResponse<IAlarmDefinition[]>;

@Injectable({ providedIn: 'root' })
export class AlarmDefinitionService {
    public resourceUrl = SERVER_API_URL + 'siemcenter/api/alarm-definitions';
    public resourceSearchUrl = SERVER_API_URL + 'siemcenter/api/_search/alarm-definitions';

    constructor(protected http: HttpClient) {}

    create(alarmDefinition: IAlarmDefinition): Observable<EntityResponseType> {
        return this.http.post<IAlarmDefinition>(this.resourceUrl, alarmDefinition, { observe: 'response' });
    }

    update(alarmDefinition: IAlarmDefinition): Observable<EntityResponseType> {
        return this.http.put<IAlarmDefinition>(this.resourceUrl, alarmDefinition, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAlarmDefinition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAlarmDefinition[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAlarmDefinition[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
