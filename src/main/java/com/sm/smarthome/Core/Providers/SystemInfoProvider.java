package com.sm.smarthome.Core.Providers;

import com.sm.smarthome.Core.Utils.MemoryHelper;
import com.sun.management.OperatingSystemMXBean;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SystemInfoProvider {

    Thread systemInfoThread = new Thread(() -> SystemInfoThreadTask());
    public SimpleDoubleProperty CpuLoad = new SimpleDoubleProperty(0);
    public SimpleObjectProperty<MemoryHelper.MemoryValue> RamFree = new SimpleObjectProperty<MemoryHelper.MemoryValue>();
    public SimpleObjectProperty<MemoryHelper.MemoryValue> RamUsage = new SimpleObjectProperty<MemoryHelper.MemoryValue>();
    public SimpleObjectProperty<MemoryHelper.MemoryValue> RamTotal = new SimpleObjectProperty<MemoryHelper.MemoryValue>();
    public SimpleStringProperty SystemDate = new SimpleStringProperty();

    public SystemInfoProvider(){
        systemInfoThread.setDaemon(true);
        systemInfoThread.start();
    }

    private Task SystemInfoThreadTask(){
        while (systemInfoThread.isAlive()){
            SystemDate.set(new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime()));

            OperatingSystemMXBean osBean = java.lang.management.ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
            CpuLoad.setValue(osBean.getCpuLoad() * 100);

            long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
            RamTotal.setValue(new MemoryHelper(memorySize).GetValue(MemoryHelper.SizeUnit.Gb));
            long freeMemorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getFreePhysicalMemorySize();
            RamFree.setValue(new MemoryHelper(freeMemorySize).GetValue(MemoryHelper.SizeUnit.Gb));
            RamUsage.setValue(new MemoryHelper(memorySize - freeMemorySize).GetValue(MemoryHelper.SizeUnit.Gb));


            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}
