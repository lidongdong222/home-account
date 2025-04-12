
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

/**
 * MySQL 代码生成
 *
 * @author lanjerry
 * @since 3.5.3
 */
public class MySQLGeneratorTest extends BaseGeneratorTest {

    public static String[] TABLES = new String[]{
            "wx_bill"
    };

    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://localhost:3306/home?serverTimezone=Asia/Shanghai", "root", "abc888")
            .build();

    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.strategy(strategyConfig().build());
        generator.packageInfo(packageConfig());
        generator.global(globalConfig().build());
        generator.execute();
    }

}