package com.oto_packages_apps_printer.task;

import com.oto_packages_apps_printer.APP;
import com.oto_packages_apps_printer.R;
import com.oto_packages_apps_printer.util.FileUtils;

import java.util.List;

/**
 * Created by bboxh on 2016/5/15.
 */
public class InitTask<Progress> extends CommandTask<Object, Progress, Boolean> {
    @Override
    protected String[] setCmd(Object[] params) {
        return new String[]{"tar", "vxzf", "/mnt/sdcard"+ APP.COMPONENT_PATH + ".tar.gz"};
    }

    @Override
    protected Boolean handleCommand(List<String> stdOut, List<String> stdErr) {

        final Boolean[] flag = {false};

        for(String line: stdErr) {
            if( line.startsWith("WARNING") ) {
                continue;
            } else if (line.contains("No such file")) {
                ERROR = APP.getApplicatioContext().getResources().getString(R.string.please_confirm_component);
            }
        }

        if(stdOut.size() > 4 ) {
            flag[0] = true;
        } else {
            return false;
        }

        CommandTask<Void, Void, Boolean> task = new CommandTask<Void, Void, Boolean>() {

            @Override
            protected String bindTAG() {
                return "InitTaskInner";
            }

            @Override
            protected String[] setCmd(Void... params) {
                return new String[]{"sh", "proot.sh", "sh", "chang_mode.sh", "/usr/share/doc"};
            }

            @Override
            protected Boolean handleCommand(List<String> stdOut, List<String> stdErr) {
                Boolean flag = false;
                if (stdOut.size() == 0) {
                    flag = true;
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                flag[0] = aBoolean;
                synchronized(this) {
                    this.notify();
                }
            }
        };

        task.start();

        try {
            synchronized(task) {
                task.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return flag[0];
    }

    @Override
    protected String getWorkPath() {
        return FileUtils.getFilePath();
    }

    @Override
    protected String bindTAG() {
        return "InitTask";
    }
}
