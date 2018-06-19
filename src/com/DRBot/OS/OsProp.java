package com.DRBot.OS;

public class OsProp {
    private String Os,OsName,OsVer,OsArch,OsEnvComNameUsDomain,
            OsSysRoot,OsLogName,OsUser,OsShell;
    
    public String getOs(){
        Os = System.getenv("os").toLowerCase();
        return Os;
    }
    public String getOsName(){
        OsName = System.getProperty("os.name").toLowerCase();
        return OsName;
    }
    public String getOSVer(){
        OsVer = System.getProperty("os.version").toLowerCase();
        return OsVer;
    }
    public String getOsArch(){
        OsArch = System.getProperty("os.arch").toLowerCase();
        return OsArch;
    }
    public String getOsEnvComNameUserDomain(){
        String OsEnvComName = System.getenv("COMPUTERNAME");
        String OsEnvUserDomain = System.getenv("USERDOMAIN");
        OsEnvComNameUsDomain = OsEnvComName + ", " + OsEnvUserDomain;
        return OsEnvComNameUsDomain;
    }
    public String getOsSystemRoot(){
        OsSysRoot = System.getenv("SystemRoot");
        return OsSysRoot;
    }
    public String getOsLinux(){
        OsLogName = System.getenv("LOGNAME");
        OsUser = System.getenv("USER");
        OsShell = System.getenv("SHELL");
        return OsLogName + ", "+OsUser+", "+OsShell;
    }
    public String getOsStat(){
        if(getOsName().startsWith("win")){
        String result = "OS Family = " + getOs()+
                                 ", OS Name = " + getOsName()+
                                 ", OS Version = "+ getOSVer()+
                                ",  OS Architecture = " + getOsArch()+
                                ",  OS Computer Name, User Domain = " + getOsEnvComNameUserDomain()+".";
        return result;
        }
        else{
            String result ="\nOS Name = " + getOsName()+
                                 "\nOS Version = "+ getOSVer()+
                                 "\nOS Architecture = " + getOsArch()+
                                "\nOS LogName,OsUser,OsShell = "+ getOsLinux()+".";
            return result;
        }
    }
    public String shortOs(){
        if(getOsName().equalsIgnoreCase("windows 7")){
            return "win7";
        }
        else
            if(getOsName().equalsIgnoreCase("windows xp")){
                return "winXp";
            }
        else
            if(getOsName().equalsIgnoreCase("windows 8")){
                return "win8";
            }
        else
                if(getOsName().equalsIgnoreCase("windows 8.1")){
                    return "win8.1";
                }
                else{
                    return null;
                }
    }
}
