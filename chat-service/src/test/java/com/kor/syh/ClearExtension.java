package com.kor.syh;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kor.syh.chat.ClearDatabase;

public class ClearExtension implements AfterEachCallback {

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		ClearDatabase databaseCleaner = getDataCleaner(context);
		databaseCleaner.clear();
	}

	private ClearDatabase getDataCleaner(ExtensionContext extensionContext) {
		return SpringExtension.getApplicationContext(extensionContext)
							  .getBean(ClearDatabase.class);
	}
}
