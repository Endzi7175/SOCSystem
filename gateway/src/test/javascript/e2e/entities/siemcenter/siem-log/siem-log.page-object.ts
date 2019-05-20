import { element, by, ElementFinder } from 'protractor';

export class SiemLogComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-siem-log div table .btn-danger'));
    title = element.all(by.css('jhi-siem-log div h2#page-heading span')).first();

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

export class SiemLogUpdatePage {
    pageTitle = element(by.id('jhi-siem-log-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    operatingSystemSelect = element(by.id('field_operatingSystem'));
    operatingSystemVersionInput = element(by.id('field_operatingSystemVersion'));
    internalIpInput = element(by.id('field_internalIp'));
    externalIpInput = element(by.id('field_externalIp'));
    hostNameInput = element(by.id('field_hostName'));
    contextInput = element(by.id('field_context'));
    messageInput = element(by.id('field_message'));
    timestampInput = element(by.id('field_timestamp'));
    directoryInput = element(by.id('field_directory'));
    severitySelect = element(by.id('field_severity'));
    sourceSelect = element(by.id('field_source'));
    filePathInput = element(by.id('field_filePath'));
    rawMessageInput = element(by.id('field_rawMessage'));
    siemLogTypeSelect = element(by.id('field_siemLogType'));
    siemAgentSelect = element(by.id('field_siemAgent'));

    async getPageTitle() {
        return this.pageTitle.getText();
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

    async setOperatingSystemVersionInput(operatingSystemVersion) {
        await this.operatingSystemVersionInput.sendKeys(operatingSystemVersion);
    }

    async getOperatingSystemVersionInput() {
        return this.operatingSystemVersionInput.getAttribute('value');
    }

    async setInternalIpInput(internalIp) {
        await this.internalIpInput.sendKeys(internalIp);
    }

    async getInternalIpInput() {
        return this.internalIpInput.getAttribute('value');
    }

    async setExternalIpInput(externalIp) {
        await this.externalIpInput.sendKeys(externalIp);
    }

    async getExternalIpInput() {
        return this.externalIpInput.getAttribute('value');
    }

    async setHostNameInput(hostName) {
        await this.hostNameInput.sendKeys(hostName);
    }

    async getHostNameInput() {
        return this.hostNameInput.getAttribute('value');
    }

    async setContextInput(context) {
        await this.contextInput.sendKeys(context);
    }

    async getContextInput() {
        return this.contextInput.getAttribute('value');
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

    async setDirectoryInput(directory) {
        await this.directoryInput.sendKeys(directory);
    }

    async getDirectoryInput() {
        return this.directoryInput.getAttribute('value');
    }

    async setSeveritySelect(severity) {
        await this.severitySelect.sendKeys(severity);
    }

    async getSeveritySelect() {
        return this.severitySelect.element(by.css('option:checked')).getText();
    }

    async severitySelectLastOption() {
        await this.severitySelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setSourceSelect(source) {
        await this.sourceSelect.sendKeys(source);
    }

    async getSourceSelect() {
        return this.sourceSelect.element(by.css('option:checked')).getText();
    }

    async sourceSelectLastOption() {
        await this.sourceSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setFilePathInput(filePath) {
        await this.filePathInput.sendKeys(filePath);
    }

    async getFilePathInput() {
        return this.filePathInput.getAttribute('value');
    }

    async setRawMessageInput(rawMessage) {
        await this.rawMessageInput.sendKeys(rawMessage);
    }

    async getRawMessageInput() {
        return this.rawMessageInput.getAttribute('value');
    }

    async siemLogTypeSelectLastOption() {
        await this.siemLogTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async siemLogTypeSelectOption(option) {
        await this.siemLogTypeSelect.sendKeys(option);
    }

    getSiemLogTypeSelect(): ElementFinder {
        return this.siemLogTypeSelect;
    }

    async getSiemLogTypeSelectedOption() {
        return this.siemLogTypeSelect.element(by.css('option:checked')).getText();
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

export class SiemLogDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-siemLog-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-siemLog'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
