package my.sdl.smarthome.remoteapp.model

import my.sdl.smarthome.remoteapp.constants.DeviceConstants


/*
*{"dt": "ac","prot":"samsung","s":"on","m":"cool", "f":"2","t":"18","tr":"0","sl":"0","sw":"2"}
s = status(on/off)
prot = brand of ac (was selected when device is added).
m = mode (auto,cool,dry,fan,heat)
f = fan (1,2,3)
t = tempurature (17 - 26)
tr = turbo (0 or 1)
sl = sleep mode (0 or 1)
//sw = swing (    "sw":"1" when low.
//         "sw":"2" when mid, and also for default.
//         "sw":"3" when high.
//         "sw":"4" when swinging.)
* sw = turbo (0 or 1)
* */
class AcRemote {
    var dt: String = "ac"

    var prot: String = ""
    var s: String = "off"

    var m: String = DeviceConstants.AcModes.AUTO

    var f: Int = 1

    var t: Int = 17

    var sl: Int = 0

    var tr: Int = 0

    var sw: Int = 0

    fun setPowerStatus() {
        s = if (isOn) "off"
        else "on"
    }

    fun operateFanSpeed() {
        f = if (f == 3) 1
        else f + 1
    }

    fun changeMode() {
        m = when (m) {
            DeviceConstants.AcModes.AUTO -> DeviceConstants.AcModes.COOL
            DeviceConstants.AcModes.COOL -> DeviceConstants.AcModes.DRY
            DeviceConstants.AcModes.DRY -> DeviceConstants.AcModes.FAN
            DeviceConstants.AcModes.FAN -> DeviceConstants.AcModes.HEAT
            else -> DeviceConstants.AcModes.AUTO
        }
    }

    val isOn: Boolean
        //    public void changeSwing() {
        get() = s.equals("on", ignoreCase = true)

    val temperature: Float
        get() = ((t - 17).toFloat()) / 9

    fun changeSleepMode() {
        sl = if ((sl == 1)) 0 else 1
    }

    fun changeSwingMode() {
        sw = if ((sw == 1)) 0 else 1
    }

    fun changeTurboMode() {
        tr = if ((tr == 1)) 0 else 1
    }
}