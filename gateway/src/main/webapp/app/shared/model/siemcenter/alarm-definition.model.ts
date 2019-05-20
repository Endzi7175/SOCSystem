export const enum AlarmRadius {
    SYSTEM = 'SYSTEM',
    AGENT = 'AGENT'
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

export const enum OperatingSystem {
    WINDOWS = 'WINDOWS',
    LINUX = 'LINUX'
}

export interface IAlarmDefinition {
    id?: number;
    name?: string;
    description?: string;
    numRepeats?: number;
    timeSpan?: number;
    keyword?: string;
    fieldname?: string;
    alarmRadius?: AlarmRadius;
    severity?: SiemLogSeverityEnum;
    logSource?: SiemLogSourceEnum;
    active?: boolean;
    operatingSystem?: OperatingSystem;
    siemLogTypeId?: number;
}

export class AlarmDefinition implements IAlarmDefinition {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public numRepeats?: number,
        public timeSpan?: number,
        public keyword?: string,
        public fieldname?: string,
        public alarmRadius?: AlarmRadius,
        public severity?: SiemLogSeverityEnum,
        public logSource?: SiemLogSourceEnum,
        public active?: boolean,
        public operatingSystem?: OperatingSystem,
        public siemLogTypeId?: number
    ) {
        this.active = this.active || false;
    }
}
