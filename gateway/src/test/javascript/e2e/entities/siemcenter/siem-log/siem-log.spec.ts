/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { SiemLogComponentsPage, SiemLogDeleteDialog, SiemLogUpdatePage } from './siem-log.page-object';

const expect = chai.expect;

describe('SiemLog e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let siemLogUpdatePage: SiemLogUpdatePage;
    let siemLogComponentsPage: SiemLogComponentsPage;
    let siemLogDeleteDialog: SiemLogDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load SiemLogs', async () => {
        await navBarPage.goToEntity('siem-log');
        siemLogComponentsPage = new SiemLogComponentsPage();
        await browser.wait(ec.visibilityOf(siemLogComponentsPage.title), 5000);
        expect(await siemLogComponentsPage.getTitle()).to.eq('Siem Logs');
    });

    it('should load create SiemLog page', async () => {
        await siemLogComponentsPage.clickOnCreateButton();
        siemLogUpdatePage = new SiemLogUpdatePage();
        expect(await siemLogUpdatePage.getPageTitle()).to.eq('Create or edit a Siem Log');
        await siemLogUpdatePage.cancel();
    });

    it('should create and save SiemLogs', async () => {
        const nbButtonsBeforeCreate = await siemLogComponentsPage.countDeleteButtons();

        await siemLogComponentsPage.clickOnCreateButton();
        await promise.all([
            siemLogUpdatePage.operatingSystemSelectLastOption(),
            siemLogUpdatePage.setOperatingSystemVersionInput('operatingSystemVersion'),
            siemLogUpdatePage.setInternalIpInput('internalIp'),
            siemLogUpdatePage.setExternalIpInput('externalIp'),
            siemLogUpdatePage.setHostNameInput('hostName'),
            siemLogUpdatePage.setContextInput('context'),
            siemLogUpdatePage.setMessageInput('message'),
            siemLogUpdatePage.setTimestampInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            siemLogUpdatePage.setDirectoryInput('directory'),
            siemLogUpdatePage.severitySelectLastOption(),
            siemLogUpdatePage.sourceSelectLastOption(),
            siemLogUpdatePage.setFilePathInput('filePath'),
            siemLogUpdatePage.setRawMessageInput('rawMessage'),
            siemLogUpdatePage.siemLogTypeSelectLastOption(),
            siemLogUpdatePage.siemAgentSelectLastOption()
        ]);
        expect(await siemLogUpdatePage.getOperatingSystemVersionInput()).to.eq('operatingSystemVersion');
        expect(await siemLogUpdatePage.getInternalIpInput()).to.eq('internalIp');
        expect(await siemLogUpdatePage.getExternalIpInput()).to.eq('externalIp');
        expect(await siemLogUpdatePage.getHostNameInput()).to.eq('hostName');
        expect(await siemLogUpdatePage.getContextInput()).to.eq('context');
        expect(await siemLogUpdatePage.getMessageInput()).to.eq('message');
        expect(await siemLogUpdatePage.getTimestampInput()).to.contain('2001-01-01T02:30');
        expect(await siemLogUpdatePage.getDirectoryInput()).to.eq('directory');
        expect(await siemLogUpdatePage.getFilePathInput()).to.eq('filePath');
        expect(await siemLogUpdatePage.getRawMessageInput()).to.eq('rawMessage');
        await siemLogUpdatePage.save();
        expect(await siemLogUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await siemLogComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last SiemLog', async () => {
        const nbButtonsBeforeDelete = await siemLogComponentsPage.countDeleteButtons();
        await siemLogComponentsPage.clickOnLastDeleteButton();

        siemLogDeleteDialog = new SiemLogDeleteDialog();
        expect(await siemLogDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Siem Log?');
        await siemLogDeleteDialog.clickOnConfirmButton();

        expect(await siemLogComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
