/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { SiemAgentComponentsPage, SiemAgentDeleteDialog, SiemAgentUpdatePage } from './siem-agent.page-object';

const expect = chai.expect;

describe('SiemAgent e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let siemAgentUpdatePage: SiemAgentUpdatePage;
    let siemAgentComponentsPage: SiemAgentComponentsPage;
    let siemAgentDeleteDialog: SiemAgentDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load SiemAgents', async () => {
        await navBarPage.goToEntity('siem-agent');
        siemAgentComponentsPage = new SiemAgentComponentsPage();
        await browser.wait(ec.visibilityOf(siemAgentComponentsPage.title), 5000);
        expect(await siemAgentComponentsPage.getTitle()).to.eq('Siem Agents');
    });

    it('should load create SiemAgent page', async () => {
        await siemAgentComponentsPage.clickOnCreateButton();
        siemAgentUpdatePage = new SiemAgentUpdatePage();
        expect(await siemAgentUpdatePage.getPageTitle()).to.eq('Create or edit a Siem Agent');
        await siemAgentUpdatePage.cancel();
    });

    it('should create and save SiemAgents', async () => {
        const nbButtonsBeforeCreate = await siemAgentComponentsPage.countDeleteButtons();

        await siemAgentComponentsPage.clickOnCreateButton();
        await promise.all([
            siemAgentUpdatePage.setCnInput('cn'),
            siemAgentUpdatePage.setPublicKeyInput('publicKey'),
            siemAgentUpdatePage.setIpAddressInput('ipAddress')
        ]);
        expect(await siemAgentUpdatePage.getCnInput()).to.eq('cn');
        expect(await siemAgentUpdatePage.getPublicKeyInput()).to.eq('publicKey');
        expect(await siemAgentUpdatePage.getIpAddressInput()).to.eq('ipAddress');
        const selectedActive = siemAgentUpdatePage.getActiveInput();
        if (await selectedActive.isSelected()) {
            await siemAgentUpdatePage.getActiveInput().click();
            expect(await siemAgentUpdatePage.getActiveInput().isSelected()).to.be.false;
        } else {
            await siemAgentUpdatePage.getActiveInput().click();
            expect(await siemAgentUpdatePage.getActiveInput().isSelected()).to.be.true;
        }
        await siemAgentUpdatePage.save();
        expect(await siemAgentUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await siemAgentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last SiemAgent', async () => {
        const nbButtonsBeforeDelete = await siemAgentComponentsPage.countDeleteButtons();
        await siemAgentComponentsPage.clickOnLastDeleteButton();

        siemAgentDeleteDialog = new SiemAgentDeleteDialog();
        expect(await siemAgentDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Siem Agent?');
        await siemAgentDeleteDialog.clickOnConfirmButton();

        expect(await siemAgentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
