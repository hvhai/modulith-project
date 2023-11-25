package com.codehunter.modulithproject;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModulithProjectApplicationTests {

	ApplicationModules modules = ApplicationModules.of(ModulithProjectApplication.class);

	@Test
	void shouldBeCompliant() {
		modules.verify();
	}

	@Test
	void writeDocumentationSnippets() {
		new Documenter(modules)
				.writeModuleCanvases()
				.writeModulesAsPlantUml()
				.writeIndividualModulesAsPlantUml();
	}

}
