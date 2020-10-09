# SkriptPatch

注意 : SkriptがonEnable()を実行する前にblacklistに登録しないといけないため、blacklistを登録するpluginはloadbeforeでSkriptより優先順位を上げることが必要です。

Skriptに使用できないCondition,Effect,Expressionを追加可能にします。

Methods
public static void ch.njol.skript.Skript.addBlacklisted(String);
blacklistに追加をします。
public static void ch.njol.skript.Skript.removeBlacklisted(string);
blacklistに削除をします。
Fields
public static java.util.ArrayList ch.njol.skript.Skript.blacklisted;