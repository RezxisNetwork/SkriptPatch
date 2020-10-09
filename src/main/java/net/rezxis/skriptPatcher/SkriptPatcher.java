package net.rezxis.skriptPatcher;

import java.io.File;

import org.zeroturnaround.zip.ZipUtil;
import org.zeroturnaround.zip.commons.FileUtils;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

public class SkriptPatcher {

	public static void main(String[] args) {
		File target = new File(args[0]);
		File dest = new File(target.getParentFile(),target.getName().replace(".jar", "")+"_patched.jar");
		File cache = new File("cache");
		cache.mkdirs();
		ZipUtil.unpack(target, cache);
		try {
			ClassPool cp = ClassPool.getDefault();
			cp.appendClassPath(target.getAbsolutePath());
			CtClass class_skript = cp.get("ch.njol.skript.Skript");
			
			CtField field_blacklisted = CtField.make("public static java.util.ArrayList blacklisted = new java.util.ArrayList();", class_skript);
			class_skript.addField(field_blacklisted);
			
			String body_inject = "{"
					+ "for (int i = 0; i < blacklisted.size(); i++) {"
					+ "if ($1.getName().equalsIgnoreCase((java.lang.String)blacklisted.get(i))) {"
					+ "return;"
					+ "}"
					+ "}"
					+ "}";
			String body_inject2 = "{"
					+ "for (int i = 0; i < blacklisted.size(); i++) {"
					+ "if ($2.getName().equalsIgnoreCase((java.lang.String)blacklisted.get(i))) {"
					+ "return;"
					+ "}"
					+ "}"
					+ "}";
			String body_addBlackListed = "{"
					+ "blacklisted.add($1);"
					+ "}";
			String body_removeBlackListed = "{"
					+ "blacklisted.remove($1);"
					+ "}";
			
			String[] methods_target = new String[] {"registerExpression","registerEffect","registerCondition"};
			
			for (String name_method : methods_target) {
				CtMethod method = class_skript.getDeclaredMethod(name_method);
				method.insertBefore(body_inject);
			}
			
			CtMethod method_registerEvent = class_skript.getDeclaredMethod("registerEvent");
			method_registerEvent.insertBefore(body_inject2);
			
			CtMethod method_addBlacklisted = CtMethod.make("public static void addBlacklisted(java.lang.String arg) "+body_addBlackListed, class_skript);
			class_skript.addMethod(method_addBlacklisted);
			CtMethod method_removeBlacklisted = CtMethod.make("public static void removeBlacklisted(java.lang.String arg) "+body_removeBlackListed, class_skript);
			class_skript.addMethod(method_removeBlacklisted);
			
			class_skript.writeFile(cache.getAbsolutePath());
			ZipUtil.pack(cache, dest);
			FileUtils.deleteDirectory(cache);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
