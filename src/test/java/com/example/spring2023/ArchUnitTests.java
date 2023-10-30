package com.example.spring2023;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public final class ArchUnitTests {

    private static final String PACKAGE_NAME = "com.example.spring2023";
    private static final JavaClasses CLASSES = new ClassFileImporter().importPackages("com.example.spring2023");
    @Test
    @DisplayName("Соблюдены требования слоёной архитектур")
    public void testLayeredArchitecture() {
        Architectures
                .layeredArchitecture()
                .consideringAllDependencies()
                .layer("API").definedBy(PACKAGE_NAME + ".API..")
                .layer("Application").definedBy(PACKAGE_NAME + ".Application..")
                .layer("DAL").definedBy(PACKAGE_NAME + ".DAL..")
                .layer("config").definedBy(PACKAGE_NAME + ".config..")
                .whereLayer("API").mayOnlyBeAccessedByLayers("config")
                .whereLayer("Application").mayOnlyBeAccessedByLayers("API", "config")
                .whereLayer("DAL").mayOnlyBeAccessedByLayers("API","Application", "config")
                .check(CLASSES);
    }

    @Test
    @DisplayName("Нельзя использовать DI для полей")
    public void noDIInClassFields() {
        ArchRuleDefinition
                .noFields()
                .should()
                .beAnnotatedWith(Autowired.class)
                .check(CLASSES);
    }
}
