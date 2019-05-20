import { element, by, ElementFinder } from 'protractor';

export class AlarmDefinitionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-alarm-definition div table .btn-danger'));
    title = element.all(by.css('jhi-alarm-definition div h2#page-heading span')).first();

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

export class AlarmDefinitionUpdatePage {
    pageTitle = element(by.id('jhi-alarm-definition-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    descriptionInput = element(by.id('field_description'));
    numRepeatsInput = element(by.id('field_numRepeats'));
    timeSpanInput = element(by.id('field_timeSpan'));
    keywordInput = element(by.id('field_keyword'));
    fieldnameInput = element(by.id('field_fieldname'));
    alarmRadiusSelect = element(by.id('field_alarmRadius'));
    severitySelect = element(by.id('field_severity'));
    logSourceSelect = element(by.id('field_logSource'));
    activeInput = element(by.id('field_active'));
    operatingSystemSelect = element(by.id('field_operatingSystem'));
    siemLogTypeSelect = element(by.id('field_siemLogType'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setDescriptionInput(description) {
        await this.descriptionInput.sendKeys(description);
    }

    async getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    async setNumRepeatsInput(numRepeats) {
        await this.numRepeatsInput.sendKeys(numRepeats);
    }

    async getNumRepeatsInput() {
        return this.numRepeatsInput.getAttribute('value');
    }

    async setTimeSpanInput(timeSpan) {
        await this.timeSpanInput.sendKeys(timeSpan);
    }

    async getTimeSpanInput() {
        return this.timeSpanInput.getAttribute('value');
    }

    async setKeywordInput(keyword) {
        await this.keywordInput.sendKeys(keyword);
    }

    async getKeywordInput() {
        return this.keywordInput.getAttribute('value');
    }

    async setFieldnameInput(fieldname) {
        await this.fieldnameInput.sendKeys(fieldname);
    }

    async getFieldnameInput() {
        return this.fieldnameInput.getAttribute('value');
    }

    async setAlarmRadiusSelect(alarmRadius) {
        await this.alarmRadiusSelect.sendKeys(alarmRadius);
    }

    async getAlarmRadiusSelect() {
        return this.alarmRadiusSelect.element(by.css('option:checked')).getText();
    }

    async alarmRadiusSelectLastOption() {
        await this.alarmRadiusSelect
            .all(by.tagName('option'))
            .last()
            .click();
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

    async setLogSourceSelect(logSource) {
        await this.logSourceSelect.sendKeys(logSource);
    }

    async getLogSourceSelect() {
        return this.logSourceSelect.element(by.css('option:checked')).getText();
    }

    async logSourceSelectLastOption() {
        await this.logSourceSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    getActiveInput() {
        return this.activeInput;
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

export class AlarmDefinitionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-alarmDefinition-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-alarmDefinition'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
