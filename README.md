# SkriptPatch

##注意  
SkriptがonEnable()を実行する前にblacklistに登録しないといけないため、blacklistを登録するpluginはloadbeforeでSkriptより優先順位を上げることが必要です。

##説明
Skriptに使用できないCondition,Effect,Expressionを追加可能にします。

##使用方法  
java -jar SkriptPatcher-0.0.1-SNAPSHOT-jar-with-dependencies.jar <Skript.jar>

##Methods  
public static void ch.njol.skript.Skript.addBlacklisted(String);  
blacklistに追加をします。  
public static void ch.njol.skript.Skript.removeBlacklisted(string);  
blacklistに削除をします。  

##Fields  
public static java.util.ArrayList ch.njol.skript.Skript.blacklisted;
