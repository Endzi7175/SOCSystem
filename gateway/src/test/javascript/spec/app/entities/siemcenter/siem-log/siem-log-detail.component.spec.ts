/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SiemLogDetailComponent } from 'app/entities/siemcenter/siem-log/siem-log-detail.component';
import { SiemLog } from 'app/shared/model/siemcenter/siem-log.model';

describe('Component Tests', () => {
    describe('SiemLog Management Detail Component', () => {
        let comp: SiemLogDetailComponent;
        let fixture: ComponentFixture<SiemLogDetailComponent>;
        const route = ({ data: of({ siemLog: new SiemLog(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SiemLogDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SiemLogDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SiemLogDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.siemLog).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
