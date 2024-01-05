package kiosk.controller.command;

import java.util.List;
import kiosk.data.ApplicationStatus;
import kiosk.domain.Store;
import kiosk.domain.menu.Category;
import kiosk.domain.menu.Menu;

public class MenuCommand implements Command {

    private final int input;
    private final List<Menu> menus;

    private MenuCommand(int input, List<Menu> menus) {
        this.input = input;
        this.menus = menus;
    }

    public static MenuCommand of(int input, Category category) {
        List<Menu> menus = Store.getMenus(category);
        validate(input, menus);
        return new MenuCommand(input, menus);
    }

    private static void validate(int input, List<Menu> menus) {
        if (input < 1 || input > menus.size()) {
            throw new IllegalArgumentException(String.format("[ERROR] 없는 메뉴 입니다. input:" + input));
        }
    }

    public Menu getChosenMenu() {
        return menus.get(input);
    }

    public ApplicationStatus status() {
        Menu chosen = getChosenMenu();
        if(chosen.hasOption()) {
            return ApplicationStatus.OPTION;
        }
        return ApplicationStatus.PURCHASE;
    }

    @Override
    public int info() {
        return input;
    }
}
