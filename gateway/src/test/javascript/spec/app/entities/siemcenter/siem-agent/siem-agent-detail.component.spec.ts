/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GatewayTestModule } from '../../../../test.module';
import { SiemAgentDetailComponent } from 'app/entities/siemcenter/siem-agent/siem-agent-detail.component';
import { SiemAgent } from 'app/shared/model/siemcenter/siem-agent.model';

describe('Component Tests', () => {
    describe('SiemAgent Management Detail Component', () => {
        let comp: SiemAgentDetailComponent;
        let fixture: ComponentFixture<SiemAgentDetailComponent>;
        const route = ({ data: of({ siemAgent: new SiemAgent(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [SiemAgentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SiemAgentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SiemAgentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.siemAgent).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
