/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SiemLogTypeDetailComponent } from 'app/entities/siemcenter/siem-log-type/siem-log-type-detail.component';
import { SiemLogType } from 'app/shared/model/siemcenter/siem-log-type.model';

describe('Component Tests', () => {
    describe('SiemLogType Management Detail Component', () => {
        let comp: SiemLogTypeDetailComponent;
        let fixture: ComponentFixture<SiemLogTypeDetailComponent>;
        const route = ({ data: of({ siemLogType: new SiemLogType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SiemLogTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SiemLogTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SiemLogTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.siemLogType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
