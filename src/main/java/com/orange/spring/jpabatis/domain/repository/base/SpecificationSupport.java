package com.orange.spring.jpabatis.domain.repository.base;

import com.orange.spring.jpabatis.util.ObjectUtils;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.expression.function.BasicFunctionExpression;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
 
/**
 * Jpa复杂查询支持
 *
 * @author hexm
 * @date 2019/8/19 15:25
 */
public class SpecificationSupport<T> implements Specification<T> {
 
    /**
     * 额外添加的or或者and条件
     */
    private Map<OptionType, SpecificationSupport<T>> AND_OR = new LinkedHashMap<>();
 
    /**
     * 里面都是and条件
     */
    private List<FieldOption> condition = new ArrayList<>();
 
    /**
     * 被忽略的属性
     */
    private List<String> ignoreColumn = new ArrayList<>();
 
    /**
     * 忽略大小写
     */
    private List<String> ignoreCase = new ArrayList<>();
 
    /**构造时的对象*/
    private T t;
 
    /**
     * 忽略空字符串
     */
    private boolean ignoreNullString = true;
 
    private SpecificationSupport() {
    }
 
    /**
     * 得到一个查询构造器
     *
     * @param probe
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K extends T> SpecificationSupport<T> of(K probe) {
        SpecificationSupport<T> s = new SpecificationSupport<>();
        s.t = probe;
        return s;
    }
 
    /**
     * 得到一个查询构造器
     *
     * @param <T>
     * @return
     */
    public static <T> SpecificationSupport<T> of(Class<T> clazz) {
        return new SpecificationSupport<>();
    }
 
    /**
     * 得到一个查询构造器
     *
     * @param <T>
     * @return
     */
    public static <T> SpecificationSupport<T> of() {
        return new SpecificationSupport<>();
    }
 
    /**
     * 前匹配 like %column
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> startsLike(String column) {
        addCondition(column, OptionType.STARTS_LIKE);
        return this;
    }
 
    /**
     * 后匹配 like column%
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> endLike(String column) {
        addCondition(column, OptionType.END_LIKE);
        return this;
    }
 
    /**
     * 匹配 like %column%
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> like(String column) {
        addCondition(column, OptionType.LIKE);
        return this;
    }
 
    /**
     * 匹配 = column
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> eq(String column) {
        addCondition(column, OptionType.EQ);
        return this;
    }
 
    /**
     * 匹配 != column
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> neq(String column) {
        addCondition(column, OptionType.NOT_EQ);
        return this;
    }
 
    /**
     * 匹配 not like %column
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> notStartLike(String column) {
        addCondition(column, OptionType.NOT_STARTS_LIKE);
        return this;
    }
 
    /**
     * 匹配 not like column%
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> notEndLike(String column) {
        addCondition(column, OptionType.NOT_END_LIKE);
        return this;
    }
 
    /**
     * 匹配 not like %column%
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> notLike(String column) {
        addCondition(column, OptionType.NOT_LIKE);
        return this;
    }
 
    /**
     * 匹配 > column
     *
     * @param column 字段属性必须为数字类型，或字符串的数字
     * @return
     */
    public SpecificationSupport<T> gt(String column) {
        addCondition(column, OptionType.GT);
        return this;
    }
 
    /**
     * 匹配 >= column
     *
     * @param column 字段属性必须为数字类型，或字符串的数字
     * @return
     */
    public SpecificationSupport<T> ge(String column) {
        addCondition(column, OptionType.GE);
        return this;
    }
 
    /**
     * 匹配 < column
     *
     * @param column 字段属性必须为数字类型，或字符串的数字
     * @return
     */
    public SpecificationSupport<T> lt(String column) {
        addCondition(column, OptionType.LT);
        return this;
    }
 
