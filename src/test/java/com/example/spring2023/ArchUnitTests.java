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
                .layer("Services").definedBy(PACKAGE_NAME + ".Services..")
                .layer("DAL").definedBy(PACKAGE_NAME + ".DAL..")
                .whereLayer("API").mayNotBeAccessedByAnyLayer()
                .whereLayer("Services").mayOnlyBeAccessedByLayers("API")
                .whereLayer("DAL").mayOnlyBeAccessedByLayers("Services")
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
