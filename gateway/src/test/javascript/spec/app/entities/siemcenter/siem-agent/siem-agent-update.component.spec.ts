/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SiemAgentUpdateComponent } from 'app/entities/siemcenter/siem-agent/siem-agent-update.component';
import { SiemAgentService } from 'app/entities/siemcenter/siem-agent/siem-agent.service';
import { SiemAgent } from 'app/shared/model/siemcenter/siem-agent.model';

describe('Component Tests', () => {
    describe('SiemAgent Management Update Component', () => {
        let comp: SiemAgentUpdateComponent;
        let fixture: ComponentFixture<SiemAgentUpdateComponent>;
        let service: SiemAgentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SiemAgentUpdateComponent]
            })
                .overrideTemplate(SiemAgentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SiemAgentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SiemAgentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SiemAgent(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.siemAgent = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SiemAgent();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.siemAgent = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