    /**
     * 匹配 <= column
     *
     * @param column 字段属性必须为数字类型，或字符串的数字
     * @return
     */
    public SpecificationSupport<T> le(String column) {
        addCondition(column, OptionType.LE);
        return this;
    }
 
 
    /**
     * 前匹配 like %column
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> startsLike(String column, String value) {
        addCondition(column, OptionType.STARTS_LIKE, value);
        return this;
    }
 
    /**
     * 后匹配 like column%
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> endLike(String column, String value) {
        addCondition(column, OptionType.END_LIKE, value);
        return this;
    }
 
    /**
     * 匹配 like %column%
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> like(String column, String value) {
        addCondition(column, OptionType.LIKE, value);
        return this;
    }
 
    /**
     * 匹配 = column
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> eq(String column, Object value) {
        addCondition(column, OptionType.EQ, value);
        return this;
    }
 
    /**
     * 匹配 != column
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> neq(String column, Object value) {
        addCondition(column, OptionType.NOT_EQ, value);
        return this;
    }
 
    /**
     * 匹配 not like %column
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> notStartLike(String column, String value) {
        addCondition(column, OptionType.NOT_STARTS_LIKE, value);
        return this;
    }
 
    /**
     * 匹配 not like column%
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> notEndLike(String column, String value) {
        addCondition(column, OptionType.NOT_END_LIKE, value);
        return this;
    }
 
    /**
     * 匹配 not like %column%
     *
     * @param column
     * @return
     */
    public SpecificationSupport<T> notLike(String column, String value) {
        addCondition(column, OptionType.NOT_LIKE, value);
        return this;
    }
 
    /**
     * 匹配 > column
     *
     * @param column 字段属性必须为数字类型
     * @return
     */
    public SpecificationSupport<T> gt(String column, Number value) {
        addCondition(column, OptionType.GT, value);
        return this;
    }
 
    /**
     * 匹配 >= column
     *
     * @param column 字段属性必须为数字类型
     * @return
     */
    public SpecificationSupport<T> ge(String column, Number value) {
        addCondition(column, OptionType.GE, value);
        return this;
    }
 
    /**
     * 匹配 < column
     *
     * @param column 字段属性必须为数字类型
     * @return
     */
    public SpecificationSupport<T> lt(String column, Number value) {
        addCondition(column, OptionType.LT, value);
        return this;
    }
 
    /**
     * 匹配 <= column
     *
     * @param column 字段属性必须为数字类型
     * @return
     */
    public SpecificationSupport<T> le(String column, Number value) {
        addCondition(column, OptionType.LE, value);
        return this;
    }
 
    /**
     * 匹配 between column1 and column2
     *
     * @param column 字段
     * @param value1 继承了比较接口的对象
     * @param value2 继承了比较接口的对象
     * @return
     */
    public SpecificationSupport<T> between(String column, Comparable value1, Comparable value2) {
        addCondition(column, OptionType.BETWEEN, value1, value2);
        return this;
    }
 
    /**
     * 匹配 in (column)
     *
     * @param column 字段
     * @param value  多个比较值
     * @return
     */
    public SpecificationSupport<T> in(String column, Object... value) {
        addCondition(column, OptionType.IN, value);
        return this;
    }
 
    /**
     * 匹配 column is null
     *
     * @param column 字段
     * @return
     */
    public SpecificationSupport<T> isNull(String column) {
        addConditionEmptyValue(column, OptionType.IS_NULL);
        return this;
    }
 
    /**
     * 匹配 column is not null
     *
     * @param column 字段
     * @return
     */
    public SpecificationSupport<T> isNotNull(String column) {
        addConditionEmptyValue(column, OptionType.IS_NOT_NULL);
        return this;
    }
 
    /**
     * 匹配 column == true
     *
     * @param column 字段
     * @return
     */
    public SpecificationSupport<T> isTrue(String column) {
        addConditionEmptyValue(column, OptionType.TRUE);
        return this;
    }
 
    /**
     * 匹配 column == false
     *
     * @param column 字段
     * @return
     */
    public SpecificationSupport<T> isFalse(String column) {
        addConditionEmptyValue(column, OptionType.FALSE);
        return this;
    }
 
    /**
     * 匹配 date_format(column,'format') ?= value
     * 注：由于指定方法名为：date_format只适用于mysql，如果使用oracle需要改为to_char
     * @param column 匹配字段
     * @param value  匹配值
     * @param format 匹配格式
     * @return
     */
    public SpecificationSupport<T> dateFormat(String column, Object value, String format, OptionType optionType) {
        Map<String, ArgType> argTypeMap = new HashMap<>();
        argTypeMap.put(column, ArgType.ROOT);   //第一个参数读取字段
        argTypeMap.put(format, ArgType.STRING); //第二个参数字符串类型
        function("date_format", new String[]{column, format}, argTypeMap, optionType, false, value);
        return this;
    }
 
