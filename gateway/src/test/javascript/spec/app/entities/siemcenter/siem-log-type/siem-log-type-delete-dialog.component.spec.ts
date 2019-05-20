/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../../test.module';
import { SiemLogTypeDeleteDialogComponent } from 'app/entities/siemcenter/siem-log-type/siem-log-type-delete-dialog.component';
import { SiemLogTypeService } from 'app/entities/siemcenter/siem-log-type/siem-log-type.service';

describe('Component Tests', () => {
    describe('SiemLogType Management Delete Component', () => {
        let comp: SiemLogTypeDeleteDialogComponent;
        let fixture: ComponentFixture<SiemLogTypeDeleteDialogComponent>;
        let service: SiemLogTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SiemLogTypeDeleteDialogComponent]
            })
                .overrideTemplate(SiemLogTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SiemLogTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SiemLogTypeService);
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
