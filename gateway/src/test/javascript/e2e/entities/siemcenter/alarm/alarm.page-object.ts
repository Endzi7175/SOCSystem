import { element, by, ElementFinder } from 'protractor';

export class AlarmComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-alarm div table .btn-danger'));
    title = element.all(by.css('jhi-alarm div h2#page-heading span')).first();

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

export class AlarmUpdatePage {
    pageTitle = element(by.id('jhi-alarm-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    messageInput = element(by.id('field_message'));
    timestampInput = element(by.id('field_timestamp'));
    activatedInput = element(by.id('field_activated'));
    repeatCountInput = element(by.id('field_repeatCount'));
    firstTimestampInput = element(by.id('field_firstTimestamp'));
    dismissedInput = element(by.id('field_dismissed'));
    contextInput = element(by.id('field_context'));
    siemAgentSelect = element(by.id('field_siemAgent'));
    alarmDefinitionSelect = element(by.id('field_alarmDefinition'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setMessageInput(message) {
        await this.messageInput.sendKeys(message);
    }

    async getMessageInput() {
        return this.messageInput.getAttribute('value');
    }

    async setTimestampInput(timestamp) {
        await this.timestampInput.sendKeys(timestamp);
    }

    async getTimestampInput() {
        return this.timestampInput.getAttribute('value');
    }

    getActivatedInput() {
        return this.activatedInput;
    }
    async setRepeatCountInput(repeatCount) {
        await this.repeatCountInput.sendKeys(repeatCount);
    }

    async getRepeatCountInput() {
        return this.repeatCountInput.getAttribute('value');
    }

    async setFirstTimestampInput(firstTimestamp) {
        await this.firstTimestampInput.sendKeys(firstTimestamp);
    }

    async getFirstTimestampInput() {
        return this.firstTimestampInput.getAttribute('value');
    }

    getDismissedInput() {
        return this.dismissedInput;
    }
    async setContextInput(context) {
        await this.contextInput.sendKeys(context);
    }

    async getContextInput() {
        return this.contextInput.getAttribute('value');
    }

    async siemAgentSelectLastOption() {
        await this.siemAgentSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async siemAgentSelectOption(option) {
        await this.siemAgentSelect.sendKeys(option);
    }

    getSiemAgentSelect(): ElementFinder {
        return this.siemAgentSelect;
    }

    async getSiemAgentSelectedOption() {
        return this.siemAgentSelect.element(by.css('option:checked')).getText();
    }

    async alarmDefinitionSelectLastOption() {
        await this.alarmDefinitionSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async alarmDefinitionSelectOption(option) {
        await this.alarmDefinitionSelect.sendKeys(option);
    }

    getAlarmDefinitionSelect(): ElementFinder {
        return this.alarmDefinitionSelect;
    }

    async getAlarmDefinitionSelectedOption() {
        return this.alarmDefinitionSelect.element(by.css('option:checked')).getText();
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

export class AlarmDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-alarm-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-alarm'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