    /**
     * 匹配 date_format(column,'format') = value
     *
     * @param column 匹配字段
     * @param value  匹配值
     * @param format 匹配格式
     * @return
     */
    public SpecificationSupport<T> dateEq(String column, Object value, String format) {
        dateFormat(column, value, format, OptionType.EQ);
        return this;
    }
 
    /**
     * 匹配 date_format(column,'format') >= value
     *
     * @param column 匹配字段
     * @param value  匹配值
     * @param format 匹配格式
     * @return
     */
    public SpecificationSupport<T> dateGe(String column, Object value, String format) {
        dateFormat(column, value, format, OptionType.GE);
        return this;
    }
 
    /**
     * 匹配 date_format(column,'format') <= value
     *
     * @param column 匹配字段
     * @param value  匹配值
     * @param format 匹配格式
     * @return
     */
    public SpecificationSupport<T> dateLe(String column, Object value, String format) {
        dateFormat(column, value, format, OptionType.LE);
        return this;
    }
 
    /**
     * 匹配 function(column) ? value
     *
     * @param functionName 方法名称
     * @param args         参数名
     * @param typeMap      参数类型
     * @param optionType   比较类型
     * @param emptyValue   是否没有参数
     * @param value        参数
     * @return
     */
    public SpecificationSupport<T> function(String functionName, Object[] args, Map<String, ArgType> typeMap, OptionType optionType, boolean emptyValue, Object... value) {
        Class<?> clazz;
        //不是空参数，根据比较的参数类型，指定函数的返回类型
        if (!emptyValue && value != null && value[0] != null) {
            clazz = value[0].getClass();
        } else {
            clazz = Boolean.class;
        }
        SupportFunction supportFunction = new SupportFunction(functionName, args, typeMap, optionType, clazz);
        addConditions(null, OptionType.FUNCTION, emptyValue, supportFunction, value);
        return this;
    }
 
    /**
     * 忽略某个字段属性
     *
     * @param columns
     * @return
     */
    public SpecificationSupport<T> ignoreProperty(String... columns) {
        ignoreColumn.addAll(Arrays.asList(columns));
        return this;
    }
 
    /**
     * 忽略大小写匹配 = lower(column)
     *
     * @param columns
     * @return
     */
    public SpecificationSupport<T> ignoreCase(String... columns) {
        ignoreCase.addAll(Arrays.asList(columns));
        return this;
    }
 
