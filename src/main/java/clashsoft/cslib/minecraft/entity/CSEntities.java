package clashsoft.cslib.minecraft.entity;

import java.util.Map;

import clashsoft.cslib.minecraft.util.Constants;
import clashsoft.cslib.reflect.CSReflection;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

public class CSEntities
{
	private static Map<String, Class>	STRING_CLASS_MAPPING	= EntityList.stringToClassMapping;
	private static Map<Class, String>	CLASS_STRING_MAPPING	= EntityList.classToStringMapping;
	private static Map<Integer, Class>	ID_CLASS_MAPPING		= EntityList.IDtoClassMapping;
	private static Map<Class, Integer>	CLASS_ID_MAPPING		= CSReflection.getValue(Constants.FIELD_EntityList_classToIDMapping, (Object) null);
	private static Map<String, Integer>	STRING_ID_MAPPING		= CSReflection.getValue(Constants.FIELD_EntityList_stringToIDMapping, (Object) null);
	
	private static void register_do(String name, Integer id, Class clazz)
	{
		STRING_CLASS_MAPPING.put(name, clazz);
		CLASS_STRING_MAPPING.put(clazz, name);
		ID_CLASS_MAPPING.put(id, clazz);
		CLASS_ID_MAPPING.put(clazz, id);
		STRING_ID_MAPPING.put(name, id);
	}
	
	/**
	 * Registers an entity of type {@code clazz} with the given {@link String}
	 * {@code name} and the given {@code id}.
	 * 
	 * @param name
	 *            the name
	 * @param id
	 *            the id
	 * @param clazz
	 *            the entity class
	 */
	public static void register(String name, int id, Class<? extends Entity> clazz)
	{
		register_do(name, id, clazz);
	}
	
	/**
	 * Registers an entity of type {@code clazz} with the given {@link String}
	 * {@code name} and the given {@code id} and sets it's spawn egg colors to
	 * {@code eggColor1} and {@code eggColor2}.
	 * 
	 * @param name
	 *            the name
	 * @param id
	 *            the id
	 * @param eggColor1
	 *            the first egg color
	 * @param eggColor2
	 *            the second egg color
	 * @param clazz
	 *            the entity class
	 */
	public static void register(String name, int id, Class<? extends Entity> clazz, int eggColor1, int eggColor2)
	{
		Integer ID = id;
		register_do(name, ID, clazz);
		EntityList.entityEggs.put(ID, new EntityList.EntityEggInfo(id, eggColor1, eggColor2));
	}
	
	/**
	 * @deprecated Use {@link #replace(Class, Class)} instead.
	 */
	public static void replace(String name, int id, Class<? extends Entity> clazz)
	{
		register(name, id, clazz);
	}
	
	/**
	 * @deprecated Use {@link #replace(Class, Class)} instead.
	 */
	public static void replace(String name, int id, Class<? extends Entity> clazz, int eggColor1, int eggColor2)
	{
		register(name, id, clazz, eggColor1, eggColor2);
	}
	
	/**
	 * Replaced the given entity type {@code oldClass} with the new entity type
	 * {@code newClass}. This ensures that all entities of type {@code oldClass}
	 * will now spawn or load as an instance of {@code newClass}.
	 * 
	 * @param oldClass
	 *            the old entity class to replace
	 * @param newClass
	 *            the new entity class
	 */
	public static void replace(Class<? extends Entity> oldClass, Class<? extends Entity> newClass)
	{
		register_do(CLASS_STRING_MAPPING.get(oldClass), CLASS_ID_MAPPING.get(oldClass), newClass);
	}
}
