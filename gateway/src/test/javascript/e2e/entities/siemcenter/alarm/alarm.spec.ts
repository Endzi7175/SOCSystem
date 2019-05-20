/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../../page-objects/jhi-page-objects';

import { AlarmComponentsPage, AlarmDeleteDialog, AlarmUpdatePage } from './alarm.page-object';

const expect = chai.expect;

describe('Alarm e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let alarmUpdatePage: AlarmUpdatePage;
    let alarmComponentsPage: AlarmComponentsPage;
    let alarmDeleteDialog: AlarmDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.loginWithOAuth('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Alarms', async () => {
        await navBarPage.goToEntity('alarm');
        alarmComponentsPage = new AlarmComponentsPage();
        await browser.wait(ec.visibilityOf(alarmComponentsPage.title), 5000);
        expect(await alarmComponentsPage.getTitle()).to.eq('Alarms');
    });

    it('should load create Alarm page', async () => {
        await alarmComponentsPage.clickOnCreateButton();
        alarmUpdatePage = new AlarmUpdatePage();
        expect(await alarmUpdatePage.getPageTitle()).to.eq('Create or edit a Alarm');
        await alarmUpdatePage.cancel();
    });

    it('should create and save Alarms', async () => {
        const nbButtonsBeforeCreate = await alarmComponentsPage.countDeleteButtons();

        await alarmComponentsPage.clickOnCreateButton();
        await promise.all([
            alarmUpdatePage.setMessageInput('message'),
            alarmUpdatePage.setTimestampInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            alarmUpdatePage.setRepeatCountInput('5'),
            alarmUpdatePage.setFirstTimestampInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            alarmUpdatePage.setContextInput('context'),
            alarmUpdatePage.siemAgentSelectLastOption(),
            alarmUpdatePage.alarmDefinitionSelectLastOption()
        ]);
        expect(await alarmUpdatePage.getMessageInput()).to.eq('message');
        expect(await alarmUpdatePage.getTimestampInput()).to.contain('2001-01-01T02:30');
        const selectedActivated = alarmUpdatePage.getActivatedInput();
        if (await selectedActivated.isSelected()) {
            await alarmUpdatePage.getActivatedInput().click();
            expect(await alarmUpdatePage.getActivatedInput().isSelected()).to.be.false;
        } else {
            await alarmUpdatePage.getActivatedInput().click();
            expect(await alarmUpdatePage.getActivatedInput().isSelected()).to.be.true;
        }
        expect(await alarmUpdatePage.getRepeatCountInput()).to.eq('5');
        expect(await alarmUpdatePage.getFirstTimestampInput()).to.contain('2001-01-01T02:30');
        const selectedDismissed = alarmUpdatePage.getDismissedInput();
        if (await selectedDismissed.isSelected()) {
            await alarmUpdatePage.getDismissedInput().click();
            expect(await alarmUpdatePage.getDismissedInput().isSelected()).to.be.false;
        } else {
            await alarmUpdatePage.getDismissedInput().click();
            expect(await alarmUpdatePage.getDismissedInput().isSelected()).to.be.true;
        }
        expect(await alarmUpdatePage.getContextInput()).to.eq('context');
        await alarmUpdatePage.save();
        expect(await alarmUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await alarmComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Alarm', async () => {
        const nbButtonsBeforeDelete = await alarmComponentsPage.countDeleteButtons();
        await alarmComponentsPage.clickOnLastDeleteButton();

        alarmDeleteDialog = new AlarmDeleteDialog();
        expect(await alarmDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Alarm?');
        await alarmDeleteDialog.clickOnConfirmButton();

        expect(await alarmComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
