/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { SiemLogTypeComponentsPage, SiemLogTypeDeleteDialog, SiemLogTypeUpdatePage } from './siem-log-type.page-object';

const expect = chai.expect;

describe('SiemLogType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let siemLogTypeUpdatePage: SiemLogTypeUpdatePage;
    let siemLogTypeComponentsPage: SiemLogTypeComponentsPage;
    let siemLogTypeDeleteDialog: SiemLogTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load SiemLogTypes', async () => {
        await navBarPage.goToEntity('siem-log-type');
        siemLogTypeComponentsPage = new SiemLogTypeComponentsPage();
        await browser.wait(ec.visibilityOf(siemLogTypeComponentsPage.title), 5000);
        expect(await siemLogTypeComponentsPage.getTitle()).to.eq('Siem Log Types');
    });

    it('should load create SiemLogType page', async () => {
        await siemLogTypeComponentsPage.clickOnCreateButton();
        siemLogTypeUpdatePage = new SiemLogTypeUpdatePage();
        expect(await siemLogTypeUpdatePage.getPageTitle()).to.eq('Create or edit a Siem Log Type');
        await siemLogTypeUpdatePage.cancel();
    });

    it('should create and save SiemLogTypes', async () => {
        const nbButtonsBeforeCreate = await siemLogTypeComponentsPage.countDeleteButtons();

        await siemLogTypeComponentsPage.clickOnCreateButton();
        await promise.all([
            siemLogTypeUpdatePage.setTimestampRegexInput('timestampRegex'),
            siemLogTypeUpdatePage.setSeverityRegexInput('severityRegex'),
            siemLogTypeUpdatePage.setContextRegexInput('contextRegex'),
            siemLogTypeUpdatePage.setMessageRegexInput('messageRegex'),
            siemLogTypeUpdatePage.setNameInput('name'),
            siemLogTypeUpdatePage.setDateTimeFormatInput('dateTimeFormat'),
            siemLogTypeUpdatePage.operatingSystemSelectLastOption()
        ]);
        expect(await siemLogTypeUpdatePage.getTimestampRegexInput()).to.eq('timestampRegex');
        expect(await siemLogTypeUpdatePage.getSeverityRegexInput()).to.eq('severityRegex');
        expect(await siemLogTypeUpdatePage.getContextRegexInput()).to.eq('contextRegex');
        expect(await siemLogTypeUpdatePage.getMessageRegexInput()).to.eq('messageRegex');
        expect(await siemLogTypeUpdatePage.getNameInput()).to.eq('name');
        expect(await siemLogTypeUpdatePage.getDateTimeFormatInput()).to.eq('dateTimeFormat');
        await siemLogTypeUpdatePage.save();
        expect(await siemLogTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await siemLogTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last SiemLogType', async () => {
        const nbButtonsBeforeDelete = await siemLogTypeComponentsPage.countDeleteButtons();
        await siemLogTypeComponentsPage.clickOnLastDeleteButton();

        siemLogTypeDeleteDialog = new SiemLogTypeDeleteDialog();
        expect(await siemLogTypeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Siem Log Type?');
        await siemLogTypeDeleteDialog.clickOnConfirmButton();

        expect(await siemLogTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
