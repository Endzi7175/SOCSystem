/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { AlarmDetailComponent } from 'app/entities/siemcenter/alarm/alarm-detail.component';
import { Alarm } from 'app/shared/model/siemcenter/alarm.model';

describe('Component Tests', () => {
    describe('Alarm Management Detail Component', () => {
        let comp: AlarmDetailComponent;
        let fixture: ComponentFixture<AlarmDetailComponent>;
        const route = ({ data: of({ alarm: new Alarm(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [AlarmDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AlarmDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AlarmDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.alarm).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
