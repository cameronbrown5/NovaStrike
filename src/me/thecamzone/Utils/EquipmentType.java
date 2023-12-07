package me.thecamzone.Utils;

public enum EquipmentType {

    PRIMARY("Primary Weapon"),
    SECONDARY("Secondary Weapon"),
    GRENADE("Grenade");

    private final String friendlyName;

    EquipmentType(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
