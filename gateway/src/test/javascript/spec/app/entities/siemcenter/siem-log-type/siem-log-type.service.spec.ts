/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { SiemLogTypeService } from 'app/entities/siemcenter/siem-log-type/siem-log-type.service';
import { ISiemLogType, SiemLogType, OperatingSystem } from 'app/shared/model/siemcenter/siem-log-type.model';

describe('Service Tests', () => {
    describe('SiemLogType Service', () => {
        let injector: TestBed;
        let service: SiemLogTypeService;
        let httpMock: HttpTestingController;
        let elemDefault: ISiemLogType;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(SiemLogTypeService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new SiemLogType(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', OperatingSystem.WINDOWS);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a SiemLogType', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new SiemLogType(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a SiemLogType', async () => {
                const returnedFromService = Object.assign(
                    {
                        timestampRegex: 'BBBBBB',
                        severityRegex: 'BBBBBB',
                        contextRegex: 'BBBBBB',
                        messageRegex: 'BBBBBB',
                        name: 'BBBBBB',
                        dateTimeFormat: 'BBBBBB',
                        operatingSystem: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of SiemLogType', async () => {
                const returnedFromService = Object.assign(
                    {
                        timestampRegex: 'BBBBBB',
                        severityRegex: 'BBBBBB',
                        contextRegex: 'BBBBBB',
                        messageRegex: 'BBBBBB',
                        name: 'BBBBBB',
                        dateTimeFormat: 'BBBBBB',
                        operatingSystem: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
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

            it('should delete a SiemLogType', async () => {
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
