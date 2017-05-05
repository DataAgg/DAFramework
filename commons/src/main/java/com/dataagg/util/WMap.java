package com.dataagg.util;

import org.nutz.lang.util.NutMap;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by watano on 2017/3/4.
 */
public class WMap extends NutMap {
	private static final long serialVersionUID = -852470603991540244L;

	public WMap() {
		super();
	}

	public WMap(Map<String, Object> data) {
		super(data);
	}

	public String string(String key) {
		return this.getAs(key, String.class);
	}

	public String[] strings(String key) {
		List<String> strings = new ArrayList<>();
		for (Object v : this.getArray(key, Object.class)) {
			strings.add(v.toString());
		}
		return strings.toArray(new String[]{});
	}

	public String[] stringsNoDuplicate(String key) {
		Set<String> strings = new LinkedHashSet<>();
		for (Object v : this.getArray(key, Object.class)) {
			strings.add(v.toString());
		}
		return strings.toArray(new String[]{});
	}

	public WMap map(String key) {
		return this.getAs(key, WMap.class);
	}

	public List<?> list(String key) {
		return this.getAs(key, List.class);
	}

	public void addAll(WMap data2) {
		for (String key : data2.keySet()) {
			addX(key, data2.get(key));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addX(String key, Object value) {
		if (value == null) {
			return;
		}
		Object currValue = get(key);
		if (currValue == null) {
			put(key, value);
		} else if (currValue instanceof Map && value instanceof Map) {
			WMap obj = map(key);
			Map vObj = (Map) value;
			for (Object k : vObj.keySet()) {
				obj.addX(k.toString(), vObj.get(k));
			}
			put(key, obj);
		} else if (currValue instanceof List) {
			List<Object> lst = getList(key, Object.class);
			if (value instanceof List) {
				lst.addAll((List) value);
			} else if (value instanceof Object[]) {
				for(Object o:(Object[])value){
					lst.add(o);
				}
			} else {
				lst.add(value);
			}
			put(key, lst);
		} else if (value instanceof List) {
			((List<Object>) value).add(currValue);
			put(key, value);
		} else if (value instanceof Object[]) {
			List<Object> all = new ArrayList<>();
			all.add(currValue);
			for(Object o:(Object[])value){
				all.add(o);
			}
			put(key, all);
		} else {
			put(key, value);
		}
	}

	public static <T> Set<T> convert(List<T> lst) {
		Set<T> set = new LinkedHashSet<>();
		if (lst != null) {
			for (T o : lst) {
				if (!set.contains(o)) {
					set.add(o);
				}
			}
		}
		return set;
	}

	@SafeVarargs
	public static <T> Set<T> convert(T... lst) {
		Set<T> set = new LinkedHashSet<>();
		if (lst != null) {
			for (T o : lst) {
				if (!set.contains(o)) {
					set.add(o);
				}
			}
		}
		return set;
	}


}
