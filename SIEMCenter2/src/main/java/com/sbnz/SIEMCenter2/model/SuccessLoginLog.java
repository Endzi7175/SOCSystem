package com.sbnz.SIEMCenter2.model;

import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
@Role(Role.Type.EVENT)
@Expires("2m")
public class SuccessLoginLog extends LogEntry {
	
	public SuccessLoginLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SuccessLoginLog(int informationSystemType, String message, String category, int logLevel, String ipAddress,
			String userId, Date timestamp, String machineId) {
		super(informationSystemType, message, category, logLevel, ipAddress, userId, timestamp, machineId);
		// TODO Auto-generated constructor stub
	}

	public SuccessLoginLog(String line) {
		super(line);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
