package com.sbnz.SIEMCenter2.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Rule {
		
		public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

		public List<Condition> conditions;
		
		private static transient List<String> fieldsNames;
		static {
			fieldsNames = Arrays
					.asList(LogEntry.class.getFields())
					.stream()
					.map(f->f.getName())
					.collect(Collectors.toList());
		}

	    @Override
	    public String toString(){
	        StringBuilder statementBuilder = new StringBuilder();

	        for (Condition condition : getConditions()) {

	            String operator = null;

	            switch (condition.getOperator()) {
	                case EQUAL_TO:
	                    operator = "==";
	                    break;
	                case NOT_EQUAL_TO:
	                    operator = "!=";
	                    break;
	                case GREATER_THAN:
	                    operator = ">";
	                    break;
	                case LESS_THAN:
	                    operator = "<";
	                    break;
	                case GREATER_THAN_OR_EQUAL_TO:
	                    operator = ">=";
	                    break;
	                case LESS_THAN_OR_EQUAL_TO:
	                    operator = "<=";
	                    break;
	            }
	            
	            if(!fieldsNames.contains(condition.getField()))
	            {
	            	//throw new IllegalArgumentException();
	            }
	            
	            statementBuilder.append(condition.getField()).append(" ").append(operator).append(" ");

	            if (condition.getValue() instanceof String) {
	                statementBuilder.append("'").append(condition.getValue()).append("'");
	            } else {
	                statementBuilder.append(condition.getValue());
	            }
	            switch (condition.getTrailingOperator()) {
	            	case AND:
	            		operator = " && ";
	            		break;
	            	case OR:
	            		operator =" || ";
	            		break;
            		
	            }
	            statementBuilder.append(operator);
	        }

	        String statement = statementBuilder.toString();
	        
	        // remove trailing &&
	        return statement.substring(0, statement.length() - 4);
	    }
	    
	    private List<Condition> getConditions() {
	    	return conditions;
		}

}


