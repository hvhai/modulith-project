package com.codehunter.modulithproject;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;


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

    @Test
    void writeSummary() throws IOException {

//		String docsPathName = "target/spring-modulith-docs";
        String docsPathName = "build/spring-modulith-docs";
        String summaryFileName = "all-docs.adoc";

        Path docsPath = Paths.get(docsPathName);
        Map<String, StringBuilder> fileMap = new TreeMap<>();

        try (Stream<Path> files = Files.list(docsPath)) {
            files
                    .filter(Files::isRegularFile)
                    .filter(path -> !path.getFileName().toString().equals(summaryFileName))
                    .forEach(filePath -> {
                        String fileName = filePath.getFileName().toString();

                        String fileHandle = fileName.substring(0, fileName.lastIndexOf('.'));
                        String relativePath = docsPath.relativize(filePath).toString().replace("\\", "/");

                        // Determine the include directive based on file extension
                        String includeDirective = fileName.endsWith(".puml") ? "plantuml::" : "include::";

                        fileMap.computeIfAbsent(fileHandle, k -> new StringBuilder())
                                .append(includeDirective).append(relativePath).append("[]\n");
                    });
        } catch (IOException e) {
            System.err.println("Skip writing summary: ");
            return;
        }

        if (fileMap.isEmpty()) {
            System.err.println("Skip writing summary: Nothing to summarize in");
            return;
        }

        // Create summary file
        File indexFile = new File(docsPathName + "/" + summaryFileName);
        try (FileWriter writer = new FileWriter(indexFile)) {
            fileMap.forEach((handle, references) -> {
                try {
                    writer.write("== " + handle + "\n");
                    writer.write(references.toString());
                    writer.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }

}
