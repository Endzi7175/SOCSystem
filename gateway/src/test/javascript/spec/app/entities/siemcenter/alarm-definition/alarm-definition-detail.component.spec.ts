/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { AlarmDefinitionDetailComponent } from 'app/entities/siemcenter/alarm-definition/alarm-definition-detail.component';
import { AlarmDefinition } from 'app/shared/model/siemcenter/alarm-definition.model';

describe('Component Tests', () => {
    describe('AlarmDefinition Management Detail Component', () => {
        let comp: AlarmDefinitionDetailComponent;
        let fixture: ComponentFixture<AlarmDefinitionDetailComponent>;
        const route = ({ data: of({ alarmDefinition: new AlarmDefinition(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [AlarmDefinitionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AlarmDefinitionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AlarmDefinitionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.alarmDefinition).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
