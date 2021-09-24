package JDBC.service;

import JDBC.model.Developers;
import JDBC.repository.CustomRepository;

import java.util.List;

public class CustomService {

    private final CustomRepository REPOSITORY;

    public CustomService() {

        this.REPOSITORY = new CustomRepository();
    }

    public int getProjectsDevsSalary(String projectName) {

        return REPOSITORY.getProjectsDevsSalary(projectName);
    }

    public List<Developers> getDevsByProject(String projectName) {

        return REPOSITORY.getDevsByProject(projectName);
    }

    public List<Developers> getDevsByLanguage(String languageName) {

        return REPOSITORY.getDevsByLanguage(languageName);
    }

    public List<Developers> getDevsByLevel(String levelName) {

        return REPOSITORY.getDevsByLevel(levelName);
    }

    public List<String> getProjects() {

        return REPOSITORY.getProjects();
    }
}