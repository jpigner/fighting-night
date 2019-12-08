package com.gdx.anight.utils;

import java.util.Random;

import com.gdx.anight.enums.UserDataType;

public class RandomUtils {
	
	public static UserDataType randomUserData() {
		RandomEnum<UserDataType> randomEnum = new RandomEnum<UserDataType>(UserDataType.class);
		return randomEnum.random();
	}
	
	@SuppressWarnings("rawtypes")
	private static class RandomEnum<E extends Enum> {
		
		private static final Random RANDOMWERT = new Random();
		private final E[] values;
		
		public RandomEnum(Class<E> werte) {
			values = werte.getEnumConstants();
		}
		
		public E random() {
			return values[RANDOMWERT.nextInt(values.length)];
		}
	}

}
