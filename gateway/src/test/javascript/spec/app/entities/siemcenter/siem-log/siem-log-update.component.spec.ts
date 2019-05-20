/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SiemLogUpdateComponent } from 'app/entities/siemcenter/siem-log/siem-log-update.component';
import { SiemLogService } from 'app/entities/siemcenter/siem-log/siem-log.service';
import { SiemLog } from 'app/shared/model/siemcenter/siem-log.model';

describe('Component Tests', () => {
    describe('SiemLog Management Update Component', () => {
        let comp: SiemLogUpdateComponent;
        let fixture: ComponentFixture<SiemLogUpdateComponent>;
        let service: SiemLogService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SiemLogUpdateComponent]
            })
                .overrideTemplate(SiemLogUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SiemLogUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SiemLogService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new SiemLog(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.siemLog = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new SiemLog();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.siemLog = entity;
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
