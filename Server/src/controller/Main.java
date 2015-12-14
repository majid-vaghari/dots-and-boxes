package controller;

import net.server.ServerListener;

/**
 * <p> Main method will be declared here. </p> <p> Created by Majid Vaghari on 12/9/2015. </p>
 *
 * @author Majid Vaghari
 * @version 1.3.0
 * @since version 1.3.0
 */
public class Main {
    public static void main(String[] args) {
        MainController.submitTask(new ServerListener());
    }
}
