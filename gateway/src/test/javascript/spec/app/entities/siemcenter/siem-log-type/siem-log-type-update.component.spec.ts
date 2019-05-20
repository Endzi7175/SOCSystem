/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SiemLogTypeUpdateComponent } from 'app/entities/siemcenter/siem-log-type/siem-log-type-update.component';
import { SiemLogTypeService } from 'app/entities/siemcenter/siem-log-type/siem-log-type.service';
import { SiemLogType } from 'app/shared/model/siemcenter/siem-log-type.model';

describe('Component Tests', () => {
    describe('SiemLogType Management Update Component', () => {
        let comp: SiemLogTypeUpdateComponent;
        let fixture: ComponentFixture<SiemLogTypeUpdateComponent>;
        let service: SiemLogTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SiemLogTypeUpdateComponent]
            })
                .overrideTemplate(SiemLogTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SiemLogTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SiemLogTypeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SiemLogType(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.siemLogType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SiemLogType();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.siemLogType = entity;
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
