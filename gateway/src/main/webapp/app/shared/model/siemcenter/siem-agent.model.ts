export interface ISiemAgent {
    id?: number;
    cn?: string;
    publicKey?: string;
    ipAddress?: string;
    active?: boolean;
}

export class SiemAgent implements ISiemAgent {
    constructor(public id?: number, public cn?: string, public publicKey?: string, public ipAddress?: string, public active?: boolean) {
        this.active = this.active || false;
    }
}
