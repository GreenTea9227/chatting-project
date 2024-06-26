package com.kor.syh.testsupport;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class ClearExtension implements AfterEachCallback {

	@Override
	public void afterEach(ExtensionContext context) {
		ClearDatabase databaseCleaner = getDataCleaner(context);
		databaseCleaner.clear();
	}

	private ClearDatabase getDataCleaner(ExtensionContext extensionContext) {
		return SpringExtension.getApplicationContext(extensionContext)
							  .getBean(ClearDatabase.class);
	}
}
