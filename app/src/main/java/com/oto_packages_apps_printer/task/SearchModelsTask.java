package com.oto_packages_apps_printer.task;

import com.oto_packages_apps_printer.model.ModelsItem;
import com.oto_packages_apps_printer.model.PPDItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bboxh on 2016/5/16.
 */
public class SearchModelsTask<Params, Progress> extends CommandTask<Params, Progress, ModelsItem> {
    @Override
    protected String[] setCmd(Params[] params) {
        return new String[]{"sh", "proot.sh", "lpinfo", "-m"};
    }

    @Override
    protected ModelsItem handleCommand(List<String> stdOut, List<String> stdErr) {
        for(String line: stdErr){
            if(line.startsWith("WARNING")) {
                continue;
            } else if (line.contains("Bad file descriptor")) {
                if(startCups()) {
                    runCommandAgain();
                    return null;
                } else {
                    ERROR = "Cups start failed.";
                    return null;
                }
            }
        }

        List<String> brand = new ArrayList<>();
        Map<String, List<PPDItem>> models = new HashMap<>();
        for(String line:stdOut) {
            String[] splitLine = line.split(" ");
            String currentPPD = splitLine[0];
            String currentBrand = splitLine[1];
            String currentDriver = "";
            for(int i = 2; i < splitLine.length;i++) {
                currentDriver = currentDriver+ " " + splitLine[i];
            }

            List<PPDItem> location;

            if(brand.contains(currentBrand)) {
                location = models.get(currentBrand);
            } else {
                brand.add(currentBrand);
                location = new ArrayList<>();
                models.put(currentBrand,location);
            }

            location.add(new PPDItem(currentPPD,currentBrand,currentDriver));
        }

        return new ModelsItem(brand,models);
    }

    @Override
    protected String bindTAG() {
        return "SearchModelsTask";
    }
}
