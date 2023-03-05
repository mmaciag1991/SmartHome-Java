package com.sm.smarthome.Core.Utils;

public class MemoryHelper {
    private long size;
    public MemoryHelper(long size){
        this.size = size;
    }
    public MemoryValue GetValue(SizeUnit sizeUnit) {

        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;
        float sizeTerra = sizeGb * sizeKb;

        return switch (sizeUnit) {
            case Kb -> new MemoryValue(size, size / sizeKb, SizeUnit.Kb);
            case Mb -> new MemoryValue(size, size / sizeMb, SizeUnit.Mb);
            case Gb -> new MemoryValue(size, size / sizeGb, SizeUnit.Gb);
            case Tb -> new MemoryValue(size, size / sizeTerra, SizeUnit.Tb);
        };
    }

    public class MemoryValue{
        private long size;
        private float value;
        private SizeUnit unit;

        public MemoryValue(long size, float value, SizeUnit unit){
            this.size = size;
            this.value = value;
            this.unit = unit;
        }

        public long getSize() {
            return size;
        }

        public float getValue() {
            return value;
        }

        public SizeUnit getUnit() {
            return unit;
        }
    }
    public enum SizeUnit{
        Kb,
        Mb,
        Gb,
        Tb
    }
}