    /**
     * 添加条件
     *
     * @param column
     * @param optionType
     */
    private void addCondition(String column, OptionType optionType) {
        try {
            addCondition(column, optionType, ObjectUtils.getField(t, column).get(t));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 添加条件
     *
     * @param column
     * @param optionType
     */
    private void addConditionEmptyValue(String column, OptionType optionType) {
        addConditions(column, optionType, true, null);
    }
 
    /**
     * 添加条件
     *
     * @param column
     * @param optionType
     */
    @SafeVarargs
    private final <K> void addCondition(String column, OptionType optionType, K... value) {
        addConditions(column, optionType, false, null, value);
    }
 
    /**
     * 添加条件
     *
     * @param column
     * @param optionType
     */
    @SafeVarargs
    private final <K> void addConditions(String column, OptionType optionType, boolean emptyValue, SupportFunction function, K... value) {
        if (emptyValue) {
            condition.add(new FieldOption<>(column, optionType, value, function));
        } else {
            boolean flag = true;
            for (K k : value) {
                if (k == null) {
                    flag = false;
                    break;
                }
                if ("".equals(k) && ignoreNullString) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                condition.add(new FieldOption<>(column, optionType, value, function));
            }
        }
    }
 
    /**
     * and操作，创建一个消费者，依靠lambed表达式，消费一个创建好的对象，以添加到定义的队列中
     *
     * @param and
     * @return
     */
    public SpecificationSupport<T> and(Consumer<SpecificationSupport<T>> and) {
        if (and != null) {
            SpecificationSupport<T> s = SpecificationSupport.of();
            and.accept(s);
            if (!s.isEmpty()) {
                AND_OR.put(OptionType.AND, s);
            }
        }
        return this;
    }
 
    /**
     * or操作，创建一个消费者，依靠lambed表达式，消费一个创建好的对象，以添加到定义的队列中
     *
     * @param or
     * @return
     */
    public SpecificationSupport<T> or(Consumer<SpecificationSupport<T>> or) {
        if (or != null) {
            SpecificationSupport<T> s = SpecificationSupport.of();
            or.accept(s);
            if (!s.isEmpty()) {
                AND_OR.put(OptionType.OR, s);
            }
        }
        return this;
    }
 
    /**
     * @param root
     * @param query
     * @param cb
     * @return
     */
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (t != null) {
            Map<String, Field> fieldMap = ObjectUtils.getFieldMap(t.getClass());
            fieldMap.forEach((s1, field) -> {
                try {
                    if (!Modifier.isFinal(field.getModifiers())) {
                        if (!condition.contains(new FieldOption<>(field.getName(), null, null))) {
                            addCondition(field.getName(), OptionType.EQ, field.get(t));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
        Predicate p = null;
        List<Predicate> predicates = new ArrayList<>();
        for (FieldOption option : condition) {
            //不在忽略字段中
            if (!ignoreColumn.contains(option.name)) {
                Predicate predicate = getPredicate(root, cb, option);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
        }
        if (!predicates.isEmpty()) {
            p = cb.and(predicates.toArray(new Predicate[0]));
        }
        for (Map.Entry<OptionType, SpecificationSupport<T>> entry : AND_OR.entrySet()) {
            OptionType optionType = entry.getKey();
            SpecificationSupport<T> specificationSupport = entry.getValue();
            if (optionType == OptionType.AND) {
                if (p == null) {
                    p = cb.and(specificationSupport.toPredicate(root, query, cb));
                } else {
                    p = cb.and(specificationSupport.toPredicate(root, query, cb), p);
                }
            } else {
                if (p == null) {
                    p = cb.or(specificationSupport.toPredicate(root, query, cb));
                } else {
                    p = cb.or(specificationSupport.toPredicate(root, query, cb), p);
                }
            }
        }
        return p;
    }
 
    /**
     * 设置是否忽略空串
     *
     * @param ignoreNullString
     */
    public void setIgnoreNullString(boolean ignoreNullString) {
        this.ignoreNullString = ignoreNullString;
    }
 
    /**
     * 判断构造器条件是否为空
     *
     * @return
     */
    protected boolean isEmpty() {
        return condition.isEmpty() && AND_OR.isEmpty();
    }
 
    /**
     * 判断条件类型，添加到组合中
     *
     * @param root
     * @param cb
     * @param option
     * @return
     */
    @SuppressWarnings("unchecked")
    private Predicate getPredicate(Root<T> root, CriteriaBuilder cb, FieldOption option) {
        switch (option.getOptionType()) {
            case EQ:
                if (ignoreCase.contains(option.getName())) {
                    return cb.equal(cb.lower(root.get(option.getName())), option.getValue()[0].toString().toLowerCase());
                }
                return cb.equal(root.get(option.getName()), option.getValue()[0]);
            case GE:
                return cb.ge(root.get(option.getName()), (Number) option.getValue()[0]);
            case GT:
                return cb.gt(root.get(option.getName()), (Number) option.getValue()[0]);
            case IN:
                if (ignoreCase.contains(option.getName())) {
                    CriteriaBuilder.In<Object> in = cb.in(cb.lower(root.get(option.getName())));
                    for (Object o : option.getValue()) {
                        in.value(o.toString().toLowerCase());
                    }
                    return cb.and(in);
                }
                return root.get(option.getName()).in(option.getValue());
            case LE:
                return cb.le(root.get(option.getName()), (Number) option.getValue()[0]);
            case LT:
                Number o = null;
                if (option.getValue()[0] instanceof String) {
                    o = new BigInteger((String) option.getValue()[0]);
                }
                if (o == null) {
                    o = (Number) option.getValue()[0];
                }
                return cb.lt(root.get(option.getName()), o);
            case LIKE:
                if (ignoreCase.contains(option.getName())) {
                    return cb.like(cb.lower(root.get(option.getName())), "%" + option.getValue()[0].toString().toLowerCase() + "%");
                }
                return cb.like(root.get(option.getName()), "%" + option.getValue()[0] + "%");
            case STARTS_LIKE:
                if (ignoreCase.contains(option.getName())) {
                    return cb.like(cb.lower(root.get(option.getName())), "%" + option.getValue()[0].toString().toLowerCase());
                }
                return cb.like(root.get(option.getName()), "%" + option.getValue()[0]);
            case END_LIKE:
                if (ignoreCase.contains(option.getName())) {
                    return cb.like(cb.lower(root.get(option.getName())), option.getValue()[0].toString().toLowerCase() + "%");
                }
                return cb.like(root.get(option.getName()), option.getValue()[0] + "%");
            case IS_NULL:
                return root.get(option.getName()).isNull();
            case IS_NOT_NULL:
                return root.get(option.getName()).isNotNull();
            case NOT_LIKE:
                if (ignoreCase.contains(option.getName())) {
                    return cb.notLike(cb.lower(root.get(option.getName())), "%" + option.getValue()[0].toString().toLowerCase() + "%");
                }
                return cb.notLike(root.get(option.getName()), "%" + option.getValue()[0] + "%");
            case NOT_STARTS_LIKE:
                if (ignoreCase.contains(option.getName())) {
                    return cb.notLike(cb.lower(root.get(option.getName())), "%" + option.getValue()[0].toString().toLowerCase());
                }
                return cb.notLike(root.get(option.getName()), "%" + option.getValue()[0]);
            case NOT_END_LIKE:
                if (ignoreCase.contains(option.getName())) {
                    return cb.notLike(cb.lower(root.get(option.getName())), option.getValue()[0].toString().toLowerCase() + "%");
                }
                return cb.notLike(root.get(option.getName()), option.getValue()[0] + "%");
            case NOT_EQ:
                if (ignoreCase.contains(option.getName())) {
                    return cb.notEqual(cb.lower(root.get(option.getName())), option.getValue()[0].toString().toLowerCase());
                }
                return cb.notEqual(root.get(option.getName()), option.getValue()[0]);
            case BETWEEN:
                return cb.between(root.get(option.getName()), (Comparable) option.getValue()[0], (Comparable) option.getValue()[1]);
            case TRUE:
                return cb.isTrue(root.get(option.getName()));
            case FALSE:
                return cb.isFalse(root.get(option.getName()));
            case FUNCTION:
                SupportFunction function = option.getFunction();
                Expression<?>[] args = Arrays.stream(function.args).map(o1 -> {
                    ArgType o2 = function.getArgType().get(o1);
                    if (o2 != null) {
                        if (o2 == ArgType.ROOT) {
                            return root.get(o1.toString());
                        } else if (o2 == ArgType.STRING) {
                            return cb.literal(o1);
                        } else if (o2 == ArgType.FUNCTION) {
                            return new BasicFunctionExpression((CriteriaBuilderImpl) cb, String.class, o1.toString());
                        }
                    }
                    return cb.literal(o1);
                }).toArray((IntFunction<Expression<?>[]>) Expression[]::new);
                Expression<?> expression = cb.function(function.function, function.resultType, args);
                return getExpression(cb, expression, option);
        }
        return null;
    }
 
    /**
     * 获取方法表达式
     *
     * @param cb
     * @param expression
     * @param option
     * @return
     */
    @SuppressWarnings("unchecked")
    private Predicate getExpression(CriteriaBuilder cb, Expression<?> expression, FieldOption option) {
        switch (option.function.optionType) {
            case EQ:
                return cb.equal(expression, option.getValue()[0]);
            case GE:
                return cb.ge((Expression<? extends Number>) expression, (Number) option.getValue()[0]);
            case GT:
                return cb.gt((Expression<? extends Number>) expression, (Number) option.getValue()[0]);
            case IN:
                CriteriaBuilder.In<Object> in = cb.in(expression);
                for (Object o : option.getValue()) {
                    in.value(o);
                }
                return cb.and(in);
            case LE:
                return cb.le((Expression<? extends Number>) expression, (Number) option.getValue()[0]);
            case LT:
                return cb.lt((Expression<? extends Number>) expression, (Number) option.getValue()[0]);
            case LIKE:
                return cb.like((Expression<String>) expression, "%" + option.getValue()[0] + "%");
            case STARTS_LIKE:
                return cb.like((Expression<String>) expression, "%" + option.getValue()[0]);
            case END_LIKE:
                return cb.like((Expression<String>) expression, option.getValue()[0] + "%");
            case IS_NULL:
                return cb.isNull(expression);
            case IS_NOT_NULL:
                return cb.isNotNull(expression);
            case NOT_LIKE:
                return cb.notLike((Expression<String>) expression, "%" + option.getValue()[0] + "%");
            case NOT_STARTS_LIKE:
                return cb.notLike((Expression<String>) expression, "%" + option.getValue()[0]);
            case NOT_END_LIKE:
                return cb.notLike((Expression<String>) expression, option.getValue()[0] + "%");
            case NOT_EQ:
                return cb.notEqual(expression, option.getValue()[0]);
            case BETWEEN:
                return cb.between((Expression<? extends Comparable>) expression, (Comparable) option.getValue()[0], (Comparable) option.getValue()[1]);
            case TRUE:
                return cb.isTrue((Expression<Boolean>) expression);
            case FALSE:
                return cb.isFalse((Expression<Boolean>) expression);
        }
        return null;
    }
 
    public enum OptionType {
        FUNCTION,   //执行函数
        AND,        //添加and
        OR,         //添加or
        BETWEEN,    //between x1 and x2
        STARTS_LIKE,//like %s
        END_LIKE,   //like s%
        LIKE,       //like %s%
        EQ,         //=
        GT,         //>
        LT,         //<
        GE,         //>=
        LE,         //<=
        IN,         //in
        TRUE,
        FALSE,
        REGEXP,     //regexp(column,regex)
        IS_NULL,    // is null
        IS_NOT_NULL,//is not null
        NOT_EQ,     //!=
        NOT_LIKE,   //not like %s%
        NOT_STARTS_LIKE,//not like %s
        NOT_END_LIKE,   //not like s%
        IGNORE_CASE //忽略大小写
    }
 
    private static class FieldOption<T> {
        private String name;
        private OptionType optionType;
        private T[] value;
        private SupportFunction function;
 
        FieldOption(String name, OptionType optionType, T[] value) {
            this.name = name;
            this.optionType = optionType;
            this.value = value;
        }
 
        FieldOption(String name, OptionType optionType, T[] value, SupportFunction function) {
            this.name = name;
            this.optionType = optionType;
            this.value = value;
            this.function = function;
        }
 
        public String getName() {
            return name;
        }
 
        public void setName(String name) {
            this.name = name;
        }
 
        OptionType getOptionType() {
            return optionType;
        }
 
        public void setOptionType(OptionType optionType) {
            this.optionType = optionType;
        }
 
        public T[] getValue() {
            return value;
        }
 
        public void setValue(T[] value) {
            this.value = value;
        }
 
        public SupportFunction getFunction() {
            return function;
        }
 
        public void setFunction(SupportFunction function) {
            this.function = function;
        }
 
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FieldOption option = (FieldOption) o;
            return Objects.equals(name, option.name);
        }
 
        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
 
    /**
     * 方法对象
     */
    private static class SupportFunction {
        private String function;
        private Class<?> resultType;
        private Object[] args;
        private Map<String, ArgType> argType;   //key:argName value:argType
        private OptionType optionType;
 
        SupportFunction(String function, Object[] args, Map<String, ArgType> argType, OptionType optionType, Class<?> resultType) {
            this.function = function;
            this.args = args;
            this.argType = argType;
            this.optionType = optionType;
            this.resultType = resultType;
        }
 
        public String getFunction() {
            return function;
        }
 
        public void setFunction(String function) {
            this.function = function;
        }
 
        public Class<?> getResultType() {
            return resultType;
        }
 
        public void setResultType(Class<?> resultType) {
            this.resultType = resultType;
        }
 
        public Object[] getArgs() {
            return args;
        }
 
        public void setArgs(Object[] args) {
            this.args = args;
        }
 
        public Map<String, ArgType> getArgType() {
            return argType;
        }
 
        public void setArgType(Map<String, ArgType> argType) {
            this.argType = argType;
        }
 
        public OptionType getOptionType() {
            return optionType;
        }
 
        public void setOptionType(OptionType optionType) {
            this.optionType = optionType;
        }
    }
 
    //参数类型，是读取字段，还是字符串参数
    enum ArgType {
        ROOT,
        STRING,
        FUNCTION
    }
}