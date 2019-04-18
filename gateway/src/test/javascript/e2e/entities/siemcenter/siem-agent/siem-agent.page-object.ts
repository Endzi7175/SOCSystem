import { element, by, ElementFinder } from 'protractor';

export class SiemAgentComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-siem-agent div table .btn-danger'));
    title = element.all(by.css('jhi-siem-agent div h2#page-heading span')).first();

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

export class SiemAgentUpdatePage {
    pageTitle = element(by.id('jhi-siem-agent-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    cnInput = element(by.id('field_cn'));
    publicKeyInput = element(by.id('field_publicKey'));
    ipAddressInput = element(by.id('field_ipAddress'));
    activeInput = element(by.id('field_active'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setCnInput(cn) {
        await this.cnInput.sendKeys(cn);
    }

    async getCnInput() {
        return this.cnInput.getAttribute('value');
    }

    async setPublicKeyInput(publicKey) {
        await this.publicKeyInput.sendKeys(publicKey);
    }

    async getPublicKeyInput() {
        return this.publicKeyInput.getAttribute('value');
    }

    async setIpAddressInput(ipAddress) {
        await this.ipAddressInput.sendKeys(ipAddress);
    }

    async getIpAddressInput() {
        return this.ipAddressInput.getAttribute('value');
    }

    getActiveInput() {
        return this.activeInput;
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

export class SiemAgentDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-siemAgent-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-siemAgent'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
