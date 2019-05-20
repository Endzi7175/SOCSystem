/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { AlarmDefinitionDeleteDialogComponent } from 'app/entities/siemcenter/alarm-definition/alarm-definition-delete-dialog.component';
import { AlarmDefinitionService } from 'app/entities/siemcenter/alarm-definition/alarm-definition.service';

describe('Component Tests', () => {
    describe('AlarmDefinition Management Delete Component', () => {
        let comp: AlarmDefinitionDeleteDialogComponent;
        let fixture: ComponentFixture<AlarmDefinitionDeleteDialogComponent>;
        let service: AlarmDefinitionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [AlarmDefinitionDeleteDialogComponent]
            })
                .overrideTemplate(AlarmDefinitionDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AlarmDefinitionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AlarmDefinitionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
