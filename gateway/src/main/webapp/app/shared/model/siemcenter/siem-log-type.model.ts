export const enum OperatingSystem {
    WINDOWS = 'WINDOWS',
    LINUX = 'LINUX'
}

export interface ISiemLogType {
    id?: number;
    timestampRegex?: string;
    severityRegex?: string;
    contextRegex?: string;
    messageRegex?: string;
    name?: string;
    dateTimeFormat?: string;
    operatingSystem?: OperatingSystem;
}

export class SiemLogType implements ISiemLogType {
    constructor(
        public id?: number,
        public timestampRegex?: string,
        public severityRegex?: string,
        public contextRegex?: string,
        public messageRegex?: string,
        public name?: string,
        public dateTimeFormat?: string,
        public operatingSystem?: OperatingSystem
    ) {}
}
