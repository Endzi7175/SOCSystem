import { element, by, ElementFinder } from 'protractor';

export class SiemLogTypeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-siem-log-type div table .btn-danger'));
    title = element.all(by.css('jhi-siem-log-type div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class SiemLogTypeUpdatePage {
    pageTitle = element(by.id('jhi-siem-log-type-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    timestampRegexInput = element(by.id('field_timestampRegex'));
    severityRegexInput = element(by.id('field_severityRegex'));
    contextRegexInput = element(by.id('field_contextRegex'));
    messageRegexInput = element(by.id('field_messageRegex'));
    nameInput = element(by.id('field_name'));
    dateTimeFormatInput = element(by.id('field_dateTimeFormat'));
    operatingSystemSelect = element(by.id('field_operatingSystem'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setTimestampRegexInput(timestampRegex) {
        await this.timestampRegexInput.sendKeys(timestampRegex);
    }

    async getTimestampRegexInput() {
        return this.timestampRegexInput.getAttribute('value');
    }

    async setSeverityRegexInput(severityRegex) {
        await this.severityRegexInput.sendKeys(severityRegex);
    }

    async getSeverityRegexInput() {
        return this.severityRegexInput.getAttribute('value');
    }

    async setContextRegexInput(contextRegex) {
        await this.contextRegexInput.sendKeys(contextRegex);
    }

    async getContextRegexInput() {
        return this.contextRegexInput.getAttribute('value');
    }

    async setMessageRegexInput(messageRegex) {
        await this.messageRegexInput.sendKeys(messageRegex);
    }

    async getMessageRegexInput() {
        return this.messageRegexInput.getAttribute('value');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setDateTimeFormatInput(dateTimeFormat) {
        await this.dateTimeFormatInput.sendKeys(dateTimeFormat);
    }

    async getDateTimeFormatInput() {
        return this.dateTimeFormatInput.getAttribute('value');
    }

    async setOperatingSystemSelect(operatingSystem) {
        await this.operatingSystemSelect.sendKeys(operatingSystem);
    }

    async getOperatingSystemSelect() {
        return this.operatingSystemSelect.element(by.css('option:checked')).getText();
    }

    async operatingSystemSelectLastOption() {
        await this.operatingSystemSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class SiemLogTypeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-siemLogType-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-siemLogType'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
