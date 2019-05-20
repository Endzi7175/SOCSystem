import { Moment } from 'moment';

export interface IAlarm {
    id?: number;
    message?: string;
    timestamp?: Moment;
    activated?: boolean;
    repeatCount?: number;
    firstTimestamp?: Moment;
    dismissed?: boolean;
    context?: string;
    siemAgentId?: number;
    alarmDefinitionId?: number;
}

export class Alarm implements IAlarm {
    constructor(
        public id?: number,
        public message?: string,
        public timestamp?: Moment,
        public activated?: boolean,
        public repeatCount?: number,
        public firstTimestamp?: Moment,
        public dismissed?: boolean,
        public context?: string,
        public siemAgentId?: number,
        public alarmDefinitionId?: number
    ) {
        this.activated = this.activated || false;
        this.dismissed = this.dismissed || false;
    }
}
