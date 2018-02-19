package kr.mubeat.cms.hibernate;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by moonkyu.lee on 2017. 7. 18..
 */
public class CustomMySQLDialect extends MySQL5Dialect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public CustomMySQLDialect() {
        super();
        this.registerFunction(
                "GROUP_CONCAT",
                new StandardSQLFunction(
                        "GROUP_CONCAT",
                        StandardBasicTypes.STRING
                )
        );
    }
}
