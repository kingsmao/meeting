package com.chainup.entity;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AbstractExample {
	protected String orderByClause;
	
	protected Integer limitStart;
	
	protected Integer limitEnd;
	
	protected Pageable pageable;//分页
	
	
	public AbstractExample() {
		super();
	}
    public AbstractExample(Pageable pageable) {
		super();
		if(pageable!=null){
			this.pageable = pageable;
			this.limitStart = pageable.getPageSize() * pageable.getPageNumber();
			this.limitEnd = pageable.getPageSize();
		}
	}
	public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }
    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }
    public Integer getLimitEnd() {
        return limitEnd;
    }
    
	public Pageable getPageable() {
		return pageable;
	}
	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	/**

	 * 公共查询类

	 * @author mybatis

	 *

	 */
	public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        public Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        public Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        public Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        public Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
	
	 protected abstract static class CoreCriteria {
		 protected List<Criterion> criteria;

	        protected CoreCriteria() {
	            super();
	            criteria = new ArrayList<Criterion>();
	        }

	        public boolean isValid() {
	            return criteria.size() > 0;
	        }

	        public List<Criterion> getAllCriteria() {
	            return criteria;
	        }

	        public List<Criterion> getCriteria() {
	            return criteria;
	        }

	        protected void addCriterion(String condition) {
	            if (condition == null) {
	                throw new RuntimeException("Value for condition cannot be null");
	            }
	            criteria.add(new Criterion(condition));
	        }

	        protected void addCriterion(String condition, Object value, String property) {
	            if (value == null) {
	                throw new RuntimeException("Value for " + property + " cannot be null");
	            }
	            criteria.add(new Criterion(condition, value));
	        }

	        protected void addCriterion(String condition, Object value1, Object value2, String property) {
	            if (value1 == null || value2 == null) {
	                throw new RuntimeException("Between values for " + property + " cannot be null");
	            }
	            criteria.add(new Criterion(condition, value1, value2));
	        }

	        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
	            if (value == null) {
	                throw new RuntimeException("Value for " + property + " cannot be null");
	            }
	            addCriterion(condition, new java.sql.Date(value.getTime()), property);
	        }

	        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
	            if (values == null || values.size() == 0) {
	                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
	            }
	            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
	            Iterator<Date> iter = values.iterator();
	            while (iter.hasNext()) {
	                dateList.add(new java.sql.Date(iter.next().getTime()));
	            }
	            addCriterion(condition, dateList, property);
	        }

	        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
	            if (value1 == null || value2 == null) {
	                throw new RuntimeException("Between values for " + property + " cannot be null");
	            }
	            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
	        }
	        private String toUpperCase(String property){
	        	return property.replaceAll("[A-Z]", "_$0").toUpperCase()+" ";//则替换注意 $0前面空格

	        }
	        
	        public Criteria andIsNull(String columnName ) {
	            addCriterion(toUpperCase(columnName)+" is null");
	            return (Criteria) this;
	        }

	        public Criteria andIsNotNull(String columnName) {
	            addCriterion(toUpperCase(columnName)+" is not null");
	            return (Criteria) this;
	        }

	        public Criteria andEqualTo(String columnName,Object value) {
	            addCriterion(toUpperCase(columnName)+" =", value, columnName);
	            return (Criteria) this;
	        }

	        public Criteria andNotEqualTo(String columnName,Object value) {
	            addCriterion(toUpperCase(columnName)+" <>", value, columnName);
	            return (Criteria) this;
	        }

	        public Criteria andGreaterThan(String columnName,Object value) {
	            addCriterion(toUpperCase(columnName)+" >", value, columnName);
	            return (Criteria) this;
	        }

	        public Criteria andGreaterThanOrEqualTo(String columnName,Object value) {
	            addCriterion(toUpperCase(columnName)+" >=", value, columnName);
	            return (Criteria) this;
	        }

	        public Criteria andLessThan(String columnName,Object value) {
	            addCriterion(toUpperCase(columnName)+" <", value, columnName);
	            return (Criteria) this;
	        }

	        public Criteria andLessThanOrEqualTo(String columnName,Object value) {
	            addCriterion(toUpperCase(columnName)+" <=", value, columnName);
	            return (Criteria) this;
	        }

	        public Criteria andIn(String columnName,List<Object> values) {
	            addCriterion(toUpperCase(columnName)+" in", values, columnName);
	            return (Criteria) this;
	        }

	        public Criteria andNotIn(String columnName,List<Object> values) {
	            addCriterion(toUpperCase(columnName)+" not in", values, columnName);
	            return (Criteria) this;
	        }
	        
	        public Criteria andLike(String columnName,String value) {
	            addCriterion(toUpperCase(columnName)+" like", value, columnName);
	            return (Criteria) this;
	        }

	        public Criteria andNotLike(String columnName,String value) {
	        	addCriterion(toUpperCase(columnName)+" not like", value, columnName);
	            return (Criteria) this;
	        }
	        public Criteria andBetween(String columnName,Object value1, Object value2) {
	            addCriterion(toUpperCase(columnName)+" between", value1, value2, columnName);
	            return (Criteria) this;
	        }

	        public Criteria andNotBetween(String columnName,Object value1, Object value2) {
	            addCriterion(toUpperCase(columnName)+" not between", value1, value2, columnName);
	            return (Criteria) this;
	        }
	 }
	 
	   public static class Criteria extends CoreCriteria {

	        public Criteria() {
	            super();
	        }
	    }

}
