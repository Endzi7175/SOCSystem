import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISiemLogType } from 'app/shared/model/siemcenter/siem-log-type.model';

type EntityResponseType = HttpResponse<ISiemLogType>;
type EntityArrayResponseType = HttpResponse<ISiemLogType[]>;

@Injectable({ providedIn: 'root' })
export class SiemLogTypeService {
    public resourceUrl = SERVER_API_URL + 'siemcenter/api/siem-log-types';
    public resourceSearchUrl = SERVER_API_URL + 'siemcenter/api/_search/siem-log-types';

    constructor(protected http: HttpClient) {}

    create(siemLogType: ISiemLogType): Observable<EntityResponseType> {
        return this.http.post<ISiemLogType>(this.resourceUrl, siemLogType, { observe: 'response' });
    }

    update(siemLogType: ISiemLogType): Observable<EntityResponseType> {
        return this.http.put<ISiemLogType>(this.resourceUrl, siemLogType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISiemLogType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISiemLogType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISiemLogType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
