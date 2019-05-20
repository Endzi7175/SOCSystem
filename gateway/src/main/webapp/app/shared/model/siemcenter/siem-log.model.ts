import { Moment } from 'moment';

export const enum OperatingSystem {
    WINDOWS = 'WINDOWS',
    LINUX = 'LINUX'
}

export const enum SiemLogSeverityEnum {
    TRACE = 'TRACE',
    DEBUG = 'DEBUG',
    INFO = 'INFO',
    WARN = 'WARN',
    ERROR = 'ERROR',
    FATAL = 'FATAL'
}

export const enum SiemLogSourceEnum {
    WEBSERVER = 'WEBSERVER',
    APPLICATION = 'APPLICATION',
    OPERATINGSYSTEM = 'OPERATINGSYSTEM'
}

export interface ISiemLog {
    id?: number;
    operatingSystem?: OperatingSystem;
    operatingSystemVersion?: string;
    internalIp?: string;
    externalIp?: string;
    hostName?: string;
    context?: string;
    message?: string;
    timestamp?: Moment;
    directory?: string;
    severity?: SiemLogSeverityEnum;
    source?: SiemLogSourceEnum;
    filePath?: string;
    rawMessage?: string;
    siemLogTypeId?: number;
    siemAgentId?: number;
}

export class SiemLog implements ISiemLog {
    constructor(
        public id?: number,
        public operatingSystem?: OperatingSystem,
        public operatingSystemVersion?: string,
        public internalIp?: string,
        public externalIp?: string,
        public hostName?: string,
        public context?: string,
        public message?: string,
        public timestamp?: Moment,
        public directory?: string,
        public severity?: SiemLogSeverityEnum,
        public source?: SiemLogSourceEnum,
        public filePath?: string,
        public rawMessage?: string,
        public siemLogTypeId?: number,
        public siemAgentId?: number
    ) {}
}
