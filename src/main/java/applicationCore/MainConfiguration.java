package applicationCore;

import applicationCore.controller.OperationsConsoleListener;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
@ComponentScan("applicationCore")
public class MainConfiguration {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MainConfiguration.class);

        OperationsConsoleListener consoleListener = context.getBean(OperationsConsoleListener.class);
        consoleListener.operationsConsoleListener();
        context.close();
    }
}
