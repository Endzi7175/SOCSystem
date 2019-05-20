import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISiemLog } from 'app/shared/model/siemcenter/siem-log.model';

type EntityResponseType = HttpResponse<ISiemLog>;
type EntityArrayResponseType = HttpResponse<ISiemLog[]>;

@Injectable({ providedIn: 'root' })
export class SiemLogService {
    public resourceUrl = SERVER_API_URL + 'siemcenter/api/siem-logs';
    public resourceSearchUrl = SERVER_API_URL + 'siemcenter/api/_search/siem-logs';

    constructor(protected http: HttpClient) {}

    create(siemLog: ISiemLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(siemLog);
        return this.http
            .post<ISiemLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(siemLog: ISiemLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(siemLog);
        return this.http
            .put<ISiemLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISiemLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISiemLog[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISiemLog[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(siemLog: ISiemLog): ISiemLog {
        const copy: ISiemLog = Object.assign({}, siemLog, {
            timestamp: siemLog.timestamp != null && siemLog.timestamp.isValid() ? siemLog.timestamp.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.timestamp = res.body.timestamp != null ? moment(res.body.timestamp) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((siemLog: ISiemLog) => {
                siemLog.timestamp = siemLog.timestamp != null ? moment(siemLog.timestamp) : null;
            });
        }
        return res;
    }
}
