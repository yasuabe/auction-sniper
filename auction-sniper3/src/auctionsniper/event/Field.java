package auctionsniper.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Field {

	private final String key;
	private final String value;

	public Field(String key, String value) {
		this.key   = key;
		this.value = value;
	}
	static List<Field> fieldsIn(String messageBody) {
		return Field.fromStrings(messageBody.split(";"));
	}
	static List<Field> fromStrings(String[] strings) {
		List<Field> results = new ArrayList<>();
		for (String s: strings) results.add(fromString(s));
		return results;
	}
	private static Field fromString(String field) {
		String[] pair = field.split(":");
		
		String key   = pair[0].trim();
		String value = pair[1].trim();

		return new Field(key, value);
	}
	public void register(Map<String, String> fields) {
		fields.put(key, value);
	}
}