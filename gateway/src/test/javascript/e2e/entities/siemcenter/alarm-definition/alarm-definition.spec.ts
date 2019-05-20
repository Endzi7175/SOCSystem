/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { AlarmDefinitionComponentsPage, AlarmDefinitionDeleteDialog, AlarmDefinitionUpdatePage } from './alarm-definition.page-object';

const expect = chai.expect;

describe('AlarmDefinition e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let alarmDefinitionUpdatePage: AlarmDefinitionUpdatePage;
    let alarmDefinitionComponentsPage: AlarmDefinitionComponentsPage;
    let alarmDefinitionDeleteDialog: AlarmDefinitionDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load AlarmDefinitions', async () => {
        await navBarPage.goToEntity('alarm-definition');
        alarmDefinitionComponentsPage = new AlarmDefinitionComponentsPage();
        await browser.wait(ec.visibilityOf(alarmDefinitionComponentsPage.title), 5000);
        expect(await alarmDefinitionComponentsPage.getTitle()).to.eq('Alarm Definitions');
    });

    it('should load create AlarmDefinition page', async () => {
        await alarmDefinitionComponentsPage.clickOnCreateButton();
        alarmDefinitionUpdatePage = new AlarmDefinitionUpdatePage();
        expect(await alarmDefinitionUpdatePage.getPageTitle()).to.eq('Create or edit a Alarm Definition');
        await alarmDefinitionUpdatePage.cancel();
    });

    it('should create and save AlarmDefinitions', async () => {
        const nbButtonsBeforeCreate = await alarmDefinitionComponentsPage.countDeleteButtons();

        await alarmDefinitionComponentsPage.clickOnCreateButton();
        await promise.all([
            alarmDefinitionUpdatePage.setNameInput('name'),
            alarmDefinitionUpdatePage.setDescriptionInput('description'),
            alarmDefinitionUpdatePage.setNumRepeatsInput('5'),
            alarmDefinitionUpdatePage.setTimeSpanInput('5'),
            alarmDefinitionUpdatePage.setKeywordInput('keyword'),
            alarmDefinitionUpdatePage.setFieldnameInput('fieldname'),
            alarmDefinitionUpdatePage.alarmRadiusSelectLastOption(),
            alarmDefinitionUpdatePage.severitySelectLastOption(),
            alarmDefinitionUpdatePage.logSourceSelectLastOption(),
            alarmDefinitionUpdatePage.operatingSystemSelectLastOption(),
            alarmDefinitionUpdatePage.siemLogTypeSelectLastOption()
        ]);
        expect(await alarmDefinitionUpdatePage.getNameInput()).to.eq('name');
        expect(await alarmDefinitionUpdatePage.getDescriptionInput()).to.eq('description');
        expect(await alarmDefinitionUpdatePage.getNumRepeatsInput()).to.eq('5');
        expect(await alarmDefinitionUpdatePage.getTimeSpanInput()).to.eq('5');
        expect(await alarmDefinitionUpdatePage.getKeywordInput()).to.eq('keyword');
        expect(await alarmDefinitionUpdatePage.getFieldnameInput()).to.eq('fieldname');
        const selectedActive = alarmDefinitionUpdatePage.getActiveInput();
        if (await selectedActive.isSelected()) {
            await alarmDefinitionUpdatePage.getActiveInput().click();
            expect(await alarmDefinitionUpdatePage.getActiveInput().isSelected()).to.be.false;
        } else {
            await alarmDefinitionUpdatePage.getActiveInput().click();
            expect(await alarmDefinitionUpdatePage.getActiveInput().isSelected()).to.be.true;
        }
        await alarmDefinitionUpdatePage.save();
        expect(await alarmDefinitionUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await alarmDefinitionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last AlarmDefinition', async () => {
        const nbButtonsBeforeDelete = await alarmDefinitionComponentsPage.countDeleteButtons();
        await alarmDefinitionComponentsPage.clickOnLastDeleteButton();

        alarmDefinitionDeleteDialog = new AlarmDefinitionDeleteDialog();
        expect(await alarmDefinitionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Alarm Definition?');
        await alarmDefinitionDeleteDialog.clickOnConfirmButton();

        expect(await alarmDefinitionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
