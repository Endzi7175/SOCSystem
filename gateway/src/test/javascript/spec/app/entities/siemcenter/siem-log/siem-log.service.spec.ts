/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SiemLogService } from 'app/entities/siemcenter/siem-log/siem-log.service';
import { ISiemLog, SiemLog, OperatingSystem, SiemLogSeverityEnum, SiemLogSourceEnum } from 'app/shared/model/siemcenter/siem-log.model';

describe('Service Tests', () => {
    describe('SiemLog Service', () => {
        let injector: TestBed;
        let service: SiemLogService;
        let httpMock: HttpTestingController;
        let elemDefault: ISiemLog;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(SiemLogService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new SiemLog(
                0,
                OperatingSystem.WINDOWS,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                SiemLogSeverityEnum.TRACE,
                SiemLogSourceEnum.WEBSERVER,
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        timestamp: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a SiemLog', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        timestamp: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        timestamp: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new SiemLog(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a SiemLog', async () => {
                const returnedFromService = Object.assign(
                    {
                        operatingSystem: 'BBBBBB',
                        operatingSystemVersion: 'BBBBBB',
                        internalIp: 'BBBBBB',
                        externalIp: 'BBBBBB',
                        hostName: 'BBBBBB',
                        context: 'BBBBBB',
                        message: 'BBBBBB',
                        timestamp: currentDate.format(DATE_TIME_FORMAT),
                        directory: 'BBBBBB',
                        severity: 'BBBBBB',
                        source: 'BBBBBB',
                        filePath: 'BBBBBB',
                        rawMessage: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        timestamp: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of SiemLog', async () => {
                const returnedFromService = Object.assign(
                    {
                        operatingSystem: 'BBBBBB',
                        operatingSystemVersion: 'BBBBBB',
                        internalIp: 'BBBBBB',
                        externalIp: 'BBBBBB',
                        hostName: 'BBBBBB',
                        context: 'BBBBBB',
                        message: 'BBBBBB',
                        timestamp: currentDate.format(DATE_TIME_FORMAT),
                        directory: 'BBBBBB',
                        severity: 'BBBBBB',
                        source: 'BBBBBB',
                        filePath: 'BBBBBB',
                        rawMessage: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        timestamp: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a SiemLog', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
