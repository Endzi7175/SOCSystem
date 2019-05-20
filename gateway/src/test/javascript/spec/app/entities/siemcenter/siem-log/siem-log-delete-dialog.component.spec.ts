/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { SiemLogDeleteDialogComponent } from 'app/entities/siemcenter/siem-log/siem-log-delete-dialog.component';
import { SiemLogService } from 'app/entities/siemcenter/siem-log/siem-log.service';

describe('Component Tests', () => {
    describe('SiemLog Management Delete Component', () => {
        let comp: SiemLogDeleteDialogComponent;
        let fixture: ComponentFixture<SiemLogDeleteDialogComponent>;
        let service: SiemLogService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SiemLogDeleteDialogComponent]
            })
                .overrideTemplate(SiemLogDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SiemLogDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SiemLogService);
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
