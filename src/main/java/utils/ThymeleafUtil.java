package utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.StringWriter;
import java.io.Writer;

public class ThymeleafUtil {

    private static final TemplateEngine engine;

    static {
        engine = new TemplateEngine();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();

        // 设置模板解析器的属性
        resolver.setPrefix("/templates/");        // 设置模板目录
        resolver.setSuffix(".html");              // 设置模板文件后缀
        resolver.setTemplateMode("HTML");         // 设置模板模式
        resolver.setCharacterEncoding("UTF-8");   // 设置字符编码
        resolver.setCacheable(true);              // 启用缓存

        engine.setTemplateResolver(resolver);
    }

    /**
     * 处理 Thymeleaf 模板并写入结果
     *
     * @param template 模板名称（不包括路径和后缀）
     * @param context  Thymeleaf 上下文，包含模板变量
     * @param writer   用于写入模板处理结果的 Writer
     */
    public static void process(String template, Context context, Writer writer) {
        engine.process(template, context, writer);
    }

    /**
     * 处理 Thymeleaf 模板并返回渲染后的 HTML 字符串
     *
     * @param template 模板名称
     * @param context  Thymeleaf 上下文
     * @return 渲染后的 HTML 字符串
     */
    public static String processToString(String template, Context context) {
        StringWriter writer = new StringWriter();
        engine.process(template, context, writer);
        return writer.toString();
    }

    /**
     * 获取单例的 TemplateEngine 实例
     *
     * @return TemplateEngine 实例
     */
    public static TemplateEngine getTemplateEngine() {
        return engine;
    }
}
