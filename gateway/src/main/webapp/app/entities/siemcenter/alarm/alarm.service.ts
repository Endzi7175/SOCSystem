import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAlarm } from 'app/shared/model/siemcenter/alarm.model';

type EntityResponseType = HttpResponse<IAlarm>;
type EntityArrayResponseType = HttpResponse<IAlarm[]>;

@Injectable({ providedIn: 'root' })
export class AlarmService {
    public resourceUrl = SERVER_API_URL + 'siemcenter/api/alarms';
    public resourceSearchUrl = SERVER_API_URL + 'siemcenter/api/_search/alarms';

    constructor(protected http: HttpClient) {}

    create(alarm: IAlarm): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(alarm);
        return this.http
            .post<IAlarm>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(alarm: IAlarm): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(alarm);
        return this.http
            .put<IAlarm>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAlarm>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAlarm[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAlarm[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(alarm: IAlarm): IAlarm {
        const copy: IAlarm = Object.assign({}, alarm, {
            timestamp: alarm.timestamp != null && alarm.timestamp.isValid() ? alarm.timestamp.toJSON() : null,
            firstTimestamp: alarm.firstTimestamp != null && alarm.firstTimestamp.isValid() ? alarm.firstTimestamp.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.timestamp = res.body.timestamp != null ? moment(res.body.timestamp) : null;
            res.body.firstTimestamp = res.body.firstTimestamp != null ? moment(res.body.firstTimestamp) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((alarm: IAlarm) => {
                alarm.timestamp = alarm.timestamp != null ? moment(alarm.timestamp) : null;
                alarm.firstTimestamp = alarm.firstTimestamp != null ? moment(alarm.firstTimestamp) : null;
            });
        }
        return res;
    }
}
