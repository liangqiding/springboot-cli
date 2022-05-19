package com.springboot.cli.config;

import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;

/**
 * 不创建shiro内部的session
 * @author ding
 */
public class ShiroDefaultSubjectFactory extends DefaultSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        // 不创建shiro内部的session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}

