package com.springboot.cli.utils;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;


/**
 * 配置参考 ： https://baomidou.com/guide/generator.html
 *
 * description: TODO
 */
public class GenerationUtils {

    /**
     * 数据库地址
     */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/example?useUnicode=true&useSSL=false&characterEncoding=utf8&useTimezone=true&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true&useServerPrepStmts=true&allowMultiQueries=true";
    /**
     * db 账号
     */
    private static final String USERNAME = "root";
    /**
     * db 密码
     */
    private static final String PASSWORD = "123456";
    /**
     * db 驱动
     */
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    /**
     * 存放路径，包路径
     */
    private static final String PACKAGE = "com.springboot.cli";
    /**
     * 需要生成的数据库表名，数组，可多个
     */
    private static final String[] TABLE_NAME = {"area"};

    public static void main(String[] args) {

        boolean fileOverride = false;
        GlobalConfig config = new GlobalConfig();
        String path = System.getProperty("user.dir");
        config.setActiveRecord(true)
                .setAuthor("ding")
                .setOutputDir(path + "\\src\\main\\java\\")
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setFileOverride(fileOverride);
        //****************************** resource ***************************************
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(DB_URL)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setDriverName(DRIVER_NAME)
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        System.out.println("转换类型：" + fieldType);
                        //tinyint转换成Boolean
                        if (fieldType.toLowerCase().contains("tinyint")) {
                            return DbColumnType.BOOLEAN;
                        }
                        if (fieldType.toLowerCase().contains("datetime")) {
                            return DbColumnType.DATE;
                        }
                        return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
                    }
                });

        //****************************** Policy configuration ******************************************************
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("gmt_modified", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("modifier_id", FieldFill.INSERT_UPDATE));
        tableFillList.add(new TableFill("creator_id", FieldFill.INSERT));
        tableFillList.add(new TableFill("gmt_create", FieldFill.INSERT));
        tableFillList.add(new TableFill("available_flag", FieldFill.INSERT));
        tableFillList.add(new TableFill("deleted_flag", FieldFill.INSERT));
        tableFillList.add(new TableFill("sync_flag", FieldFill.INSERT));
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setTableFillList(tableFillList)
                .setInclude(TABLE_NAME);
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(PACKAGE)
                                .setController("controller")
                                .setEntity("domain")
                                .setMapper("dao")
                                .setXml("dao")
                )
                .setTemplate(
                        new TemplateConfig()
                                .setServiceImpl("templates/serviceImpl.java")
                )
                .execute();
    }
}

