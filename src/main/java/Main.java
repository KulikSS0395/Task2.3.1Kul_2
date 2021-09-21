import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import web.config.DataConfig;
import web.model.User;
import web.service.UserService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.addUser(new User("Max", "Ivanov", "lo@mail.ru"));
        userService.addUser(new User("Max", "semenov", "lo@mail.ru"));

        List<User> userList = userService.getListUsers();

        for (User us : userList) {
            System.out.println(us);
        }
    }
}
