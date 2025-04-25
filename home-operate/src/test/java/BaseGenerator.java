import com.baomidou.mybatisplus.generator.config.*;

/**
 * 基础测试类
 *
 * @author lanjerry
 * @since 3.5.3
 */
public class BaseGenerator {



    /**
     * 策略配置
     */
    protected static StrategyConfig.Builder strategyConfig() {
        StrategyConfig.Builder builder=  new StrategyConfig.Builder().addInclude(MySQLGeneratorTest.TABLES);
        builder.entityBuilder().enableLombok()
                .enableTableFieldAnnotation()
                .disableSerialVersionUID()
                .enableFileOverride();
        return builder;
    }

    /**
     * 全局配置
     */
    protected static GlobalConfig.Builder globalConfig() {
        return new GlobalConfig.Builder()
                .outputDir("G:\\home-account\\home-operate\\src\\main\\java")
                .author("ldd");
    }

    /**
     * 包配置
     */
    protected static PackageConfig packageConfig() {
        return new PackageConfig.Builder()
                .parent("com.ldd.home.operate")
                .entity("entity")
                .mapper("mapper")
                .xml("mapper").build();
    }

    /**
     * 模板配置
     */
    protected static TemplateConfig.Builder templateConfig() {
        return new TemplateConfig.Builder();
    }

    /**
     * 注入配置
     */
    protected static InjectionConfig.Builder injectionConfig() {
        // 测试自定义输出文件之前注入操作，该操作再执行生成代码前 debug 查看
        return new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
        });
    }
}