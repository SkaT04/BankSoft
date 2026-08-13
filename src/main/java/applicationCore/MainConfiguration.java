package applicationCore;

import applicationCore.controller.OperationsConsoleListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
public class MainConfiguration {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("applicationCore");

        OperationsConsoleListener consoleListener = context.getBean(OperationsConsoleListener.class);
        consoleListener.operationsConsoleListener();
        context.close();
    }
}
