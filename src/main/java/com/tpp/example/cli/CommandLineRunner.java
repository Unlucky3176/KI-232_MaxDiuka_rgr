//package com.tpp.example.cli;
//
//import com.tpp.example.service.CommandExecutor;
//import com.tpp.example.utils.CommandParser;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//import java.util.Scanner;
//
//@Component
//class CLI implements CommandLineRunner {
//
//    private final CommandExecutor executor;
//
//    public CLI(CommandExecutor executor) {
//        this.executor = executor;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (args != null && args.length > 0) {
//            String cmdLine = String.join(" ", args);
//            executeAndPrint(cmdLine);
//            System.exit(0);
//        } else {
//            System.out.println("Enter commands (insert/update/delete/read ...) or 'exit' to quit:");
//            Scanner sc = new Scanner(System.in);
//            while (true) {
//                System.out.print("> ");
//                if (!sc.hasNextLine()) break;
//                String line = sc.nextLine();
//                if (line == null) break;
//                line = line.trim();
//                if (line.equalsIgnoreCase("exit") || line.equalsIgnoreCase("quit")) break;
//                if (line.isEmpty()) continue;
//                executeAndPrint(line);
//            }
//            System.out.println("Bye");
//            System.exit(0);
//        }
//    }
//
//    private void executeAndPrint(String line) {
//        Optional<CommandParser.Command> ocmd = CommandParser.parse(line);
//        if (ocmd.isEmpty()) {
//            System.out.println("Malformed command. Expected: insert table(key='value', ...)");
//            return;
//        }
//        try {
//            String res = executor.execute(ocmd.get());
//            System.out.println(res);
//        } catch (Exception e) {
//            System.err.println("Error: " + e.getMessage());
//        }
//    }
//}
