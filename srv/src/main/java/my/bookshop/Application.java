package my.bookshop;

import com.sap.cds.services.runtime.CdsRuntime;
import com.sap.hcp.cf.logging.servlet.filter.RequestLoggingFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> loggingFilter() {
        FilterRegistrationBean<RequestLoggingFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new RequestLoggingFilter());
        filterRegistrationBean.setName("request-logging");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistrationBean;
    }

    @Bean
    public ApplicationRunner runner(CdsRuntime runtime) {
        return args -> {
            var model = runtime.getCdsModel();
            var optional = model.findEntity("my.bookshop.Authors");
            if (optional.isEmpty()) {
                System.out.println("Entity not found");
                return;
            }
            var entity = optional.get();
            entity.annotations().forEach(a -> {
                System.out.println(a.getName());
                a.getKey();
            });
        };
    }

}
