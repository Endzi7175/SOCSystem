/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { AlarmDefinitionUpdateComponent } from 'app/entities/siemcenter/alarm-definition/alarm-definition-update.component';
import { AlarmDefinitionService } from 'app/entities/siemcenter/alarm-definition/alarm-definition.service';
import { AlarmDefinition } from 'app/shared/model/siemcenter/alarm-definition.model';

describe('Component Tests', () => {
    describe('AlarmDefinition Management Update Component', () => {
        let comp: AlarmDefinitionUpdateComponent;
        let fixture: ComponentFixture<AlarmDefinitionUpdateComponent>;
        let service: AlarmDefinitionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [AlarmDefinitionUpdateComponent]
            })
                .overrideTemplate(AlarmDefinitionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AlarmDefinitionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AlarmDefinitionService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AlarmDefinition(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.alarmDefinition = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AlarmDefinition();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.alarmDefinition = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
