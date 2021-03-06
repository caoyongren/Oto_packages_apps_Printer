package com.oto_packages_apps_printer.task;

import java.util.List;

/**
 * Created by bboxh on 2016/5/16.
 */
public class DeletePrinterTask<Progress> extends CommandTask<String, Progress, Boolean> {

    @Override
    protected String[] setCmd(String[] name) {
        String printerName = name[0];
        return new String[]{"sh","proot.sh","lpadmin","-x",printerName};
    }

    @Override
    protected Boolean handleCommand(List<String> stdOut, List<String> stdErr) {

        for(String line: stdErr) {
            if( line.startsWith("WARNING") ) {
                continue;
            } else if (line.contains("Bad file descriptor")) {
                if(startCups()) {
                    runCommandAgain();
                    return false;
                } else {
                    ERROR = "Cups start failed.";
                    return false;
                }
            }
        }

        boolean flag = true;
        for(String line:stdErr) {
            if (line.contains("The printer or class does not exist.")) {
                ERROR = "The printer or class does not exist.";
                flag = false;
            }
        }
        return flag;
    }

    @Override
    protected String bindTAG() {
        return "DeletePrinterTask";
    }
}
